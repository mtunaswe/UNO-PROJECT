@echo off
title UNO Game
echo Starting UNO Game...
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Java is not installed or not in PATH!
    echo Please install Java JDK from https://adoptium.net/
    echo.
    pause
    exit /b 1
)

REM Check if JAR file exists
if not exist "UNO-Game.jar" (
    echo ERROR: UNO-Game.jar not found!
    echo Please run build.bat first to create the JAR file.
    echo.
    pause
    exit /b 1
)

REM Run the game
java -jar UNO-Game.jar

pause
