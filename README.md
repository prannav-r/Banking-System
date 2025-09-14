# Banking System - Java Console Application

A complete Java console application that replicates the functionality of the original Python Tkinter banking system with MySQL database integration.

## 🚀 Quick Start

```bash
# 1. Setup database credentials
cp env.example .env
# Edit .env with your MySQL credentials

# 2. Run the application
./run.sh          # Linux/Mac
run.bat           # Windows
# Or: mvn exec:java
```

## ✨ Features

### 🔐 User Authentication

- **Separate Login/Register** options with comprehensive validation
- **Username uniqueness** checking
- **Password strength** validation (6+ chars, letters + numbers)
- **Age verification** (18+ years required)
- **Contact number** validation (exactly 10 digits)

### 💰 Banking Operations

- **Deposit Amount**: Add money to your account
- **Transfer Amount**: Send money to other accounts using account numbers
- **Balance Enquiry**: Check current account balance
- **Account Details**: View complete account information in formatted table

### ⚙️ Account Management

- **Change Password**: Update account password with verification
- **Delete Account**: Permanently remove account with password confirmation
- **Logout**: Return to main entry menu

### 🗄️ Database Integration

- **MySQL Database**: Full database connectivity with proper SQL operations
- **Transaction Support**: ACID compliant transfer operations
- **Data Persistence**: All data stored in MySQL database
- **Auto Setup**: Automatic database and table creation

## 📁 Project Structure

```
Banking-System/
├── src/main/java/com/banking/system/
│   ├── BankingSystemApplication.java    # Main console application
│   ├── model/User.java                  # User entity class
│   ├── service/BankingService.java       # Core banking operations
│   ├── dao/DatabaseManager.java         # Database connection management
│   ├── config/DatabaseConfig.java       # Environment configuration
│   └── util/ValidationUtil.java         # Input validation utilities
├── pom.xml                              # Maven configuration
├── run.bat / run.sh                     # Console startup scripts
├── setup-db.bat / setup-db.sh           # Database setup scripts
├── env.example                          # Database config template
├── README.md                            # Project documentation
└── Main Project.py                      # Original Python version
```

## 🛠️ Prerequisites

- **Java 17** or higher
- **Maven 3.6** or higher
- **MySQL Server** 8.0 or higher

## 📋 Installation & Setup

### 1. Database Setup

**Option A: Use the setup script (Recommended)**

```bash
# Linux/Mac
chmod +x setup-db.sh
./setup-db.sh

# Windows
setup-db.bat
```

**Option B: Manual setup**

```bash
# Copy environment template
cp env.example .env

# Edit .env file with your MySQL credentials
DB_HOST=localhost
DB_PORT=3306
DB_NAME=bank
DB_USERNAME=root
DB_PASSWORD=your_password
```

### 2. Build & Run

```bash
# Build the project
mvn clean compile

# Run the application
mvn exec:java

# Or use the convenient scripts
./run.sh          # Linux/Mac
run.bat           # Windows
```

## 🎯 Usage Guide

### Application Flow

1. **Start Application** → Database connection established
2. **Main Entry Menu** → Choose LOGIN or REGISTER
3. **Authentication** → Login with credentials or register new account
4. **Main Menu** → Access banking operations
5. **Operations** → Deposit, Transfer, Balance, Account Details, etc.
6. **Logout** → Return to entry menu

### Registration Validation Rules

- **Username**: Must be unique (checked against database)
- **Password**: Minimum 6 characters, must contain letters and numbers
- **Name**: Minimum 3 characters
- **Age**: Must be 18 years or older
- **Contact Number**: Exactly 10 digits
- **City**: Minimum 2 characters

### Banking Operations

Once logged in, you can:

- **Deposit**: Add money to your account
- **Transfer**: Send money to other accounts (requires recipient account number)
- **Balance**: View current balance
- **Account Details**: See complete account information in table format
- **Change Password**: Update password with current password verification
- **Delete Account**: Permanently remove account (requires password confirmation)
- **Logout**: Return to main entry menu

## 🗄️ Database Schema

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

## 🔧 Environment Configuration

The application uses environment variables for database configuration:

| Variable      | Default   | Description       |
| ------------- | --------- | ----------------- |
| `DB_HOST`     | localhost | Database host     |
| `DB_PORT`     | 3306      | Database port     |
| `DB_NAME`     | bank      | Database name     |
| `DB_USERNAME` | root      | Database username |
| `DB_PASSWORD` | (empty)   | Database password |

**Setting Environment Variables:**

```bash
# Option 1: .env file (recommended)
cp env.example .env
# Edit .env with your credentials

# Option 2: Direct environment variables
export DB_PASSWORD=your_password    # Linux/Mac
set DB_PASSWORD=your_password      # Windows
```

## 🏗️ Architecture

### Layered Architecture

- **Presentation Layer**: `BankingSystemApplication.java` (Console UI)
- **Service Layer**: `BankingService.java` (Business Logic)
- **Data Access Layer**: `DatabaseManager.java` (Database Operations)
- **Model Layer**: `User.java` (Data Models)
- **Utility Layer**: `ValidationUtil.java` (Input Validation)

### Key Design Patterns

- **DAO Pattern**: Database access abstraction
- **Service Layer Pattern**: Business logic separation
- **Builder Pattern**: User object construction
- **Validation Pattern**: Input validation utilities

## 🧪 Testing

```bash
# Run tests
mvn test

# Test validation rules
./test-validation.bat    # Windows
./test-validation.sh     # Linux/Mac
```

## 📦 Building Executable JAR

```bash
mvn clean package
```

The JAR file will be created in the `target/` directory.

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

## 🔄 Migration from Python

This Java version improves upon the original Python Tkinter application:

| Feature                | Python Version   | Java Version            |
| ---------------------- | ---------------- | ----------------------- |
| **UI**                 | Tkinter GUI      | Console Interface       |
| **Architecture**       | Monolithic       | Layered Architecture    |
| **Type Safety**        | Dynamic          | Static Typing           |
| **Validation**         | Basic            | Comprehensive           |
| **Error Handling**     | Basic            | Exception Handling      |
| **Database**           | MySQL            | MySQL with Transactions |
| **Session Management** | Global Variables | Service Layer           |

## 📝 Development Scripts

- `run.bat` / `run.sh` - Start the application
- `setup-db.bat` / `setup-db.sh` - Interactive database setup

## 📄 License

This project is open source and available under the MIT License.

---

**⚠️ Security Notice**: This is a demonstration banking system. In production, implement additional security measures including encryption, audit logging, rate limiting, and compliance features.
