@echo off
echo Testing MySQL connection...
echo.

set /p mysql_password="Enter your MySQL root password: "

echo Testing connection with password...
mysql -h localhost -u root -p%mysql_password% -e "SELECT 'Connection successful!' as status;" 2>nul

if %errorlevel% equ 0 (
    echo ✅ MySQL connection successful!
    echo.
    echo Now create a .env file with this content:
    echo DB_HOST=localhost
    echo DB_PORT=3306
    echo DB_NAME=bank
    echo DB_USERNAME=root
    echo DB_PASSWORD=%mysql_password%
    echo.
    echo Then run: run.bat
) else (
    echo ❌ MySQL connection failed!
    echo Please check your password and ensure MySQL is running.
)

pause
