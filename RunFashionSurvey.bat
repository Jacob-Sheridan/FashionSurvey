@echo off
setlocal

rem Get the directory of the script
set SCRIPT_DIR=%~dp0

rem Set the path to the custom JRE folder relative to the script directory
set JRE_PATH=%SCRIPT_DIR%custom-jre

rem Check if the JRE exists
if not exist "%JRE_PATH%\bin\java.exe" (
    echo JRE not found. Please make sure the custom JRE folder is in the correct location.
    pause
    exit /b
)

rem Run the JAR file using the custom JRE
"%JRE_PATH%\bin\java.exe" -jar "%SCRIPT_DIR%fashionsurvey.jar"

pause
endlocal
