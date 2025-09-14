@echo off
REM Simple Maven runner with environment variable loading

REM Load environment variables from .env file
if exist .env (
    echo Loading environment variables from .env file...
    for /f "usebackq tokens=1,2 delims==" %%a in (.env) do (
        if not "%%a"=="" (
            if not "%%a:~0,1%"=="#" (
                if not "%%a:~0,1%"==" " (
                    set "%%a=%%b"
                )
            )
        )
    )
)

REM Run Maven with environment variables
echo Running Banking System Swing GUI with Maven...
mvn clean compile exec:java -Dexec.mainClass="com.banking.system.BankingSystemSwingApplication"

pause
