@echo off
REM Banking System Debug Runner for Windows
echo ==========================================
echo     Banking System DEBUG MODE
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
echo Current environment variables:
echo DB_HOST=%DB_HOST%
echo DB_PORT=%DB_PORT%
echo DB_NAME=%DB_NAME%
echo DB_USERNAME=%DB_USERNAME%
echo DB_PASSWORD=%DB_PASSWORD%
echo.

echo Compiling debug version...
mvn clean compile

if %errorlevel% neq 0 (
    echo Error: Compilation failed
    pause
    exit /b 1
)

echo Running debug application...
echo ==========================================
mvn exec:java -Dexec.mainClass="com.banking.system.BankingSystemDebugApplication"

pause
