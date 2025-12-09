@echo off
echo Building UNO Game...
echo.

REM Create bin directory if it doesn't exist
if not exist "bin" mkdir bin

REM Compile all Java files
echo Compiling Java files...
javac -d bin -sourcepath src src/Runnable/Main.java src/card_model/*.java src/Controller/*.java src/game_model/*.java src/gui/*.java src/Interfaces/*.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

REM Copy resources to bin
echo Copying resources...
xcopy /E /I /Y resources bin\resources

REM Create JAR file
echo Creating JAR file...
cd bin
jar cfm ../UNO-Game.jar ../MANIFEST.MF . resources
cd ..

echo.
echo Build complete! UNO-Game.jar has been created.
echo.
pause
