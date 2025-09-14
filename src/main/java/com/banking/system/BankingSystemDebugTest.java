package com.banking.system;

import com.banking.system.config.DatabaseConfig;
import com.banking.system.dao.DatabaseManager;

/**
 * Simple debug test for Banking System
 * This will test if the environment variables are being read correctly
 */
public class BankingSystemDebugTest {

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("    BANKING SYSTEM DEBUG TEST");
        System.out.println("==========================================");
        
        System.out.println("=== Environment Variables Test ===");
        System.out.println("DB_HOST from System.getenv(): " + System.getenv("DB_HOST"));
        System.out.println("DB_PORT from System.getenv(): " + System.getenv("DB_PORT"));
        System.out.println("DB_NAME from System.getenv(): " + System.getenv("DB_NAME"));
        System.out.println("DB_USERNAME from System.getenv(): " + System.getenv("DB_USERNAME"));
        System.out.println("DB_PASSWORD from System.getenv(): " + System.getenv("DB_PASSWORD"));
        
        System.out.println("\n=== DatabaseConfig Test ===");
        System.out.println("Host: " + DatabaseConfig.getHost());
        System.out.println("Port: " + DatabaseConfig.getPort());
        System.out.println("Database: " + DatabaseConfig.getDatabaseName());
        System.out.println("Username: " + DatabaseConfig.getUsername());
        System.out.println("Password: " + (DatabaseConfig.getPassword().isEmpty() ? "[empty]" : "[set - length: " + DatabaseConfig.getPassword().length() + "]"));
        
        System.out.println("\n=== Connection URLs ===");
        System.out.println("Create DB URL: " + DatabaseConfig.buildCreateDatabaseUrl());
        System.out.println("Main DB URL: " + DatabaseConfig.buildDatabaseUrl());
        
        System.out.println("\n=== Testing Database Connection ===");
        try {
            DatabaseManager dbManager = new DatabaseManager();
            System.out.println("✅ Database connection test successful!");
        } catch (Exception e) {
            System.err.println("❌ Database connection test failed!");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n=== Debug Test Complete ===");
    }
}
