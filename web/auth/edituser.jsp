<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
    <head>
        <title>Chỉnh Sửa Người Dùng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <h2 class="text-center">Chỉnh Sửa Người Dùng</h2>

            <c:if test="${param.success == 'true'}">
                <div class="alert alert-success text-center">Cập nhật thành công!</div>
            </c:if>
            <c:if test="${param.error == 'true'}">
                <div class="alert alert-danger text-center">Cập nhật thất bại!</div>
            </c:if>

            <form method="POST" action="${pageContext.request.contextPath}/edituser" id="editUserForm">
                <input type="hidden" name="username" value="${user.username}" />

                <!-- Tên đăng nhập (readonly) -->
                <div class="mb-3">
                    <label class="form-label">Tên đăng nhập</label>
                    <input type="text" class="form-control" value="${user.username}" readonly />
                </div>

                <div class="mb-3">
                    <label class="form-label">Họ</label>
                    <input type="text" name="firstName" id="firstName" class="form-control" value="${user.firstName}" />
                    <small id="firstNameError" class="text-danger"></small>
                </div>

                <div class="mb-3">
                    <label class="form-label">Tên</label>
                    <input type="text" name="lastName" id="lastName" class="form-control" value="${user.lastName}" />
                    <small id="lastNameError" class="text-danger"></small>
                </div>

                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" name="email" id="email" class="form-control" value="${user.email}" />
                    <small id="emailError" class="text-danger"></small>
                </div>

                <div class="mb-3">
                    <label class="form-label">Số điện thoại</label>
                    <input type="text" name="phone" id="phone" class="form-control" value="${user.phone}" />
                    <small id="phoneError" class="text-danger"></small>
                </div>

                <div class="mb-3">
                    <label class="form-label">Ngày tạo tài khoản</label>
                    <input type="text" class="form-control" value="${user.dob}" readonly />
                </div>

                <div class="mb-3">
                    <label class="form-label">Trạng thái</label>
                    <select name="isActive" class="form-select">
                        <option value="1" ${user.isActive ? "selected" : ""}>Kích hoạt</option>
                        <option value="0" ${!user.isActive ? "selected" : ""}>Khóa</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">Lưu Thay Đổi</button>
                <a href="${pageContext.request.contextPath}/deleteuser?username=${user.username}" class="btn btn-danger" onclick="return confirm('Xóa người dùng này?');">Xóa</a>
            </form>
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const form = document.getElementById("editUserForm");
                const firstName = document.getElementById("firstName");
                const lastName = document.getElementById("lastName");
                const email = document.getElementById("email");
                const phone = document.getElementById("phone");

                const firstNameError = document.getElementById("firstNameError");
                const lastNameError = document.getElementById("lastNameError");
                const emailError = document.getElementById("emailError");
                const phoneError = document.getElementById("phoneError");

                form.addEventListener("submit", function (event) {
                    let isValid = true;

                    // Kiểm tra họ & tên (chỉ cho phép chữ cái, không để trống nếu nhập)
                    const nameRegex = /^[A-Za-zÀ-Ỹà-ỹ\s]+$/;
                    if (firstName.value.trim() !== "" && !nameRegex.test(firstName.value.trim())) {
                        firstNameError.textContent = "Họ không hợp lệ!";
                        isValid = false;
                    } else {
                        firstNameError.textContent = "";
                    }

                    if (lastName.value.trim() !== "" && !nameRegex.test(lastName.value.trim())) {
                        lastNameError.textContent = "Tên không hợp lệ!";
                        isValid = false;
                    } else {
                        lastNameError.textContent = "";
                    }

                    // Kiểm tra email
                    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                    if (email.value.trim() !== "" && !emailRegex.test(email.value.trim())) {
                        emailError.textContent = "Email không hợp lệ!";
                        isValid = false;
                    } else {
                        emailError.textContent = "";
                    }

                    // Kiểm tra số điện thoại: 10 số hoặc để trống
                    const phoneRegex = /^[0-9]{10}$/;
                    if (phone.value.trim() !== "" && !phoneRegex.test(phone.value.trim())) {
                        phoneError.textContent = "Số điện thoại phải có đúng 10 số!";
                        isValid = false;
                    } else {
                        phoneError.textContent = "";
                    }

                    if (!isValid) {
                        event.preventDefault();
                    }
                });
            });
        </script>
    </body>
</html>
