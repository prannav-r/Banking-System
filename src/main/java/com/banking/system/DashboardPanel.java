package com.banking.system;

import com.banking.system.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dashboard Panel for Banking System
 */
public class DashboardPanel extends JPanel {
    
    private BankingSystemSwingApplication parent;
    private JLabel welcomeLabel;
    private JLabel balanceLabel;
    private JLabel accountInfoLabel;
    private JButton depositButton;
    private JButton transferButton;
    private JButton balanceButton;
    private JButton accountDetailsButton;
    private JButton changePasswordButton;
    private JButton deleteAccountButton;
    private JButton logoutButton;
    
    public DashboardPanel(BankingSystemSwingApplication parent) {
        this.parent = parent;
        initializeComponents();
        layoutComponents();
        attachEventHandlers();
    }
    
    private void initializeComponents() {
        welcomeLabel = new JLabel();
        balanceLabel = new JLabel();
        accountInfoLabel = new JLabel();
        
        depositButton = new JButton("Deposit Money");
        transferButton = new JButton("Transfer Money");
        balanceButton = new JButton("Check Balance");
        accountDetailsButton = new JButton("Account Details");
        changePasswordButton = new JButton("Change Password");
        deleteAccountButton = new JButton("Delete Account");
        logoutButton = new JButton("Logout");
        
        // Style components
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        balanceLabel.setForeground(Color.BLUE);
        
        Dimension buttonSize = new Dimension(150, 35);
        depositButton.setPreferredSize(buttonSize);
        transferButton.setPreferredSize(buttonSize);
        balanceButton.setPreferredSize(buttonSize);
        accountDetailsButton.setPreferredSize(buttonSize);
        changePasswordButton.setPreferredSize(buttonSize);
        deleteAccountButton.setPreferredSize(buttonSize);
        logoutButton.setPreferredSize(buttonSize);
        
        deleteAccountButton.setForeground(Color.RED);
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout());
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Top panel - User info
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        topPanel.setBorder(BorderFactory.createTitledBorder("Account Information"));
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(10, 10, 10, 10);
        topPanel.add(welcomeLabel, gbc);
        
        gbc.gridy = 1;
        topPanel.add(balanceLabel, gbc);
        
        gbc.gridy = 2;
        topPanel.add(accountInfoLabel, gbc);
        
        // Center panel - Buttons
        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createTitledBorder("Banking Operations"));
        
        centerPanel.add(depositButton);
        centerPanel.add(transferButton);
        centerPanel.add(balanceButton);
        centerPanel.add(accountDetailsButton);
        centerPanel.add(changePasswordButton);
        centerPanel.add(deleteAccountButton);
        
        // Bottom panel - Logout
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(logoutButton);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void attachEventHandlers() {
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDepositDialog();
            }
        });
        
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTransferDialog();
            }
        });
        
        balanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBalanceDialog();
            }
        });
        
        accountDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAccountDetailsDialog();
            }
        });
        
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showChangePasswordDialog();
            }
        });
        
        deleteAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDeleteAccountDialog();
            }
        });
        
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.logout();
            }
        });
    }
    
    public void refreshData() {
        User user = parent.getCurrentUser();
        if (user != null) {
            welcomeLabel.setText("Welcome, " + user.getName() + "!");
            balanceLabel.setText("Current Balance: ₹" + String.format("%.2f", user.getBalance()));
            accountInfoLabel.setText("Account Number: " + user.getAccountNumber() + " | Username: " + user.getUsername());
        }
    }
    
    private void showDepositDialog() {
        String amountStr = JOptionPane.showInputDialog(this, "Enter amount to deposit:", "Deposit Money", JOptionPane.QUESTION_MESSAGE);
        if (amountStr != null && !amountStr.trim().isEmpty()) {
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount > 0) {
                    if (parent.getBankingService().deposit(amount)) {
                        JOptionPane.showMessageDialog(this, "Deposit successful! New balance: ₹" + 
                            String.format("%.2f", parent.getCurrentUser().getBalance()), "Success", JOptionPane.INFORMATION_MESSAGE);
                        refreshData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Deposit failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a positive amount.", "Invalid Amount", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showTransferDialog() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField accountField = new JTextField(15);
        JTextField amountField = new JTextField(15);
        
        panel.add(new JLabel("Recipient Account Number:"));
        panel.add(accountField);
        panel.add(new JLabel("Amount to Transfer:"));
        panel.add(amountField);
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Transfer Money", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String accountNumber = accountField.getText().trim();
            String amountStr = amountField.getText().trim();
            
            if (accountNumber.isEmpty() || amountStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Missing Information", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount > 0) {
                    if (parent.getBankingService().transfer(accountNumber, amount)) {
                        JOptionPane.showMessageDialog(this, "Transfer successful! New balance: ₹" + 
                            String.format("%.2f", parent.getCurrentUser().getBalance()), "Success", JOptionPane.INFORMATION_MESSAGE);
                        refreshData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Transfer failed. Check account number and balance.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a positive amount.", "Invalid Amount", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showBalanceDialog() {
        User user = parent.getCurrentUser();
        JOptionPane.showMessageDialog(this, 
            "Account Balance: ₹" + String.format("%.2f", user.getBalance()) + "\n" +
            "Account Number: " + user.getAccountNumber() + "\n" +
            "Account Holder: " + user.getName(),
            "Account Balance", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showAccountDetailsDialog() {
        User user = parent.getCurrentUser();
        String details = String.format(
            "Account Details:\n\n" +
            "Account Number: %s\n" +
            "Username: %s\n" +
            "Full Name: %s\n" +
            "Age: %d\n" +
            "Contact Number: %s\n" +
            "City: %s\n" +
            "Current Balance: ₹%.2f",
            user.getAccountNumber(),
            user.getUsername(),
            user.getName(),
            user.getAge(),
            user.getContactNumber(),
            user.getCity(),
            user.getBalance()
        );
        
        JTextArea textArea = new JTextArea(details);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Account Details", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showChangePasswordDialog() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        JPasswordField currentPasswordField = new JPasswordField(15);
        JPasswordField newPasswordField = new JPasswordField(15);
        JPasswordField confirmPasswordField = new JPasswordField(15);
        
        panel.add(new JLabel("Current Password:"));
        panel.add(currentPasswordField);
        panel.add(new JLabel("New Password:"));
        panel.add(newPasswordField);
        panel.add(new JLabel("Confirm New Password:"));
        panel.add(confirmPasswordField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Change Password", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String currentPassword = new String(currentPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            
            if (newPassword.equals(confirmPassword)) {
                if (parent.getBankingService().changePassword(currentPassword, newPassword)) {
                    JOptionPane.showMessageDialog(this, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Password change failed. Check current password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "New passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showDeleteAccountDialog() {
        int result = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete your account?\nThis action cannot be undone!",
            "Delete Account", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (result == JOptionPane.YES_OPTION) {
            String password = JOptionPane.showInputDialog(this, "Enter your password to confirm:", "Confirm Deletion", JOptionPane.QUESTION_MESSAGE);
            if (password != null) {
                if (parent.getBankingService().deleteAccount(password)) {
                    JOptionPane.showMessageDialog(this, "Account deleted successfully.", "Account Deleted", JOptionPane.INFORMATION_MESSAGE);
                    parent.logout();
                } else {
                    JOptionPane.showMessageDialog(this, "Account deletion failed. Check password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
