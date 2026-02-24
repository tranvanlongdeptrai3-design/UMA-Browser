# 🚀 KHỞI ĐỘNG DỄ DÀNG - UMA BROWSER

*Hướng dẫn đơn giản nhất để khởi động ứng dụng*

---

## 📌 3 CÁCH KHỞI ĐỘNG

### **☑️ CÁCH 1: Script Tương Tác (Khuyên Dùng - Windows)**

**1. Double-click file:**
```
start.bat
```

**2. Chọn mục đích từ menu:**
```
1️⃣  Khởi động Server
2️⃣  Khởi động Client
3️⃣  Khởi động Cả Server và Client
4️⃣  Build Project
5️⃣  Xem Hướng Dẫn
```

**3. Làm theo lựa chọn bạn chọn**

✨ **Đơn giản & Thân thiện!**

---

### **☑️ CÁCH 2: Script Tương Tác (macOS/Linux)**

**1. Chạy lệnh:**
```bash
chmod +x start.sh
./start.sh
```

**2. Chọn mục đích từ menu (giống Windows)**

✨ **Cross-platform!**

---

### **☑️ CÁCH 3: Command Line Manual**

#### **Terminal 1 - Khởi Động Server:**
```bash
cd d:\lập trình mạng\UMA-Browser_no.1
mvn exec:java -Dexec.mainClass="server.WebServer"
```

#### **Terminal 2 (MỚI) - Khởi Động Client:**
```bash
cd d:\lập trình mạng\UMA-Browser_no.1
mvn javafx:run
```

---

## ⏱️ THỜI GIAN KHỞI ĐỘNG

| Lần | Thời Gian | Ghi Chú |
|-----|----------|--------|
| Lần 1 | 5-10 phút | Tải dependencies |
| Lần 2+ | 10-30 giây | Dependencies đã cache |

---

## ✅ KIỂM TRA KÉT QUẢ

### **Server Terminal:**
```
🚀 Web Server đã khởi động trên port 9090
📍 Địa chỉ: http://localhost:9090
```

### **Client Window:**
- Cửa sổ JavaFX mở
- Toolbar: Back, Forward, Refresh, Home
- Address Bar: nhập URL
- Content Area: hiển thị trang web
- Status Bar: trạng thái kết nối

---

## 🌐 TEST NGAY

**Địa chỉ bar, nhập:**

| URL | Kết Quả |
|-----|---------|
| `localhost:9090/` | ✅ Trang chủ |
| `localhost:9090/about` | ✅ Trang About |
| `localhost:9090/contact` | ✅ Trang Contact |
| `localhost:9090/test` | ❌ Lỗi 404 |

Nhấn **"Go"** hoặc **Enter**

---

## 🛑 DỪNG ỨNG DỤNG

### **Dừng Server:**
```
Ctrl+C trong terminal Server
```

### **Dừng Client:**
```
Ctrl+C trong terminal Client
hoặc Đóng cửa sổ GUI
```

---

## ⚠️ QUAN TRỌNG

1. **Server phải chạy trước Client**
   - Nếu chạy Client mà Server ko chạy → "Connection Refused"

2. **Mở Terminal MỚI cho Client**
   - Không dùng terminal của Server (nó sẽ bị block)

3. **Lần đầu tải dependencies**
   - Chọn "4" (Build Project) trong menu để tải JavaFX, plugins, ...
   - Lần sau sẽ nhanh hơn rất nhiều

---

## 🐛 NẾU CÓ LỖI

### **Lỗi: "Maven not found"**
- Cài Maven: https://maven.apache.org/
- Thêm vào PATH

### **Lỗi: "Java not found"**
- Cài JDK 11+: https://www.oracle.com/java/technologies/downloads/
- Thêm vào PATH

### **Lỗi: "Port 9090 in use"**
- Cổng đang bị chiếm
- Đổi port: `mvn exec:java -Dexec.mainClass="server.WebServer" -Dexec.args="8080"`

### **Lỗi: "Connection refused"**
- Server chưa khởi động
- Khởi động Server trước Client

---

## 💡 TIPS

```bash
# Build mà không run
mvn clean compile

# Run server lại mà ko rebuild
mvn exec:java -Dexec.mainClass="server.WebServer"

# View dependencies
mvn dependency:tree

# Clear cache Maven
rmdir %userprofile%\.m2\repository /s

# Build nhanh
mvn clean compile -DskipTests
```

---

## 📚 TÌM HIỂU THÊM

| File | Mục Đích |
|------|---------|
| [QUICK_START.md](QUICK_START.md) | Bắt đầu nhanh (5 phút) |
| [README.md](README.md) | Hướng dẫn chi tiết |
| [ARCHITECTURE.md](ARCHITECTURE.md) | Kiến trúc kỹ thuật |
| [INSTALLATION_GUIDE.md](INSTALLATION_GUIDE.md) | Cài đặt môi trường |
| [EXAMPLES.md](EXAMPLES.md) | Ví dụ & mở rộng |

---

## 🎯 QUICK CHECKLIST

```
☐ Cài Java JDK 11+
☐ Cài Maven 3.6+
☐ Double-click start.bat
☐ Chọn "4" (Build) lần đầu
☐ Chọn "1" (Start Server)
☐ Mở Terminal MỚI
☐ Chạy lại start.bat
☐ Chọn "2" (Start Client)
☐ Browser mở → ✅ Success!
```

---

**Bắt đầu ngay! 🚀**

