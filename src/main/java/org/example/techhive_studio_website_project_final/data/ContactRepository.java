package org.example.techhive_studio_website_project_final.data;

import org.example.techhive_studio_website_project_final.model.ContactInquiry;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for CRUD operations on contact inquiries.
 */
public class ContactRepository {
    private static ContactRepository instance;
    private final DatabaseManager dbManager;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private ContactRepository() {
        this.dbManager = DatabaseManager.getInstance();
    }

    public static ContactRepository getInstance() {
        if (instance == null) {
            instance = new ContactRepository();
        }
        return instance;
    }

    /**
     * Save a new contact inquiry to the database.
     * 
     * @return the generated ID of the inserted record
     */
    public int save(ContactInquiry inquiry) throws SQLException {
        String sql = """
                    INSERT INTO contact_inquiries (name, email, project_type, budget, details, submitted_at, synced_to_cloud)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, inquiry.getName());
            pstmt.setString(2, inquiry.getEmail());
            pstmt.setString(3, inquiry.getProjectType());
            pstmt.setString(4, inquiry.getBudget());
            pstmt.setString(5, inquiry.getDetails());
            pstmt.setString(6, inquiry.getSubmittedAt().format(formatter));
            pstmt.setInt(7, inquiry.isSyncedToCloud() ? 1 : 0);

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                inquiry.setId(id);
                System.out.println("Contact inquiry saved with ID: " + id);
                return id;
            }
        }
        return -1;
    }

    /**
     * Get all contact inquiries from the database.
     */
    public List<ContactInquiry> findAll() throws SQLException {
        List<ContactInquiry> inquiries = new ArrayList<>();
        String sql = "SELECT * FROM contact_inquiries ORDER BY submitted_at DESC";

        try (Connection conn = dbManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                inquiries.add(mapResultSetToInquiry(rs));
            }
        }
        return inquiries;
    }

    /**
     * Get all unsynced inquiries for cloud sync.
     */
    public List<ContactInquiry> findUnsynced() throws SQLException {
        List<ContactInquiry> inquiries = new ArrayList<>();
        String sql = "SELECT * FROM contact_inquiries WHERE synced_to_cloud = 0";

        try (Connection conn = dbManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                inquiries.add(mapResultSetToInquiry(rs));
            }
        }
        return inquiries;
    }

    /**
     * Mark an inquiry as synced to cloud.
     */
    public void markAsSynced(int id) throws SQLException {
        String sql = "UPDATE contact_inquiries SET synced_to_cloud = 1 WHERE id = ?";

        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    /**
     * Delete a contact inquiry by ID.
     */
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM contact_inquiries WHERE id = ?";

        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    private ContactInquiry mapResultSetToInquiry(ResultSet rs) throws SQLException {
        ContactInquiry inquiry = new ContactInquiry();
        inquiry.setId(rs.getInt("id"));
        inquiry.setName(rs.getString("name"));
        inquiry.setEmail(rs.getString("email"));
        inquiry.setProjectType(rs.getString("project_type"));
        inquiry.setBudget(rs.getString("budget"));
        inquiry.setDetails(rs.getString("details"));

        String submittedAt = rs.getString("submitted_at");
        if (submittedAt != null && !submittedAt.isEmpty()) {
            try {
                inquiry.setSubmittedAt(LocalDateTime.parse(submittedAt, formatter));
            } catch (Exception e) {
                inquiry.setSubmittedAt(LocalDateTime.now());
            }
        }

        inquiry.setSyncedToCloud(rs.getInt("synced_to_cloud") == 1);
        return inquiry;
    }
}
