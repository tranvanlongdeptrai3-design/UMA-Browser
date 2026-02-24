package util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class đại diện cho HTTP Request được gửi qua TCP
 */
public class HttpRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String method;           // GET, POST, etc.
    private String url;              // URL yêu cầu
    private String path;             // Đường dẫn tài nguyên
    private String httpVersion;      // HTTP/1.1
    private Map<String, String> headers;  // Headers
    private String body;             // Request body
    
    public HttpRequest() {
        this.headers = new HashMap<>();
        this.httpVersion = "HTTP/1.1";
        this.method = "GET";
    }
    
    public HttpRequest(String method, String url) {
        this();
        this.method = method;
        this.url = url;
        this.path = extractPath(url);
    }
    
    /**
     * Trích xuất đường dẫn từ URL
     */
    private String extractPath(String url) {
        if (url == null || url.trim().isEmpty()) {
            return "/";
        }
        
        // Loại bỏ http:// hoặc https://
        String cleanUrl = url.replaceAll("^https?://", "");
        
        // Loại bỏ domain
        int pathStart = cleanUrl.indexOf('/');
        if (pathStart == -1) {
            return "/";
        }
        
        return cleanUrl.substring(pathStart);
    }
    
    // Getters and Setters
    public String getMethod() {
        return method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
        this.path = extractPath(url);
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public String getHttpVersion() {
        return httpVersion;
    }
    
    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }
    
    public Map<String, String> getHeaders() {
        return headers;
    }
    
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
    
    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }
    
    public String getBody() {
        return body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }
    
    @Override
    public String toString() {
        return String.format("%s %s %s", method, path, httpVersion);
    }
}
