package com.banking.system;

import com.banking.system.model.User;
import com.banking.system.service.BankingService;
import com.banking.system.util.ValidationUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main Swing GUI Application for Banking System
 */
public class BankingSystemSwingApplication extends JFrame {
    
    private BankingService bankingService;
    private User currentUser;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    // Panel references
    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;
    private DashboardPanel dashboardPanel;
    
    public BankingSystemSwingApplication() {
        this.bankingService = new BankingService();
        initializeGUI();
    }
    
    private void initializeGUI() {
        setTitle("Banking System - Java Swing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create main panel with card layout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Create panels
        loginPanel = new LoginPanel(this);
        registerPanel = new RegisterPanel(this);
        dashboardPanel = new DashboardPanel(this);
        
        // Add panels to main panel
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(registerPanel, "REGISTER");
        mainPanel.add(dashboardPanel, "DASHBOARD");
        
        add(mainPanel);
        
        // Show login panel initially
        showLoginPanel();
    }
    
    public void showLoginPanel() {
        cardLayout.show(mainPanel, "LOGIN");
        loginPanel.clearFields();
    }
    
    public void showRegisterPanel() {
        cardLayout.show(mainPanel, "REGISTER");
        registerPanel.clearFields();
    }
    
    public void showDashboardPanel() {
        cardLayout.show(mainPanel, "DASHBOARD");
        dashboardPanel.refreshData();
    }
    
    public boolean login(String username, String password) {
        User user = bankingService.login(username, password);
        if (user != null) {
            currentUser = user;
            return true;
        }
        return false;
    }
    
    public boolean register(User user) {
        return bankingService.register(user);
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public BankingService getBankingService() {
        return bankingService;
    }
    
    public void logout() {
        currentUser = null;
        showLoginPanel();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BankingSystemSwingApplication().setVisible(true);
        });
    }
}
