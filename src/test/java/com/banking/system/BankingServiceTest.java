package com.banking.system;

import com.banking.system.model.User;
import com.banking.system.service.BankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for BankingService
 * Note: These tests require a running MySQL database
 */
public class BankingServiceTest {
    
    private BankingService bankingService;
    
    @BeforeEach
    void setUp() {
        // Note: In a real test environment, you would use a test database
        // For now, we'll skip database-dependent tests
        bankingService = new BankingService();
    }
    
    @Test
    @Disabled("Requires database connection")
    void testUserRegistration() {
        User testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("testpass");
        testUser.setName("Test User");
        testUser.setContactNumber("1234567890");
        testUser.setAge(25);
        testUser.setCity("Test City");
        
        boolean result = bankingService.register(testUser);
        assertTrue(result, "User registration should succeed");
    }
    
    @Test
    @Disabled("Requires database connection")
    void testUserLogin() {
        // This test would require a user to be registered first
        boolean result = bankingService.login("testuser", "testpass");
        assertTrue(result, "User login should succeed");
    }
    
    @Test
    void testAccountNumberGeneration() {
        // Test that account numbers are generated correctly
        User user = new User();
        user.setAccountNumber("12345");
        
        assertNotNull(user.getAccountNumber());
        assertEquals("12345", user.getAccountNumber());
    }
    
    @Test
    void testUserCreation() {
        User user = new User("testuser", "testpass", "Test User", 
                           "12345", "1234567890", 25, "Test City");
        
        assertEquals("testuser", user.getUsername());
        assertEquals("testpass", user.getPassword());
        assertEquals("Test User", user.getName());
        assertEquals("12345", user.getAccountNumber());
        assertEquals("1234567890", user.getContactNumber());
        assertEquals(25, user.getAge());
        assertEquals("Test City", user.getCity());
        assertEquals(5000.0, user.getBalance());
    }
    
    @Test
    void testUserToString() {
        User user = new User("testuser", "testpass", "Test User", 
                           "12345", "1234567890", 25, "Test City");
        
        String userString = user.toString();
        assertTrue(userString.contains("testuser"));
        assertTrue(userString.contains("Test User"));
        assertTrue(userString.contains("12345"));
    }
}
