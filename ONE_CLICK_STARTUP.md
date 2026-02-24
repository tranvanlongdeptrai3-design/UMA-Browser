# 🎯 ONE-CLICK STARTUP GUIDE

*Khởi động Server và Client cùng lúc với 1 thao tác duy nhất*

---

## ⭐ Cách Dễ Nhất (Khuyên dùng)

### Windows:
1. **Double-click** `start-all.bat` file
2. Chờ ~5 giây
3. Browser mở tự động ✅

### Mac/Linux:
1. Mở Terminal
2. Chạy: `bash start-all.sh`
3. Chờ ~5 giây
4. Browser mở tự động ✅

---

## 📖 Vòng Đời One-Click Startup

### Phía Server:
```
[1] Chuẩn bị build (nếu lần đầu)
    ↓
[2] Khởi động Server (port 9090)
    ↓
[3] Lắng nghe client connections
    ↓
[4] Phục vụ requests (/ /about /contact)
```

### Phía Client:
```
[1] Chờ 5 giây (server khởi động)
    ↓
[2] Mở cửa sổ JavaFX
    ↓
[3] Kết nối đến Server (localhost:9090)
    ↓
[4] Hiển thị giao diện Browser
```

### Khi bạn đóng Client:
```
[1] Script hỏi: "Đóng server?"
    ↓
[2] Nếu chọn "Y" → Tắt Server
    ↓
[3] Nếu chọn "N" → Server vẫn chạy (có thể kết nối lại)
```

---

## ⏱️ Thời Gian Chờ

| Lần | Thời gian | Lý do |
| --- | --- | --- |
| Lần 1 | 2-3 phút | Tải dependencies JavaFX, compile |
| Lần 2+ | 30-50 giây | Chỉ compile, dependencies cached |
| Sau đó | 10-15 giây | Chỉ khởi động, không compile |

---

## 🔧 Tùy Chỉnh Thời Gian Chờ

Nếu server khởi động chậm hơn 5 giây, mở `start-all.bat`:

```batch
REM Tìm dòng này:
timeout /t 5 /nobreak

REM Thay 5 thành số lớn hơn (ví dụ 10):
timeout /t 10 /nobreak
```

---

## 🐛 Troubleshooting

### Server khởi động nhưng Browser không kết nối

**Giải pháp:**
1. Đóng Browser (`start-all.bat` đang chạy)
2. Mở Terminal/CMD mới
3. Chạy: `mvn -q javafx:run`
4. Nhập URL: `localhost:9090/`

### Lỗi "Port 9090 is in use"

**Giải pháp:**
1. Tìm process sử dụng port 9090
2. Tắt nó hoặc sử dụng port khác

**Windows:**
```batch
netstat -ano | findstr :9090
taskkill /PID <PID> /F
```

**Mac/Linux:**
```bash
lsof -i :9090
kill -9 <PID>
```

---

## 📋 So Sánh Các Cách Chạy

| Cách | Lệnh | Ưu điểm | Nhược điểm |
| --- | --- | --- | --- |
| **One-Click** | Double-click `start-all.bat` | Đơn giản, tự động | Khó customize |
| **Interactive Menu** | `start.bat` | Linh hoạt, dễ dùng | Cần 2 bước |
| **Manual** | 2 cmd windows | Kiểm soát toàn bộ | Phức tạp hơn |

---

## 🎓 Hiểu Rõ Hơn

- **start-all.bat**: Tự động khởi động tất cả → File này
- **start.bat**: Menu tương tác (1/2/3/4/5/6) → Xem [STARTUP.md](STARTUP.md)
- **Manual**: Chạy từng lệnh → Xem [QUICK_START.md](QUICK_START.md)

---

## 💡 Tips

1. **Lần đầu chạy:** Sẽ chậm một chút (tải dependencies)
2. **Server giữ nguyên:** Nếu bạn chọn "N" khi Browser đóng
3. **Khởi động lại Browser:** Chạy `mvn -q javafx:run` lần 2
4. **Dev Mode:** Sửa code + chạy lại `mvn clean install` → `start-all.bat`

---

**Happy Coding! 🚀**
