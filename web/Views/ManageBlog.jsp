<%-- 
    Document   : ManageBlog
    Created on : Mar 11, 2025, 7:50:23 AM
    Author     : admin
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý bài viết</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Quill.js -->
    <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
    <script src="https://cdn.quilljs.com/1.3.6/quill.min.js"></script>

    <script>
        function previewImage(event) {
            const reader = new FileReader();
            reader.onload = function(){
                const output = document.getElementById('imagePreview');
                output.src = reader.result;
                output.style.display = 'block';
            };
            reader.readAsDataURL(event.target.files[0]);
        }

        function confirmDelete(blogId) {
            if (confirm("Bạn có chắc muốn xóa bài viết này không?")) {
                window.location.href = "BlogServlet?action=delete&blogId=" + blogId;
            }
        }

        function changeFontSize(size) {
            var editor = document.querySelector('.ql-editor');
            editor.style.fontSize = size + "px";
        }
    </script>
    
    <style>
        .ql-editor {
            min-height: 200px;
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        <div class="row">
            <!-- Phần bên trái: Form tạo bài viết -->
            <div class="col-md-6">
                <h3>Tạo Bài Viết Mới</h3>
                <form action="BlogServlet?action=create" method="post" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label for="title" class="form-label">Tiêu đề</label>
                        <input type="text" class="form-control" id="title" name="title" required>
                    </div>

                    <!-- Quill Editor -->
                    <div class="mb-3">
                        <label for="editor" class="form-label">Nội dung</label>

                        <!-- Form chọn cỡ chữ -->
                        <div class="mb-2">
                            <label for="fontSize" class="form-label">Cỡ chữ:</label>
                            <select class="form-select" id="fontSize" onchange="changeFontSize(this.value)">
                                <option value="14">14px</option>
                                <option value="16" selected>16px (Mặc định)</option>
                                <option value="18">18px</option>
                                <option value="20">20px</option>
                                <option value="24">24px</option>
                            </select>
                        </div>

                        <div id="editor" style="height: 200px;"></div>
                        <input type="hidden" id="content" name="content">
                    </div>

                    <div class="mb-3">
                        <label for="category" class="form-label">Danh mục</label>
                        <select class="form-select" id="category" name="category" required>
                             <option value="1">ÁO DA</option>
                            <option value="2">GIÀY DA</option>
                            <option value="3">VÍ DA</option>
                            <option value="4">THẮT LƯNG</option>
                        </select>
                    </div>

                    <!-- Phần upload ảnh -->
                    <div class="mb-3">
                        <label for="imageUpload" class="form-label">Chọn ảnh:</label>
                        <input type="file" class="form-control" id="imageUpload" name="image" accept="image/*" onchange="previewImage(event)">
                    </div>
                    <div class="mb-3 text-center">
                        <img id="imagePreview" src="#" alt="Ảnh xem trước" class="img-fluid rounded" style="max-width: 100%; display: none;">
                    </div>

                    <button type="submit" class="btn btn-primary">Đăng bài</button>
                </form>
            </div>
        </div>

        <!-- Bảng danh sách bài viết -->
        <div class="row mt-5">
            <div class="col-12">
                <h3>Danh Sách Bài Viết</h3>
                <table class="table table-bordered table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>BlogID</th>
                            <th>Title</th>
                            <th>Image</th>
                            <th>Content</th>
                            <th>Category</th>
                            <th>Created At</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="blog" items="${blogList}">
                            <tr>
                                <td>${blog.blogId}</td>
                                <td>${blog.title}</td>
                                <td>
                                    <img src="${blog.imageUrl}" alt="Ảnh" style="width: 80px; height: auto;">
                                </td>
                                <td>${blog.content}</td>
                                <td>${blog.categoryBlogId}</td>
                                <td><fmt:formatDate value="${blog.createdAt}" pattern="dd/MM/yyyy HH:mm" /></td>
                                <td>
                                    <a href="editBlog.jsp?blogId=${blog.blogId}" class="btn btn-sm btn-warning">Sửa</a>
                                    <button class="btn btn-sm btn-danger" onclick="confirmDelete(${blog.blogId})">Xóa</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script>
        // Khởi tạo Quill Editor
        var quill = new Quill('#editor', {
            theme: 'snow'
        });

        // Cập nhật nội dung Quill vào input ẩn khi gửi form
        document.querySelector('form').onsubmit = function() {
            document.querySelector('#content').value = quill.root.innerHTML;
        };
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
