<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đặt lại mật khẩu</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <section class="vh-100 d-flex align-items-center justify-content-center" style="background-color: #eee;">
        <div class="card p-4" style="border-radius: 15px; width: 100%; max-width: 500px;">
            <div class="card-body">
                <h3 class="text-center mb-4">Đặt lại mật khẩu</h3>

                <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
                <% String successMessage = (String) request.getAttribute("successMessage"); %>

                <% if (errorMessage != null) { %>
                    <div class="alert alert-danger text-center"><%= errorMessage %></div>
                <% } %>

                <% if (successMessage != null) { %>
                    <div class="alert alert-success text-center"><%= successMessage %></div>
                <% } %>

                <!-- Chỉ hiển thị form nếu chưa đổi mật khẩu thành công -->
                <% if (successMessage == null) { %>
                    <form action="resetpassword" method="post" onsubmit="return validateForm()">
                        <input type="hidden" name="token" value="<%= request.getParameter("token") %>">

                        <div class="mb-3">
                            <label for="newPassword" class="form-label">Mật khẩu mới</label>
                            <input type="password" id="newPassword" name="newPassword" class="form-control" required>
                            <small id="newPasswordError" class="text-danger"></small>
                        </div>

                        <div class="mb-3">
                            <label for="confirmPassword" class="form-label">Nhập lại mật khẩu mới</label>
                            <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required>
                            <small id="confirmPasswordError" class="text-danger"></small>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">Đổi mật khẩu</button>
                        </div>
                    </form>
                <% } else { %>
                    <!-- Nút đăng nhập sau khi đổi mật khẩu thành công -->
                    <div class="text-center mt-3">
                        <a href="<%= request.getContextPath() %>/login" class="btn btn-success">Đăng nhập</a>
                    </div>
                <% } %>
            </div>
        </div>
    </section>

    <script>
        function validateForm() {
            const newPassword = document.getElementById("newPassword").value.trim();
            const confirmPassword = document.getElementById("confirmPassword").value.trim();
            const newPasswordError = document.getElementById("newPasswordError");
            const confirmPasswordError = document.getElementById("confirmPasswordError");

            let isValid = true;
            const passwordRegex = /^(?=.*[A-Z])(?=.*\d).{8,}$/;

            if (!passwordRegex.test(newPassword)) {
                newPasswordError.textContent = "Mật khẩu phải có ít nhất 8 ký tự, bao gồm ít nhất 1 chữ hoa và 1 số.";
                isValid = false;
            } else {
                newPasswordError.textContent = "";
            }

            if (newPassword !== confirmPassword) {
                confirmPasswordError.textContent = "Mật khẩu nhập lại không khớp!";
                isValid = false;
            } else {
                confirmPasswordError.textContent = "";
            }

            return isValid;
        }
    </script>
</body>
</html>
