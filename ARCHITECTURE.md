# 🏗️ KIẾN TRÚC HỆ THỐNG - UMA Browser

*Tài liệu này mô tả chi tiết kiến trúc TCP, Client-Server, và các thành phần của UMA Browser*

---

## 📐 SƠ ĐỒ KIẾN TRÚC TỔNG THỂ

```
┌─────────────────────────────────────────────────────────────────┐
│                     INTERNET / LOCAL NETWORK                     │
└─────────────────────────────────────────────────────────────────┘
                            ↑↑↑ TCP ↓↓↓
┌──────────────────────────────────────────────────────────────────┐
│                                                                  │
│  ┌─────────────────────────────┐      ┌───────────────────────┐ │
│  │  CLIENT SIDE                │      │  SERVER SIDE          │ │
│  │                             │      │                       │ │
│  │  BrowserApplication.java    │      │  WebServer.java       │ │
│  │  (JavaFX GUI)               │      │  (TCP Server)         │ │
│  │  ┌──────────────────────┐   │      │  ┌─────────────────┐  │ │
│  │  │ UI Components        │   │      │  │ ServerSocket    │  │ │
│  │  │ - Toolbar            │   │      │  │ (Port 9090)     │  │ │
│  │  │ - Address Bar        │   │      │  └─────────────────┘  │ │
│  │  │ - Content Area       │   │      │  ┌─────────────────┐  │ │
│  │  │ - Status Bar         │   │      │  │ ClientHandler   │  │ │
│  │  └──────────────────────┘   │      │  │ (Multi-thread)  │  │ │
│  │           ↑↓                │      │  └─────────────────┘  │ │
│  │  ┌──────────────────────┐   │      │                       │ │
│  │  │ HttpClient.java      │───────→ │  Request Processing    │ │
│  │  │ - Socket connection  │         │  Response Generation   │ │
│  │  │ - Send Request       │←─────── │                       │ │
│  │  │ - Receive Response   │         │  HTML Content:         │ │
│  │  └──────────────────────┘   │      │  - Home page           │ │
│  │           ↑↓                │      │  - About page          │ │
│  │  ┌──────────────────────┐   │      │  - Contact page        │ │
│  │  │ Serialization        │   │      │  - 404 page            │ │
│  │  │ - ObjectInputStream  │   │      │                       │ │
│  │  │ - ObjectOutputStream │   │      │  Serialization         │ │
│  │  └──────────────────────┘   │      │  - ObjectInputStream   │ │
│  │                             │      │  - ObjectOutputStream  │ │
│  └─────────────────────────────┘      └───────────────────────┘ │
│                                                                  │
│                          TCP Socket                              │
│           (Object Serialization over TCP Stream)                │
│                                                                  │
└──────────────────────────────────────────────────────────────────┘
```

---

## 📦 CẤU TRÚC THÀNH PHẦN CHI TIẾT

### 🎯 CLIENT COMPONENTS

#### 1. **BrowserApplication.java** (JavaFX GUI)
```
BrowserApplication
├── Khởi tạo Stage, Scene
├── Tạo UI Components:
│   ├── Toolbar
│   │   ├── Back button
│   │   ├── Forward button
│   │   ├── Refresh button
│   │   ├── Home button
│   │   └── Server selector
│   ├── Address Bar
│   │   ├── URL TextField
│   │   └── Go button
│   ├── Content Area
│   │   └── TextArea (HTML display)
│   └── Status Bar
│       └── Status Label
├── Xử lý sự kiện
│   ├── Button clicks
│   ├── URL bar enter
│   └── Server selection
├── Thread management
│   └── UI updates (Platform.runLater)
└── Error handling
```

#### 2. **HttpClient.java** (TCP Communication)
```
HttpClient
├── Constructor(host, port)
├── sendRequest(HttpRequest)
│   ├── Tạo Socket connection
│   ├── ObjectOutputStream - gửi request
│   ├── ObjectInputStream - nhận response
│   └── Đóng kết nối
├── get(url)
│   └── Tạo GET request
└── post(url, body)
    └── Tạo POST request
```

---

### 🖥️ SERVER COMPONENTS

#### 1. **WebServer.java** (Main Server)
```
WebServer
├── Constructor(port)
├── start()
│   ├── Tạo ServerSocket
│   ├── Accept client connections
│   ├── Thread pool management
│   └── Lắng nghe các request
└── stop()
    └── Đóng ServerSocket
```

#### 2. **ClientHandler.java** (Inner class)
```
ClientHandler implements Runnable
├── Constructor(Socket)
├── run()
│   ├── ObjectInputStream - đọc request
│   ├── handleRequest()
│   │   ├── Phân tích path
│   │   ├── Xác định tính năng
│   │   └── Gọi method tương ứng
│   ├── ObjectOutputStream - gửi response
│   └── Đóng kết nối
├── handleRequest(HttpRequest)
│   └── Router logic
├── getHomePage()
└── getAboutPage()
└── getContactPage()
└── get404Page()
```

---

### 📨 DATA MODELS

#### 1. **HttpRequest.java**
```
HttpRequest (Serializable)
├── method: String (GET, POST, ...)
├── url: String
├── path: String (extracted from URL)
├── httpVersion: String (HTTP/1.1)
├── headers: Map<String, String>
└── body: String

Methods:
├── extractPath(url): String
├── addHeader(key, value): void
└── toString(): String
```

#### 2. **HttpResponse.java**
```
HttpResponse (Serializable)
├── statusCode: int (200, 404, 500, ...)
├── statusMessage: String (OK, Not Found, ...)
├── httpVersion: String (HTTP/1.1)
├── headers: Map<String, String>
└── body: String (HTML content)

Methods:
├── addHeader(key, value): void
└── toString(): String
```

---

## 🔄 LUỒNG GỬI/NHẬN DỮ LIỆU

### Chu Kỳ 1: User Nhập URL

```
┌────────────────────────────────────────────────────────────────┐
│ 1. USER ACTION                                                 │
│                                                                │
│    User nhập: "localhost:9090/about"                           │
│    Nhấn: "Go" button                                          │
└────────────┬───────────────────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────────────────┐
│ 2. URL PROCESSING (BrowserApplication)                        │
│                                                                │
│    loadPageFromBar()                                           │
│    ├── Lấy text từ urlBar
│    ├── Thêm "http://" nếu cần
│    └── Gọi loadPage(url)
│                                                                │
│    loadPage(url)                                              │
│    └── Khởi tạo new Thread:
│        ├── HttpClient.get(url)
│        ├── Cập nhật UI (Platform.runLater)
│        └── Hiển thị kết quả
└────────────┬───────────────────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────────────────┐
│ 3. REQUEST CREATION (HttpClient)                              │
│                                                                │
│    HttpRequest request = new HttpRequest("GET", url)         │
│    request.addHeader("User-Agent", "UMA-Browser/1.0")        │
│                                                                │
│    Nội dung:                                                  │
│    - method = "GET"                                           │
│    - url = "http://localhost:9090/about"                      │
│    - path = "/about"                                          │
│    - headers = {User-Agent: UMA-Browser/1.0}                  │
└────────────┬───────────────────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────────────────┐
│ 4. SERIALIZATION & TCP TRANSMISSION                           │
│                                                                │
│    Socket socket = new Socket("localhost", 9090)              │
│    ObjectOutputStream oos = new ObjectOutputStream(...)       │
│                                                                │
│    oos.writeObject(request)  ← Chuyển object thành bytes     │
│    oos.flush()  ← Gửi ngay                                   │
│                                                                │
│    Bytes truyền qua TCP:                                       │
│    │AC ED 00 05 │ 00 73 72 │ ... │ (binary data)             │
│     (Java magic) (version)                                     │
└────────────┬───────────────────────────────────────────────────┘
             │
             ↓ TCP NETWORK
             │
             ↓
┌────────────────────────────────────────────────────────────────┐
│ 5. SERVER RECEIVES (WebServer)                                │
│                                                                │
│    ServerSocket.accept()  ← Chờ connection từ client         │
│    Socket clientSocket = accepted connection                 │
│                                                                │
│    ClientHandler handler = new ClientHandler(socket)         │
│    Thread thread = new Thread(handler)                        │
│    thread.start()  ← Xử lý trong thread khác                │
└────────────┬───────────────────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────────────────┐
│ 6. DESERIALIZATION & REQUEST PROCESSING                       │
│                                                                │
│    ObjectInputStream ois = new ObjectInputStream(...)         │
│    HttpRequest request = (HttpRequest) ois.readObject()      │
│                                                                │
│    Bytes được chuyển lại thành Java object:                   │
│    HttpRequest {                                              │
│        method = "GET"                                         │
│        url = "http://localhost:9090/about"                    │
│        path = "/about"                                        │
│        headers = {User-Agent: UMA-Browser/1.0}                │
│    }                                                          │
│                                                                │
│    handleRequest(request)                                     │
│    ├── Kiểm tra path: "/about"
│    ├── Match: case "/about":
│    └── Gọi: getAboutPage()
└────────────┬───────────────────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────────────────┐
│ 7. RESPONSE GENERATION                                        │
│                                                                │
│    String html = "<!DOCTYPE html>...<body>.../body></html>"  │
│                                                                │
│    HttpResponse response = new HttpResponse(200, "OK", html) │
│    response.addHeader("Content-Type", "text/html;...")       │
│                                                                │
│    Nội dung Response:                                         │
│    - statusCode = 200
│    - statusMessage = "OK"
│    - body = HTML string
│    - headers = {Content-Type: text/html}
└────────────┬───────────────────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────────────────┐
│ 8. RESPONSE SERIALIZATION & TCP TRANSMISSION                  │
│                                                                │
│    ObjectOutputStream oos = ...                               │
│    oos.writeObject(response)  ← Serialize response           │
│    oos.flush()  ← Gửi                                        │
│                                                                │
│    Bytes truyền qua TCP (chiều ngược lại)
└────────────┬───────────────────────────────────────────────────┘
             │
             ↓ TCP NETWORK
             │
             ↓
┌────────────────────────────────────────────────────────────────┐
│ 9. CLIENT RECEIVES RESPONSE                                   │
│                                                                │
│    ObjectInputStream ois = ...                                │
│    HttpResponse response = (HttpResponse)ois.readObject()    │
│                                                                │
│    Đối tượng được reconstruct:                                │
│    HttpResponse {                                             │
│        statusCode = 200
│        statusMessage = "OK"
│        body = HTML string
│        headers = {Content-Type: text/html}
│    }
└────────────┬───────────────────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────────────────┐
│ 10. UI UPDATE                                                 │
│                                                                │
│    Platform.runLater(() -> {                                 │
│        if (response.getStatusCode() == 200) {                │
│            contentArea.setText(response.getBody())           │
│            statusLabel.setText("✓ Trang đã tải...")          │
│        }                                                      │
│    })                                                         │
│                                                                │
│    Kết quả:                                                  │
│    - TextArea hiển thị HTML content
│    - Status bar cập nhật
│    - URL bar shows URL
└────────────┬───────────────────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────────────────┐
│ 11. CONNECTION CLEANUP                                        │
│                                                                │
│    ois.close()  ← Đóng stream
│    oos.close()  ← Đóng stream
│    socket.close()  ← Đóng socket
│                                                                │
│    Kết nối TCP kết thúc
└────────────────────────────────────────────────────────────────┘
```

---

## 🔌 TCP SOCKET COMMUNICATION chi tiết

### TCP 3-Way Handshake (SYN, SYN-ACK, ACK)

```
CLIENT                                    SERVER

Socket("localhost", 9090)
    │
    ├─ SYN ──────────────────────────→ Listen on 9090
    │                                     │
    │                                ServerSocket.accept()
    │
    │← SYN-ACK ─────────────────────────|
    │
    ├─ ACK ──────────────────────────→ Connection established
    │
    Connected! ←────────────────────→ Connected!

```

### Object Serialization Format

```
Java Object:
┌──────────────────────────────┐
│ HttpRequest {                │
│   method = "GET"             │
│   url = "http://..."         │
│   path = "/about"            │
│   headers = Map (5 items)     │
│   body = null                │
│ }                            │
└──────────────────────────────┘
           ↓↓↓ Serialize
┌──────────────────────────────┐
│ STREAM (Binary):             │
│                              │
│ AC ED 00 05  ← Magic number  │
│ 00 05        ← Type          │
│ 00 73        ← Class name    │
│ 72 00 ...    ← Field data    │
│ ...                          │
└──────────────────────────────┘
           ↓↓↓ TCP Network
┌──────────────────────────────┐
│ TCP Packets (fragmented):    │
│                              │
│ Packet 1: [AC ED 00 05 00...]│
│ Packet 2: [05 00 73 72 ...]  │
│ Packet 3: [...]              │
│ ...                          │
└──────────────────────────────┘
           ↓↓↓ Reconstruct
┌──────────────────────────────┐
│ Java Object (đã restore):    │
│ HttpRequest {...}             │
└──────────────────────────────┘
```

---

## 🗂️ CLASS DIAGRAM

```
┌─────────────────────────┐
│    <<interface>>        │
│      Serializable       │
└─────────────────────────┘
         ▲         ▲
         │         │
    ┌────┴────┐    │
    │          │    │
┌───┴──────┐  │    │
│HttpRequest│  │    │
│─────────  │  │    │
│-method    │  │ ┌──┴──────────┐
│-url       │  │ │ HttpResponse│
│-path      │  │ │─────────────│
│-headers   │  │ │-statusCode  │
│-body      │  │ │-statusMsg   │
│─────────  │  │ │-headers     │
│+toString()│  │ │-body        │
└──────────┘  │ │─────────────│
              │ │+toString()  │
              │ └─────────────┘
              └──────────────────


┌───────────────────────┐
│  BrowserApplication   │
│    extends           │
│  Application (JavaFX) │
├───────────────────────┤
│-httpClient            │
│-urlBar               │
│-contentArea          │
│-statusLabel          │
├───────────────────────┤
│+start(Stage)         │
│-createUI()           │
│-loadPage(url)        │
│-createToolbar()      │
│-createAddressBar()   │
│-createContentArea()  │
│-createStatusBar()    │
└───────────────────────┘
         │
         │ uses
         ↓
      ┌──────────────┐
      │  HttpClient  │
      ├──────────────┤
      │-host         │
      │-port         │
      ├──────────────┤
      │+HttpClient() │
      │+sendRequest()│
      │+get(url)     │
      │+post()       │
      └──────────────┘
             │
             │ creates/uses
             ↓
         ┌─────────┐
         │ Socket  │ (java.net)
         └─────────┘


┌──────────────────────┐
│    WebServer         │
├──────────────────────┤
│-port                │
│-serverSocket        │
│-running             │
├──────────────────────┤
│+start()             │
│+stop()              │
│+main()              │
└──────────────────────┘
         │
         │ delegates
         ↓
┌──────────────────────────┐
│   ClientHandler (inner)  │
│ implements Runnable      │
├──────────────────────────┤
│-clientSocket            │
├──────────────────────────┤
│+run()                   │
│-handleRequest()         │
│-getHomePage()          │
│-getAboutPage()         │
│-getContactPage()       │
│-get404Page()           │
└──────────────────────────┘
```

---

## 🔐 THREAD SAFETY & MULTI-THREADING

### Server Side (Multi-threaded)

```
                Main Thread
                    │
                    ├─ ServerSocket (port 9090)
                    │
                    └─ Accept loop:
                       │
                       ├─ while (running)
                       │  │
                       │  ├─ Accept client 1
                       │  │   └─ Create ClientHandler 1
                       │  │       └─ Start Thread 1
                       │  │
                       │  ├─ Accept client 2
                       │  │   └─ Create ClientHandler 2
                       │  │       └─ Start Thread 2
                       │  │
                       │  └─ Accept client 3
                       │      └─ Create ClientHandler 3
                       │          └─ Start Thread 3

Thread 1 (Client 1)    Thread 2 (Client 2)    Thread 3 (Client 3)
    │                      │                      │
    ├─ Read request        ├─ Read request        ├─ Read request
    ├─ Process             ├─ Process             ├─ Process
    ├─ Send response       ├─ Send response       ├─ Send response
    └─ Close               └─ Close               └─ Close
```

**Benefit:** Mỗi client được xư lý độc lập, không block clients khác

### Client Side (Single thread + JavaFX thread)

```
Main JavaFX Thread          Network Thread
        │                        │
        ├─ Show UI               │
        │                        │
        ├─ User clicks "Go"      │
        │  └─ Create new Thread ─┤
        │                        │
        ├─ Continue UI           ├─ Connect to server
        │  (not blocked!)        ├─ Send request
        │                        ├─ Wait for response
        │                        └─ Call Platform.runLater()
        │                             │
        ├─ Update UI ←───────────────┘
        │  (setText, etc.)
```

**Benefit:** UI không bị hang khi chờ network response

---

## 💾 DATA FLOW SUMMARY

```
USER INPUT (URL)
    │
    ↓
URL Processing (format, validate)
    │
    ↓
Create HttpRequest (with headers)
    │
    ↓
Serialize to bytes
    │
    ↓
Create TCP Socket
    │
    ↓
Send bytes through socket
    │
    ↓ (Network)
    │
    ↓
Server receives bytes
    │
    ↓
Deserialize to HttpRequest
    │
    ↓
Parse path & route
    │
    ↓
Generate HTML content
    │
    ↓
Create HttpResponse
    │
    ↓
Serialize to bytes
    │
    ↓
Send bytes through socket
    │
    ↓ (Network)
    │
    ↓
Client receives bytes
    │
    ↓
Deserialize to HttpResponse
    │
    ↓
Extract body (HTML)
    │
    ↓
Update JavaFX TextArea
    │
    ↓
USER SEES WEBPAGE
```

---

## 🚀 SCALING & IMPROVEMENTS

### Có thể cải tiến:

1. **Connection Pooling**
   - Reuse socket connections
   - Giảm overhead

2. **Caching**
   - Cache responses
   - Faster access

3. **Load Balancing**
   - Multiple servers
   - Distributed load

4. **HTTPS/SSL**
   - Secure connection
   - SSLServerSocket

5. **Database**
   - Store dynamic content
   - User management

6. **Real HTML Rendering**
   - Thay TextArea bằng WebView
   - CSS, JavaScript support

7. **Session Management**
   - Track users
   - State persistence

---

## 📊 PROTOCOL DETAILS

### HTTP Request Format (Text)

```
GET /about HTTP/1.1
User-Agent: UMA-Browser/1.0
Connection: close

```

### HTTP Response Format (Text)

```
HTTP/1.1 200 OK
Content-Type: text/html; charset=UTF-8
Content-Length: 1234

<!DOCTYPE html>
...
</html>
```

### Java Serialization Format (Binary)

```
Magic number:  AC ED
Version:       00 05
Type code:     73 (TC_OBJECT)
Class desc:    XX XX
Field count:   00 02
Field:         method (String)
Field:         url (String)
...
Data:          [variable length values]
```

---

**Architecture Version: 1.0**
**Last Updated: 2026-02-24**
