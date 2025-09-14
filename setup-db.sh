#!/bin/bash

# Database Setup Script for Banking System
echo "=========================================="
echo "    Banking System Database Setup"
echo "=========================================="

echo "This script will help you configure the database connection."
echo ""

# Check if MySQL is running
echo "Checking if MySQL is running..."
if ! pgrep -x "mysqld" > /dev/null; then
    echo "❌ MySQL is not running. Please start MySQL server first."
    echo "   On Windows: Start MySQL service or run mysqld"
    echo "   On Linux/Mac: sudo systemctl start mysql or brew services start mysql"
    exit 1
else
    echo "✅ MySQL is running"
fi

echo ""
echo "Please provide your MySQL credentials:"
echo ""

# Get database credentials
read -p "MySQL Host (default: localhost): " db_host
db_host=${db_host:-localhost}

read -p "MySQL Port (default: 3306): " db_port
db_port=${db_port:-3306}

read -p "MySQL Username (default: root): " db_username
db_username=${db_username:-root}

read -s -p "MySQL Password: " db_password
echo ""

read -p "Database Name (default: bank): " db_name
db_name=${db_name:-bank}

# Test connection
echo ""
echo "Testing database connection..."

# Create temporary connection test
mysql -h "$db_host" -P "$db_port" -u "$db_username" -p"$db_password" -e "SELECT 1;" 2>/dev/null

if [ $? -eq 0 ]; then
    echo "✅ Database connection successful!"
    
    # Create .env file
    echo ""
    echo "Creating .env file with your configuration..."
    
    cat > .env << EOF
# Database Configuration
DB_HOST=$db_host
DB_PORT=$db_port
DB_NAME=$db_name
DB_USERNAME=$db_username
DB_PASSWORD=$db_password
EOF
    
    echo "✅ .env file created successfully!"
    echo ""
    echo "You can now run the banking system:"
    echo "  ./run.sh    # Linux/Mac"
    echo "  run.bat     # Windows"
    echo ""
    echo "Or run directly with Maven:"
    echo "  mvn exec:java"
    
else
    echo "❌ Database connection failed!"
    echo "Please check your credentials and ensure MySQL is running."
    echo ""
    echo "You can manually create a .env file with:"
    echo "  DB_HOST=$db_host"
    echo "  DB_PORT=$db_port"
    echo "  DB_NAME=$db_name"
    echo "  DB_USERNAME=$db_username"
    echo "  DB_PASSWORD=$db_password"
fi
