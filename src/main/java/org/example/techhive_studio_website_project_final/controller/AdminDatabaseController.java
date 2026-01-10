package org.example.techhive_studio_website_project_final.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.example.techhive_studio_website_project_final.core.SceneManager;
import org.example.techhive_studio_website_project_final.data.ContactRepository;
import org.example.techhive_studio_website_project_final.data.ContributorRepository;
import org.example.techhive_studio_website_project_final.data.CustomerRequestRepository;
import org.example.techhive_studio_website_project_final.model.ContactInquiry;
import org.example.techhive_studio_website_project_final.model.ContributorApplication;
import org.example.techhive_studio_website_project_final.model.CustomerRequest;
import org.example.techhive_studio_website_project_final.service.AdminSession;
import org.example.techhive_studio_website_project_final.service.FirestoreService;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller for the Admin Database page with tabbed interface.
 * Displays data from SQLite: Contact Inquiries, Contributor Applications, and
 * Project Requests.
 */
public class AdminDatabaseController {

    // TabPane
    @FXML
    private TabPane dataTabPane;

    // Contact Inquiries Tab
    @FXML
    private TableView<ContactInquiry> contactTable;
    @FXML
    private TableColumn<ContactInquiry, Integer> contactIdCol;
    @FXML
    private TableColumn<ContactInquiry, String> contactNameCol;
    @FXML
    private TableColumn<ContactInquiry, String> contactEmailCol;
    @FXML
    private TableColumn<ContactInquiry, String> contactProjectTypeCol;
    @FXML
    private TableColumn<ContactInquiry, String> contactBudgetCol;
    @FXML
    private TableColumn<ContactInquiry, String> contactDetailsCol;
    @FXML
    private TableColumn<ContactInquiry, String> contactDateCol;
    @FXML
    private TableColumn<ContactInquiry, String> contactSyncCol;
    @FXML
    private TableColumn<ContactInquiry, Void> contactActionsCol;

    // Contributor Applications Tab
    @FXML
    private TableView<ContributorApplication> contributorTable;
    @FXML
    private TableColumn<ContributorApplication, Integer> contribIdCol;
    @FXML
    private TableColumn<ContributorApplication, String> contribNameCol;
    @FXML
    private TableColumn<ContributorApplication, String> contribEmailCol;
    @FXML
    private TableColumn<ContributorApplication, String> contribExpertiseCol;
    @FXML
    private TableColumn<ContributorApplication, String> contribPortfolioCol;
    @FXML
    private TableColumn<ContributorApplication, String> contribStatusCol;
    @FXML
    private TableColumn<ContributorApplication, String> contribDateCol;
    @FXML
    private TableColumn<ContributorApplication, Void> contribActionsCol;

    // Project Requests Tab
    @FXML
    private TableView<CustomerRequest> projectTable;
    @FXML
    private TableColumn<CustomerRequest, Integer> projectIdCol;
    @FXML
    private TableColumn<CustomerRequest, String> projectNameCol;
    @FXML
    private TableColumn<CustomerRequest, String> projectEmailCol;
    @FXML
    private TableColumn<CustomerRequest, String> projectDescCol;
    @FXML
    private TableColumn<CustomerRequest, String> projectServiceCol;
    @FXML
    private TableColumn<CustomerRequest, String> projectStatusCol;
    @FXML
    private TableColumn<CustomerRequest, Void> projectActionsCol;

    // Stats Labels
    @FXML
    private Label totalContactsLabel;
    @FXML
    private Label totalContributorsLabel;
    @FXML
    private Label totalProjectsLabel;
    @FXML
    private Label pendingCountLabel;

    // Repositories
    private final ContactRepository contactRepo = ContactRepository.getInstance();
    private final ContributorRepository contributorRepo = ContributorRepository.getInstance();
    private final CustomerRequestRepository projectRepo = CustomerRequestRepository.getInstance();
    private final FirestoreService firestoreService = FirestoreService.getInstance();

    // Data lists
    private ObservableList<ContactInquiry> contactList = FXCollections.observableArrayList();
    private ObservableList<ContributorApplication> contributorList = FXCollections.observableArrayList();
    private ObservableList<CustomerRequest> projectList = FXCollections.observableArrayList();

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");

    @FXML
    public void initialize() {
        // Check admin access
        if (!AdminSession.getInstance().isAdmin()) {
            SceneManager.getInstance().switchView("fxml/HomeView.fxml");
            return;
        }

        setupContactTable();
        setupContributorTable();
        setupProjectTable();
        loadAllData();
    }

    // ===================== CONTACT INQUIRIES =====================

    private void setupContactTable() {
        contactIdCol.setCellValueFactory(
                data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        contactIdCol.setStyle("-fx-alignment: CENTER;");

        contactNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        contactEmailCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));

        contactProjectTypeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProjectType()));

        contactBudgetCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBudget()));

        contactDetailsCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDetails()));
        contactDetailsCol.setCellFactory(col -> createTruncatedCell());

        contactDateCol.setCellValueFactory(data -> {
            if (data.getValue().getSubmittedAt() != null) {
                return new SimpleStringProperty(data.getValue().getSubmittedAt().format(DATE_FORMAT));
            }
            return new SimpleStringProperty("-");
        });

        contactSyncCol
                .setCellValueFactory(data -> new SimpleStringProperty(data.getValue().isSyncedToCloud() ? "✓" : "✗"));
        contactSyncCol.setStyle("-fx-alignment: CENTER;");

        contactActionsCol.setCellFactory(col -> new TableCell<>() {
            private final Button deleteBtn = new Button("Delete");
            private final Button syncBtn = new Button("Sync");
            private final HBox buttons = new HBox(8, syncBtn, deleteBtn);
            {
                deleteBtn.getStyleClass().addAll("action-btn", "reject-btn");
                syncBtn.getStyleClass().addAll("action-btn", "accept-btn");
                buttons.setAlignment(Pos.CENTER);

                deleteBtn.setOnAction(e -> {
                    ContactInquiry item = getTableView().getItems().get(getIndex());
                    handleDeleteContact(item);
                });

                syncBtn.setOnAction(e -> {
                    ContactInquiry item = getTableView().getItems().get(getIndex());
                    handleSyncContact(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : buttons);
            }
        });

        contactTable.setItems(contactList);
    }

    private void handleDeleteContact(ContactInquiry inquiry) {
        try {
            contactRepo.delete(inquiry.getId());
            contactList.remove(inquiry);
            updateStats();
            System.out.println("[ADMIN] Deleted contact inquiry #" + inquiry.getId());
        } catch (SQLException e) {
            System.err.println("Failed to delete contact: " + e.getMessage());
        }
    }

    private void handleSyncContact(ContactInquiry inquiry) {
        if (inquiry.isSyncedToCloud())
            return;

        firestoreService.saveContactInquiry(inquiry).thenAccept(success -> {
            if (success) {
                try {
                    contactRepo.markAsSynced(inquiry.getId());
                    Platform.runLater(() -> {
                        inquiry.setSyncedToCloud(true);
                        contactTable.refresh();
                        System.out.println("[ADMIN] Synced contact inquiry #" + inquiry.getId());
                    });
                } catch (SQLException e) {
                    System.err.println("Failed to mark as synced: " + e.getMessage());
                }
            }
        });
    }

    // ===================== CONTRIBUTOR APPLICATIONS =====================

    private void setupContributorTable() {
        contribIdCol.setCellValueFactory(
                data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        contribIdCol.setStyle("-fx-alignment: CENTER;");

        contribNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        contribEmailCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));

        contribExpertiseCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getExpertise()));

        contribPortfolioCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPortfolioUrl()));

        contribStatusCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
        contribStatusCol.setCellFactory(col -> createStatusBadgeCell());

        contribDateCol.setCellValueFactory(data -> {
            if (data.getValue().getSubmittedAt() != null) {
                return new SimpleStringProperty(data.getValue().getSubmittedAt().format(DATE_FORMAT));
            }
            return new SimpleStringProperty("-");
        });

        contribActionsCol.setCellFactory(col -> new TableCell<>() {
            private final Button acceptBtn = new Button("Accept");
            private final Button rejectBtn = new Button("Reject");
            private final HBox buttons = new HBox(8, acceptBtn, rejectBtn);
            {
                acceptBtn.getStyleClass().addAll("action-btn", "accept-btn");
                rejectBtn.getStyleClass().addAll("action-btn", "reject-btn");
                buttons.setAlignment(Pos.CENTER);

                acceptBtn.setOnAction(e -> {
                    ContributorApplication app = getTableView().getItems().get(getIndex());
                    handleContributorAction(app, "Accepted");
                });

                rejectBtn.setOnAction(e -> {
                    ContributorApplication app = getTableView().getItems().get(getIndex());
                    handleContributorAction(app, "Rejected");
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    ContributorApplication app = getTableView().getItems().get(getIndex());
                    setGraphic("Pending".equals(app.getStatus()) ? buttons : null);
                }
            }
        });

        contributorTable.setItems(contributorList);
    }

    private void handleContributorAction(ContributorApplication app, String newStatus) {
        try {
            contributorRepo.updateStatus(app.getId(), newStatus);
            app.setStatus(newStatus);
            contributorTable.refresh();
            updateStats();
            System.out.println("[ADMIN] Contributor #" + app.getId() + " status: " + newStatus);

            // Sync to Firestore
            firestoreService.saveContributorApplication(app);
        } catch (SQLException e) {
            System.err.println("Failed to update contributor status: " + e.getMessage());
        }
    }

    // ===================== PROJECT REQUESTS =====================

    private void setupProjectTable() {
        projectIdCol.setCellValueFactory(
                data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        projectIdCol.setStyle("-fx-alignment: CENTER;");

        projectNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomerName()));

        projectEmailCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));

        projectDescCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProjectDescription()));
        projectDescCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    String truncated = item.length() > 40 ? item.substring(0, 40) + "..." : item;
                    setText(truncated);
                    setTooltip(new Tooltip(item));
                }
            }
        });

        projectServiceCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getServiceType()));

        projectStatusCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
        projectStatusCol.setCellFactory(col -> createStatusBadgeCell());

        projectActionsCol.setCellFactory(col -> new TableCell<>() {
            private final Button acceptBtn = new Button("Accept");
            private final Button rejectBtn = new Button("Reject");
            private final HBox buttons = new HBox(8, acceptBtn, rejectBtn);
            {
                acceptBtn.getStyleClass().addAll("action-btn", "accept-btn");
                rejectBtn.getStyleClass().addAll("action-btn", "reject-btn");
                buttons.setAlignment(Pos.CENTER);

                acceptBtn.setOnAction(e -> {
                    CustomerRequest req = getTableView().getItems().get(getIndex());
                    handleProjectAction(req, "Accepted");
                });

                rejectBtn.setOnAction(e -> {
                    CustomerRequest req = getTableView().getItems().get(getIndex());
                    handleProjectAction(req, "Rejected");
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    CustomerRequest req = getTableView().getItems().get(getIndex());
                    setGraphic("Pending".equals(req.getStatus()) ? buttons : null);
                }
            }
        });

        projectTable.setItems(projectList);
    }

    private void handleProjectAction(CustomerRequest request, String newStatus) {
        request.setStatus(newStatus);
        projectRepo.updateStatus(request.getId(), newStatus);
        projectTable.refresh();
        updateStats();
        System.out.println("[ADMIN] Project #" + request.getId() + " status: " + newStatus);
    }

    // ===================== HELPER METHODS =====================

    private <T> TableCell<T, String> createTruncatedCell() {
        return new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    String truncated = item.length() > 30 ? item.substring(0, 30) + "..." : item;
                    setText(truncated);
                    setTooltip(new Tooltip(item));
                }
            }
        };
    }

    private <T> TableCell<T, String> createStatusBadgeCell() {
        return new TableCell<>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setGraphic(null);
                } else {
                    Label badge = new Label(status);
                    badge.getStyleClass().add("status-badge");
                    switch (status) {
                        case "Pending" -> badge.getStyleClass().add("status-pending");
                        case "Accepted" -> badge.getStyleClass().add("status-accepted");
                        case "Rejected" -> badge.getStyleClass().add("status-rejected");
                    }
                    setGraphic(badge);
                    setAlignment(Pos.CENTER);
                }
            }
        };
    }

    private void loadAllData() {
        // Load from SQLite
        try {
            List<ContactInquiry> contacts = contactRepo.findAll();
            contactList.setAll(contacts);
        } catch (SQLException e) {
            System.err.println("Failed to load contacts: " + e.getMessage());
        }

        try {
            List<ContributorApplication> contributors = contributorRepo.findAll();
            contributorList.setAll(contributors);
        } catch (SQLException e) {
            System.err.println("Failed to load contributors: " + e.getMessage());
        }

        // Load project requests (in-memory for now)
        projectList.setAll(projectRepo.findAll());

        updateStats();
    }

    private void updateStats() {
        if (totalContactsLabel != null) {
            totalContactsLabel.setText(String.valueOf(contactList.size()));
        }
        if (totalContributorsLabel != null) {
            totalContributorsLabel.setText(String.valueOf(contributorList.size()));
        }
        if (totalProjectsLabel != null) {
            totalProjectsLabel.setText(String.valueOf(projectList.size()));
        }
        if (pendingCountLabel != null) {
            long pending = contributorList.stream()
                    .filter(c -> "Pending".equals(c.getStatus())).count()
                    + projectList.stream()
                            .filter(p -> "Pending".equals(p.getStatus())).count();
            pendingCountLabel.setText(String.valueOf(pending));
        }
    }

    @FXML
    private void onRefreshClick() {
        loadAllData();
    }

    @FXML
    private void onHomeClick() {
        SceneManager.getInstance().switchView("fxml/HomeView.fxml");
    }

    @FXML
    private void onLogoutClick() {
        AdminSession.getInstance().logout();
        SceneManager.getInstance().switchViewWithFade("fxml/LoginView.fxml");
    }
}
