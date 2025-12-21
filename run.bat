@echo off
set JFX_PATH=lib\javafx-sdk\javafx-sdk-21.0.1\lib
set SQLITE_PATH=lib\sqlite-jdbc.jar

echo Compiling...
javac --module-path "%JFX_PATH%" --add-modules javafx.controls,javafx.fxml -cp "%SQLITE_PATH%" -d bin src\main\java\com\techhive\*.java

echo Copying resources...
copy "src\main\resources\styles.css" "bin\styles.css"
xcopy /s /y "src\main\resources\com" "bin\com"

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)

echo Running...
java --module-path "%JFX_PATH%" --add-modules javafx.controls,javafx.fxml -cp "bin;%SQLITE_PATH%" com.techhive.Main
