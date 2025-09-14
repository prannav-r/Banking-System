package com.banking.system;

import com.banking.system.model.User;
import com.banking.system.util.ValidationUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Registration Panel for Banking System
 */
public class RegisterPanel extends JPanel {
    
    private BankingSystemSwingApplication parent;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField contactField;
    private JTextField cityField;
    private JButton registerButton;
    private JButton backButton;
    private JLabel statusLabel;
    
    public RegisterPanel(BankingSystemSwingApplication parent) {
        this.parent = parent;
        initializeComponents();
        layoutComponents();
        attachEventHandlers();
    }
    
    private void initializeComponents() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        nameField = new JTextField(20);
        ageField = new JTextField(20);
        contactField = new JTextField(20);
        cityField = new JTextField(20);
        registerButton = new JButton("Register");
        backButton = new JButton("Back to Login");
        statusLabel = new JLabel(" ");
        
        // Style components
        registerButton.setPreferredSize(new Dimension(120, 30));
        backButton.setPreferredSize(new Dimension(150, 30));
        statusLabel.setForeground(Color.RED);
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout());
        
        // Scrollable panel for form
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // Title
        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        formPanel.add(titleLabel, gbc);
        
        // Form fields
        String[] labels = {"Username:", "Password:", "Confirm Password:", "Full Name:", "Age:", "Contact Number:", "City:"};
        JTextField[] fields = {usernameField, passwordField, confirmPasswordField, nameField, ageField, contactField, cityField};
        
        for (int i = 0; i < labels.length; i++) {
            gbc.gridwidth = 1; gbc.insets = new Insets(5, 0, 5, 10);
            gbc.gridx = 0; gbc.gridy = i + 1; gbc.anchor = GridBagConstraints.EAST;
            formPanel.add(new JLabel(labels[i]), gbc);
            
            gbc.gridx = 1; gbc.gridy = i + 1; gbc.anchor = GridBagConstraints.WEST;
            formPanel.add(fields[i], gbc);
        }
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = labels.length + 1; gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);
        formPanel.add(registerButton, gbc);
        
        gbc.gridx = 1; gbc.gridy = labels.length + 1;
        formPanel.add(backButton, gbc);
        
        // Status label
        gbc.gridx = 0; gbc.gridy = labels.length + 2; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        formPanel.add(statusLabel, gbc);
        
        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void attachEventHandlers() {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRegistration();
            }
        });
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.showLoginPanel();
            }
        });
    }
    
    private void performRegistration() {
        // Get form data
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String name = nameField.getText().trim();
        String ageStr = ageField.getText().trim();
        String contact = contactField.getText().trim();
        String city = cityField.getText().trim();
        
        // Validate required fields
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || 
            name.isEmpty() || ageStr.isEmpty() || contact.isEmpty() || city.isEmpty()) {
            statusLabel.setText("Please fill in all fields");
            return;
        }
        
        // Validate password confirmation
        if (!password.equals(confirmPassword)) {
            statusLabel.setText("Passwords do not match");
            passwordField.setText("");
            confirmPasswordField.setText("");
            return;
        }
        
        // Validate age
        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            statusLabel.setText("Please enter a valid age");
            return;
        }
        
        // Use validation utility
        ValidationUtil.ValidationResult usernameResult = ValidationUtil.validateUsername(username);
        if (!usernameResult.isValid()) {
            statusLabel.setText(usernameResult.getMessage());
            return;
        }
        
        ValidationUtil.ValidationResult passwordResult = ValidationUtil.validatePassword(password);
        if (!passwordResult.isValid()) {
            statusLabel.setText(passwordResult.getMessage());
            passwordField.setText("");
            confirmPasswordField.setText("");
            return;
        }
        
        ValidationUtil.ValidationResult nameResult = ValidationUtil.validateName(name);
        if (!nameResult.isValid()) {
            statusLabel.setText(nameResult.getMessage());
            return;
        }
        
        ValidationUtil.ValidationResult ageResult = ValidationUtil.validateAge(age);
        if (!ageResult.isValid()) {
            statusLabel.setText(ageResult.getMessage());
            return;
        }
        
        ValidationUtil.ValidationResult contactResult = ValidationUtil.validateContactNumber(contact);
        if (!contactResult.isValid()) {
            statusLabel.setText(contactResult.getMessage());
            return;
        }
        
        ValidationUtil.ValidationResult cityResult = ValidationUtil.validateCity(city);
        if (!cityResult.isValid()) {
            statusLabel.setText(cityResult.getMessage());
            return;
        }
        
        // Check if username already exists
        if (parent.getBankingService().userExists(username)) {
            statusLabel.setText("Username already exists. Please choose a different username.");
            return;
        }
        
        // Create user object
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setAge(age);
        user.setContactNumber(contact);
        user.setCity(city);
        
        // Register user
        if (parent.register(user)) {
            statusLabel.setText("Registration successful! Please login with your credentials.");
            statusLabel.setForeground(Color.GREEN);
            // Clear fields after successful registration
            clearFields();
        } else {
            statusLabel.setText("Registration failed. Please try again.");
            statusLabel.setForeground(Color.RED);
        }
    }
    
    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        nameField.setText("");
        ageField.setText("");
        contactField.setText("");
        cityField.setText("");
        statusLabel.setText(" ");
        statusLabel.setForeground(Color.RED);
    }
}
