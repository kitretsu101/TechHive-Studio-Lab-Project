package org.example.techhive_studio_website_project_final.data;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Singleton manager for SQLite database connections.
 * Handles connection lifecycle and schema initialization.
 */
public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;
    private static final String DB_NAME = "techhive.db";

    private DatabaseManager() {
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Get a database connection, creating one if needed.
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String userHome = System.getProperty("user.home");
            File appDir = new File(userHome, ".techhive");
            if (!appDir.exists()) {
                appDir.mkdirs();
            }

            String dbPath = new File(appDir, DB_NAME).getAbsolutePath();
            String url = "jdbc:sqlite:" + dbPath;

            connection = DriverManager.getConnection(url);
            System.out.println("SQLite database connected: " + dbPath);
        }
        return connection;
    }

    /**
     * Initialize the database schema (create tables if not exist).
     */
    public void initializeDatabase() {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();

            // Contact Inquiries table
            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS contact_inquiries (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            name TEXT NOT NULL,
                            email TEXT NOT NULL,
                            project_type TEXT,
                            budget TEXT,
                            details TEXT,
                            submitted_at TEXT DEFAULT CURRENT_TIMESTAMP,
                            synced_to_cloud INTEGER DEFAULT 0
                        )
                    """);

            // Contributor Applications table
            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS contributor_applications (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            name TEXT NOT NULL,
                            email TEXT NOT NULL,
                            expertise TEXT,
                            portfolio_url TEXT,
                            about TEXT,
                            status TEXT DEFAULT 'pending',
                            submitted_at TEXT DEFAULT CURRENT_TIMESTAMP,
                            synced_to_cloud INTEGER DEFAULT 0
                        )
                    """);

            // Users table (for caching user data locally)
            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS users (
                            uid TEXT PRIMARY KEY,
                            email TEXT NOT NULL,
                            display_name TEXT,
                            role TEXT,
                            provider TEXT
                        )
                    """);

            System.out.println("Database schema initialized successfully.");
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Close the database connection.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }
}
