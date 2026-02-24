package server;

import util.HttpRequest;
import util.HttpResponse;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Web Server chính lắng nghe trên cổng TCP
 */
public class WebServer {
    private int port;
    private ServerSocket serverSocket;
    private boolean running = false;
    
    public WebServer(int port) {
        this.port = port;
    }
    
    /**
     * Khởi động Server
     */
    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            running = true;
            System.out.println("🚀 Web Server đã khởi động trên port " + port);
            System.out.println("📍 Địa chỉ: http://localhost:" + port);
            
            // Tiếp tục lắng nghe các kết nối
            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    
                    // Xử lý mỗi client trong một thread riêng
                    Thread clientThread = new Thread(new ClientHandler(clientSocket));
                    clientThread.start();
                    
                } catch (SocketException e) {
                    if (running) {
                        System.err.println("Lỗi kết nối: " + e.getMessage());
                    }
                }
            }
            
        } catch (IOException e) {
            System.err.println("Lỗi khởi động server: " + e.getMessage());
        }
    }
    
    /**
     * Dừng Server
     */
    public void stop() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            System.out.println("Server đã dừng");
        } catch (IOException e) {
            System.err.println("Lỗi dừng server: " + e.getMessage());
        }
    }
    
    /**
     * Inner class xử lý từng client connection
     */
    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
        
        @Override
        public void run() {
            try {
                // Đọc request từ client
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                HttpRequest request = (HttpRequest) ois.readObject();
                
                System.out.println("📨 Yêu cầu nhận được: " + request);
                
                // Xử lý request và tạo response
                HttpResponse response = handleRequest(request);
                
                // Gửi response về client
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                oos.writeObject(response);
                oos.flush();
                
                System.out.println("📤 Response gửi: " + response);
                
                // Đóng kết nối
                oos.close();
                ois.close();
                clientSocket.close();
                
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Lỗi xử lý client: " + e.getMessage());
            }
        }
        
        /**
         * Xử lý HTTP request và trả về response
         */
        private HttpResponse handleRequest(HttpRequest request) {
            String path = request.getPath().isEmpty() ? "/" : request.getPath();
            
            // Xử lý các trang khác nhau
            if (path.equals("/")) {
                return getHomePage();
            } else if (path.equals("/about")) {
                return getAboutPage();
            } else if (path.equals("/contact")) {
                return getContactPage();
            } else if (path.equals("/chapter")) {
                return getChapterPage();
            } else {
                return get404Page(path);
            }
        }
        
        /**
         * Trang chủ
         */
        private HttpResponse getHomePage() {
            String html = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>UMA Browser - Trang Chủ</title>\n" +
                    "    <style>\n" +
                    "        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }\n" +
                    "        h1 { color: #333; }\n" +
                    "        a { color: #0066cc; text-decoration: none; margin: 10px 10px 10px 0; display: inline-block; }\n" +
                    "        a:hover { text-decoration: underline; }\n" +
                    "        .info { background-color: white; padding: 20px; border-radius: 5px; }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"info\">\n" +
                    "        <h1>🌐 Chào mừng đến UMA Browser</h1>\n" +
                    "        <p>Đây là một trình duyệt Web được xây dựng bằng Java và JavaFX</p>\n" +
                    "        <p>Sử dụng giao thức TCP và mô hình Client-Server</p>\n" +
                    "        <h3>Điều hướng:</h3>\n" +
                    "        <nav>\n" +
                    "            <a href=\"/\">Trang Chủ</a>\n" +
                    "            <a href=\"/about\">Về Chúng Tôi</a>\n" +
                    "            <a href=\"/contact\">Liên Hệ</a>\n" +
                    "            <a href=\"/chapter\">📖 Đọc Chương Trình</a>\n" +
                    "        </nav>\n" +
                    "        <p><small>Thời gian phục vụ: " + getCurrentDateTime() + "</small></p>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>";
            
            HttpResponse response = new HttpResponse(200, "OK", html);
            response.addHeader("Content-Type", "text/html; charset=UTF-8");
            return response;
        }
        
        /**
         * Trang About
         */
        private HttpResponse getAboutPage() {
            String html = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Về Chúng Tôi</title>\n" +
                    "    <style>\n" +
                    "        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }\n" +
                    "        h1 { color: #333; }\n" +
                    "        a { color: #0066cc; text-decoration: none; margin-right: 10px; }\n" +
                    "        a:hover { text-decoration: underline; }\n" +
                    "        .info { background-color: white; padding: 20px; border-radius: 5px; }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"info\">\n" +
                    "        <h1>📋 Về UMA Browser</h1>\n" +
                    "        <p>UMA Browser là một dự án giáo dục để học tập về:</p>\n" +
                    "        <ul>\n" +
                    "            <li>Giao thức TCP/IP</li>\n" +
                    "            <li>Mô hình Client-Server</li>\n" +
                    "            <li>Lập trình mạng Java</li>\n" +
                    "            <li>Xây dựng giao diện JavaFX</li>\n" +
                    "        </ul>\n" +
                    "        <p><a href=\"/\">← Quay lại trang chủ</a></p>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>";
            
            HttpResponse response = new HttpResponse(200, "OK", html);
            response.addHeader("Content-Type", "text/html; charset=UTF-8");
            return response;
        }
        
        /**
         * Trang Contact
         */
        private HttpResponse getContactPage() {
            String html = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Liên Hệ</title>\n" +
                    "    <style>\n" +
                    "        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }\n" +
                    "        h1 { color: #333; }\n" +
                    "        a { color: #0066cc; text-decoration: none; margin-right: 10px; }\n" +
                    "        a:hover { text-decoration: underline; }\n" +
                    "        .info { background-color: white; padding: 20px; border-radius: 5px; }\n" +
                    "        .contact-item { margin: 10px 0; }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"info\">\n" +
                    "        <h1>📧 Liên Hệ Chúng Tôi</h1>\n" +
                    "        <div class=\"contact-item\">\n" +
                    "            <strong>Email:</strong> support@umabrowser.local\n" +
                    "        </div>\n" +
                    "        <div class=\"contact-item\">\n" +
                    "            <strong>Địa chỉ:</strong> localhost:9090\n" +
                    "        </div>\n" +
                    "        <div class=\"contact-item\">\n" +
                    "            <strong>Loại giao tiếp:</strong> TCP Socket\n" +
                    "        </div>\n" +
                    "        <p><a href=\"/\">← Quay lại trang chủ</a></p>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>";
            
            HttpResponse response = new HttpResponse(200, "OK", html);
            response.addHeader("Content-Type", "text/html; charset=UTF-8");
            return response;
        }
        
        /**
         * Trang Chapter - Đọc chương trình
         */
        private HttpResponse getChapterPage() {
            String html = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Đọc Chương Trình</title>\n" +
                    "    <style>\n" +
                    "        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; line-height: 1.6; }\n" +
                    "        h1 { color: #333; border-bottom: 3px solid #0066cc; padding-bottom: 10px; }\n" +
                    "        h2 { color: #0066cc; margin-top: 20px; }\n" +
                    "        h3 { color: #555; }\n" +
                    "        a { color: #0066cc; text-decoration: none; margin-right: 10px; }\n" +
                    "        a:hover { text-decoration: underline; }\n" +
                    "        .info { background-color: white; padding: 20px; border-radius: 5px; margin-bottom: 20px; }\n" +
                    "        .chapter-content { background-color: #f9f9f9; padding: 15px; border-left: 4px solid #0066cc; margin: 15px 0; }\n" +
                    "        .code-block { background-color: #272822; color: #f8f8f2; padding: 15px; border-radius: 5px; overflow-x: auto; font-family: 'Courier New', monospace; margin: 10px 0; }\n" +
                    "        .highlight { background-color: #ffffcc; padding: 2px 5px; }\n" +
                    "        ul { margin-left: 20px; }\n" +
                    "        li { margin: 8px 0; }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"info\">\n" +
                    "        <h1>📖 Đọc Chương Trình - UMA Browser</h1>\n" +
                    "        <p><em>Tìm hiểu cách hoạt động của trình duyệt web Java</em></p>\n" +
                    "        \n" +
                    "        <h2>Chương 1: Kiến Trúc Hệ Thống</h2>\n" +
                    "        <div class=\"chapter-content\">\n" +
                    "            <h3>1.1 Mô Hình Client-Server</h3>\n" +
                    "            <p>UMA Browser sử dụng mô hình <span class=\"highlight\">Client-Server</span> với giao thức TCP/IP:</p>\n" +
                    "            <ul>\n" +
                    "                <li><strong>Server (WebServer.java):</strong> Lắng nghe trên port 9090, xử lý các yêu cầu HTTP</li>\n" +
                    "                <li><strong>Client (BrowserApplication.java):</strong> Giao diện JavaFX, gửi yêu cầu và hiển thị nội dung</li>\n" +
                    "                <li><strong>Giao thức:</strong> TCP Socket với Object Serialization</li>\n" +
                    "            </ul>\n" +
                    "        </div>\n" +
                    "        \n" +
                    "        <h2>Chương 2: Luồng Xử Lý Request</h2>\n" +
                    "        <div class=\"chapter-content\">\n" +
                    "            <h3>2.1 Quy Trình Gửi/Nhận Dữ Liệu</h3>\n" +
                    "            <ol>\n" +
                    "                <li>User nhập URL và nhấn \"Go\"</li>\n" +
                    "                <li>Client tạo đối tượng <code>HttpRequest</code></li>\n" +
                    "                <li>Request được serialize thành byte stream</li>\n" +
                    "                <li>Gửi qua TCP Socket đến Server</li>\n" +
                    "                <li>Server deserialize và xử lý request</li>\n" +
                    "                <li>Server tạo <code>HttpResponse</code> với nội dung HTML</li>\n" +
                    "                <li>Response được serialize và gửi về Client</li>\n" +
                    "                <li>Client deserialize và hiển thị nội dung</li>\n" +
                    "            </ol>\n" +
                    "        </div>\n" +
                    "        \n" +
                    "        <h2>Chương 3: Ví Dụ Code</h2>\n" +
                    "        <div class=\"chapter-content\">\n" +
                    "            <h3>3.1 Tạo HTTP Request</h3>\n" +
                    "            <div class=\"code-block\">\n" +
                    "HttpRequest request = new HttpRequest(\"GET\", url);\n" +
                    "request.addHeader(\"User-Agent\", \"UMA-Browser/1.0\");\n" +
                    "HttpResponse response = httpClient.sendRequest(request);\n" +
                    "            </div>\n" +
                    "            \n" +
                    "            <h3>3.2 Xử Lý Request Trên Server</h3>\n" +
                    "            <div class=\"code-block\">\n" +
                    "String path = request.getPath();\n" +
                    "if (path.equals(\"/\")) {\n" +
                    "    return getHomePage();\n" +
                    "} else if (path.equals(\"/chapter\")) {\n" +
                    "    return getChapterPage();\n" +
                    "}\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "        \n" +
                    "        <h2>Chương 4: Các Tính Năng Chính</h2>\n" +
                    "        <div class=\"chapter-content\">\n" +
                    "            <ul>\n" +
                    "                <li>✅ <strong>TCP Socket Communication:</strong> Giao tiếp tin cậy giữa client và server</li>\n" +
                    "                <li>✅ <strong>Multi-threading:</strong> Server xử lý nhiều client đồng thời</li>\n" +
                    "                <li>✅ <strong>Object Serialization:</strong> Chuyển đổi objects thành byte stream</li>\n" +
                    "                <li>✅ <strong>JavaFX GUI:</strong> Giao diện người dùng hiện đại</li>\n" +
                    "                <li>✅ <strong>HTTP Protocol:</strong> Hỗ trợ GET/POST requests</li>\n" +
                    "            </ul>\n" +
                    "        </div>\n" +
                    "        \n" +
                    "        <h2>Chương 5: Mở Rộng Tính Năng</h2>\n" +
                    "        <div class=\"chapter-content\">\n" +
                    "            <p>Bạn có thể mở rộng UMA Browser với:</p>\n" +
                    "            <ul>\n" +
                    "                <li>🔧 Thêm các trang mới</li>\n" +
                    "                <li>🔧 Hỗ trợ POST requests với form data</li>\n" +
                    "                <li>🔧 Thêm cookie và session management</li>\n" +
                    "                <li>🔧 Cải thiện HTML rendering với WebView</li>\n" +
                    "                <li>🔧 Thêm HTTPS support</li>\n" +
                    "                <li>🔧 Implement caching mechanism</li>\n" +
                    "            </ul>\n" +
                    "        </div>\n" +
                    "        \n" +
                    "        <p style=\"margin-top: 30px;\">\n" +
                    "            <a href=\"/\">← Quay lại trang chủ</a> |\n" +
                    "            <a href=\"/about\">Về chúng tôi</a> |\n" +
                    "            <a href=\"/contact\">Liên hệ</a>\n" +
                    "        </p>\n" +
                    "        \n" +
                    "        <p><small>Thời gian phục vụ: " + getCurrentDateTime() + "</small></p>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>";
            
            HttpResponse response = new HttpResponse(200, "OK", html);
            response.addHeader("Content-Type", "text/html; charset=UTF-8");
            return response;
        }
        
        /**
         * Trang lỗi 404
         */
        private HttpResponse get404Page(String path) {
            String html = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>404 - Không Tìm Thấy</title>\n" +
                    "    <style>\n" +
                    "        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }\n" +
                    "        h1 { color: #d32f2f; font-size: 48px; }\n" +
                    "        a { color: #0066cc; text-decoration: none; }\n" +
                    "        a:hover { text-decoration: underline; }\n" +
                    "        .error { background-color: white; padding: 20px; border-radius: 5px; }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"error\">\n" +
                    "        <h1>❌ 404</h1>\n" +
                    "        <p>Trang \"" + path + "\" không tồn tại</p>\n" +
                    "        <p><a href=\"/\">← Quay lại trang chủ</a></p>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>";
            
            HttpResponse response = new HttpResponse(404, "Not Found", html);
            response.addHeader("Content-Type", "text/html; charset=UTF-8");
            return response;
        }
        
        /**
         * Lấy thời gian hiện tại
         */
        private String getCurrentDateTime() {
            return LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }
    
    /**
     * Main method - Khởi động Server
     */
    public static void main(String[] args) {
        int port = 9090;
        
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Cổng không hợp lệ, sử dụng cổng mặc định 9090");
            }
        }
        
        WebServer server = new WebServer(port);
        server.start();
    }
}
