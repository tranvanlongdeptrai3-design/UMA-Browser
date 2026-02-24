@echo off
REM Script để chạy Web Server
REM Sử dụng Maven để compile và run

echo.
echo ========================================
echo   🚀 UMA Browser - Web Server
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
echo 🌐 Server sẽ khởi động trên: http://localhost:9090
echo.
echo Các trang có sẵn:
echo   - http://localhost:9090/ (Trang chủ)
echo   - http://localhost:9090/about (Về chúng tôi)
echo   - http://localhost:9090/contact (Liên hệ)
echo.
echo Để dừng server, nhấn Ctrl+C
echo.
echo ========================================
echo.

REM Chạy Server
echo 🚀 Đang khởi động Server...
echo.
mvn -q exec:java -Dexec.mainClass="server.WebServer"

pause
