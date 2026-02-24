#!/bin/bash

# UMA Browser - Quick Start Script (macOS/Linux)

clear

echo ""
echo "╔══════════════════════════════════════════════════════════╗"
echo "║           🌐 UMA BROWSER - QUICK START 🌐              ║"
echo "║      TCP/IP Web Browser with JavaFX Interface          ║"
echo "╚══════════════════════════════════════════════════════════╝"
echo ""

# Kiểm tra Maven
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven chưa được cài hoặc không cấu hình PATH"
    echo ""
    echo "Cài Maven từ: https://maven.apache.org/"
    exit 1
fi

# Kiểm tra Java
if ! command -v java &> /dev/null; then
    echo "❌ Java chưa được cài hoặc không cấu hình PATH"
    echo ""
    echo "Cài JDK 11+ từ: https://www.oracle.com/java/technologies/downloads/"
    exit 1
fi

echo "✓ Java và Maven đã sẵn sàng"
echo ""

while true; do
    echo ""
    echo "📋 CHỌN MỤC ĐÍCH:"
    echo ""
    echo "   1️⃣  Khởi động Server (TCP port 9090)"
    echo "   2️⃣  Khởi động Client (JavaFX Browser)"
    echo "   3️⃣  Build Project (Compile & Dependencies)"
    echo "   4️⃣  Xem Hướng Dẫn"
    echo "   5️⃣  Thoát"
    echo ""
    read -p "👉 Nhập lựa chọn (1-5): " choice

    case $choice in
        1)
            clear
            echo ""
            echo "🖥️  KHỞI ĐỘNG WEB SERVER"
            echo "════════════════════════════════════════════════════════════"
            echo ""
            echo "Server sẽ lắng nghe trên: http://localhost:9090"
            echo ""
            echo "Các trang khả dụng:"
            echo "   • http://localhost:9090/ (Trang chủ)"
            echo "   • http://localhost:9090/about (Về chúng tôi)"
            echo "   • http://localhost:9090/contact (Liên hệ)"
            echo ""
            echo "⚠️  Để dừng server, nhấn: Ctrl+C"
            echo ""
            echo "🚀 Đang khởi động Server..."
            echo ""
            mvn -q exec:java -Dexec.mainClass="server.WebServer"
            ;;
        2)
            clear
            echo ""
            echo "🌐 KHỞI ĐỘNG BROWSER CLIENT"
            echo "════════════════════════════════════════════════════════════"
            echo ""
            echo "⚠️  QUAN TRỌNG: Server phải chạy TRƯỚC!"
            echo ""
            echo "Nếu chưa khởi động server, hãy:"
            echo "   1. Chạy script này và chọn '1' (Khởi động Server)"
            echo "   2. Mở terminal mới (Cmd+T)"
            echo "   3. Chạy lại script này và chọn '2' (Khởi động Client)"
            echo ""
            echo "🚀 Đang khởi động Browser..."
            echo ""
            mvn -q javafx:run
            ;;
        3)
            clear
            echo ""
            echo "📦 Đang BUILD PROJECT..."
            echo ""
            echo "Quá trình này sẽ:"
            echo "   • Tải tất cả dependencies (JavaFX, plugins)"
            echo "   • Compile source code"
            echo "   • Chuẩn bị chạy ứng dụng"
            echo ""
            echo "⏳ Vui lòng đợi... (lần đầu có thể mất 5-10 phút)"
            echo ""
            mvn clean install
            if [ $? -eq 0 ]; then
                echo ""
                echo "✅ Build thành công!"
            else
                echo ""
                echo "❌ Build thất bại!"
            fi
            echo ""
            ;;
        4)
            clear
            echo ""
            echo "📖 HƯỚNG DẪN KHỞI ĐỘNG"
            echo "════════════════════════════════════════════════════════════"
            echo ""
            echo "LẦN ĐẦU CHẠY:"
            echo "   1. Chạy: chmod +x start.sh && ./start.sh"
            echo "   2. Chọn '3' (Build Project) - tải dependencies"
            echo "   3. Chọn '1' (Start Server)"
            echo "   4. Mở terminal mới (Cmd+T):"
            echo "      cd d:\lập\ trình\ mạng\UMA-Browser_no.1"
            echo "      ./start.sh"
            echo "   5. Chọn '2' (Start Client)"
            echo ""
            echo "CÁC LẦN CHẠY TIẾP:"
            echo "   1. Chọn '1' (Start Server)"
            echo "   2. Mở terminal mới"
            echo "   3. Chọn '2' (Start Client)"
            echo ""
            echo "CÁC TRANG CÓ SẴN:"
            echo "   • localhost:9090/        (Trang chủ)"
            echo "   • localhost:9090/about   (Về chúng tôi)"
            echo "   • localhost:9090/contact (Liên hệ)"
            echo ""
            echo "DỪNG SERVER:"
            echo "   • Ctrl+C trong terminal server"
            echo ""
            echo "DỪNG CLIENT:"
            echo "   • Ctrl+C hoặc đóng cửa sổ"
            echo ""
            echo "TÌM HIỂU THÊM:"
            echo "   • QUICK_START.md (nhanh)"
            echo "   • README.md (chi tiết)"
            echo "   • ARCHITECTURE.md (kiến trúc)"
            echo ""
            ;;
        5)
            echo ""
            echo "Tạm biệt! 👋"
            exit 0
            ;;
        *)
            echo ""
            echo "❌ Lựa chọn không hợp lệ"
            echo ""
            ;;
    esac
done
