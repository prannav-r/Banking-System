# Banking System - Java Swing GUI Application

A modern Java Swing GUI application that provides a complete banking system with MySQL database integration. This application replicates and enhances the functionality of the original Python Tkinter banking system with a professional graphical user interface.

## 🚀 Quick Start

### Prerequisites

- **Java 17** or higher
- **Maven 3.6** or higher
- **MySQL Server** 8.0 or higher

### Installation & Setup

**1. Clone/Download the Project**

```bash
git clone <repository-url>
cd Banking-System
```

**2. Database Setup**

```bash
# Option A: Use setup script (Recommended)
setup-db.bat    # Windows
./setup-db.sh   # Linux/Mac

# Option B: Manual setup
cp env.example .env
# Edit .env with your MySQL credentials
```

**3. Run the Application**

```bash
# Windows (Recommended)
run-maven.bat

# Windows (Full featured)
run.bat

# Linux/Mac
./run.sh

# Direct Maven
mvn exec:java
```

## ✨ Features

### 🎨 Modern GUI Interface

- **Professional Swing GUI** with system look and feel
- **Intuitive navigation** between login, registration, and dashboard
- **Responsive design** with proper spacing and layout
- **Real-time validation** with immediate feedback
- **Interactive dialogs** for all banking operations

### 🔐 User Authentication

- **Secure Login System** with username/password authentication
- **User Registration** with comprehensive validation
- **Username Uniqueness** checking against database
- **Password Strength** validation (6+ characters, letters + numbers)
- **Age Verification** (18+ years required)
- **Contact Number** validation (exactly 10 digits)
- **City Validation** (minimum 2 characters)

### 💰 Banking Operations

- **Deposit Money** - Add funds to your account with confirmation dialogs
- **Transfer Money** - Send money to other accounts using account numbers
- **Balance Enquiry** - View current account balance with detailed information
- **Account Details** - Complete account information in formatted display

### ⚙️ Account Management

- **Change Password** - Update password with current password verification
- **Delete Account** - Permanently remove account with password confirmation
- **Account Information** - View detailed account profile
- **Session Management** - Secure logout functionality

### 🗄️ Database Integration

- **MySQL Database** - Full database connectivity with proper SQL operations
- **Transaction Support** - ACID compliant transfer operations
- **Data Persistence** - All data stored securely in MySQL database
- **Auto Setup** - Automatic database and table creation
- **Environment Configuration** - Secure credential management

## 📁 Project Structure

```
Banking-System/
├── src/main/java/com/banking/system/
│   ├── BankingSystemSwingApplication.java # Main Swing GUI application
│   ├── LoginPanel.java                   # Login interface panel
│   ├── RegisterPanel.java                # Registration interface panel
│   ├── DashboardPanel.java               # Main dashboard panel
│   ├── model/
│   │   └── User.java                     # User entity class
│   ├── service/
│   │   └── BankingService.java           # Core banking business logic
│   ├── dao/
│   │   └── DatabaseManager.java          # Database connection management
│   ├── config/
│   │   └── DatabaseConfig.java           # Environment configuration
│   └── util/
│       └── ValidationUtil.java            # Input validation utilities
├── pom.xml                               # Maven configuration
├── run.bat / run.sh                      # Application runners
├── run-maven.bat                         # Simple Maven runner (recommended)
├── setup-db.bat / setup-db.sh            # Database setup scripts
├── env.example                           # Database config template
├── README.md                             # Project documentation
└── Main Project.py                       # Original Python version
```

## 🎯 Usage Guide

### Application Flow

1. **Launch Application** → Database connection established
2. **Login Screen** → Enter credentials or register new account
3. **Registration** → Create new account with validation
4. **Dashboard** → Access all banking operations
5. **Banking Operations** → Deposit, Transfer, Balance, Account Details, etc.
6. **Logout** → Return to login screen

### GUI Navigation

**Login Panel:**

- Enter username and password
- Click "Login" or press Enter
- Click "Register New Account" to create account

**Registration Panel:**

- Fill in all required fields
- Real-time validation feedback
- Click "Register" to create account
- Click "Back to Login" to return

**Dashboard Panel:**

- View account information and balance
- Click any operation button for banking functions
- Interactive dialogs guide you through each operation
- Click "Logout" to return to login

### Banking Operations

**Deposit Money:**

- Click "Deposit Money" button
- Enter amount in dialog
- Confirm deposit
- View updated balance

**Transfer Money:**

- Click "Transfer Money" button
- Enter recipient account number
- Enter transfer amount
- Confirm transfer
- View updated balance

**Check Balance:**

- Click "Check Balance" button
- View detailed balance information

**Account Details:**

- Click "Account Details" button
- View complete account information

**Change Password:**

- Click "Change Password" button
- Enter current password
- Enter new password twice
- Confirm password change

**Delete Account:**

- Click "Delete Account" button
- Confirm deletion
- Enter password to verify
- Account permanently deleted

## 🔧 Configuration

### Environment Variables

The application uses environment variables for database configuration:

| Variable      | Default   | Description       |
| ------------- | --------- | ----------------- |
| `DB_HOST`     | localhost | Database host     |
| `DB_PORT`     | 3306      | Database port     |
| `DB_NAME`     | bank      | Database name     |
| `DB_USERNAME` | root      | Database username |
| `DB_PASSWORD` | (empty)   | Database password |

### Setting Environment Variables

**Option 1: .env file (Recommended)**

```bash
# Copy template
cp env.example .env

# Edit .env file
DB_HOST=localhost
DB_PORT=3306
DB_NAME=bank
DB_USERNAME=root
DB_PASSWORD=your_password
```

**Option 2: Direct environment variables**

```bash
# Windows Command Prompt
set DB_PASSWORD=your_password

# Windows PowerShell
$env:DB_PASSWORD="your_password"

# Linux/Mac
export DB_PASSWORD=your_password
```

## 🗄️ Database Schema

### userdata Table

```sql
CREATE TABLE userdata (
    username VARCHAR(30) PRIMARY KEY,
    password VARCHAR(30) NOT NULL,
    name VARCHAR(30) NOT NULL,
    actno VARCHAR(5) UNIQUE NOT NULL,
    contactno VARCHAR(10) NOT NULL,
    age INT NOT NULL,
    city VARCHAR(10) NOT NULL,
    balance DOUBLE DEFAULT 5000.0
);
```

## 🏗️ Architecture

### Layered Architecture

- **Presentation Layer**: Swing GUI panels (LoginPanel, RegisterPanel, DashboardPanel)
- **Application Layer**: BankingSystemSwingApplication (main GUI controller)
- **Service Layer**: BankingService (business logic)
- **Data Access Layer**: DatabaseManager (database operations)
- **Model Layer**: User (data models)
- **Utility Layer**: ValidationUtil (input validation)

### Design Patterns

- **MVC Pattern**: Model-View-Controller separation
- **DAO Pattern**: Database access abstraction
- **Service Layer Pattern**: Business logic separation
- **Builder Pattern**: User object construction
- **Validation Pattern**: Input validation utilities

## 🧪 Testing

### Running Tests

```bash
# Run unit tests
mvn test

# Run specific test class
mvn test -Dtest=BankingServiceTest
```

### Manual Testing

1. **Database Connection**: Verify environment variables are loaded
2. **User Registration**: Test all validation rules
3. **User Login**: Test authentication
4. **Banking Operations**: Test deposit, transfer, balance
5. **Account Management**: Test password change, account deletion

## 📦 Building & Deployment

### Building Executable JAR

```bash
mvn clean package
```

The JAR file will be created in the `target/` directory.

### Running JAR File

```bash
java -jar target/banking-system-1.0.0.jar
```

## 🐛 Troubleshooting

### Common Issues

**Database Connection Error**

- Ensure MySQL server is running
- Check environment variables in `.env` file
- Verify MySQL credentials and port (default: 3306)
- Test connection: `mysql -h localhost -u root -p`

**Environment Variables Not Loading**

- Ensure `.env` file is in project root
- Check `.env` format (no spaces around `=`)
- Verify variable names match exactly
- Try setting variables directly in terminal

**Compilation Errors**

- Ensure Java 17+ is installed: `java -version`
- Check Maven installation: `mvn -version`
- Verify all dependencies: `mvn dependency:resolve`

**GUI Not Displaying**

- Check if display is available (for headless systems)
- Verify Java Swing is supported
- Try running with: `java -Djava.awt.headless=false`

**Runtime Errors**

- Check database permissions
- Verify table creation permissions
- Ensure sufficient disk space
- Check Java memory settings

## 🔄 Migration from Python

This Java Swing version improves upon the original Python Tkinter application:

| Feature                 | Python Version   | Java Swing Version      |
| ----------------------- | ---------------- | ----------------------- |
| **UI Framework**        | Tkinter GUI      | Java Swing GUI          |
| **Architecture**        | Monolithic       | Layered Architecture    |
| **Type Safety**         | Dynamic          | Static Typing           |
| **Validation**          | Basic            | Comprehensive           |
| **Error Handling**      | Basic            | Exception Handling      |
| **Database**            | MySQL            | MySQL with Transactions |
| **Session Management**  | Global Variables | Service Layer           |
| **User Experience**     | Basic Forms      | Professional GUI        |
| **Validation Feedback** | Console Output   | Real-time GUI Feedback  |

## 📝 Development Scripts

- `run-maven.bat` - **Recommended** Windows Maven runner with environment variable loading
- `run.bat` / `run.sh` - Full featured application runners
- `setup-db.bat` / `setup-db.sh` - Interactive database setup

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is open source and available under the MIT License.

## ⚠️ Security Notice

This is a demonstration banking system. In production, implement additional security measures including:

- Password encryption/hashing
- SSL/TLS encryption
- Audit logging
- Rate limiting
- Input sanitization
- Session management
- Compliance features (PCI DSS, etc.)

---

**Built with ❤️ using Java Swing and MySQL**
