package com.banking.system.util;

import com.banking.system.service.BankingService;

/**
 * Validation utility class for user input validation
 */
public class ValidationUtil {
    
    private static BankingService bankingService;
    
    /**
     * Set the banking service instance for validation
     */
    public static void setBankingService(BankingService service) {
        bankingService = service;
    }
    
    /**
     * Validate username
     * @param username the username to validate
     * @return ValidationResult with success status and error message
     */
    public static ValidationResult validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return new ValidationResult(false, "Username cannot be empty");
        }
        
        if (username.length() < 3) {
            return new ValidationResult(false, "Username must be at least 3 characters long");
        }
        
        if (username.length() > 30) {
            return new ValidationResult(false, "Username cannot exceed 30 characters");
        }
        
        // Check for special characters (optional - you can customize this)
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            return new ValidationResult(false, "Username can only contain letters, numbers, and underscores");
        }
        
        // Check if username already exists
        if (bankingService != null && bankingService.userExists(username)) {
            return new ValidationResult(false, "Username already exists. Please choose a different username");
        }
        
        return new ValidationResult(true, "Username is valid");
    }
    
    /**
     * Validate password
     * @param password the password to validate
     * @return ValidationResult with success status and error message
     */
    public static ValidationResult validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            return new ValidationResult(false, "Password cannot be empty");
        }
        
        if (password.length() < 6) {
            return new ValidationResult(false, "Password must be at least 6 characters long");
        }
        
        if (password.length() > 50) {
            return new ValidationResult(false, "Password cannot exceed 50 characters");
        }
        
        // Check for at least one letter and one number (optional enhancement)
        if (!password.matches(".*[a-zA-Z].*")) {
            return new ValidationResult(false, "Password must contain at least one letter");
        }
        
        if (!password.matches(".*[0-9].*")) {
            return new ValidationResult(false, "Password must contain at least one number");
        }
        
        return new ValidationResult(true, "Password is valid");
    }
    
    /**
     * Validate full name
     * @param name the full name to validate
     * @return ValidationResult with success status and error message
     */
    public static ValidationResult validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ValidationResult(false, "Full name cannot be empty");
        }
        
        if (name.trim().length() < 3) {
            return new ValidationResult(false, "Full name must be at least 3 characters long");
        }
        
        if (name.length() > 50) {
            return new ValidationResult(false, "Full name cannot exceed 50 characters");
        }
        
        // Check if name contains only letters and spaces
        if (!name.matches("^[a-zA-Z\\s]+$")) {
            return new ValidationResult(false, "Full name can only contain letters and spaces");
        }
        
        return new ValidationResult(true, "Full name is valid");
    }
    
    /**
     * Validate age
     * @param age the age to validate
     * @return ValidationResult with success status and error message
     */
    public static ValidationResult validateAge(int age) {
        if (age < 18) {
            return new ValidationResult(false, "Age must be at least 18 years old");
        }
        
        if (age > 120) {
            return new ValidationResult(false, "Age cannot exceed 120 years");
        }
        
        return new ValidationResult(true, "Age is valid");
    }
    
    /**
     * Validate contact number
     * @param contactNo the contact number to validate
     * @return ValidationResult with success status and error message
     */
    public static ValidationResult validateContactNumber(String contactNo) {
        if (contactNo == null || contactNo.trim().isEmpty()) {
            return new ValidationResult(false, "Contact number cannot be empty");
        }
        
        // Remove any spaces or dashes
        String cleanContact = contactNo.replaceAll("[\\s-]", "");
        
        if (!cleanContact.matches("^[0-9]{10}$")) {
            return new ValidationResult(false, "Contact number must be exactly 10 digits");
        }
        
        return new ValidationResult(true, "Contact number is valid");
    }
    
    /**
     * Validate city
     * @param city the city to validate
     * @return ValidationResult with success status and error message
     */
    public static ValidationResult validateCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            return new ValidationResult(false, "City cannot be empty");
        }
        
        if (city.trim().length() < 2) {
            return new ValidationResult(false, "City must be at least 2 characters long");
        }
        
        if (city.length() > 30) {
            return new ValidationResult(false, "City cannot exceed 30 characters");
        }
        
        // Check if city contains only letters and spaces
        if (!city.matches("^[a-zA-Z\\s]+$")) {
            return new ValidationResult(false, "City can only contain letters and spaces");
        }
        
        return new ValidationResult(true, "City is valid");
    }
    
    /**
     * Validate all user registration fields
     * @param username the username
     * @param password the password
     * @param name the full name
     * @param age the age
     * @param contactNo the contact number
     * @param city the city
     * @return ValidationResult with success status and error message
     */
    public static ValidationResult validateRegistration(String username, String password, String name, 
                                                       int age, String contactNo, String city) {
        ValidationResult result;
        
        // Validate username
        result = validateUsername(username);
        if (!result.isValid()) return result;
        
        // Validate password
        result = validatePassword(password);
        if (!result.isValid()) return result;
        
        // Validate name
        result = validateName(name);
        if (!result.isValid()) return result;
        
        // Validate age
        result = validateAge(age);
        if (!result.isValid()) return result;
        
        // Validate contact number
        result = validateContactNumber(contactNo);
        if (!result.isValid()) return result;
        
        // Validate city
        result = validateCity(city);
        if (!result.isValid()) return result;
        
        return new ValidationResult(true, "All fields are valid");
    }
    
    /**
     * Validation result class
     */
    public static class ValidationResult {
        private final boolean valid;
        private final String message;
        
        public ValidationResult(boolean valid, String message) {
            this.valid = valid;
            this.message = message;
        }
        
        public boolean isValid() {
            return valid;
        }
        
        public String getMessage() {
            return message;
        }
    }
}
