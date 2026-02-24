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
