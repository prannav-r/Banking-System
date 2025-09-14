@echo off
REM Test script for validation rules
echo ==========================================
echo     Testing Validation Rules
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
echo Testing the new validation rules...
echo.

echo Compiling and running the application...
mvn exec:java -Dexec.mainClass="com.banking.system.BankingSystemApplication"

pause
