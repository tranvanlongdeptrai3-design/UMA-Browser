# 🌐 UMA Browser - Hướng Dẫn Chi Tiết

## 📋 Mô Tả Dự Án

UMA Browser là một trình duyệt web được xây dựng bằng Java, sử dụng:
- **Giao thức TCP**: Để giao tiếp giữa Client và Server
- **Mô hình Client-Server**: Kiến trúc mạng cơ bản
- **JavaFX**: Giao diện người dùng
- **HTTP Protocol**: Giao thức trên lớp ứng dụng

---

## ⚡ BẮT ĐẦU NHANH (1 Thao Tác)

**Windows:** Double-click `start-all.bat`  
**Mac/Linux:** `bash start-all.sh`

✅ Server + Client khởi động tự động!  
⏱️ Thời gian: ~30 giây (lần đầu: 2-3 phút)

👉 Chi tiết: Xem [ONE_CLICK_STARTUP.md](ONE_CLICK_STARTUP.md)

---

## 🏗️ Kiến Trúc Dự Án

```
UMA-Browser_no.1/
├── pom.xml                          (Maven configuration)
├── README.md                        (File này)
├── run_server.bat                   (Script chạy Server)
├── run_client.bat                   (Script chạy Client)
│
└── src/main/java/
    ├── server/
    │   └── WebServer.java           (Web Server - lắng nghe TCP port 9090)
    ├── client/
    │   ├── HttpClient.java          (HTTP Client - kết nối TCP)
    │   └── BrowserApplication.java  (Giao diện JavaFX)
    └── util/
        ├── HttpRequest.java         (Class đại diện HTTP Request)
        └── HttpResponse.java        (Class đại diện HTTP Response)
```

---

## 🔧 Yêu Cầu Hệ Thống

- **Java Development Kit (JDK)**: JDK 11 hoặc cao hơn
- **Maven**: Để build dự án
- **JavaFX SDK**: Được cài tự động qua pom.xml

### Cài Đặt:

1. **Tải JDK 11+**: https://www.oracle.com/java/technologies/downloads/
   - Hoặc sử dụng OpenJDK

2. **Tải Maven**: https://maven.apache.org/download.cgi
   - Thêm Maven vào environment variables
   - Kiểm tra: `mvn --version`

3. **Kiểm tra Java**:
   ```bash
   java -version
   javac -version
   ```

---

## 📝 Cách Chạy Ứng Dụng

### **Cách 1: Sử dụng Script Batch (Windows)**

Đã chuẩn bị script tự động cho bạn:

```bash
# Chạy Server
run_server.bat

# Chạy Client (trong terminal mới)
run_client.bat
```

### **Cách 2: Sử dụng Command Line**

#### Bước 1: Compile Project
```bash
mvn clean compile
```

#### Bước 2: Chạy Server
```bash
# Cách 1: Chạy bằng Maven
mvn exec:java -Dexec.mainClass="server.WebServer"

# Cách 2: Chạy bằng Java trực tiếp
cd target/classes
java server.WebServer
```

#### Bước 3: Chạy Client (trong terminal khác)
```bash
# Cách 1: Chạy bằng Maven
mvn javafx:run

# Cách 2: Chạy bằng Java trực tiếp
cd target/classes
java -p /đường/dẫn/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml client.BrowserApplication
```

---

## 🚀 Hướng Dẫn Từng Bước Chi Tiết

### **Bước 1: Chuẩn Bị Các File**

Tất cả các file đã được tạo sẵn:

```
✓ pom.xml - Maven configuration
✓ WebServer.java - Server component
✓ HttpClient.java - Client component  
✓ BrowserApplication.java - UI
✓ HttpRequest.java - Request format
✓ HttpResponse.java - Response format
```

### **Bước 2: Cài Đặt Dependencies**

Chạy Maven để tải dependencies (JavaFX, plugins, ...):

```bash
mvn clean install
```

Đây có thể mất 1-2 phút lần đầu tiên.

### **Bước 3: Khởi Động Server**

Mở **Command Prompt/Power Shell** và chạy:

```bash
cd d:\lập trình mạng\UMA-Browser_no.1
mvn exec:java -Dexec.mainClass="server.WebServer"
```

**Khi Server sẵn sàng, bạn sẽ thấy:**
```
🚀 Web Server đã khởi động trên port 9090
📍 Địa chỉ: http://localhost:9090
```

**Server sẽ lắng nghe các request trên TCP port 9090**

### **Bước 4: Khởi Động Client**

Mở **Command Prompt/Power Shell mới** và chạy:

```bash
cd d:\lập trình mạng\UMA-Browser_no.1
mvn javafx:run
```

**Giao diện Browser sẽ mở với:**
- Thanh công cụ (Back, Forward, Refresh, Home)
- Thanh địa chỉ (URL Bar)
- Khu vực nội dung (để hiển thị HTML)
- Thanh trạng thái (hiển thị trạng thái)

---

## 📖 Hướng Dẫn Sử Dụng Browser

### **Các Trang Khả Dụng:**

| URL | Mô Tả |
|-----|-------|
| `http://localhost:9090/` | Trang chủ |
| `http://localhost:9090/about` | Trang thông tin |
| `http://localhost:9090/contact` | Trang liên hệ |
| `http://localhost:9090/chapter` | Đọc chương trình - Hướng dẫn chi tiết |
| Các URL khác | Sẽ trả về Error 404 |

### **Cách Điều Hướng:**

1. **Nhập URL** trong thanh địa chỉ:
   - Vd: `localhost:9090/about`
   - Hoặc: `http://localhost:9090/contact`

2. **Nhấn nút "Go"** hoặc **Enter**

3. **Nhấn nút "Home"** để về trang chủ

4. **Refresh** để tải lại trang

### **Chuyển Server:**

Sử dụng combo box "🖥 Server:" để chuyển sang server khác nếu cần.

---

## 🔍 Cách Thức Hoạt Động

### **Kiến Trúc TCP Communication**

```
┌─────────────────────────────────────────┐
│          Browser (Client)               │
│┌───────────────────────────────────────┐│
││  JavaFX UI (BrowserApplication)       ││
││  - Địa chỉ Bar                        ││
││  - Hiển thị nội dung HTML             ││
│└───────────────────────────────────────┘│
│                   │                      │
│            HttpClient                    │
│            (TCP Socket)                  │
└─────────────────────────────────────────┘
              TCP Stream
         (serialize object)
            ↓↓↓  ↑↑↑
┌─────────────────────────────────────────┐
│      Server (WebServer)                 │
│┌───────────────────────────────────────┐│
││  ServerSocket (lắng nghe 9090)         ││
││  ClientHandler (xử lý mỗi request)     ││
│└───────────────────────────────────────┘│
└─────────────────────────────────────────┘
```

### **Quy Trình Gửi/Nhận Data:**

1. **Client gửi request:**
   - Tạo HttpRequest object
   - Serialize thành byte stream
   - Gửi qua TCP socket

2. **Server nhận request:**
   - Deserialize thành HttpRequest
   - Xác định đường dẫn (path)
   - Xử lý và tạo HttpResponse
   - Serialize response thành byte stream
   - Gửi về client

3. **Client nhận response:**
   - Deserialize thành HttpResponse
   - Hiển thị HTML content trong TextArea
   - Cập nhật trạng thái

---

## 🛠️ Triển Khai Tính Năng Mới

### **Thêm Trang Mới**

Chỉnh sửa [WebServer.java](src/main/java/server/WebServer.java) method `handleRequest()`:

```java
} else if (path.equals("/newpage")) {
    return getNewPage();
}
```

Thêm method:
```java
private HttpResponse getNewPage() {
    String html = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head><title>New Page</title></head>\n" +
            "<body><h1>Hello World</h1></body>\n" +
            "</html>";
    HttpResponse response = new HttpResponse(200, "OK", html);
    response.addHeader("Content-Type", "text/html; charset=UTF-8");
    return response;
}
```

### **Thêm POST Support**

```java
if (request.getMethod().equals("GET")) {
    // Xử lý GET
} else if (request.getMethod().equals("POST")) {
    String body = request.getBody();
    // Xử lý POST data
}
```

### **Chuyển Đổi Server Port**

Chạy: `mvn exec:java -Dexec.mainClass="server.WebServer" -Dexec.args="8080"`

---

## 🐛 Xử Lý Lỗi Thường Gặp

| Lỗi | Nguyên Nhân | Giải Pháp |
|-----|-----------|----------|
| "Connection refused" | Server chưa chạy | Chạy Server trước, rồi chạy Client |
| "Port already in use" | Cổng 9090 đang bị chiếm | Dùng cổng khác: `java server.WebServer 8080` |
| "JavaFX not found" | JavaFX không cài | Chạy `mvn install` để tải JavaFX |
| "Class not found" | Chưa compile | Chạy `mvn clean compile` |
| "Cannot find symbol" | Thiếu import | Chạy `mvn clean compile -e` xem chi tiết |

---

## 📚 Khái Niệm Lập Trình Mạng

### **TCP (Transmission Control Protocol)**
- Giao thức tin cậy, có thiết lập kết nối trước
- Đảm bảo dữ liệu đến đúng thứ tự, không mất
- Socket API trong Java: `ServerSocket`, `Socket`

### **Mô Hình Client-Server**
- **Server**: Chờ kết nối, phục vụ requests
- **Client**: Chủ động kết nối, gửi requests
- Một server phục vụ nhiều clients

### **HTTP Protocol**
- Applicationlayer protocol chạy trên TCP
- Định dạng: `METHOD PATH HTTP/1.1\nHEADERS\n\nBODY`
- Methods: GET, POST, PUT, DELETE, ...

### **Object Serialization**
- Chuyển Java objects thành byte stream
- `ObjectInputStream` / `ObjectOutputStream`
- Dùng để truyền objects qua network

---

## 📦 File Cấu Hình Chi Tiết

### **pom.xml**
- Định nghĩa dependencies (JavaFX)
- Build plugins (Maven compiler, JavaFX plugin)
- Project metadata

### **HttpRequest.java**
- Đại diện HTTP request
- Chứa method, URL, headers, body
- `Serializable` để gửi qua network

### **HttpResponse.java**
- Đại diện HTTP response
- Chứa status code, headers, body
- `Serializable` để gửi qua network

### **WebServer.java**
- Lắng nghe trên TCP port 9090
- Tiếp nhận múi connections từ clients
- Xử lý request trong ClientHandler threads

### **HttpClient.java**
- Kết nối đến server qua TCP
- Gửi HttpRequest objects
- Nhận HttpResponse objects

### **BrowserApplication.java**
- JavaFX Application chính
- UI: toolbar, address bar, content area, status bar
- Xử lý user input và hiển thị kết quả

---

## 🎯 Kiểm Thử Tính Năng

### **Test 1: Truy Cập Trang Chủ**
1. Khởi động Server
2. Khởi động Client
3. Nhấn "Home"
4. ✓ Nên thấy trang chủ HTML

### **Test 2: Điều Hướng**
1. Nhập: `localhost:9090/about`
2. Nhấn "Go"
3. ✓ Nên thấy trang About

### **Test 3: Trang Không Tồn Tại**
1. Nhập: `localhost:9090/notfound`
2. Nhấn "Go"
3. ✓ Nên thấy lỗi 404

### **Test 4: Kết Nối Lỗi**
1. Tắt Server
2. Nhấn Refresh
3. ✓ Nên thấy lỗi "Connection refused"

---

## 💡 Mở Rộng Tính Năng (Advanced)

### **1. Download File**
```java
// Thêm header trong response
response.addHeader("Content-Disposition", "attachment; filename=file.pdf");
```

### **2. Cookie Support**
```java
request.addHeader("Cookie", "sessionId=12345");
response.addHeader("Set-Cookie", "sessionId=12345");
```

### **3. HTTPS Support**
```java
// Sử dụng SSLServerSocket thay vì ServerSocket
SSLServerSocket sslServerSocket = (SSLServerSocket) 
    SSLServerSocketFactory.getDefault().createServerSocket(443);
```

### **4. Web Rendering**
```java
// Thay TextArea bằng WebView để render HTML thực sự
WebView webView = new WebView();
webView.getEngine().loadContent(html);
```

### **5. Caching**
```java
Map<String, HttpResponse> cache = new HashMap<>();
if (cache.containsKey(url)) {
    return cache.get(url);  // Trả từ cache
}
```

---

## 🔗 Liên Kết Hữu Ích

- **Java Network Programming**: https://docs.oracle.com/javase/tutorial/networking/
- **JavaFX Documentation**: https://openjfx.io/
- **HTTP Protocol**: https://tools.ietf.org/html/rfc7230
- **TCP/IP Explained**: https://www.cisco.com/c/en/us/support/docs/ip/

---

## ✅ Checklist Hoàn Thành

- [x] Tạo cấu trúc dự án
- [x] Cấu hình Maven (pom.xml)
- [x] Tạo HTTP Protocol classes (Request, Response)
- [x] Xây dựng Web Server (TCP, port 9090)
- [x] Xây dựng HTTP Client (TCP Socket)
- [x] Tạo giao diện JavaFX
- [x] Hỗ trợ nhiều trang (/, /about, /contact, 404)
- [x] Xử lý lỗi kết nối
- [x] Viết hướng dẫn chi tiết

---

## 📞 Hỗ Trợ

Nếu gặp sự cố:
1. Kiểm tra Java version: `java -version`
2. Kiểm tra Maven: `mvn -v`
3. Xem console output để tìm lỗi chi tiết
4. Chắc chắn port 9090 không bị chiếm
5. Làm lại từ bước: `mvn clean install`

---

**Tạo bởi**: Hướng dẫn lập trình mạng Java
**Phiên bản**: 1.0.0
**Ngày**: 2026-02-24
