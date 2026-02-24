package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import util.HttpResponse;

/**
 * Ứng dụng Browser JavaFX
 */
public class BrowserApplication extends Application {
    
    private HttpClient httpClient;
    private TextField urlBar;
    private TextArea contentArea;
    private Label statusLabel;
    private ComboBox<String> serverSelector;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("🌐 UMA Browser v1.0");
            primaryStage.setWidth(1000);
            primaryStage.setHeight(700);
            
            // Khởi tạo HTTP Client
            httpClient = new HttpClient("localhost", 9090);
            
            // Tạo giao diện
            VBox root = createUI();
            
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            System.out.println("🚀 UMA Browser đã khởi động");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Tạo giao diện ứng dụng
     */
    private VBox createUI() {
        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);
        
        // 1. Toolbar - Thanh công cụ
        HBox toolbar = createToolbar();
        
        // 2. Address Bar - Thanh địa chỉ
        HBox addressBar = createAddressBar();
        
        // 3. Content Area - Khu vực nội dung
        VBox contentSection = createContentArea();
        
        // 4. Status Bar - Thanh trạng thái
        HBox statusBar = createStatusBar();
        
        // Thêm các thành phần vào root
        root.getChildren().addAll(toolbar, addressBar, contentSection, statusBar);
        VBox.setVgrow(contentSection, Priority.ALWAYS);
        
        return root;
    }
    
    /**
     * Tạo thanh công cụ (Toolbar)
     */
    private HBox createToolbar() {
        HBox toolbar = new HBox();
        toolbar.setPadding(new Insets(5));
        toolbar.setSpacing(10);
        toolbar.setStyle("-fx-border-color: #ddd; -fx-border-width: 0 0 1 0; -fx-background-color: #f9f9f9;");
        
        // Nút Back
        Button backBtn = new Button("⬅ Back");
        backBtn.setStyle("-fx-font-size: 12px; -fx-padding: 5px 15px;");
        backBtn.setOnAction(e -> showAlert("Chức năng Back chưa có sẵn", "Vui lòng sử dụng URL bar để điều hướng"));
        
        // Nút Forward
        Button forwardBtn = new Button("Forward ➡");
        forwardBtn.setStyle("-fx-font-size: 12px; -fx-padding: 5px 15px;");
        forwardBtn.setOnAction(e -> showAlert("Chức năng Forward chưa có sẵn", "Vui lòng sử dụng URL bar để điều hướng"));
        
        // Nút Refresh
        Button refreshBtn = new Button("🔄 Refresh");
        refreshBtn.setStyle("-fx-font-size: 12px; -fx-padding: 5px 15px;");
        refreshBtn.setOnAction(e -> {
            String url = urlBar.getText();
            if (!url.isEmpty()) {
                loadPage(url);
            }
        });
        
        // Nút Home
        Button homeBtn = new Button("🏠 Home");
        homeBtn.setStyle("-fx-font-size: 12px; -fx-padding: 5px 15px;");
        homeBtn.setOnAction(e -> loadPage("http://localhost:9090/"));
        
        Separator separator = new Separator();
        separator.setStyle("-fx-padding: 5px 0;");
        separator.setPrefWidth(20);
        
        // Server Selector
        Label serverLabel = new Label("🖥 Server:");
        serverLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        
        serverSelector = new ComboBox<>();
        serverSelector.getItems().addAll(
                "localhost:9090",
                "localhost:8080",
                "localhost:3000"
        );
        serverSelector.setValue("localhost:9090");
        serverSelector.setPrefWidth(150);
        serverSelector.setOnAction(e -> updateServerConnection());
        
        toolbar.getChildren().addAll(
                backBtn, forwardBtn, refreshBtn, homeBtn,
                separator, serverLabel, serverSelector
        );
        
        return toolbar;
    }
    
    /**
     * Tạo thanh địa chỉ (Address Bar)
     */
    private HBox createAddressBar() {
        HBox addressBar = new HBox();
        addressBar.setPadding(new Insets(5, 10, 5, 10));
        addressBar.setSpacing(10);
        addressBar.setStyle("-fx-border-color: #ddd; -fx-border-width: 0 0 1 0;");
        
        // Label
        Label urlLabel = new Label("📍 URL:");
        urlLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        
        // URL Bar
        urlBar = new TextField();
        urlBar.setPromptText("Nhập URL (vd: http://localhost:9090/ hoặc localhost:9090/about)");
        urlBar.setStyle("-fx-padding: 8px; -fx-font-size: 12px;");
        urlBar.setOnAction(e -> loadPageFromBar());
        
        // Nút Go
        Button goBtn = new Button("Go");
        goBtn.setStyle("-fx-font-size: 12px; -fx-padding: 8px 20px;");
        goBtn.setOnAction(e -> loadPageFromBar());
        
        addressBar.getChildren().addAll(urlLabel, urlBar, goBtn);
        HBox.setHgrow(urlBar, Priority.ALWAYS);
        
        return addressBar;
    }
    
    /**
     * Tạo khu vực nội dung (Content Area)
     */
    private VBox createContentArea() {
        VBox contentSection = new VBox();
        contentSection.setPadding(new Insets(10));
        contentSection.setStyle("-fx-border-color: #ddd; -fx-border-width: 0; -fx-background-color: white;");
        
        // Text Area để hiển thị HTML content
        contentArea = new TextArea();
        contentArea.setEditable(false);
        contentArea.setWrapText(true);
        contentArea.setStyle("-fx-font-size: 11px; -fx-font-family: 'Courier New';");
        contentArea.setText("Welcome to UMA Browser!\n\nNhập URL ở thanh địa chỉ hoặc nhấn nút 'Home' để bắt đầu.\n\n" +
                "Các trang khả dụng:\n" +
                "- http://localhost:9090/ (Trang chủ)\n" +
                "- http://localhost:9090/about (Về chúng tôi)\n" +
                "- http://localhost:9090/contact (Liên hệ)");
        
        contentSection.getChildren().add(contentArea);
        VBox.setVgrow(contentArea, Priority.ALWAYS);
        
        return contentSection;
    }
    
    /**
     * Tạo thanh trạng thái (Status Bar)
     */
    private HBox createStatusBar() {
        HBox statusBar = new HBox();
        statusBar.setPadding(new Insets(5, 10, 5, 10));
        statusBar.setStyle("-fx-border-color: #ddd; -fx-border-width: 1 0 0 0; -fx-background-color: #f9f9f9;");
        
        statusLabel = new Label("✓ Sẵn sàng");
        statusLabel.setStyle("-fx-font-size: 11px;");
        
        statusBar.getChildren().add(statusLabel);
        
        return statusBar;
    }
    
    /**
     * Tải trang từ URL bar
     */
    private void loadPageFromBar() {
        String url = urlBar.getText().trim();
        if (url.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập URL");
            return;
        }
        
        // Nếu không có http://, thêm vào
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        
        loadPage(url);
    }
    
    /**
     * Hàm tải trang web
     */
    private void loadPage(String url) {
        statusLabel.setText("⏳ Đang tải...");
        contentArea.setText("");
        
        // Chạy trong thread riêng để không block UI
        new Thread(() -> {
            try {
                // Cập nhật URL Bar
                Platform.runLater(() -> urlBar.setText(url));
                
                // Gửi request đến server
                HttpResponse response = httpClient.get(url);
                
                // Cập nhật UI
                Platform.runLater(() -> {
                    if (response.getStatusCode() == 200) {
                        String html = response.getBody();
                        contentArea.setText(html);
                        statusLabel.setText("✓ Trang đã tải thành công - " + response.getStatusCode() + " " + response.getStatusMessage());
                        urlBar.setText(url);
                    } else {
                        contentArea.setText(response.getBody());
                        statusLabel.setText("⚠ Lỗi: " + response.getStatusCode() + " " + response.getStatusMessage());
                    }
                });
                
            } catch (Exception e) {
                Platform.runLater(() -> {
                    contentArea.setText("❌ Lỗi kết nối:\n\n" + e.getMessage() + 
                            "\n\nChắc chắn rằng:\n" +
                            "1. Server đang chạy\n" +
                            "2. Server cổng đúng (mặc định: 9090)\n" +
                            "3. Địa chỉ server đúng");
                    statusLabel.setText("✗ Lỗi: " + e.getMessage());
                });
                e.printStackTrace();
            }
        }).start();
    }
    
    /**
     * Cập nhật kết nối server
     */
    private void updateServerConnection() {
        String selected = serverSelector.getValue();
        String[] parts = selected.split(":");
        
        if (parts.length == 2) {
            String host = parts[0];
            int port = Integer.parseInt(parts[1]);
            httpClient = new HttpClient(host, port);
            statusLabel.setText("✓ Đã kết nối " + selected);
        }
    }
    
    /**
     * Hiển thị hộp thoại Alert
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Main method - Khởi động ứng dụng
     */
    public static void main(String[] args) {
        launch(args);
    }
}
