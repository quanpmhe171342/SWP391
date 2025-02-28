<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="Model.User" %>

<html>
    <head>
        <title>Trang Đổi Mật Khẩu</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <section class="vh-100 d-flex align-items-center justify-content-center" style="background-color: #eee;">
            <div class="card p-4" style="border-radius: 15px; width: 100%; max-width: 500px;">
                <div class="card-body">
                    <%-- Lấy thông tin người dùng từ session --%>
                    <%
                        User user = (User) session.getAttribute("user");  // Lấy đối tượng User từ session
                        if (user != null) {
                            String username = user.getUsername();  // Truy cập thuộc tính username từ đối tượng User
                    %>
                    <h4 class="text-center">Chào mừng, <%= username %>!</h4>
                    <% 
                        } else {
                    %>
                    <h4 class="text-center">Chào mừng bạn!</h4>
                    <% 
                        }
                    %>

                    <h3 class="text-center mb-4">Đổi Mật Khẩu</h3>
                    <% String error = (String) request.getAttribute("error"); %>
                    <% String success = (String) request.getAttribute("success"); %>

                    <% if (error != null) { %>
                    <div class="alert alert-danger text-center"><%= error %></div>
                    <% } %>

                    <% if (success != null) { %>
                    <div class="alert alert-success text-center"><%= success %></div>
                    <% } %>

                    <form action="../SWP391/changepassword" method="POST">
                        <div class="mb-3">
                            <label for="oldPassword" class="form-label">Mật khẩu cũ</label>
                            <input type="password" id="oldPassword" name="oldPassword" class="form-control" required>
                            <small id="oldPasswordError" class="text-danger"></small>
                        </div>

                        <div class="mb-3">
                            <label for="newPassword" class="form-label">Mật khẩu mới</label>
                            <input type="password" id="newPassword" name="newPassword" class="form-control" required>
                            <small id="newPasswordError" class="text-danger"></small>
                        </div>

                        <div class="mb-3">
                            <label for="repeatNewPassword" class="form-label">Nhập lại mật khẩu mới</label>
                            <input type="password" id="repeatNewPassword" name="repeatNewPassword" class="form-control" required>
                            <small id="repeatNewPasswordError" class="text-danger"></small>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">Đổi Mật Khẩu</button>
                        </div>
                    </form>

                    <div class="text-center mt-3">
                        <p>Quay lại trang <a href="../SWP391/login" class="fw-bold">đăng nhập</a>.</p>
                    </div>
                </div>
            </div>
        </section>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const form = document.querySelector("form");
                const oldPassword = document.getElementById("oldPassword");
                const newPassword = document.getElementById("newPassword");
                const repeatNewPassword = document.getElementById("repeatNewPassword");

                const oldPasswordError = document.getElementById("oldPasswordError");
                const newPasswordError = document.getElementById("newPasswordError");

                form.addEventListener("submit", function (event) {
                    let isValid = true;

                    // Kiểm tra mật khẩu cũ
                    if (oldPassword.value.trim() === "") {
                        oldPasswordError.textContent = "Vui lòng nhập mật khẩu cũ.";
                        isValid = false;
                    } else {
                        oldPasswordError.textContent = "";
                    }

                    // Kiểm tra mật khẩu mới (ít nhất 8 ký tự, 1 chữ hoa, 1 số)
                    const passwordRegex = /^(?=.*[A-Z])(?=.*\d).{8,}$/;
                    if (!passwordRegex.test(newPassword.value.trim())) {
                        newPasswordError.textContent = "Mật khẩu phải có ít nhất 8 ký tự, bao gồm ít nhất 1 chữ hoa và 1 số.";
                        isValid = false;
                    } else if (newPassword.value.trim() !== repeatNewPassword.value.trim()) {
                        newPasswordError.textContent = "Mật khẩu mới và nhập lại mật khẩu không khớp!";
                        isValid = false;
                    } else {
                        newPasswordError.textContent = "";
                    }

                    if (!isValid) {
                        event.preventDefault(); // Ngăn chặn gửi form nếu có lỗi
                    }
                });
            });
        </script>
    </body>
</html>

