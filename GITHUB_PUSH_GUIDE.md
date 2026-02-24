# 📤 HƯỚNG DẪN ĐẨY CODE LÊN GITHUB

*Hướng dẫn chi tiết để push UMA Browser repository lên GitHub*

---

## 🚀 BƯỚC 1: TẠO REPOSITORY TRÊN GITHUB

### 1.1 Đăng Nhập GitHub
1. Truy cập: https://github.com/login
2. Nhập username & password của bạn

### 1.2 Tạo Repository Mới
1. Click dấu **+** ở góc trên phải → "New repository"
2. Hoặc truy cập: https://github.com/new

### 1.3 Điền Thông Tin Repository

```
Repository name: UMA-Browser
Description: TCP/IP Web Browser with JavaFX
Public ✓ (hoặc Private tùy bạn)

Tùy chọn:
☐ Initialize with README (đã có README.md)
☐ Add .gitignore (đã có)
☐ Choose license (optional)
```

**Nhấn "Create repository"**

---

## 🔗 BƯỚC 2: LIÊN KẾT LOCAL REPO VỚI GITHUB

Sau khi tạo repository, GitHub sẽ hiển thị các lệnh. HOẶC làm theo dưới đây:

### 2.1 Thêm Remote URL

**Thay `<USERNAME>` và `<REPO_NAME>` bằng của bạn:**

```bash
cd d:\lập trình mạng\UMA-Browser_no.1

git remote add origin https://github.com/<USERNAME>/UMA-Browser.git

# Ví dụ:
# git remote add origin https://github.com/john123/UMA-Browser.git
```

### 2.2 Kiểm Tra Remote Được Thêm

```bash
git remote -v
```

Kết quả mong đợi:
```
origin  https://github.com/<USERNAME>/UMA-Browser.git (fetch)
origin  https://github.com/<USERNAME>/UMA-Browser.git (push)
```

---

## 📤 BƯỚC 3: PUSH CODE LÊN GITHUB

### 3.1 Đặt Branch Mặc Định (Nếu Cần)

```bash
git branch -M main
```

### 3.2 Push Code

```bash
git push -u origin main
```

**Lần đầu tiên:**
- Nó sẽ yêu cầu xác thực
- Nhập GitHub username & password (hoặc Personal Access Token)

**Kết quả mong đợi:**
```
Enumerating objects: 18, done.
Counting objects: 100% (18/18), done.
Delta compression using up to 8 threads
Compressing objects: 100% (12/12), done.
Writing objects: 100% (18/18), 45.23 KiB | 1.23 MiB/s, done.
Total 18 (delta 0), reused 0 (delta 0), pack-reused 0
To https://github.com/<USERNAME>/UMA-Browser.git
 * [new branch]      main -> main
Branch 'main' set up to track remote branch 'main' from 'origin'.
```

---

## 🔐 BƯỚC 4: CẤU HÌNH AUTHENTICATION (TÙY CHỌN)

Nếu gặp lỗi authentication, sử dụng Personal Access Token:

### 4.1 Tạo Personal Access Token

1. GitHub → Settings → Developer settings → Personal access tokens
2. Click "Generate new token (classic)"
3. Chọn scopes: `repo`, `read:user`
4. Tạo token và **COPY** (chỉ hiện 1 lần)

### 4.2 Sử Dụng Token Để Push

```bash
git push https://<TOKEN>@github.com/<USERNAME>/UMA-Browser.git main
```

Hoặc lưu credentials:
```bash
git config --global credential.helper store
git push origin main
# Rồi nhập token khi được yêu cầu
```

---

## ✅ BƯỚC 5: KIỂM TRA KẾT QUẢ

### 5.1 Kiểm Tra GitHub Web

1. Truy cập: https://github.com/`<USERNAME>`/UMA-Browser
2. Nên thấy tất cả files, commit, branches

### 5.2 Kiểm Tra Local

```bash
git log --oneline
```

Kết quả:
```
653ac47 (HEAD -> main, origin/main) Initial commit: UMA Browser -TCP/IP & Client...
```

---

## 📝 CÁCH COMMIT & PUSH THÊM TRONG TƯƠNG LAI

### Thêm Tính Năng Mới

```bash
# 1. Sửa đổi files

# 2. Kiểm tra thay đổi
git status

# 3. Thêm files
git add .

# 4. Commit
git commit -m "Add new feature: [mô tả]"

# 5. Push
git push origin main
```

### Ví Dụ:
```bash
# Thêm trang mới
git add src/main/java/server/WebServer.java
git commit -m "Add /tutorial page to WebServer"
git push origin main

# Update documentation
git add README.md
git commit -m "Update README with new examples"
git push origin main
```

---

## 🐛 TROUBLESHOOTING

### ❌ Lỗi: "fatal: 'origin' does not appear to be a 'git' repository"

**Nguyên nhân:** Chưa thêm remote URL

**Giải pháp:**
```bash
git remote add origin https://github.com/<USERNAME>/UMA-Browser.git
git push -u origin main
```

---

### ❌ Lỗi: "Please make sure you have the correct access rights"

**Nguyên nhân:** Authentication fail (sai password/token)

**Giải pháp:**
```bash
# Xóa cached credentials
git credential-manager erase https://github.com

# Push lại (sẽ yêu cầu credentials)
git push origin main
```

---

### ❌ Lỗi: "The following untracked working tree files would be overwritten"

**Nguyên nhân:** Có files chưa committed

**Giải pháp:**
```bash
git status  # Xem files
git add .
git commit -m "Stage all changes"
git push origin main
```

---

### ❌ Lỗi: "fatal: branch 'master' does not fully exist"

**Nguyên nhân:** Branch tên sai (master vs main)

**Giải pháp:**
```bash
git branch  # Xem branches hiện tại
git branch -M main  # Đổi tên thành main
git push -u origin main
```

---

## 📊 KIỂM TRA GIT STATUS

```bash
# Xem status
git status

# Xem commits
git log --oneline

# Xem remote
git remote -v

# Xem branches
git branch -a

# Xem file thay đổi
git diff
```

---

## 💡 TIP & TRICKS

### Xóa Remote (nếu sai)
```bash
git remote remove origin
git remote add origin https://github.com/<USERNAME>/<REPO>.git
```

### Clone Repository (để test)
```bash
git clone https://github.com/<USERNAME>/UMA-Browser.git
cd UMA-Browser
```

### Tải Latest Changes (nếu push từ máy khác)
```bash
git pull origin main
```

### View Remote URL
```bash
git remote get-url origin
```

---

## 🎉 HOÀN THÀNH!

✅ Repository đã được tạo
✅ Code đã được push
✅ GitHub repo khả dụng công khai

**Repository URL:**
```
https://github.com/<USERNAME>/UMA-Browser
```

**Clone URL:**
```
https://github.com/<USERNAME>/UMA-Browser.git
```

---

## 📱 LƯU Ý MỘT SỐ ĐIỀU

1. **Public vs Private:**
   - Public: Ai cũng thấy, thích hợp cho portfolio
   - Private: Chỉ bạn & collaborators thấy

2. **License:**
   - MIT License: Flexible, liberal
   - GPL: Copyleft
   - CC0: Public domain

3. **README vs Wiki:**
   - README.md: Hiển thị trên repo page
   - wiki/: Trang từ chi tiết

4. **Issues & Pull Requests:**
   - Issues: Bug reports, feature requests
   - PRs: Code contributions

5. **Releases & Tags:**
   ```bash
   git tag v1.0.0
   git push origin v1.0.0
   ```

---

## 🔗 USEFUL LINKS

- GitHub: https://github.com
- Git Documentation: https://git-scm.com/doc
- GitHub Guides: https://guides.github.com/
- Git Cheat Sheet: https://github.github.com/training-kit/downloads/github-git-cheat-sheet.pdf

---

**Happy coding on GitHub! 🚀**

Last Updated: 2026-02-24
