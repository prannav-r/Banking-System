@echo off
echo ==========================================
echo     Environment Variables Debug
echo ==========================================

echo Checking for .env file...
if exist .env (
    echo ✅ .env file found
    echo.
    echo Contents of .env file:
    echo ------------------------
    type .env
    echo ------------------------
    echo.
    
    echo Loading environment variables...
    for /f "usebackq delims=" %%i in (.env) do (
        set "%%i"
    )
    
    echo.
    echo Current environment variables:
    echo DB_HOST=%DB_HOST%
    echo DB_PORT=%DB_PORT%
    echo DB_NAME=%DB_NAME%
    echo DB_USERNAME=%DB_USERNAME%
    echo DB_PASSWORD=%DB_PASSWORD%
    
    if "%DB_PASSWORD%"=="" (
        echo ❌ DB_PASSWORD is empty!
        echo Please check your .env file.
    ) else (
        echo ✅ DB_PASSWORD is set
    )
    
) else (
    echo ❌ .env file not found!
    echo Please create a .env file with your database credentials.
)

echo.
pause
