package com.banking.system;

import com.banking.system.dao.DatabaseManagerDebug;

/**
 * Debug version of Banking System Application
 * This version will show detailed environment variable information
 */
public class BankingSystemDebugApplication {

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("    BANKING SYSTEM DEBUG MODE");
        System.out.println("==========================================");
        
        System.out.println("=== System Environment ===");
        System.out.println("Java version: " + System.getProperty("java.version"));
        System.out.println("OS: " + System.getProperty("os.name"));
        System.out.println("User: " + System.getProperty("user.name"));
        
        System.out.println("\n=== All Environment Variables ===");
        System.getenv().entrySet().stream()
            .sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
            .forEach(entry -> {
                if (entry.getKey().contains("DB") || entry.getKey().contains("PATH")) {
                    System.out.println("  " + entry.getKey() + " = '" + entry.getValue() + "'");
                }
            });
        
        System.out.println("\n=== Testing Database Connection ===");
        try {
            DatabaseManagerDebug dbManager = new DatabaseManagerDebug();
            System.out.println("✅ Database connection test successful!");
        } catch (Exception e) {
            System.err.println("❌ Database connection test failed!");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n=== Debug Complete ===");
        System.out.println("If you see this message, the debug run completed.");
        System.out.println("Check the output above for environment variable details.");
    }
}
