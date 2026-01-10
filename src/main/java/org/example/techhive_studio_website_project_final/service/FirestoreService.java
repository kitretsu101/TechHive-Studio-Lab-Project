package org.example.techhive_studio_website_project_final.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.example.techhive_studio_website_project_final.data.ContactRepository;
import org.example.techhive_studio_website_project_final.data.ContributorRepository;
import org.example.techhive_studio_website_project_final.model.ContactInquiry;
import org.example.techhive_studio_website_project_final.model.ContributorApplication;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Service for Firebase Firestore operations.
 * Syncs local data to cloud for admin review.
 */
public class FirestoreService {
    private static FirestoreService instance;
    private String projectId;
    private final Gson gson = new Gson();

    private static final String FIRESTORE_BASE_URL = "https://firestore.googleapis.com/v1/projects/";

    private FirestoreService() {
    }

    public static FirestoreService getInstance() {
        if (instance == null) {
            instance = new FirestoreService();
        }
        return instance;
    }

    /**
     * Initialize Firestore with project ID from config.
     */
    public void initialize() {
        try {
            InputStream is = getClass().getResourceAsStream("/firebase-config.json");
            if (is != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                    JsonObject config = gson.fromJson(reader, JsonObject.class);
                    this.projectId = config.get("projectId").getAsString();
                    System.out.println("Firestore initialized for project: " + projectId);
                }
            } else {
                System.err.println("firebase-config.json not found!");
                this.projectId = "YOUR_PROJECT_ID";
            }
        } catch (Exception e) {
            System.err.println("Failed to initialize Firestore: " + e.getMessage());
        }
    }

    /**
     * Save a contact inquiry to Firestore.
     */
    public CompletableFuture<Boolean> saveContactInquiry(ContactInquiry inquiry) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String collectionUrl = FIRESTORE_BASE_URL + projectId +
                        "/databases/(default)/documents/contact_inquiries";

                JsonObject document = createContactDocument(inquiry);

                URL url = URI.create(collectionUrl).toURL();
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(document.toString().getBytes(StandardCharsets.UTF_8));
                }

                int responseCode = conn.getResponseCode();
                if (responseCode == 200 || responseCode == 201) {
                    System.out.println("Contact inquiry synced to Firestore: ID " + inquiry.getId());
                    return true;
                } else {
                    System.err.println("Failed to sync contact inquiry. Response code: " + responseCode);
                    return false;
                }
            } catch (Exception e) {
                System.err.println("Error syncing contact inquiry: " + e.getMessage());
                return false;
            }
        });
    }

    /**
     * Save a contributor application to Firestore.
     */
    public CompletableFuture<Boolean> saveContributorApplication(ContributorApplication application) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String collectionUrl = FIRESTORE_BASE_URL + projectId +
                        "/databases/(default)/documents/contributor_applications";

                JsonObject document = createApplicationDocument(application);

                URL url = URI.create(collectionUrl).toURL();
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(document.toString().getBytes(StandardCharsets.UTF_8));
                }

                int responseCode = conn.getResponseCode();
                if (responseCode == 200 || responseCode == 201) {
                    System.out.println("Contributor application synced to Firestore: ID " + application.getId());
                    return true;
                } else {
                    System.err.println("Failed to sync contributor application. Response code: " + responseCode);
                    return false;
                }
            } catch (Exception e) {
                System.err.println("Error syncing contributor application: " + e.getMessage());
                return false;
            }
        });
    }

    /**
     * Sync all pending local submissions to Firestore.
     */
    public void syncPendingSubmissions() {
        CompletableFuture.runAsync(() -> {
            try {
                // Sync contact inquiries
                List<ContactInquiry> unsyncedInquiries = ContactRepository.getInstance().findUnsynced();
                for (ContactInquiry inquiry : unsyncedInquiries) {
                    boolean synced = saveContactInquiry(inquiry).get();
                    if (synced) {
                        ContactRepository.getInstance().markAsSynced(inquiry.getId());
                    }
                }
                System.out.println("Synced " + unsyncedInquiries.size() + " contact inquiries.");

                // Sync contributor applications
                List<ContributorApplication> unsyncedApplications = ContributorRepository.getInstance().findUnsynced();
                for (ContributorApplication app : unsyncedApplications) {
                    boolean synced = saveContributorApplication(app).get();
                    if (synced) {
                        ContributorRepository.getInstance().markAsSynced(app.getId());
                    }
                }
                System.out.println("Synced " + unsyncedApplications.size() + " contributor applications.");

            } catch (Exception e) {
                System.err.println("Error during sync: " + e.getMessage());
            }
        });
    }

    // Helper methods to create Firestore document format
    private JsonObject createContactDocument(ContactInquiry inquiry) {
        JsonObject fields = new JsonObject();

        fields.add("name", createStringValue(inquiry.getName()));
        fields.add("email", createStringValue(inquiry.getEmail()));
        fields.add("projectType", createStringValue(inquiry.getProjectType()));
        fields.add("budget", createStringValue(inquiry.getBudget()));
        fields.add("details", createStringValue(inquiry.getDetails()));
        fields.add("submittedAt", createStringValue(inquiry.getSubmittedAt().toString()));
        fields.add("localId", createIntegerValue(inquiry.getId()));

        JsonObject document = new JsonObject();
        document.add("fields", fields);
        return document;
    }

    private JsonObject createApplicationDocument(ContributorApplication app) {
        JsonObject fields = new JsonObject();

        fields.add("name", createStringValue(app.getName()));
        fields.add("email", createStringValue(app.getEmail()));
        fields.add("expertise", createStringValue(app.getExpertise()));
        fields.add("portfolioUrl", createStringValue(app.getPortfolioUrl()));
        fields.add("about", createStringValue(app.getAbout()));
        fields.add("status", createStringValue(app.getStatus()));
        fields.add("submittedAt", createStringValue(app.getSubmittedAt().toString()));
        fields.add("localId", createIntegerValue(app.getId()));

        JsonObject document = new JsonObject();
        document.add("fields", fields);
        return document;
    }

    private JsonObject createStringValue(String value) {
        JsonObject obj = new JsonObject();
        obj.addProperty("stringValue", value != null ? value : "");
        return obj;
    }

    private JsonObject createIntegerValue(int value) {
        JsonObject obj = new JsonObject();
        obj.addProperty("integerValue", String.valueOf(value));
        return obj;
    }
}
