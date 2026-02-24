@echo off
REM Script để chạy Browser Client
REM Sử dụng Maven JavaFX plugin để run với JavaFX

echo.
echo ========================================
echo   🌐 UMA Browser - Client
echo ========================================
echo.

REM Kiểm tra xem Maven có cài không
where mvn >nul 2>nul
if errorlevel 1 (
    echo ❌ Maven chưa được cài hoặc không có trong PATH
    echo.
    echo Vui lòng cài Maven từ: https://maven.apache.org/download.cgi
    echo.
    pause
    exit /b 1
)

REM Kiểm tra Java
where java >nul 2>nul
if errorlevel 1 (
    echo ❌ Java chưa được cài hoặc không có trong PATH
    echo.
    echo Vui lòng cài JDK 11+ từ: https://www.oracle.com/java/technologies/downloads/
    echo.
    pause
    exit /b 1
)

echo ✓ Java và Maven đã được phát hiện
echo.

REM Kiểm tra xem target/classes có tồn tại không
if not exist target\classes (
    echo 📦 Đang compile project...
    call mvn clean compile
    if errorlevel 1 (
        echo ❌ Lỗi khi compile project
        pause
        exit /b 1
    )
)

echo.
echo ========================================
echo.
echo 🔗 Chuẩn bị kết nối đến Server...
echo.
echo ⚠️  Chắc chắn rằng Web Server đã khởi động!
echo    Nếu chưa, hãy chạy: run_server.bat
echo.
echo Khuyến cáo:
echo   1. Khởi động Server trước (run_server.bat)
echo   2. Sau đó khởi động Client (dòng lệnh này)
echo.
echo ========================================
echo.

REM Chạy Client
echo 🚀 Đang khởi động Browser...
echo.
mvn -q javafx:run

pause
