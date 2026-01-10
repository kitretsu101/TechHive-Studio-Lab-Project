package org.example.techhive_studio_website_project_final.data;

import org.example.techhive_studio_website_project_final.model.ContributorApplication;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for CRUD operations on contributor applications.
 */
public class ContributorRepository {
    private static ContributorRepository instance;
    private final DatabaseManager dbManager;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private ContributorRepository() {
        this.dbManager = DatabaseManager.getInstance();
    }

    public static ContributorRepository getInstance() {
        if (instance == null) {
            instance = new ContributorRepository();
        }
        return instance;
    }

    /**
     * Save a new contributor application to the database.
     * 
     * @return the generated ID of the inserted record
     */
    public int save(ContributorApplication application) throws SQLException {
        String sql = """
                    INSERT INTO contributor_applications (name, email, expertise, portfolio_url, about, status, submitted_at, synced_to_cloud)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, application.getName());
            pstmt.setString(2, application.getEmail());
            pstmt.setString(3, application.getExpertise());
            pstmt.setString(4, application.getPortfolioUrl());
            pstmt.setString(5, application.getAbout());
            pstmt.setString(6, application.getStatus());
            pstmt.setString(7, application.getSubmittedAt().format(formatter));
            pstmt.setInt(8, application.isSyncedToCloud() ? 1 : 0);

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                application.setId(id);
                System.out.println("Contributor application saved with ID: " + id);
                return id;
            }
        }
        return -1;
    }

    /**
     * Get all contributor applications from the database.
     */
    public List<ContributorApplication> findAll() throws SQLException {
        List<ContributorApplication> applications = new ArrayList<>();
        String sql = "SELECT * FROM contributor_applications ORDER BY submitted_at DESC";

        try (Connection conn = dbManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                applications.add(mapResultSetToApplication(rs));
            }
        }
        return applications;
    }

    /**
     * Get all unsynced applications for cloud sync.
     */
    public List<ContributorApplication> findUnsynced() throws SQLException {
        List<ContributorApplication> applications = new ArrayList<>();
        String sql = "SELECT * FROM contributor_applications WHERE synced_to_cloud = 0";

        try (Connection conn = dbManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                applications.add(mapResultSetToApplication(rs));
            }
        }
        return applications;
    }

    /**
     * Get applications by status.
     */
    public List<ContributorApplication> findByStatus(String status) throws SQLException {
        List<ContributorApplication> applications = new ArrayList<>();
        String sql = "SELECT * FROM contributor_applications WHERE status = ? ORDER BY submitted_at DESC";

        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    applications.add(mapResultSetToApplication(rs));
                }
            }
        }
        return applications;
    }

    /**
     * Update the status of an application.
     */
    public void updateStatus(int id, String status) throws SQLException {
        String sql = "UPDATE contributor_applications SET status = ? WHERE id = ?";

        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
    }

    /**
     * Mark an application as synced to cloud.
     */
    public void markAsSynced(int id) throws SQLException {
        String sql = "UPDATE contributor_applications SET synced_to_cloud = 1 WHERE id = ?";

        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    /**
     * Delete an application by ID.
     */
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM contributor_applications WHERE id = ?";

        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    private ContributorApplication mapResultSetToApplication(ResultSet rs) throws SQLException {
        ContributorApplication app = new ContributorApplication();
        app.setId(rs.getInt("id"));
        app.setName(rs.getString("name"));
        app.setEmail(rs.getString("email"));
        app.setExpertise(rs.getString("expertise"));
        app.setPortfolioUrl(rs.getString("portfolio_url"));
        app.setAbout(rs.getString("about"));
        app.setStatus(rs.getString("status"));

        String submittedAt = rs.getString("submitted_at");
        if (submittedAt != null && !submittedAt.isEmpty()) {
            try {
                app.setSubmittedAt(LocalDateTime.parse(submittedAt, formatter));
            } catch (Exception e) {
                app.setSubmittedAt(LocalDateTime.now());
            }
        }

        app.setSyncedToCloud(rs.getInt("synced_to_cloud") == 1);
        return app;
    }
}
