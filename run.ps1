# Banking System Java Application Runner (PowerShell)
# This script helps run the Java banking system application

Write-Host "=========================================="
Write-Host "    Banking System Java Application"
Write-Host "=========================================="

# Load environment variables from .env file if it exists
if (Test-Path ".env") {
    Write-Host "Loading environment variables from .env file..."
    Get-Content ".env" | ForEach-Object {
        if ($_ -match "^([^#][^=]+)=(.*)$") {
            $name = $matches[1].Trim()
            $value = $matches[2].Trim()
            [Environment]::SetEnvironmentVariable($name, $value, "Process")
        }
    }
    Write-Host "Environment variables loaded."
} else {
    Write-Host "No .env file found. Using default values."
}

# Check if Java is installed
try {
    $javaVersion = java -version 2>&1 | Select-Object -First 1
    Write-Host "Java version: $javaVersion"
} catch {
    Write-Host "Error: Java is not installed or not in PATH"
    Write-Host "Please install Java 17 or higher"
    Read-Host "Press Enter to exit"
    exit 1
}

# Check if Maven is installed
try {
    $mavenVersion = mvn -version 2>&1 | Select-String "Apache Maven" | Select-Object -First 1
    Write-Host "Maven version: $mavenVersion"
} catch {
    Write-Host "Error: Maven is not installed or not in PATH"
    Write-Host "Please install Maven 3.6 or higher"
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host ""

# Display database configuration
Write-Host "Database Configuration:"
Write-Host "  Host: $($env:DB_HOST)"
if (-not $env:DB_HOST) { Write-Host "  Host: localhost" }
Write-Host "  Port: $($env:DB_PORT)"
if (-not $env:DB_PORT) { Write-Host "  Port: 3306" }
Write-Host "  Database: $($env:DB_NAME)"
if (-not $env:DB_NAME) { Write-Host "  Database: bank" }
Write-Host "  Username: $($env:DB_USERNAME)"
if (-not $env:DB_USERNAME) { Write-Host "  Username: root" }
Write-Host "  Password: $($env:DB_PASSWORD)"
if (-not $env:DB_PASSWORD) { Write-Host "  Password: [empty]" }
Write-Host ""

# Compile the project
Write-Host "Compiling the project..."
mvn clean compile

if ($LASTEXITCODE -ne 0) {
    Write-Host "Error: Compilation failed"
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host "Compilation successful!"
Write-Host ""

# Run the application
Write-Host "Starting Banking System Application..."
Write-Host "=========================================="
mvn exec:java -Dexec.mainClass="com.banking.system.BankingSystemApplication"

Read-Host "Press Enter to exit"
