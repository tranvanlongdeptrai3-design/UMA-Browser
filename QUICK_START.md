# ⚡ QUICK START - SỬ DỤNG NHANH

*Chỉ có 3 bước để chạy UMA Browser - bắt đầu ngay!*

---

## 📋 YÊU CẦU (Kiểm Tra Trước)

```bash
java -version          # JDK 11+
mvn --version          # Maven
```

Nếu không có, đọc [INSTALLATION_GUIDE.md](INSTALLATION_GUIDE.md)

---

## 🚀 3 BƯỚC CHẠY NHANH

### ✅ BƯỚC 1: Tải Dependencies

```bash
cd d:\lập trình mạng\UMA-Browser_no.1
mvn clean install
```

⏱️ Lần đầu: 5-10 phút | Lần sau: 10-30 giây

---

### ✅ BƯỚC 2: Chạy Server

**Windows - Dễ nhất:**
```bash
run_server.bat
```

**Hoặc Manual:**
```bash
mvn exec:java -Dexec.mainClass="server.WebServer"
```

**Khi thấy dòng này → SUCCESS:**
```
🚀 Web Server đã khởi động trên port 9090
```

---

### ✅ BƯỚC 3: Chạy Client (Terminal MỚI!)

**Windows - Dễ nhất:**
```bash
run_client.bat
```

**Hoặc Manual:**
```bash
mvn javafx:run
```

**Giao diện Browser sẽ mở!** 🎉

---

## 🧪 TEST NGAY

1. Nhấn nút **"Home"** → Thấy trang chủ
2. Nhập trong URL Bar: `localhost:9090/about` → Nhấn "Go"
3. Thấy trang About → **CHÚC MỪNG!** ✅

---

## 🔍 TROUBLESHOOTING NHANH

| Vấn đề | Giải Pháp |
|--------|----------|
| "Port in use" | Khởi động lại máy hoặc đổi port |
| "Connection refused" | Chắc Server chạy trước Client |
| "Maven/Java not found" | Cài JDK + Maven (xem Installation Guide) |
| "BUILD FAILURE" | `mvn clean install` lại |

---

## 📖 Tài Liệu Thêm

- **[README.md](README.md)** - Hướng dẫn chi tiết
- **[INSTALLATION_GUIDE.md](INSTALLATION_GUIDE.md)** - Cài đặt từng bước
- **[ARCHITECTURE.md](ARCHITECTURE.md)** - Kiến trúc hệ thống

---

## 💡 TIPS

```bash
# Chạy server trên port khác
mvn exec:java -Dexec.mainClass="server.WebServer" -Dexec.args="8080"

# Build nhanh
mvn clean compile -DskipTests

# Xem chi tiết lỗi
mvn clean install -X
```

---

**Thế đó! Bạn đã có UMA Browser đầy đủ! 🎉**

Đó là:
- ✅ Web Server TCP
- ✅ HTTP Client
- ✅ JavaFX GUI
- ✅ Multi-threaded handling
- ✅ HTML rendering

**Happy Browser! 🌐**
