package com.banking.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Login Panel for Banking System
 */
public class LoginPanel extends JPanel {
    
    private BankingSystemSwingApplication parent;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel statusLabel;
    
    public LoginPanel(BankingSystemSwingApplication parent) {
        this.parent = parent;
        initializeComponents();
        layoutComponents();
        attachEventHandlers();
    }
    
    private void initializeComponents() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        registerButton = new JButton("Register New Account");
        statusLabel = new JLabel(" ");
        
        // Style components
        loginButton.setPreferredSize(new Dimension(120, 30));
        registerButton.setPreferredSize(new Dimension(180, 30));
        statusLabel.setForeground(Color.RED);
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout());
        
        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        // Title
        JLabel titleLabel = new JLabel("Banking System Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(titleLabel, gbc);
        
        // Username
        gbc.gridwidth = 1; gbc.insets = new Insets(5, 0, 5, 10);
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(usernameField, gbc);
        
        // Password
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(passwordField, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);
        mainPanel.add(loginButton, gbc);
        
        gbc.gridx = 1; gbc.gridy = 3;
        mainPanel.add(registerButton, gbc);
        
        // Status label
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        mainPanel.add(statusLabel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void attachEventHandlers() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
        
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.showRegisterPanel();
            }
        });
        
        // Enter key support
        KeyStroke enterKey = KeyStroke.getKeyStroke("ENTER");
        usernameField.getInputMap().put(enterKey, "login");
        usernameField.getActionMap().put("login", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
        
        passwordField.getInputMap().put(enterKey, "login");
        passwordField.getActionMap().put("login", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
    }
    
    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter both username and password");
            return;
        }
        
        if (parent.login(username, password)) {
            statusLabel.setText("Login successful!");
            parent.showDashboardPanel();
        } else {
            statusLabel.setText("Invalid username or password");
            passwordField.setText("");
        }
    }
    
    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        statusLabel.setText(" ");
    }
}
