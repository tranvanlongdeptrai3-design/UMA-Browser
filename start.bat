@echo off
chcp 65001 >nul
cls

echo.
echo ╔══════════════════════════════════════════════════════════╗
echo ║           🌐 UMA BROWSER - QUICK START 🌐              ║
echo ║      TCP/IP Web Browser with JavaFX Interface          ║
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

:menu
echo.
echo 📋 CHỌN MỤC ĐÍCH:
echo.
echo   1️⃣  Khởi động Server (TCP port 9090)
echo   2️⃣  Khởi động Client (JavaFX Browser)
echo   3️⃣  Khởi động Cả Server và Client (Khuyên dùng)
echo   4️⃣  Build Project (Compile & Dependencies)
echo   5️⃣  Xem Hướng Dẫn
echo   6️⃣  Thoát
echo.

set /p choice=👉 Nhập lựa chọn [1-6]: 

if "%choice%"=="1" goto start_server
if "%choice%"=="2" goto start_client
if "%choice%"=="3" goto start_both
if "%choice%"=="4" goto build_project
if "%choice%"=="5" goto show_guide
if "%choice%"=="6" exit /b 0

echo.
echo ❌ Lựa chọn không hợp lệ
echo.
goto menu

:build_project
cls
echo.
echo 📦 Đang BUILD PROJECT...
echo.
echo Quá trình này sẽ:
echo   • Tải tất cả dependencies (JavaFX, plugins)
echo   • Compile source code
echo   • Chuẩn bị chạy ứng dụng
echo.
echo ⏳ Vui lòng đợi... (lần đầu có thể mất 5-10 phút)
echo.
call mvn clean install
if errorlevel 1 (
    echo.
    echo ❌ Build thất bại!
    echo.
    pause
    goto menu
)
echo.
echo ✅ Build thành công!
echo.
pause
goto menu

:start_server
cls
echo.
echo 🖥️  KHỞI ĐỘNG WEB SERVER
echo ════════════════════════════════════════════════════════════
echo.
echo Server sẽ lắng nghe trên: http://localhost:9090
echo.
echo Các trang khả dụng:
echo   • http://localhost:9090/ (Trang chủ)
echo   • http://localhost:9090/about (Về chúng tôi)
echo   • http://localhost:9090/contact (Liên hệ)
echo.
echo ⚠️  Để dừng server, nhấn: Ctrl+C
echo.
echo 🚀 Đang khởi động Server...
echo.
call mvn -q exec:java -Dexec.mainClass="server.WebServer"
pause
goto menu

:start_client
cls
echo.
echo 🌐 KHỞI ĐỘNG BROWSER CLIENT
echo ════════════════════════════════════════════════════════════
echo.
echo ⚠️  QUAN TRỌNG: Server phải chạy TRƯỚC!
echo.
echo Nếu chưa khởi động server, hãy:
echo   1. Chạy script này và chọn "1" (Khởi động Server)
echo   2. Mở terminal/CMD mới
echo   3. Chạy lại script này và chọn "2" (Khởi động Client)
echo.
echo 🚀 Đang khởi động Browser...
echo.
call mvn -q javafx:run
pause
goto menu

:start_both
cls
echo.
echo 🚀 KHỞI ĐỘNG SERVER VÀ CLIENT
echo ════════════════════════════════════════════════════════════
echo.
echo Điều này sẽ:
echo   1. Khởi động Server (nếu cần build)
echo   2. Hướng dẫn bạn khởi động Client
echo.
echo.
echo 📦 Kiểm tra/Build dependencies...
if not exist target\classes (
    echo   • Tải dependencies
    echo   • Compile source code
    echo.
    call mvn clean compile -q
    if errorlevel 1 (
        echo.
        echo ❌ Build thất bại!
        echo.
        pause
        goto menu
    )
)
echo   ✓ Sẵn sàng
echo.

echo.
echo 🖥️  KHỞI ĐỘNG SERVER...
echo.
echo Server sẽ chạy ở cửa sổ này. KHÔNG ĐÓNG CỬA SỔ NÀY!
echo.
echo ⏳ Server đang khởi động...
echo.

REM Khởi chạy server trong background không được trong batch, nên ta sẽ hướng dẫn
echo ❌ Xin lỗi, batch script không thể chạy 2 cửa sổ cùng lúc.
echo.
echo Vui lòng:
echo   1️⃣  Click vào số 1 (Start Server)
echo   2️⃣  Mở CMD/Terminal KHÁC (Win+R, gõ cmd)
echo   3️⃣  Chạy lại: cd d:\lập trình mạng\UMA-Browser_no.1 && start.bat
echo   4️⃣  Click vào số 2 (Start Client)
echo.
pause
goto menu

:show_guide
cls
echo.
echo 📖 HƯỚNG DẪN KHỞI ĐỘNG
echo ════════════════════════════════════════════════════════════
echo.
echo BẦU TIÊN LẦN CHẠY:
echo   1. Chọn "4" (Build Project) - tải dependencies
echo   2. Chọn "1" (Start Server)
echo   3. Mở CMD mới: Win+R → cmd → Enter
echo   4. Gõ: cd d:\lập trình mạng\UMA-Browser_no.1 ^&^& start.bat
echo   5. Chọn "2" (Start Client)
echo.
echo NHỮNG LẦN CHẠY TIẾP:
echo   1. Chọn "1" (Start Server)
echo   2. Mở CMD mới
echo   3. Chạy start.bat
echo   4. Chọn "2" (Start Client)
echo.
echo CÁC TRANG CÓ SẴN:
echo   • localhost:9090/        (Trang chủ)
echo   • localhost:9090/about   (Về chúng tôi)
echo   • localhost:9090/contact (Liên hệ)
echo.
echo DỪNG SERVER:
echo   • Trong terminal server: Ctrl+C
echo.
echo DỪNG CLIENT:
echo   • Ctrl+C hoặc đóng cửa sổ
echo.
echo TÌM HIỂU THÊM:
echo   • Đọc QUICK_START.md (nhanh)
echo   • Đọc README.md (chi tiết)
echo   • Đọc ARCHITECTURE.md (kiến trúc)
echo.
pause
goto menu
