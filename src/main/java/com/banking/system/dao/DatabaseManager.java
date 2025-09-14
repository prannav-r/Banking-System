package com.banking.system.dao;

import com.banking.system.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database connection and management class
 */
public class DatabaseManager {

    public DatabaseManager() {
        initializeDatabase();
    }

    /**
     * Initialize database connection and create tables if they don't exist
     */
    private void initializeDatabase() {
        System.out.println("Connecting to database...");
        DatabaseConfig.printConfiguration();

        try {
            // Create database if it doesn't exist
            createDatabaseIfNotExists();
            
            // Create table if it doesn't exist
            createTableIfNotExists();
            
            System.out.println("Database connection established successfully!");
            
        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
            System.err.println("Please check your database configuration and ensure MySQL server is running.");
            System.err.println("You can set the following environment variables:");
            System.err.println("  DB_HOST - Database host (default: localhost)");
            System.err.println("  DB_PORT - Database port (default: 3306)");
            System.err.println("  DB_NAME - Database name (default: bank)");
            System.err.println("  DB_USERNAME - Database username (default: root)");
            System.err.println("  DB_PASSWORD - Database password (default: empty)");
            System.exit(1);
        }
    }

    /**
     * Create database if it doesn't exist
     */
    private void createDatabaseIfNotExists() throws SQLException {
        try (Connection conn = DriverManager.getConnection(
                DatabaseConfig.buildCreateDatabaseUrl(), 
                DatabaseConfig.getUsername(), 
                DatabaseConfig.getPassword());
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DatabaseConfig.getDatabaseName());
            stmt.executeUpdate("USE " + DatabaseConfig.getDatabaseName());
        }
    }

    /**
     * Create userdata table if it doesn't exist
     */
    private void createTableIfNotExists() throws SQLException {
        String createTableSql = """
            CREATE TABLE IF NOT EXISTS userdata (
                username VARCHAR(30) PRIMARY KEY,
                password VARCHAR(30) NOT NULL,
                name VARCHAR(30) NOT NULL,
                actno VARCHAR(5) UNIQUE NOT NULL,
                contactno VARCHAR(10) NOT NULL,
                age INT NOT NULL,
                city VARCHAR(10) NOT NULL,
                balance DOUBLE DEFAULT 5000.0
            )
            """;
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate(createTableSql);
        }
    }

    /**
     * Get database connection
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            DatabaseConfig.buildDatabaseUrl(), 
            DatabaseConfig.getUsername(), 
            DatabaseConfig.getPassword());
    }
}
