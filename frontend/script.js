// Banking System Frontend JavaScript
class BankingSystem {
  constructor() {
    this.currentUser = null;
    this.init();
  }

  // Initialize the application
  init() {
    this.setupEventListeners();
    this.checkLoginStatus();
  }

  // Setup event listeners
  setupEventListeners() {
    // Login form
    document.getElementById("loginForm").addEventListener("submit", (e) => {
      e.preventDefault();
      this.handleLogin();
    });

    // Register form
    document.getElementById("registerForm").addEventListener("submit", (e) => {
      e.preventDefault();
      this.handleRegister();
    });

    // Deposit form
    document.getElementById("depositForm").addEventListener("submit", (e) => {
      e.preventDefault();
      this.handleDeposit();
    });

    // Transfer form
    document.getElementById("transferForm").addEventListener("submit", (e) => {
      e.preventDefault();
      this.handleTransfer();
    });

    // Change password form
    document
      .getElementById("changePasswordForm")
      .addEventListener("submit", (e) => {
        e.preventDefault();
        this.handleChangePassword();
      });

    // Delete account form
    document
      .getElementById("deleteAccountForm")
      .addEventListener("submit", (e) => {
        e.preventDefault();
        this.handleDeleteAccount();
      });

    // Close modals when clicking outside
    window.addEventListener("click", (e) => {
      if (e.target.classList.contains("modal")) {
        this.closeModal(e.target.id);
      }
    });

    // Real-time validation
    this.setupRealTimeValidation();
  }

  // Setup real-time validation
  setupRealTimeValidation() {
    // Username validation
    document.getElementById("regUsername").addEventListener("input", (e) => {
      this.validateField(e.target, this.validateUsername(e.target.value));
    });

    // Password validation
    document.getElementById("regPassword").addEventListener("input", (e) => {
      this.validateField(e.target, this.validatePassword(e.target.value));
    });

    // Name validation
    document.getElementById("regFullName").addEventListener("input", (e) => {
      this.validateField(e.target, this.validateName(e.target.value));
    });

    // Age validation
    document.getElementById("regAge").addEventListener("input", (e) => {
      this.validateField(e.target, this.validateAge(parseInt(e.target.value)));
    });

    // Contact validation
    document.getElementById("regContact").addEventListener("input", (e) => {
      this.validateField(e.target, this.validateContactNumber(e.target.value));
    });

    // City validation
    document.getElementById("regCity").addEventListener("input", (e) => {
      this.validateField(e.target, this.validateCity(e.target.value));
    });
  }

  // Validation methods
  validateUsername(username) {
    if (!username || username.trim().length < 3) {
      return {
        valid: false,
        message: "Username must be at least 3 characters long",
      };
    }
    if (username.length > 30) {
      return { valid: false, message: "Username cannot exceed 30 characters" };
    }
    if (!username.match(/^[a-zA-Z0-9_]+$/)) {
      return {
        valid: false,
        message: "Username can only contain letters, numbers, and underscores",
      };
    }
    return { valid: true, message: "Username is valid" };
  }

  validatePassword(password) {
    if (!password || password.length < 6) {
      return {
        valid: false,
        message: "Password must be at least 6 characters long",
      };
    }
    if (!password.match(/.*[a-zA-Z].*/)) {
      return {
        valid: false,
        message: "Password must contain at least one letter",
      };
    }
    if (!password.match(/.*[0-9].*/)) {
      return {
        valid: false,
        message: "Password must contain at least one number",
      };
    }
    return { valid: true, message: "Password is valid" };
  }

  validateName(name) {
    if (!name || name.trim().length < 3) {
      return {
        valid: false,
        message: "Name must be at least 3 characters long",
      };
    }
    if (!name.match(/^[a-zA-Z\s]+$/)) {
      return {
        valid: false,
        message: "Name can only contain letters and spaces",
      };
    }
    return { valid: true, message: "Name is valid" };
  }

  validateAge(age) {
    if (isNaN(age) || age < 18) {
      return { valid: false, message: "Age must be at least 18 years old" };
    }
    if (age > 120) {
      return { valid: false, message: "Age cannot exceed 120 years" };
    }
    return { valid: true, message: "Age is valid" };
  }

  validateContactNumber(contact) {
    const cleanContact = contact.replace(/[\s-]/g, "");
    if (!cleanContact.match(/^[0-9]{10}$/)) {
      return {
        valid: false,
        message: "Contact number must be exactly 10 digits",
      };
    }
    return { valid: true, message: "Contact number is valid" };
  }

  validateCity(city) {
    if (!city || city.trim().length < 2) {
      return {
        valid: false,
        message: "City must be at least 2 characters long",
      };
    }
    if (!city.match(/^[a-zA-Z\s]+$/)) {
      return {
        valid: false,
        message: "City can only contain letters and spaces",
      };
    }
    return { valid: true, message: "City is valid" };
  }

  // Validate field and show feedback
  validateField(field, result) {
    const helpText = field.parentNode.querySelector(".help-text");
    if (helpText) {
      helpText.textContent = result.message;
      helpText.style.color = result.valid ? "#28a745" : "#dc3545";
    }

    field.style.borderColor = result.valid ? "#28a745" : "#dc3545";
  }

  // Check if user is already logged in
  checkLoginStatus() {
    const loggedInUser = localStorage.getItem("currentUser");
    if (loggedInUser) {
      this.currentUser = JSON.parse(loggedInUser);
      this.showDashboard();
    }
  }

  // Handle user login
  async handleLogin() {
    const username = document.getElementById("loginUsername").value;
    const password = document.getElementById("loginPassword").value;

    try {
      const response = await fetch("http://localhost:8080/api/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });

      const data = await response.json();

      if (data.success) {
        this.currentUser = data.data.user;
        localStorage.setItem("currentUser", JSON.stringify(data.data.user));
        this.showNotification("Login successful!", "success");
        this.showDashboard();
      } else {
        this.showNotification(data.message || "Login failed!", "error");
      }
    } catch (error) {
      console.error("Login error:", error);
      this.showNotification("Login failed! Please try again.", "error");
    }
  }

  // Handle user registration
  async handleRegister() {
    const formData = {
      username: document.getElementById("regUsername").value,
      password: document.getElementById("regPassword").value,
      fullName: document.getElementById("regFullName").value,
      age: parseInt(document.getElementById("regAge").value),
      contactNo: document.getElementById("regContact").value,
      city: document.getElementById("regCity").value,
    };

    // Validate all fields
    const validations = [
      this.validateUsername(formData.username),
      this.validatePassword(formData.password),
      this.validateName(formData.fullName),
      this.validateAge(formData.age),
      this.validateContactNumber(formData.contactNo),
      this.validateCity(formData.city),
    ];

    const invalidFields = validations.filter((v) => !v.valid);
    if (invalidFields.length > 0) {
      this.showNotification(invalidFields[0].message, "error");
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/api/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      const data = await response.json();

      if (data.success) {
        this.showNotification("Account created successfully!", "success");
        this.showLogin();
        this.clearRegisterForm();
      } else {
        this.showNotification(data.message || "Registration failed!", "error");
      }
    } catch (error) {
      console.error("Registration error:", error);
      this.showNotification("Registration failed! Please try again.", "error");
    }
  }

  // Handle deposit
  async handleDeposit() {
    const amount = parseFloat(document.getElementById("depositAmount").value);

    if (amount <= 0) {
      this.showNotification("Please enter a valid amount!", "error");
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:8080/api/deposit?username=${this.currentUser.username}&amount=${amount}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      const data = await response.json();

      if (data.success) {
        this.currentUser.balance = data.data.newBalance;
        this.updateUser();
        this.updateBalanceDisplay();
        this.closeModal("depositModal");
        this.showNotification(
          `Amount ₹${amount} has been deposited successfully!`,
          "success"
        );
        document.getElementById("depositForm").reset();
      } else {
        this.showNotification(data.message || "Deposit failed!", "error");
      }
    } catch (error) {
      console.error("Deposit error:", error);
      this.showNotification("Deposit failed! Please try again.", "error");
    }
  }

  // Handle transfer
  async handleTransfer() {
    const accountNumber = document.getElementById("transferAccount").value;
    const amount = parseFloat(document.getElementById("transferAmount").value);

    if (amount <= 0) {
      this.showNotification("Please enter a valid amount!", "error");
      return;
    }

    if (amount > this.currentUser.balance) {
      this.showNotification("Insufficient balance!", "error");
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:8080/api/transfer?username=${this.currentUser.username}&accountNumber=${accountNumber}&amount=${amount}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      const data = await response.json();

      if (data.success) {
        this.currentUser.balance = data.data.newBalance;
        this.updateUser();
        this.updateBalanceDisplay();
        this.closeModal("transferModal");
        this.showNotification(
          `Amount ₹${amount} has been transferred successfully!`,
          "success"
        );
        document.getElementById("transferForm").reset();
      } else {
        this.showNotification(data.message || "Transfer failed!", "error");
      }
    } catch (error) {
      console.error("Transfer error:", error);
      this.showNotification("Transfer failed! Please try again.", "error");
    }
  }

  // Handle change password
  async handleChangePassword() {
    const currentPassword = document.getElementById("currentPassword").value;
    const newPassword = document.getElementById("newPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    const passwordValidation = this.validatePassword(newPassword);
    if (!passwordValidation.valid) {
      this.showNotification(passwordValidation.message, "error");
      return;
    }

    if (newPassword !== confirmPassword) {
      this.showNotification("New passwords don't match!", "error");
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:8080/api/change-password?username=${this.currentUser.username}&currentPassword=${currentPassword}&newPassword=${newPassword}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      const data = await response.json();

      if (data.success) {
        this.currentUser.password = newPassword;
        this.updateUser();
        this.closeModal("changePasswordModal");
        this.showNotification("Password changed successfully!", "success");
        document.getElementById("changePasswordForm").reset();
      } else {
        this.showNotification(
          data.message || "Password change failed!",
          "error"
        );
      }
    } catch (error) {
      console.error("Password change error:", error);
      this.showNotification(
        "Password change failed! Please try again.",
        "error"
      );
    }
  }

  // Handle delete account
  async handleDeleteAccount() {
    const password = document.getElementById("deletePassword").value;

    try {
      const response = await fetch(
        `http://localhost:8080/api/delete-account?username=${this.currentUser.username}&password=${password}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      const data = await response.json();

      if (data.success) {
        this.currentUser = null;
        localStorage.removeItem("currentUser");
        this.closeModal("deleteAccountModal");
        this.showNotification(
          "Your account has been successfully deleted!",
          "success"
        );
        this.showMainEntry();
      } else {
        this.showNotification(
          data.message || "Account deletion failed!",
          "error"
        );
      }
    } catch (error) {
      console.error("Account deletion error:", error);
      this.showNotification(
        "Account deletion failed! Please try again.",
        "error"
      );
    }
  }

  // Update user in localStorage
  updateUser() {
    localStorage.setItem("currentUser", JSON.stringify(this.currentUser));
  }

  // Show dashboard
  showDashboard() {
    this.hideAllPages();
    document.getElementById("dashboardPage").classList.add("active");
    document.getElementById("currentUser").textContent =
      this.currentUser.username;
    this.updateBalanceDisplay();
  }

  // Show main entry page
  showMainEntry() {
    this.hideAllPages();
    document.getElementById("mainEntryPage").classList.add("active");
  }

  // Show login page
  showLogin() {
    this.hideAllPages();
    document.getElementById("loginPage").classList.add("active");
  }

  // Show register page
  showRegister() {
    this.hideAllPages();
    document.getElementById("registerPage").classList.add("active");
  }

  // Hide all pages
  hideAllPages() {
    document.querySelectorAll(".page").forEach((page) => {
      page.classList.remove("active");
    });
  }

  // Show modal
  showModal(modalId) {
    document.getElementById(modalId).classList.add("active");
  }

  // Close modal
  closeModal(modalId) {
    document.getElementById(modalId).classList.remove("active");
  }

  // Update balance display
  updateBalanceDisplay() {
    document.getElementById(
      "currentBalance"
    ).textContent = `₹${this.currentUser.balance.toLocaleString()}`;
  }

  // Show balance enquiry
  showBalance() {
    this.showNotification(
      `Your current balance is: ₹${this.currentUser.balance.toLocaleString()}`,
      "info"
    );
  }

  // Show account details
  showAccountDetails() {
    const detailsHtml = `
            <div class="account-details">
                <h3>Account Information</h3>
                <table class="account-details-table">
                    <tr>
                        <th>Field</th>
                        <th>Value</th>
                    </tr>
                    <tr>
                        <td>Username</td>
                        <td>${this.currentUser.username}</td>
                    </tr>
                    <tr>
                        <td>Full Name</td>
                        <td>${this.currentUser.fullName}</td>
                    </tr>
                    <tr>
                        <td>Account Number</td>
                        <td>${this.currentUser.accountNumber}</td>
                    </tr>
                    <tr>
                        <td>Contact Number</td>
                        <td>${this.currentUser.contactNo}</td>
                    </tr>
                    <tr>
                        <td>Age</td>
                        <td>${this.currentUser.age}</td>
                    </tr>
                    <tr>
                        <td>City</td>
                        <td>${this.currentUser.city}</td>
                    </tr>
                    <tr>
                        <td>Balance</td>
                        <td>₹${this.currentUser.balance.toLocaleString()}</td>
                    </tr>
                </table>
            </div>
        `;

    document.getElementById("accountDetailsContent").innerHTML = detailsHtml;
    this.showModal("accountDetailsModal");
  }

  // Clear register form
  clearRegisterForm() {
    document.getElementById("registerForm").reset();
    // Clear validation feedback
    document.querySelectorAll("#registerForm .help-text").forEach((help) => {
      help.textContent = "";
      help.style.color = "#7f8c8d";
    });
    document.querySelectorAll("#registerForm input").forEach((input) => {
      input.style.borderColor = "#e1e8ed";
    });
  }

  // Show notification
  showNotification(message, type = "info") {
    const notification = document.getElementById("notification");
    notification.textContent = message;
    notification.className = `notification ${type}`;
    notification.classList.add("show");

    setTimeout(() => {
      notification.classList.remove("show");
    }, 3000);
  }

  // Logout
  logout() {
    this.currentUser = null;
    localStorage.removeItem("currentUser");
    this.showMainEntry();
    this.showNotification("Logged out successfully!", "info");
  }
}

// Global functions for HTML onclick events
function showMainEntry() {
  bankingSystem.showMainEntry();
}

function showLogin() {
  bankingSystem.showLogin();
}

function showRegister() {
  bankingSystem.showRegister();
}

function logout() {
  bankingSystem.logout();
}

function showDeposit() {
  bankingSystem.showModal("depositModal");
}

function showTransfer() {
  bankingSystem.showModal("transferModal");
}

function showChangePassword() {
  bankingSystem.showModal("changePasswordModal");
}

function showDeleteAccount() {
  bankingSystem.showModal("deleteAccountModal");
}

function showBalance() {
  bankingSystem.showBalance();
}

function showAccountDetails() {
  bankingSystem.showAccountDetails();
}

function closeModal(modalId) {
  bankingSystem.closeModal(modalId);
}

// Initialize the banking system when the page loads
let bankingSystem;
document.addEventListener("DOMContentLoaded", () => {
  bankingSystem = new BankingSystem();
});
