package util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class đại diện cho HTTP Response từ Server
 */
public class HttpResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int statusCode;              // 200, 404, 500, etc.
    private String statusMessage;        // OK, Not Found, etc.
    private String httpVersion;          // HTTP/1.1
    private Map<String, String> headers; // Headers
    private String body;                 // Response body (HTML, etc.)
    
    public HttpResponse() {
        this.headers = new HashMap<>();
        this.httpVersion = "HTTP/1.1";
        this.statusCode = 200;
        this.statusMessage = "OK";
    }
    
    public HttpResponse(int statusCode, String statusMessage, String body) {
        this();
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.body = body;
    }
    
    // Getters and Setters
    public int getStatusCode() {
        return statusCode;
    }
    
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    
    public String getStatusMessage() {
        return statusMessage;
    }
    
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
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
        return String.format("%s %d %s", httpVersion, statusCode, statusMessage);
    }
}
