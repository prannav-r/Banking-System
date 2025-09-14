@echo off
REM Test script for new login/register separation feature
echo ==========================================
echo     Testing New Login/Register Feature
echo ==========================================

REM Load environment variables from .env file if it exists
if exist .env (
    echo Loading environment variables from .env file...
    for /f "usebackq delims=" %%i in (.env) do (
        set "%%i"
    )
    echo Environment variables loaded.
) else (
    echo No .env file found. Using default values.
)

echo.
echo Testing the new separate login and register options...
echo.

echo Compiling and running the application...
mvn exec:java -Dexec.mainClass="com.banking.system.BankingSystemApplication"

pause
