#!/bin/bash

clear

echo ""
echo "╔══════════════════════════════════════════════════════════╗"
echo "║           🚀 UMA BROWSER - ONE CLICK START 🚀           ║"
echo "║      Khởi động Server và Client cùng lúc               ║"
echo "╚══════════════════════════════════════════════════════════╝"
echo ""

# Kiểm tra Maven
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven chưa được cài hoặc không cấu hình PATH"
    echo ""
    echo "Vui lòng cài Maven từ: https://maven.apache.org/"
    echo ""
    read -p "Nhấn Enter để thoát..."
    exit 1
fi

# Kiểm tra Java
if ! command -v java &> /dev/null; then
    echo "❌ Java chưa được cài hoặc không cấu hình PATH"
    echo ""
    echo "Vui lòng cài JDK 11+ từ: https://www.oracle.com/java/technologies/downloads/"
    echo ""
    read -p "Nhấn Enter để thoát..."
    exit 1
fi

echo "✓ Java và Maven đã sẵn sàng"
echo ""

# Kiểm tra xem có cần build không
if [ ! -d "target/classes" ]; then
    echo "📦 Build dependencies (lần đầu tiên)..."
    echo ""
    mvn clean install -q
    if [ $? -ne 0 ]; then
        echo ""
        echo "❌ Build thất bại!"
        echo ""
        read -p "Nhấn Enter để thoát..."
        exit 1
    fi
    echo "✅ Build thành công!"
    echo ""
fi

echo ""
echo "🖥️  STARTING SERVER..."
echo ""

# Khởi động Server trong tmux session hoặc background
if command -v tmux &> /dev/null; then
    # Nếu có tmux, sử dụng tmux
    tmux new-session -d -s uma-server "cd '$(pwd)' && mvn -q exec:java -Dexec.mainClass='server.WebServer'"
else
    # Nếu không có tmux, chạy background
    mvn -q exec:java -Dexec.mainClass="server.WebServer" &
    SERVER_PID=$!
fi

# Đợi server khởi động
echo "⏳ Chờ server khởi động (5 giây)..."
sleep 5

echo ""
echo "🌐 STARTING CLIENT..."
echo ""

# Khởi động Client
mvn -q javafx:run

# Sau khi client đóng
echo ""
echo "📋 Client đã đóng."
echo ""
read -p "Đóng server? (Y/N): " choice

if [[ "$choice" =~ ^[Yy]$ ]]; then
    echo "✅ Đóng server..."
    if command -v tmux &> /dev/null; then
        tmux kill-session -t uma-server 2>/dev/null
    else
        if [ ! -z "$SERVER_PID" ]; then
            kill $SERVER_PID 2>/dev/null
        fi
    fi
    echo ""
    echo "👋 Tạm biệt! Cảm ơn đã sử dụng UMA Browser"
fi

echo ""
read -p "Nhấn Enter để thoát..."
