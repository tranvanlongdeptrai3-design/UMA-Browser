# 💡 EXAMPLES & EXTENSIONS - UMA Browser

*Ví dụ code và cách mở rộng tính năng*

---

## 📌 MỤC LỤC

1. [Ví dụ Cơ Bản](#ví-dụ-cơ-bản)
2. [Thêm Trang Mới](#thêm-trang-mới)
3. [Thêm POST Handler](#thêm-post-handler)
4. [Style HTML](#style-html)
5. [Thay Đổi Giao Diện](#thay-đổi-giao-diện)
6. [Nâng Cao](#nâng-cao)

---

## <a name="ví-dụ-cơ-bản"></a>1️⃣ VÍ DỤ CƠ BẢN

### Khởi Tạo Server

```java
// WebServer.java - main method
public static void main(String[] args) {
    int port = 9090;
    
    if (args.length > 0) {
        port = Integer.parseInt(args[0]);
    }
    
    WebServer server = new WebServer(port);
    server.start();  // Bắt đầu lắng nghe
}
```

### Khởi Tạo Client

```java
// HttpClient.java
HttpClient client = new HttpClient("localhost", 9090);

// Gửi GET request
HttpResponse response = client.get("http://localhost:9090/");
System.out.println("Status: " + response.getStatusCode());
System.out.println("Body: " + response.getBody());
```

### Sử Dụng Browser GUI

```java
// BrowserApplication.java
public class MyBrowser {
    public static void main(String[] args) {
        BrowserApplication.main(args);  // Khởi động GUI
    }
}
```

---

## <a name="thêm-trang-mới"></a>2️⃣ THÊM TRANG MỚI

### Bước 1: Thêm Case trong handleRequest()

Mở **WebServer.java**, tìm method `handleRequest()`:

```java
private HttpResponse handleRequest(HttpRequest request) {
    String path = request.getPath().isEmpty() ? "/" : request.getPath();
    
    if (path.equals("/")) {
        return getHomePage();
    } else if (path.equals("/about")) {
        return getAboutPage();
    } else if (path.equals("/contact")) {
        return getContactPage();
    
    // ✨ THÊM TRANG MỚI:
    } else if (path.equals("/tutorial")) {
        return getTutorialPage();
    
    } else {
        return get404Page(path);
    }
}
```

### Bước 2: Tạo Method Tương Ứng

```java
private HttpResponse getTutorialPage() {
    String html = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Hướng Dẫn Sử Dụng</title>\n" +
            "    <style>\n" +
            "        body { font-family: Arial; margin: 20px; }\n" +
            "        h1 { color: #0066cc; }\n" +
            "        .step { background: #f0f0f0; padding: 10px; margin: 10px 0; }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <h1>📚 Hướng Dẫn Sử Dụng Browser</h1>\n" +
            "    <div class=\"step\">\n" +
            "        <h2>Bước 1: Nhập URL</h2>\n" +
            "        <p>Sử dụng thanh địa chỉ để nhập trang muốn truy cập</p>\n" +
            "    </div>\n" +
            "    <div class=\"step\">\n" +
            "        <h2>Bước 2: Nhấn Go hoặc Enter</h2>\n" +
            "        <p>Browser sẽ tải trang</p>\n" +
            "    </div>\n" +
            "    <p><a href=\"/\">← Quay lại trang chủ</a></p>\n" +
            "</body>\n" +
            "</html>";
    
    HttpResponse response = new HttpResponse(200, "OK", html);
    response.addHeader("Content-Type", "text/html; charset=UTF-8");
    return response;
}
```

### Bước 3: Rebuild & Test

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="server.WebServer"

# Trong client, nhập URL: localhost:9090/tutorial
```

---

## <a name="thêm-post-handler"></a>3️⃣ THÊM POST HANDLER

### Bước 1: Tạo Trang HTML với Form

```java
private HttpResponse getFormPage() {
    String html = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <title>Contact Form</title>\n" +
            "    <style>\n" +
            "        body { font-family: Arial; margin: 20px; }\n" +
            "        .form-group { margin: 15px 0; }\n" +
            "        label { display: block; margin-bottom: 5px; }\n" +
            "        input, textarea { padding: 8px; width: 300px; }\n" +
            "        button { padding: 10px 20px; background: #0066cc; color: white; }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <h1>📝 Liên Hệ Chúng Tôi</h1>\n" +
            "    <form method=\"POST\" action=\"/submit-form\">\n" +
            "        <div class=\"form-group\">\n" +
            "            <label for=\"name\">Tên:</label>\n" +
            "            <input type=\"text\" id=\"name\" name=\"name\" required>\n" +
            "        </div>\n" +
            "        <div class=\"form-group\">\n" +
            "            <label for=\"email\">Email:</label>\n" +
            "            <input type=\"email\" id=\"email\" name=\"email\" required>\n" +
            "        </div>\n" +
            "        <div class=\"form-group\">\n" +
            "            <label for=\"message\">Tin nhắn:</label>\n" +
            "            <textarea id=\"message\" name=\"message\" required></textarea>\n" +
            "        </div>\n" +
            "        <button type=\"submit\">Gửi</button>\n" +
            "    </form>\n" +
            "    <p><a href=\"/\">← Quay lại</a></p>\n" +
            "</body>\n" +
            "</html>";
    
    HttpResponse response = new HttpResponse(200, "OK", html);
    response.addHeader("Content-Type", "text/html; charset=UTF-8");
    return response;
}
```

### Bước 2: Xử Lý POST Request

```java
private HttpResponse handleRequest(HttpRequest request) {
    String path = request.getPath();
    
    // ✨ THÊM POST HANDLER
    if (request.getMethod().equals("POST")) {
        if (path.equals("/submit-form")) {
            return handleFormSubmit(request);
        }
    }
    
    // GET requests
    if (path.equals("/form")) {
        return getFormPage();
    }
    // ... other GET handlers
}

private HttpResponse handleFormSubmit(HttpRequest request) {
    String body = request.getBody();
    // Parse: name=John&email=john@example.com&message=Hello
    
    String html = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head><title>Thank You</title></head>\n" +
            "<body>\n" +
            "    <h1>✓ Cảm ơn!</h1>\n" +
            "    <p>Chúng tôi đã nhận được tin nhắn của bạn.</p>\n" +
            "    <p>Form data: " + body + "</p>\n" +
            "    <p><a href=\"/\">← Quay lại</a></p>\n" +
            "</body>\n" +
            "</html>";
    
    HttpResponse response = new HttpResponse(200, "OK", html);
    response.addHeader("Content-Type", "text/html; charset=UTF-8");
    return response;
}
```

---

## <a name="style-html"></a>4️⃣ STYLE HTML (CSS)

### Ví Dụ HTML với CSS Đẹp

```java
private HttpResponse getStyledPage() {
    String html = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Styled Page</title>\n" +
            "    <style>\n" +
            "        * {\n" +
            "            margin: 0;\n" +
            "            padding: 0;\n" +
            "            box-sizing: border-box;\n" +
            "        }\n" +
            "        \n" +
            "        body {\n" +
            "            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n" +
            "            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
            "            min-height: 100vh;\n" +
            "            padding: 20px;\n" +
            "        }\n" +
            "        \n" +
            "        .container {\n" +
            "            max-width: 800px;\n" +
            "            margin: 0 auto;\n" +
            "            background: white;\n" +
            "            border-radius: 10px;\n" +
            "            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);\n" +
            "            padding: 40px;\n" +
            "        }\n" +
            "        \n" +
            "        h1 {\n" +
            "            color: #333;\n" +
            "            margin-bottom: 20px;\n" +
            "            text-align: center;\n" +
            "        }\n" +
            "        \n" +
            "        .grid {\n" +
            "            display: grid;\n" +
            "            grid-template-columns: 1fr 1fr;\n" +
            "            gap: 20px;\n" +
            "            margin: 20px 0;\n" +
            "        }\n" +
            "        \n" +
            "        .card {\n" +
            "            background: #f8f9fa;\n" +
            "            padding: 20px;\n" +
            "            border-radius: 8px;\n" +
            "            border-left: 4px solid #667eea;\n" +
            "        }\n" +
            "        \n" +
            "        .card h2 {\n" +
            "            color: #667eea;\n" +
            "            margin-bottom: 10px;\n" +
            "        }\n" +
            "        \n" +
            "        a {\n" +
            "            color: #667eea;\n" +
            "            text-decoration: none;\n" +
            "        }\n" +
            "        \n" +
            "        a:hover {\n" +
            "            text-decoration: underline;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <div class=\"container\">\n" +
            "        <h1>🎨 Trang Style Đẹp</h1>\n" +
            "        <div class=\"grid\">\n" +
            "            <div class=\"card\">\n" +
            "                <h2>🚀 Tính Năng 1</h2>\n" +
            "                <p>Mô tả tính năng</p>\n" +
            "            </div>\n" +
            "            <div class=\"card\">\n" +
            "                <h2>✨ Tính Năng 2</h2>\n" +
            "                <p>Mô tả tính năng</p>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "        <p style=\"text-align: center;\">\n" +
            "            <a href=\"/\">← Quay lại trang chủ</a>\n" +
            "        </p>\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>";
    
    HttpResponse response = new HttpResponse(200, "OK", html);
    response.addHeader("Content-Type", "text/html; charset=UTF-8");
    return response;
}
```

---

## <a name="thay-đổi-giao-diện"></a>5️⃣ THAY ĐỔI GIAO DIỆN JAVAFX

### Thay Đổi Màu Sắc & Font

```java
// BrowserApplication.java - method createUI()

private VBox createUI() {
    VBox root = new VBox();
    root.setPadding(new Insets(10));
    root.setSpacing(10);
    root.setStyle("-fx-background-color: #f0f0f0;");  // ✨ Thay đổi màu nền
    
    // Toolbar
    HBox toolbar = createToolbar();
    toolbar.setStyle("-fx-background-color: #2c3e50; -fx-border-color: #95a5a6;");  // ✨ Đen
    
    // ... rest of code
    
    return root;
}
```

### Thêm Menu Bar

```java
private MenuBar createMenuBar() {
    MenuBar menuBar = new MenuBar();
    
    // File Menu
    Menu fileMenu = new Menu("File");
    MenuItem newTab = new MenuItem("New Tab");
    MenuItem exit = new MenuItem("Exit");
    exit.setOnAction(e -> Platform.exit());
    fileMenu.getItems().addAll(newTab, new SeparatorMenuItem(), exit);
    
    // Help Menu
    Menu helpMenu = new Menu("Help");
    MenuItem about = new MenuItem("About");
    about.setOnAction(e -> showAlert("About", "UMA Browser v1.0"));
    helpMenu.getItems().add(about);
    
    menuBar.getMenus().addAll(fileMenu, helpMenu);
    return menuBar;
}
```

Thêm vào `createUI()`:
```java
VBox root = new VBox();

// ✨ Thêm menu bar
MenuBar menuBar = createMenuBar();
root.getChildren().add(0, menuBar);

// ... rest
```

---

## <a name="nâng-cao"></a>6️⃣ NÂNG CAO

### Caching Responses

```java
// HttpClient.java
private Map<String, HttpResponse> cache = new HashMap<>();

public HttpResponse get(String url) throws IOException {
    // ✨ Kiểm tra cache trước
    if (cache.containsKey(url)) {
        System.out.println("Cache hit: " + url);
        return cache.get(url);
    }
    
    HttpRequest request = new HttpRequest("GET", url);
    request.addHeader("User-Agent", "UMA-Browser/1.0");
    HttpResponse response = sendRequest(request);
    
    // ✨ Lưu vào cache
    cache.put(url, response);
    
    return response;
}

public void clearCache() {
    cache.clear();
}
```

### HTTPS/SSL Support

```java
// WebServer.java
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public void startSSL(String kesytorePath, String keyPassword) {
    try {
        System.setProperty("javax.net.ssl.keyStore", keystorePath);
        System.setProperty("javax.net.ssl.keyStorePassword", keyPassword);
        
        SSLServerSocketFactory factory = 
            (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        
        SSLServerSocket sslServerSocket = 
            (SSLServerSocket) factory.createServerSocket(9443);
        
        System.out.println("🔒 HTTPS Server đã khởi động trên port 9443");
        // ... accept connections
        
    } catch (Exception e) {
        System.err.println("Lỗi SSL: " + e.getMessage());
    }
}
```

### Database Integration

```java
// WebServer.java - add database support
import java.sql.*;

private HttpResponse getDatabasePage() {
    try {
        // Kết nối database
        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/mydb",
            "user", "password"
        );
        
        // Query
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM pages");
        
        // Build HTML từ database
        StringBuilder html = new StringBuilder();
        html.append("<html><body><h1>Database Results</h1><ul>");
        
        while (rs.next()) {
            html.append("<li>").append(rs.getString("title")).append("</li>");
        }
        
        html.append("</ul></body></html>");
        
        rs.close();
        stmt.close();
        conn.close();
        
        HttpResponse response = new HttpResponse(200, "OK", html.toString());
        response.addHeader("Content-Type", "text/html; charset=UTF-8");
        return response;
        
    } catch (SQLException e) {
        return new HttpResponse(500, "Server Error", 
            "Database error: " + e.getMessage());
    }
}
```

### Real HTML Rendering (WebView)

```java
// BrowserApplication.java
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;

private VBox createContentArea() {
    VBox contentSection = new VBox();
    
    // ✨ Thay TextArea bằng WebView
    WebView webView = new WebView();
    WebEngine webEngine = webView.getEngine();
    
    // Load HTML content
    webEngine.loadContent("<html><body><h1>Hello</h1></body></html>");
    
    contentSection.getChildren().add(webView);
    VBox.setVgrow(webView, Priority.ALWAYS);
    
    return contentSection;
}
```

---

## 🎯 CHECKLIST THỰC HÀNH

- [ ] Thêm trang `/tutorial`
- [ ] Thêm `/form` với POST handler
- [ ] Style trang web với CSS đẹp
- [ ] Thên menu bar trong GUI
- [ ] Implement response caching
- [ ] Thử thêm WebView để render HTML thực
- [ ] Thực hiện HTTPS support
- [ ] Kết nối database (MySQL)

---

**Happy Coding! 🚀**
