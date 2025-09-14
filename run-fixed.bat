@echo off
REM Banking System Java Application Runner for Windows
REM This script helps run the Java banking system application

echo ==========================================
echo     Banking System Java Application
echo ==========================================

REM Load environment variables from .env file if it exists
if exist .env (
    echo Loading environment variables from .env file...
    REM Read .env file and set variables
    for /f "usebackq delims=" %%i in (.env) do (
        set "%%i"
    )
    echo Environment variables loaded.
) else (
    echo No .env file found. Using default values.
)

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

REM Check if Maven is installed
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven 3.6 or higher
    pause
    exit /b 1
)

echo Java version:
java -version
echo.
echo Maven version:
mvn -version | findstr "Apache Maven"
echo.

REM Display database configuration
echo Database Configuration:
echo   Host: %DB_HOST%
if "%DB_HOST%"=="" echo   Host: localhost
echo   Port: %DB_PORT%
if "%DB_PORT%"=="" echo   Port: 3306
echo   Database: %DB_NAME%
if "%DB_NAME%"=="" echo   Database: bank
echo   Username: %DB_USERNAME%
if "%DB_USERNAME%"=="" echo   Username: root
echo   Password: %DB_PASSWORD%
if "%DB_PASSWORD%"=="" echo   Password: [empty]
echo.

REM Compile the project
echo Compiling the project...
mvn clean compile

if %errorlevel% neq 0 (
    echo Error: Compilation failed
    pause
    exit /b 1
)

echo Compilation successful!
echo.

REM Run the application
echo Starting Banking System Application...
echo ==========================================
mvn exec:java -Dexec.mainClass="com.banking.system.BankingSystemApplication"

pause
