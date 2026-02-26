# 📑 INDEX - UMA Browser Project

## Danh sách đầy đủ tất cả files và tài liệu

---

## 📂 CẤU TRÚC DỰ ÁN

```text
UMA-Browser_no.1/
│
├── 📄 Tài Liệu (Documentation)
│   ├── README.md                    ← Hướng dẫn chính
│   ├── QUICK_START.md               ← Bắt đầu nhanh (1 thao tác)
│   ├── ONE_CLICK_STARTUP.md         ← Chi tiết khởi động một cách
│   ├── STARTUP.md                   ← Menu tương tác
│   ├── INSTALLATION_GUIDE.md        ← Cài đặt chi tiết
│   ├── ARCHITECTURE.md              ← Kiến trúc hệ thống
│   └── INDEX.md                     ← File này
│
├── 🔧 Script Chạy (Scripts)
│   ├── start-all.bat                ← ⭐ Khởi động cả Server+Client (Windows)
│   ├── start.bat                    ← Menu tương tác (Windows)
│   ├── start-all.sh                 ← ⭐ Khởi động cả Server+Client (Mac/Linux)
│   ├── start.sh                     ← Menu tương tác (Mac/Linux)
│   ├── run_server.bat               ← Chỉ Server (Windows)
│   ├── run_client.bat               ← Chỉ Client (Windows)
│   └── pom.xml                      ← Maven configuration
│
└── 📦 Source Code
    └── src/main/java/
        ├── server/
        │   └── WebServer.java       ← TCP Server chính
        │
        ├── client/
        │   ├── HttpClient.java      ← TCP Client
        │   └── BrowserApplication.java ← JavaFX UI
        │
        └── util/
            ├── HttpRequest.java     ← HTTP Request model
            └── HttpResponse.java    ← HTTP Response model

```

---

## 📄 TÀI LIỆU CHI TIẾT

### 🚀 Để bắt đầu lập tức

👉 [QUICK_START.md](QUICK_START.md) - Chỉ 1 thao tác! Double-click `start-all.bat`

### 🎯 Để khởi động tự động

👉 [ONE_CLICK_STARTUP.md](ONE_CLICK_STARTUP.md) - Chi tiết cách khởi động một cách

### 📖 Để hiểu chi tiết

👉 [README.md](README.md) - Hướng dẫn hoàn chỉnh, khái niệm, kiểm thử

### ⚙️ Để cài đặt bước-bước

👉 [INSTALLATION_GUIDE.md](INSTALLATION_GUIDE.md) - JDK, Maven, cài sự cố

### 🏗️ Để hiểu kiến trúc

👉 [ARCHITECTURE.md](ARCHITECTURE.md) - TCP, Client-Server, Serialization

---

## 📦 SOURCE CODE FILES

### Server (`src/main/java/server/`)

#### WebServer.java (570 dòng)

Máy chủ TCP lắng nghe port 9090

**Tính năng chính:**

- ServerSocket lắng nghe clients
- Multi-threaded handling (ClientHandler)
- 5 trang HTML có sẵn
- HTTP response generation
- Xử lý lỗi

**Các page khả dụng:**

- `/` - Trang chủ
- `/about` - Về chúng tôi
- `/contact` - Liên hệ
- `/chapter` - Đọc chương trình (Hướng dẫn chi tiết)
- `/notfound` - Error 404

**Class chính:**

```java
public class WebServer {
    public void start() { ... }
    public void stop() { ... }
    public static void main(String[] args) { ... }
}

private static class ClientHandler implements Runnable { ... }
```

---

### Client (`src/main/java/client/`)

#### HttpClient.java (80 dòng)

Client TCP kết nối đến server

**Tính năng chính:**

- Tạo Socket connection
- Object serialization
- Get/Post requests
- Response receiving

**Methods:**

```java
public class HttpClient {
    public HttpResponse sendRequest(HttpRequest request) { ... }
    public HttpResponse get(String url) { ... }
    public HttpResponse post(String url, String body) { ... }
}
```

#### BrowserApplication.java (450 dòng)

Giao diện JavaFX cho trình duyệt

**UI Components:**

- Toolbar (Back, Forward, Refresh, Home, Server selector)
- Address Bar (URL input + Go button)
- Content Area (TextArea để hiển thị HTML)
- Status Bar (Trạng thái)

**Public Methods:**

```java
public class BrowserApplication extends Application {
    @Override public void start(Stage primaryStage) { ... }
    private VBox createUI() { ... }
    private void loadPage(String url) { ... }
}
```

---

### Utilities (`src/main/java/util/`)

#### HttpRequest.java (110 dòng)

Model cho HTTP Request

**Fields:**

```java
private String method;              // GET, POST
private String url;                 // Full URL
private String path;                // /about
private String httpVersion;         // HTTP/1.1
private Map<String, String> headers;
private String body;
```

#### HttpResponse.java (90 dòng)

Model cho HTTP Response

**Fields:**

```java
private int statusCode;             // 200, 404
private String statusMessage;       // OK, Not Found
private String httpVersion;         // HTTP/1.1
private Map<String, String> headers;
private String body;                // HTML content
```

---

## 🔧 CONFIGURATION FILE

### pom.xml (90 dòng)

Maven Project Object Model

**Key sections:**

```xml
<project>
  <groupId>com.umabrowser</groupId>
  <artifactId>uma-browser</artifactId>
  <version>1.0.0</version>
  
  <dependencies>
    <!-- JavaFX 21.0.1 -->
  </dependencies>
  
  <build>
    <plugins>
      <!-- Maven compiler -->
      <!-- JavaFX Maven plugin -->
    </plugins>
  </build>
</project>
```

**Plugins:**

- maven-compiler-plugin (Java 11)
- javafx-maven-plugin (run client)

**Dependencies:**

- javafx-controls
- javafx-fxml
- javafx-web
- javafx-graphics

---

## 🚀 SCRIPT FILES

### start-all.bat (Windows - Khuyên dùng)

**Để:** Khởi động Server + Client tự động

**Làm:**

1. Build dependencies (nếu cần)
2. Khởi động Server (cửa sổ mới)
3. Chờ 5 giây
4. Khởi động Client (cửa sổ hiện tại)
5. Hỏi có đóng server khi client tắt

**Cách dùng:**

```bash
Double-click start-all.bat
```

### start-all.sh (Mac/Linux - Khuyên dùng)

**Để:** Khởi động Server + Client tự động

**Cách dùng:**

```bash
bash start-all.sh
```

### start.bat (Windows - Menu tương tác)

**Để:** Menu chọn (Server, Client, Both, Build)

**Cách dùng:**

```bash
start.bat
```

### start.sh (Mac/Linux - Menu tương tác)

**Để:** Menu chọn (Server, Client, Both, Build)

**Cách dùng:**

```bash
bash start.sh
```

### run_server.bat (Windows)

**Để:** Chạy chỉ Server

**Cách dùng:**

```bash
run_server.bat
```

### run_client.bat (Windows)

**Để:** Chạy chỉ Client

**Cách dùng:**

```bash
run_client.bat
```

---

## 📊 PROJECT STATISTICS

| Metric | Value |
| --- | --- |
| Total Lines of Code | ~1,500 |
| Java Classes | 5 |
| Documentation Pages | 5 |
| Server Port | 9090 |
| Min Java Version | JDK 11 |
| JavaFX Version | 21.0.1 |
| Maven Version | 3.6.3+ |

---

## 🎯 FLOW DỰ ÁN

### Development Flow

```text
1. User mở Browser (BrowserApplication)
2. Nhập URL, nhấn "Go"
3. Client tạo HttpRequest
4. Gửi qua TCP Socket
5. Server nhận, deserialize
6. ClientHandler xử lý
7. Generate HttpResponse
8. Gửi qua TCP Socket
9. Client nhận, deserialize
10. Update JavaFX UI
11. Hiển thị HTML
```

### File Dependencies

```text
BrowserApplication.java
    ↓
HttpClient.java
    ↓
HttpRequest.java
    ↓
TCP Socket

WebServer.java
    ↓
ClientHandler (inner class)
    ↓
HttpResponse.java
    ↓
TCP Socket
```

---

## 🔑 KEY CONCEPTS

| Concept | File | Explanation |
| --- | --- | --- |
| TCP Server | WebServer.java | ServerSocket, multi-threaded |
| TCP Client | HttpClient.java | Socket, send/receive |
| Serialization | HttpRequest, Response | Object → bytes |
| GUI | BrowserApplication.java | JavaFX components |
| HTTP Protocol | HttpRequest, Response | GET, headers, body |
| Routing | ClientHandler | Path-based content |

---

## 📚 VÀ CÓ THỂ TÌMTHÊM

### Documentation Ngoài

- **Official JavaFX Docs**: [https://openjfx.io/](https://openjfx.io/)
- **Java Networking**: [https://docs.oracle.com/javase/tutorial/networking/](https://docs.oracle.com/javase/tutorial/networking/)
- **HTTP RFC**: [https://tools.ietf.org/html/rfc7230](https://tools.ietf.org/html/rfc7230)
- **Maven**: [https://maven.apache.org/](https://maven.apache.org/)

### Concepts Cần Học

- TCP/IP Networking
- Object Serialization
- Multi-threading
- JavaFX UI Framework
- HTTP Protocol
- Maven Build System

---

## ✅ CHECKLIST KHÁM PHÁ

- [ ] Đọc QUICK_START.md
- [ ] Chạy server.bat
- [ ] Chạy client.bat
- [ ] Navigate giữa các trang
- [ ] Đọc README.md (detailed guide)
- [ ] Đọc INSTALLATION_GUIDE.md
- [ ] Hiểu ARCHITECTURE.md
- [ ] Khám phá source code
  - [ ] WebServer.java
  - [ ] BrowserApplication.java
  - [ ] HttpClient.java
  - [ ] HttpRequest/Response.java
- [ ] Thử modify code (add new page)
- [ ] Chạy lại để test changes

---

## 🎓 LEARNING PATH

### Level 1: User (Chạy ứng dụng)

1. QUICK_START.md (5 min)
2. Chạy 2 script
3. Sử dụng Browser

### Level 2: Developer (Hiểu code)

1. README.md (30 min)
2. ARCHITECTURE.md (45 min)
3. Đọc source code (60 min)

### Level 3: Advanced (Modify code)

1. Add new page
2. Modify HTML content
3. Change styling
4. Add POST handler

---

## 📞 SUPPORT

**Có vấn đề?**

1. Kiểm tra QUICK_START.md
2. Đọc INSTALLATION_GUIDE.md (Troubleshooting section)
3. Xem lỗi console output
4. Google error message
5. Đọc ARCHITECTURE.md để hiểu flow

## Common Issues

- Port in use → Dùng port khác
- Java not found → Install JDK
- Build fail → `mvn clean install`
- Connection refused → Server không chạy

---

## 🔄 UPDATE LOG

### v1.0.0 (2026-02-24)

- ✅ Initial release
- ✅ Server with 4 pages
- ✅ Client GUI
- ✅ Complete documentation
- ✅ Multi-threaded handling
- ✅ TCP serialization

---

## 📄 LICENSE & CREDITS

**Type:** Educational Project
**Created:** 2026-02-24
**Purpose:** Learn Java Networking + JavaFX
**Used Technologies:** Java 11+, JavaFX 21, Maven, TCP/IP

---

## 🎉 READY TO START

### Quick Links

1. ⚡ [QUICK_START.md](QUICK_START.md) - 3 steps, run now!
2. 📖 [README.md](README.md) - Detailed guide
3. ⚙️ [INSTALLATION_GUIDE.md](INSTALLATION_GUIDE.md) - Setup help
4. 🏗️ [ARCHITECTURE.md](ARCHITECTURE.md) - Technical deep dive

---

Have fun exploring! 🚀
