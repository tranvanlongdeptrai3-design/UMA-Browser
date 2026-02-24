# 🔧 HƯỚNG DẪN CÀI ĐẶT CHI TIẾT - UMA Browser

*Tài liệu này cung cấp hướng dẫn từng bước để cài đặt và chạy UMA Browser*

---

## 📋 MỤC LỤC
1. [Kiểm Tra Yêu Cầu Hệ Thống](#kiểm-tra-yêu-cầu)
2. [Cài Đặt Java JDK](#cài-đặt-java-jdk)
3. [Cài Đặt Maven](#cài-đặt-maven)
4. [Cài Đặt Dependencies](#cài-đặt-dependencies)
5. [Chạy Ứng Dụng](#chạy-ứng-dụng)
6. [Xác Minh Cài Đặt](#xác-minh-cài-đặt)
7. [Xử Lý Sự Cố](#xử-lý-sự-cố)

---

## <a name="kiểm-tra-yêu-cầu"></a>1️⃣ KIỂM TRA YÊU CẦU HỆ THỐNG

### Yêu Cầu Tối Thiểu:
- **OS**: Windows 10/11, macOS, hoặc Linux
- **Java**: JDK 11 hoặc cao hơn
- **Maven**: 3.6.3 hoặc cao hơn
- **RAM**: ≥ 2GB
- **Ổ cứng**: ≥ 1GB (để tải dependencies)

### Kiểm Tra Hiện Tại:

#### Windows:
```cmd
REM Mở Command Prompt (Win+R, gõ cmd)

REM Kiểm tra Java
java -version

REM Kiểm tra Maven
mvn --version
```

#### macOS/Linux:
```bash
java -version
mvn --version
```

---

## <a name="cài-đặt-java-jdk"></a>2️⃣ CÀI ĐẶT JAVA JDK

### 🔹 WINDOWS - Cài JDK

**Bước 1:** Tải JDK
- Truy cập: https://www.oracle.com/java/technologies/downloads/
- Chọn **JDK 21** (hoặc 11+, khuyên dùng LTS)
- Chọn **Windows**
- Tải file `.exe`

**Bước 2:** Cài đặt
1. Double-click file `.exe` vừa tải
2. Chọn cài vào: `C:\Program Files\Java\jdk-21` (hoặc default)
3. Chọn "Next" đến hết
4. ✓ Cài đặt xong

**Bước 3:** Thiết lập Environment Variable

1. Mở "Environment Variables":
   - Win+R → `sysdm.cpl` → Enter
   - Hoặc: Settings → System → Advanced system settings

2. Click "Environment Variables" button

3. Dưới "User variables" hoặc "System variables", click "New":
   - Variable name: `JAVA_HOME`
   - Variable value: `C:\Program Files\Java\jdk-21` (điều chỉnh theo thư mục của bạn)

4. Tìm biến `Path` → Click "Edit":
   - Thêm: `%JAVA_HOME%\bin`

5. Click "OK" hết

**Bước 4:** Kiểm tra
```cmd
java -version
javac -version
```

Sẽ thấy version của Java (vd: 21.0.1)

---

### 🔹 macOS - Cài JDK

```bash
# Cách 1: Sử dụng Homebrew (nếu có cài)
brew install openjdk@21

# Cách 2: Tải từ Oracle
# Tuy như Windows, nhưng lưu vào /Library/Java/JavaVirtualMachines/

# Kiểm tra
java -version
```

---

### 🔹 Linux - Cài JDK

```bash
# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-21-jdk

# Fedora/CentOS
sudo dnf install java-21-openjdk-devel

# Kiểm tra
java -version
```

---

## <a name="cài-đặt-maven"></a>3️⃣ CÀI ĐẶT MAVEN

### 🔹 WINDOWS - Cài Maven

**Bước 1:** Tải Maven
- Truy cập: https://maven.apache.org/download.cgi
- Chọn **Binary zip archive** (dòng apache-maven-3.x.x-bin.zip)
- Tải xuống

**Bước 2:** Giải nén
1. Giải nén file `.zip` tới: `C:\Program Files\apache-maven-3.9.5`
2. Ghi nhớ đường dẫn này

**Bước 3:** Thiết lập Environment Variable

1. Mở "Environment Variables" (Y hệt JDK)

2. Thêm variable mới:
   - Variable name: `MAVEN_HOME`
   - Variable value: `C:\Program Files\apache-maven-3.9.5`

3. Edit biến `Path`, thêm: `%MAVEN_HOME%\bin`

4. Click "OK"

**Bước 4:** Kiểm tra
```cmd
mvn --version
```

Sẽ thấy:
```
Apache Maven 3.9.5 (...java 21.0.1 ...)
```

---

### 🔹 macOS/Linux - Cài Maven

```bash
# macOS với Homebrew
brew install maven

# Linux (Ubuntu/Debian)
sudo apt install maven

# Linux (Fedora/CentOS)
sudo dnf install maven

# Kiểm tra
mvn --version
```

---

## <a name="cài-đặt-dependencies"></a>4️⃣ CÀI ĐẶT DEPENDENCIES

### Đây là phần Maven tự động làm!

**Bước 1:** Mở Command Prompt/Terminal

```cmd
REM Windows
cd d:\lập trình mạng\UMA-Browser_no.1

REM macOS/Linux
cd ~/path/to/UMA-Browser_no.1
```

**Bước 2:** Tải Dependencies
```cmd
mvn clean install
```

Quá trình này sẽ:
- ✓ Tải JavaFX SDK
- ✓ Tải các plugin cần thiết
- ✓ Compile source code
- ✓ Chuẩn bị chạy ứng dụng

**Lần đầu có thể mất 5-10 phút** (phụ thuộc vào tốc độ internet)

Khi hoàn thành sẽ thấy:
```
[INFO] BUILD SUCCESS
```

---

## <a name="chạy-ứng-dụng"></a>5️⃣ CHẠY ỨNG DỤNG

### ✅ CÁCH DỄ NHẤT (Windows):

Đơn giản là double-click:
1. **`run_server.bat`** - Để khởi động Server
2. **`run_client.bat`** - Để khởi động Client

---

### 🔹 CÁCH MANUAL - Windows

**Terminal 1 - Chạy Server:**
```cmd
cd d:\lập trình mạng\UMA-Browser_no.1
mvn exec:java -Dexec.mainClass="server.WebServer"
```

**Output khi Server sẵn sàng:**
```
🚀 Web Server đã khởi động trên port 9090
📍 Địa chỉ: http://localhost:9090
```

**Terminal 2 - Chạy Client (mở terminal mới!):**
```cmd
cd d:\lập trình mạng\UMA-Browser_no.1
mvn javafx:run
```

**Giao diện sẽ mở lên!**

---

### 🔹 CÁCH MANUAL - macOS/Linux

**Terminal 1:**
```bash
cd ~/path/to/UMA-Browser_no.1
mvn exec:java -Dexec.mainClass="server.WebServer"
```

**Terminal 2 (mở terminal mới):**
```bash
cd ~/path/to/UMA-Browser_no.1
mvn javafx:run
```

---

## <a name="xác-minh-cài-đặt"></a>6️⃣ XÁC MINH CÀI ĐẶT

### Test Server

1. Khởi động Server (như bước trên)
2. Mở terminal/cmd khác:

```cmd
REM Windows - Kiểm tra kết nối TCP
telnet localhost 9090

REM Nếu kết nối được thì Server OK!
```

Hoặc dùng PowerShell:
```powershell
Test-NetConnection -ComputerName localhost -Port 9090
```

### Test Client

1. Khởi động Client
2. Nhấn "Home" 
3. Should see trang chủ HTML

---

## <a name="xử-lý-sự-cố"></a>7️⃣ XỬ LỰ SỰ CỐ

### ❌ Lỗi: "Java không được tìm thấy"

**Nguyên nhân:** Java chưa được cài hoặc PATH sai

**Giải pháp:**
```cmd
REM Kiểm tra xem Java ở đâu
where java

REM Hoặc tìm thủ công:
dir "C:\Program Files\Java"

REM Thiết lập PATH tạm thời:
set PATH=%PATH%;C:\Program Files\Java\jdk-21\bin
java -version
```

---

### ❌ Lỗi: "Maven không được tìm thấy"

**Nguyên nhân:** Maven chưa được cài hoặc PATH sai

**Giải pháp:**
```cmd
REM Kiểm tra Maven
where mvn

REM Thiết lập PATH tạm thời:
set PATH=%PATH%;C:\Program Files\apache-maven-3.9.5\bin
mvn --version
```

---

### ❌ Lỗi: "Port 9090 already in use"

**Nguyên nhân:** Cổng 9090 đang bị sử dụng

**Giải pháp - Windows:**
```cmd
REM Tìm process sử dụng port 9090
netstat -ano | findstr :9090

REM Dừng process (thay PID bằng số ID)
taskkill /PID <PID> /F

REM Hoặc sử dụng cổng khác
mvn exec:java -Dexec.mainClass="server.WebServer" -Dexec.args="8080"
```

---

### ❌ Lỗi: "Connection refused"

**Nguyên nhân:** Server chưa khởi động

**Giải pháp:**
1. Đảm bảo Server đã khởi động và thấy:
   ```
   🚀 Web Server đã khởi động trên port 9090
   ```
2. Mở terminal mới KHÁC cho Client
3. Chạy Client

---

### ❌ Lỗi: "Maven BUILD FAILURE"

**Nguyên nhân:** Lỗi compile

**Giải pháp:**
```cmd
REM 1. Clean project
mvn clean

REM 2. Install dependencies
mvn install

REM 3. Compile
mvn compile -e

REM Xem error chi tiết với -e flag
```

---

### ❌ Lỗi: "JavaFX libraries not found"

**Nguyên nhân:** JavaFX chưa tải

**Giải pháp:**
```cmd
REM Force tải dependencies
mvn clean dependency:resolve
mvn clean install
```

---

### ⚠️ Lỗi: "... module not found: javafx.controls"

**Nguyên nhân:** JavaFX module chưa sẵn sàng

**Giải pháp:**
```cmd
REM Clear Maven cache
rmdir %userprofile%\.m2\repository /s /q

REM Hoặc Linux
rm -rf ~/.m2/repository

REM Rồi cài lại
mvn clean install
```

---

## 🎯 CHECKLIST CÀI ĐẶT

Đánh dấu khi hoàn thành:

```
☐ Java JDK 11+ đã cài
  Kiểm tra: java -version
  Kết quả:______________________

☐ JAVA_HOME environment variable được thiết lập
  Kiểm tra: echo %JAVA_HOME%
  Kết quả: ______________________

☐ Maven đã cài
  Kiểm tra: mvn --version
  Kết quả: ______________________

☐ MAVEN_HOME environment variable được thiết lập
  Kiểm tra: echo %MAVEN_HOME%
  Kết quả: ______________________

☐ Dependencies đã tải (mvn clean install)
  Kết quả: BUILD SUCCESS

☐ Server chạy được
  Lệnh: mvn exec:java -Dexec.mainClass="server.WebServer"
  Kết quả: Server lắng nghe trên 9090

☐ Client chạy được
  Lệnh: mvn javafx:run
  Kết quả: Giao diện JavaFX hiện lên

☐ Có thể load trang (Home page)
  URL: http://localhost:9090/
  Kết quả: Thấy HTML content
```

---

## 📞 CẢN GIÚP?

Nếu vẫn gặp vấn đề:

1. **Đọc lại README.md** - Có thêm info
2. **Kiểm tra verbose output:**
   ```cmd
   mvn clean install -X
   mvn javafx:run -X
   ```
3. **Xem error message kỹ** - Thường rất cụ thể
4. **Làm fresh install:**
   ```cmd
   mvn clean
   mvn install
   ```

---

## 💡 TIPS & TRICKS

### Build nhanh hơn
```cmd
REM Skip tests
mvn clean compile -DskipTests

REM Sử dụng nhiều thread
mvn clean install -T 1C
```

### Chạy server trên cổng khác
```cmd
mvn exec:java -Dexec.mainClass="server.WebServer" -Dexec.args="8080"

REM Rồi client sẽ kết nối localhost:8080
```

### View Maven dependency tree
```cmd
mvn dependency:tree
```

### Xóa cache Maven (nếu corrupted)
```cmd
REM Windows
rmdir %userprofile%\.m2\repository /s /q

REM macOS/Linux
rm -rf ~/.m2/repository
```

---

**Nếu theo hướng dẫn này mà vẫn lỗi, thứ tự kiểm tra:**

1. Java -version ✓
2. Maven --version ✓
3. mvn clean install ✓
4. Run Server ✓
5. Run Client ✓

Mỗi bước phải pass trước khi sang bước tiếp theo!

---

**Last Updated: 2026-02-24**
