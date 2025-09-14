# Banking System - Java Console Application

A complete Java console application that replicates the functionality of the original Python Tkinter banking system with MySQL database integration.

## Features

### 🔐 User Authentication

- **Login**: Secure user authentication with username and password
- **Registration**: Create new accounts with complete user information
- **Session Management**: Maintains user session throughout the application

### 💰 Banking Operations

- **Deposit Amount**: Add money to your account
- **Balance Enquiry**: Check current account balance
- **Transfer Amount**: Send money to other accounts using account numbers
- **Account Details**: View complete account information in formatted table

### ⚙️ Account Management

- **Change Password**: Update account password with verification
- **Delete Account**: Permanently remove account with password confirmation
- **Account Information**: View detailed account profile

### 🗄️ Database Integration

- **MySQL Database**: Full database connectivity with proper SQL operations
- **Transaction Support**: ACID compliant transfer operations
- **Data Persistence**: All data stored in MySQL database
- **Table Management**: Automatic database and table creation

## Project Structure

```
src/main/java/com/banking/system/
├── BankingSystemApplication.java    # Main application entry point
├── model/
│   └── User.java                   # User entity class
├── service/
│   └── BankingService.java         # Core banking operations
└── dao/
    └── DatabaseManager.java         # Database connection management
```

## Prerequisites

- **Java 17** or higher
- **Maven 3.6** or higher
- **MySQL Server** 8.0 or higher
- **MySQL Connector/J** (included in dependencies)

## Installation & Setup

### 1. Clone/Download the Project

```bash
git clone <repository-url>
cd Banking-System
```

### 2. Database Setup

1. Install MySQL Server
2. Start MySQL service
3. Configure database connection using environment variables (see below)
4. Note: The application will create the 'bank' database and 'userdata' table automatically

#### Quick Database Setup

**Option A: Use the setup script (Recommended)**

```bash
# Linux/Mac
chmod +x setup-db.sh
./setup-db.sh

# Windows
setup-db.bat
```

**Option B: Manual setup**

1. Copy the environment template: `cp env.example .env`
2. Edit `.env` file with your MySQL credentials
3. Run the application

#### Environment Variables Configuration

The application uses environment variables for database configuration. You can set them in several ways:

**Option 1: Using .env file (Recommended)**

```bash
# Copy the example file
cp env.example .env

# Edit .env file with your database credentials
DB_HOST=localhost
DB_PORT=3306
DB_NAME=bank
DB_USERNAME=root
DB_PASSWORD=your_password
```

**Option 2: Set environment variables directly**

```bash
# Linux/Mac
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=bank
export DB_USERNAME=root
export DB_PASSWORD=your_password

# Windows
set DB_HOST=localhost
set DB_PORT=3306
set DB_NAME=bank
set DB_USERNAME=root
set DB_PASSWORD=your_password
```

**Option 3: Use default values**
If no environment variables are set, the application will use these defaults:

- Host: localhost
- Port: 3306
- Database: bank
- Username: root
- Password: (empty)

### 3. Build the Project

```bash
mvn clean compile
```

### 4. Run the Application

```bash
mvn exec:java
```

Or compile and run manually:

```bash
mvn clean package
java -cp target/classes:target/dependency/* com.banking.system.BankingSystemApplication
```

## Usage

### Starting the Application

1. Configure your database connection using environment variables (see above)
2. Run the application using Maven or Java command
3. The application will automatically connect to the database and create tables if needed

**Quick Start:**

```bash
# Using run script (recommended)
./run.sh          # Linux/Mac
run.bat           # Windows

# Or using Maven directly
mvn exec:java
```

### Creating an Account

1. Choose "Sign Up" option (if available) or register through the service
2. Fill in all required information:
   - Username (must be unique)
   - Password
   - Full Name
   - Age
   - Contact Number
   - City
3. Account will be created with ₹5,000 initial balance

### Banking Operations

Once logged in, you can:

- **Deposit**: Add money to your account
- **Transfer**: Send money to other accounts (requires account number)
- **Check Balance**: View your current balance
- **View Details**: See complete account information
- **Change Password**: Update your password
- **Delete Account**: Permanently remove your account

## Database Schema

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

## Features Comparison

| Feature         | Python Version    | Java Version            |
| --------------- | ----------------- | ----------------------- |
| Login/Signup    | ✅ Tkinter GUI    | ✅ Console Interface    |
| Deposit         | ✅ Console Input  | ✅ Console Input        |
| Transfer        | ✅ Console Input  | ✅ Console Input        |
| Balance Check   | ✅ Console Output | ✅ Console Output       |
| Change Password | ✅ Console Input  | ✅ Console Input        |
| Delete Account  | ✅ Console Input  | ✅ Console Input        |
| Account Details | ✅ Console Table  | ✅ Console Table        |
| Data Storage    | ✅ MySQL Database | ✅ MySQL Database       |
| UI/UX           | ✅ Desktop App    | ✅ Console App          |
| Architecture    | ✅ Monolithic     | ✅ Layered Architecture |

## Key Improvements in Java Version

1. **Better Architecture**: Layered architecture with separation of concerns
2. **Type Safety**: Strong typing with compile-time error checking
3. **Transaction Support**: ACID compliant database operations
4. **Error Handling**: Comprehensive exception handling
5. **Code Organization**: Modular design with proper package structure
6. **Database Management**: Automatic database and table creation
7. **Input Validation**: Better input validation and error messages

## Technical Details

### Dependencies

- **MySQL Connector/J 8.0.33**: Database connectivity
- **JUnit 5.9.2**: Unit testing framework
- **Maven**: Build and dependency management

### Database Operations

- **Connection Pooling**: Efficient database connection management
- **Prepared Statements**: SQL injection prevention
- **Transactions**: ACID compliant operations
- **Auto-commit Control**: Manual transaction management for transfers

### Error Handling

- **SQLException Handling**: Comprehensive database error handling
- **Input Validation**: Number format and range validation
- **User Feedback**: Clear error messages and success notifications

## Running Tests

```bash
mvn test
```

## Building Executable JAR

```bash
mvn clean package
```

The JAR file will be created in the `target/` directory.

## Troubleshooting

### Common Issues

1. **Database Connection Error**

   - Ensure MySQL server is running
   - Check environment variables are set correctly
   - Verify MySQL port (default: 3306)
   - Test connection manually: `mysql -h localhost -u root -p`

2. **Environment Variables Not Loading**

   - Ensure .env file is in the project root directory
   - Check .env file format (no spaces around =)
   - Verify environment variable names match exactly
   - Try setting variables directly in terminal

3. **Compilation Errors**

   - Ensure Java 17+ is installed
   - Check Maven installation
   - Verify all dependencies are downloaded

4. **Runtime Errors**
   - Check database permissions
   - Verify table creation permissions
   - Ensure sufficient disk space

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is open source and available under the MIT License.

---

**Note**: This is a demonstration banking system. In a real-world application, you would need additional security measures, encryption, audit logging, and compliance features.
