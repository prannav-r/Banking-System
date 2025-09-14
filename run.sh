#!/bin/bash

# Banking System Java Application Runner
# This script helps run the Java banking system application

echo "=========================================="
echo "    Banking System Java Application"
echo "=========================================="

# Load environment variables from .env file if it exists
if [ -f .env ]; then
    echo "Loading environment variables from .env file..."
    export $(cat .env | grep -v '^#' | xargs)
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 17 or higher"
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven 3.6 or higher"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "Error: Java 17 or higher is required. Current version: $JAVA_VERSION"
    exit 1
fi

echo "Java version: $(java -version 2>&1 | head -n 1)"
echo "Maven version: $(mvn -version | head -n 1)"
echo ""

# Display database configuration
echo "Database Configuration:"
echo "  Host: ${DB_HOST:-localhost}"
echo "  Port: ${DB_PORT:-3306}"
echo "  Database: ${DB_NAME:-bank}"
echo "  Username: ${DB_USERNAME:-root}"
echo "  Password: ${DB_PASSWORD:-[empty]}"
echo ""

# Compile the project
echo "Compiling the project..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "Error: Compilation failed"
    exit 1
fi

echo "Compilation successful!"
echo ""

# Run the application
echo "Starting Banking System Application..."
echo "=========================================="
mvn exec:java -Dexec.mainClass="com.banking.system.BankingSystemApplication"
