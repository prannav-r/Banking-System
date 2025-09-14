@echo off
REM Database Setup Script for Banking System (Windows)
echo ==========================================
echo     Banking System Database Setup
echo ==========================================

echo This script will help you configure the database connection.
echo.

REM Check if MySQL is running
echo Checking if MySQL is running...
tasklist /FI "IMAGENAME eq mysqld.exe" 2>NUL | find /I /N "mysqld.exe">NUL
if "%ERRORLEVEL%"=="0" (
    echo ✅ MySQL is running
) else (
    echo ❌ MySQL is not running. Please start MySQL server first.
    echo    Start MySQL service or run mysqld.exe
    pause
    exit /b 1
)

echo.
echo Please provide your MySQL credentials:
echo.

REM Get database credentials
set /p db_host="MySQL Host (default: localhost): "
if "%db_host%"=="" set db_host=localhost

set /p db_port="MySQL Port (default: 3306): "
if "%db_port%"=="" set db_port=3306

set /p db_username="MySQL Username (default: root): "
if "%db_username%"=="" set db_username=root

set /p db_password="MySQL Password: "

set /p db_name="Database Name (default: bank): "
if "%db_name%"=="" set db_name=bank

REM Test connection
echo.
echo Testing database connection...

mysql -h "%db_host%" -P "%db_port%" -u "%db_username%" -p"%db_password%" -e "SELECT 1;" >nul 2>&1

if %errorlevel% equ 0 (
    echo ✅ Database connection successful!
    
    REM Create .env file
    echo.
    echo Creating .env file with your configuration...
    
    (
        echo # Database Configuration
        echo DB_HOST=%db_host%
        echo DB_PORT=%db_port%
        echo DB_NAME=%db_name%
        echo DB_USERNAME=%db_username%
        echo DB_PASSWORD=%db_password%
    ) > .env
    
    echo ✅ .env file created successfully!
    echo.
    echo You can now run the banking system:
    echo   run.bat
    echo.
    echo Or run directly with Maven:
    echo   mvn exec:java
    
) else (
    echo ❌ Database connection failed!
    echo Please check your credentials and ensure MySQL is running.
    echo.
    echo You can manually create a .env file with:
    echo   DB_HOST=%db_host%
    echo   DB_PORT=%db_port%
    echo   DB_NAME=%db_name%
    echo   DB_USERNAME=%db_username%
    echo   DB_PASSWORD=%db_password%
)

pause
