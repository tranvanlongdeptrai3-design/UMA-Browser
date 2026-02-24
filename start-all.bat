@echo off
chcp 65001 >nul
cls

echo.
echo ╔══════════════════════════════════════════════════════════╗
echo ║           🚀 UMA BROWSER - ONE CLICK START 🚀           ║
echo ║      Khởi động Server và Client cùng lúc               ║
echo ╚══════════════════════════════════════════════════════════╝
echo.

REM Kiểm tra Maven
where mvn >nul 2>nul
if errorlevel 1 (
    echo ❌ Maven chưa được cài hoặc không cấu hình PATH
    echo.
    echo Vui lòng cài Maven từ: https://maven.apache.org/
    echo.
    pause
    exit /b 1
)

REM Kiểm tra Java
where java >nul 2>nul
if errorlevel 1 (
    echo ❌ Java chưa được cài hoặc không cấu hình PATH
    echo.
    echo Vui lòng cài JDK 11+ từ: https://www.oracle.com/java/technologies/downloads/
    echo.
    pause
    exit /b 1
)

echo ✓ Java và Maven đã sẵn sàng
echo.

REM Kiểm tra xem có cần build không
if not exist target\classes (
    echo 📦 Build dependencies (lần đầu tiên)...
    echo.
    call mvn clean install -q
    if errorlevel 1 (
        echo.
        echo ❌ Build thất bại!
        echo.
        pause
        exit /b 1
    )
    echo ✅ Build thành công!
    echo.
)

echo.
echo 🖥️  STARTING SERVER...
echo.
REM Khởi động Server trong cửa sổ mới
start "UMA Browser - SERVER" mvn -q exec:java -Dexec.mainClass="server.WebServer"

REM Đợi server khởi động
echo ⏳ Chờ server khởi động (5 giây)...
timeout /t 5 /nobreak

echo.
echo 🌐 STARTING CLIENT...
echo.
REM Khởi động Client trong cửa sổ hiện tại
mvn -q javafx:run

REM Nếu client đóng, hỏi có muốn đóng server không
echo.
echo 📋 Client đã đóng.
echo.
set /p choice=Đóng server? [Y/N]: 
if /i "%choice%"=="y" (
    echo ✅ Đóng server...
    taskkill /FI "WINDOWTITLE eq UMA Browser - SERVER" /T /F >nul 2>nul
    echo.
    echo 👋 Tạm biệt! Cảm ơn đã sử dụng UMA Browser
)

echo.
pause
