package client;

import util.HttpRequest;
import util.HttpResponse;

import java.io.*;
import java.net.Socket;

/**
 * HTTP Client kết nối đến Web Server qua TCP
 */
public class HttpClient {
    private String host;
    private int port;
    
    public HttpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    /**
     * Gửi HTTP request đến server và nhận response
     */
    public HttpResponse sendRequest(HttpRequest request) throws IOException {
        try {
            // Tạo socket kết nối đến server
            Socket socket = new Socket(host, port);
            System.out.println("✅ Đã kết nối đến server: " + host + ":" + port);
            
            // Gửi request
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(request);
            oos.flush();
            System.out.println("📨 Request gửi: " + request);
            
            // Nhận response
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            HttpResponse response = (HttpResponse) ois.readObject();
            System.out.println("📤 Response nhận: " + response);
            
            // Đóng kết nối
            ois.close();
            oos.close();
            socket.close();
            
            return response;
            
        } catch (ClassNotFoundException e) {
            throw new IOException("Lỗi deserialize response: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new IOException("Lỗi kết nối đến server: " + e.getMessage(), e);
        }
    }
    
    /**
     * Gửi GET request
     */
    public HttpResponse get(String url) throws IOException {
        HttpRequest request = new HttpRequest("GET", url);
        request.addHeader("User-Agent", "UMA-Browser/1.0");
        return sendRequest(request);
    }
    
    /**
     * Gửi POST request
     */
    public HttpResponse post(String url, String body) throws IOException {
        HttpRequest request = new HttpRequest("POST", url);
        request.setBody(body);
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        request.addHeader("User-Agent", "UMA-Browser/1.0");
        return sendRequest(request);
    }
}
