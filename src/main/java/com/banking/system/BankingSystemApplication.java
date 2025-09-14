package com.banking.system;

import com.banking.system.model.User;
import com.banking.system.service.BankingService;
import com.banking.system.util.ValidationUtil;

import java.util.Scanner;

/**
 * Main Banking System Application
 * Console-based interface replicating the Python Tkinter application
 */
public class BankingSystemApplication {
    private static BankingService bankingService;
    private static Scanner scanner;
    private static boolean isRunning = true;

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("    WELCOME TO ONLINE BANKING SYSTEM");
        System.out.println("==========================================");
        
        bankingService = new BankingService();
        ValidationUtil.setBankingService(bankingService);
        scanner = new Scanner(System.in);
        
        // Start the application with main menu
        showMainEntryMenu();
        
        scanner.close();
    }

    /**
     * Display main entry menu with login and register options
     */
    private static void showMainEntryMenu() {
        while (isRunning) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("            MAIN MENU");
            System.out.println("=".repeat(50));
            System.out.println("1. LOGIN");
            System.out.println("2. REGISTER");
            System.out.println("3. EXIT");
            System.out.println("=".repeat(50));
            
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1" -> showLoginMenu();
                case "2" -> showRegisterMenu();
                case "3" -> {
                    System.out.println("Thank you for using Banking System!");
                    isRunning = false;
                }
                default -> {
                    System.out.println("Invalid choice! Please enter 1, 2, or 3.");
                    showMainEntryMenu();
                }
            }
        }
    }

    /**
     * Display login menu
     */
    private static void showLoginMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                LOGIN");
        System.out.println("=".repeat(50));
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        User loggedInUser = bankingService.login(username, password);
        if (loggedInUser != null) {
            System.out.println("Logged in successfully!!");
            showMainMenu();
        } else {
            System.out.println("Login unsuccessful!!");
            System.out.println("Password and Username do not match!!");
            
            System.out.print("Do you want to try again? (y/n): ");
            String choice = scanner.nextLine().toLowerCase();
            
            if (choice.equals("y")) {
                showLoginMenu();
            } else {
                showMainEntryMenu();
            }
        }
    }

    /**
     * Display registration menu with validation
     */
    private static void showRegisterMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("            REGISTER NEW ACCOUNT");
        System.out.println("=".repeat(50));
        System.out.println("Please fill up the details");
        System.out.println("Validation Rules:");
        System.out.println("- Username: 3-30 characters, unique");
        System.out.println("- Password: 6+ characters, must contain letters and numbers");
        System.out.println("- Name: 3+ characters, letters and spaces only");
        System.out.println("- Age: 18+ years");
        System.out.println("- Contact: Exactly 10 digits");
        System.out.println("- City: 2+ characters, letters and spaces only");
        System.out.println("=".repeat(50));
        
        String username, password, fullName, contactNo, city;
        int age;
        
        // Get username with validation
        while (true) {
            System.out.print("Username: ");
            username = scanner.nextLine();
            ValidationUtil.ValidationResult result = ValidationUtil.validateUsername(username);
            if (result.isValid()) {
                break;
            } else {
                System.out.println("❌ " + result.getMessage());
            }
        }
        
        // Get password with validation
        while (true) {
            System.out.print("Password: ");
            password = scanner.nextLine();
            ValidationUtil.ValidationResult result = ValidationUtil.validatePassword(password);
            if (result.isValid()) {
                break;
            } else {
                System.out.println("❌ " + result.getMessage());
            }
        }
        
        // Get full name with validation
        while (true) {
            System.out.print("Full Name: ");
            fullName = scanner.nextLine();
            ValidationUtil.ValidationResult result = ValidationUtil.validateName(fullName);
            if (result.isValid()) {
                break;
            } else {
                System.out.println("❌ " + result.getMessage());
            }
        }
        
        // Get age with validation
        while (true) {
            System.out.print("Age: ");
            try {
                age = Integer.parseInt(scanner.nextLine());
                ValidationUtil.ValidationResult result = ValidationUtil.validateAge(age);
                if (result.isValid()) {
                    break;
                } else {
                    System.out.println("❌ " + result.getMessage());
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid age! Please enter a valid number.");
            }
        }
        
        // Get contact number with validation
        while (true) {
            System.out.print("Contact No.: ");
            contactNo = scanner.nextLine();
            ValidationUtil.ValidationResult result = ValidationUtil.validateContactNumber(contactNo);
            if (result.isValid()) {
                break;
            } else {
                System.out.println("❌ " + result.getMessage());
            }
        }
        
        // Get city with validation
        while (true) {
            System.out.print("City: ");
            city = scanner.nextLine();
            ValidationUtil.ValidationResult result = ValidationUtil.validateCity(city);
            if (result.isValid()) {
                break;
            } else {
                System.out.println("❌ " + result.getMessage());
            }
        }
        
        // All validations passed, create user
        User newUser = new User(username, password, fullName, "", contactNo, age, city);
        
        if (bankingService.register(newUser)) {
            System.out.println("✅ Account Created successfully!!");
            System.out.println("You can now login with your credentials.");
            showMainEntryMenu();
        } else {
            System.out.println("❌ Registration failed! Please try again.");
            System.out.print("Do you want to try again? (y/n): ");
            String choice = scanner.nextLine().toLowerCase();
            
            if (choice.equals("y")) {
                showRegisterMenu();
            } else {
                showMainEntryMenu();
            }
        }
    }

    /**
     * Display main menu after successful login
     */
    private static void showMainMenu() {
        while (isRunning) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("                ONLINE BANKING SYSTEM");
            System.out.println("=".repeat(60));
            System.out.println("You are logged in as: " + bankingService.getCurrentUser().getUsername());
            
            System.out.println("\nMAIN MENU");
            System.out.println("1. DEPOSIT AMOUNT");
            System.out.println("2. BALANCE ENQUIRY");
            System.out.println("3. TRANSFER AMOUNT");
            System.out.println("4. CHANGE PASSWORD");
            System.out.println("5. DELETE ACCOUNT");
            System.out.println("6. ACCOUNT DETAILS");
            System.out.println("7. LOGOUT");
            System.out.println("8. EXIT");
            
            System.out.print("\nEnter your choice: ");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1" -> depositAmount();
                case "2" -> balanceEnquiry();
                case "3" -> transferAmount();
                case "4" -> changePassword();
                case "5" -> deleteAccount();
                case "6" -> accountDetails();
                case "7" -> {
                    bankingService.logout();
                    System.out.println("Logged out successfully!");
                    showMainEntryMenu();
                }
                case "8" -> {
                    System.out.println("Thank you for using Banking System!");
                    isRunning = false;
                }
                default -> {
                    System.out.println("Please enter valid choice!");
                    showMainMenu();
                }
            }
        }
    }

    /**
     * Handle deposit amount operation
     */
    private static void depositAmount() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("            AMOUNT DEPOSIT");
        System.out.println("=".repeat(40));
        
        System.out.print("Enter the amount to be deposited: ");
        try {
            double amount = Double.parseDouble(scanner.nextLine());
            
            if (bankingService.deposit(amount)) {
                System.out.println("Amount has been deposited successfully!!");
            } else {
                System.out.println("Deposit failed! Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount! Please enter a valid number.");
        }
    }

    /**
     * Handle balance enquiry operation
     */
    private static void balanceEnquiry() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("            BALANCE ENQUIRY");
        System.out.println("=".repeat(40));
        
        double balance = bankingService.getBalance();
        System.out.println("Balance: ₹" + String.format("%.2f", balance));
    }

    /**
     * Handle transfer amount operation
     */
    private static void transferAmount() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("            AMOUNT TRANSFER");
        System.out.println("=".repeat(40));
        
        System.out.print("Enter the account number of person: ");
        String accountNumber = scanner.nextLine();
        
        System.out.print("Enter the amount to be transferred: ");
        try {
            double amount = Double.parseDouble(scanner.nextLine());
            
            if (bankingService.transfer(accountNumber, amount)) {
                System.out.println("Amount has been transferred successfully!!");
                
                System.out.print("Do you want to make another transaction? (y/n): ");
                String choice = scanner.nextLine().toLowerCase();
                
                if (choice.equals("y")) {
                    transferAmount();
                }
            } else {
                System.out.println("Specified account doesn't exist!");
                System.out.println("Transaction cancelled!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount! Please enter a valid number.");
        }
    }

    /**
     * Handle change password operation
     */
    private static void changePassword() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("            CHANGE PASSWORD");
        System.out.println("=".repeat(40));
        
        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Enter your existing password: ");
            String currentPassword = scanner.nextLine();
            
            System.out.print("Enter your new password: ");
            String newPassword = scanner.nextLine();
            
            System.out.print("Re-enter your new password: ");
            String confirmPassword = scanner.nextLine();
            
            if (bankingService.changePassword(currentPassword, newPassword)) {
                if (newPassword.equals(confirmPassword)) {
                    System.out.println("Password Changed successfully!");
                    return;
                } else {
                    System.out.println("Passwords don't match!");
                    continue;
                }
            } else {
                System.out.println("Password is incorrect!");
                attempts++;
                
                if (attempts < 3) {
                    System.out.println("You only have " + (3 - attempts) + " attempts left!");
                } else {
                    System.out.println("Please retry later!");
                    return;
                }
            }
        }
    }

    /**
     * Handle delete account operation
     */
    private static void deleteAccount() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("            DELETE ACCOUNT");
        System.out.println("=".repeat(40));
        
        while (true) {
            System.out.print("Are you sure you want to delete your account? (y/n): ");
            String confirmation = scanner.nextLine().toLowerCase();
            
            if (confirmation.equals("y")) {
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();
                
                if (bankingService.deleteAccount(password)) {
                    System.out.println("Your account has been successfully deleted!");
                    isRunning = false;
                    return;
                } else {
                    System.out.println("Password incorrect!");
                    deleteAccount();
                }
            } else {
                return;
            }
        }
    }

    /**
     * Handle account details operation
     */
    private static void accountDetails() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("            ACCOUNT DETAILS");
        System.out.println("=".repeat(40));
        
        User user = bankingService.getAccountDetails();
        
        System.out.println("=".repeat(100));
        System.out.printf("| %-15s | %-15s | %-15s | %-12s | %-15s | %-8s | %-10s | %-10s |%n",
                "USERNAME", "PASSWORD", "NAME", "ACC NUMBER", "CONTACT NUMBER", "AGE", "CITY", "BALANCE");
        System.out.println("=".repeat(100));
        
        System.out.printf("| %-15s | %-15s | %-15s | %-12s | %-15s | %-8d | %-10s | %-10.2f |%n",
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getAccountNumber(),
                user.getContactNumber(),
                user.getAge(),
                user.getCity(),
                user.getBalance());
        
        System.out.println("=".repeat(100));
    }

}
