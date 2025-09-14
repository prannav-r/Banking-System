package com.banking.system.config;

/**
 * Configuration utility class for managing environment variables
 * Debug version with detailed logging
 */
public class DatabaseConfigDebug {
    
    // Environment variable names
    public static final String DB_HOST_ENV = "DB_HOST";
    public static final String DB_PORT_ENV = "DB_PORT";
    public static final String DB_NAME_ENV = "DB_NAME";
    public static final String DB_USERNAME_ENV = "DB_USERNAME";
    public static final String DB_PASSWORD_ENV = "DB_PASSWORD";
    
    // Default values
    public static final String DEFAULT_HOST = "localhost";
    public static final String DEFAULT_PORT = "3306";
    public static final String DEFAULT_DB_NAME = "bank";
    public static final String DEFAULT_USERNAME = "root";
    public static final String DEFAULT_PASSWORD = "";
    
    /**
     * Get environment variable value or return default
     */
    public static String getEnvOrDefault(String envVar, String defaultValue) {
        String value = System.getenv(envVar);
        System.out.println("DEBUG: Environment variable " + envVar + " = " + (value != null ? "'" + value + "'" : "null"));
        return value != null ? value : defaultValue;
    }
    
    /**
     * Get database host from environment or default
     */
    public static String getHost() {
        return getEnvOrDefault(DB_HOST_ENV, DEFAULT_HOST);
    }
    
    /**
     * Get database port from environment or default
     */
    public static String getPort() {
        return getEnvOrDefault(DB_PORT_ENV, DEFAULT_PORT);
    }
    
    /**
     * Get database name from environment or default
     */
    public static String getDatabaseName() {
        return getEnvOrDefault(DB_NAME_ENV, DEFAULT_DB_NAME);
    }
    
    /**
     * Get database username from environment or default
     */
    public static String getUsername() {
        return getEnvOrDefault(DB_USERNAME_ENV, DEFAULT_USERNAME);
    }
    
    /**
     * Get database password from environment or default
     */
    public static String getPassword() {
        return getEnvOrDefault(DB_PASSWORD_ENV, DEFAULT_PASSWORD);
    }
    
    /**
     * Build database URL
     */
    public static String buildDatabaseUrl() {
        return String.format("jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                           getHost(), getPort(), getDatabaseName());
    }
    
    /**
     * Build database creation URL (without database name)
     */
    public static String buildCreateDatabaseUrl() {
        return String.format("jdbc:mysql://%s:%s/?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                           getHost(), getPort());
    }
    
    /**
     * Print current configuration with debug info
     */
    public static void printConfiguration() {
        System.out.println("=== DEBUG: Environment Variables ===");
        System.out.println("All environment variables containing 'DB':");
        System.getenv().entrySet().stream()
            .filter(entry -> entry.getKey().contains("DB"))
            .forEach(entry -> System.out.println("  " + entry.getKey() + " = '" + entry.getValue() + "'"));
        
        System.out.println("\n=== Database Configuration ===");
        System.out.println("  Host: " + getHost());
        System.out.println("  Port: " + getPort());
        System.out.println("  Database: " + getDatabaseName());
        System.out.println("  Username: " + getUsername());
        System.out.println("  Password: " + (getPassword().isEmpty() ? "[empty]" : "[set - length: " + getPassword().length() + "]"));
        
        System.out.println("\n=== Connection URLs ===");
        System.out.println("  Create DB URL: " + buildCreateDatabaseUrl());
        System.out.println("  Main DB URL: " + buildDatabaseUrl());
    }
}
