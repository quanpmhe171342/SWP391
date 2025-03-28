<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
    <head>
        <title>Thông Tin Cá Nhân</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <h2 class="text-center">Thông Tin Cá Nhân</h2>

            <!-- Hiển thị thông báo -->
            <c:if test="${param.success == 'true'}">
                <div class="alert alert-success text-center">Cập nhật thành công!</div>
            </c:if>
            <c:if test="${param.error == 'true'}">
                <div class="alert alert-danger text-center">Cập nhật thất bại!</div>
            </c:if>

            <form method="POST" action="${pageContext.request.contextPath}/updateprofile" id="profileForm">
                <input type="hidden" name="userId" value="${sessionScope.user.userId}" />

                <!-- Tên đăng nhập (readonly) -->
                <div class="mb-3">
                    <label class="form-label">Tên đăng nhập</label>
                    <input type="text" class="form-control" value="${sessionScope.user.username}" readonly />
                </div>

                <!-- Họ -->
                <div class="mb-3">
                    <label class="form-label">Họ</label>
                    <input type="text" name="firstName" id="firstName" class="form-control"
                           value="${sessionScope.user.firstName != null ? sessionScope.user.firstName : ''}" />
                    <small id="firstNameError" class="text-danger"></small>
                </div>

                <!-- Tên -->
                <div class="mb-3">
                    <label class="form-label">Tên</label>
                    <input type="text" name="lastName" id="lastName" class="form-control"
                           value="${sessionScope.user.lastName != null ? sessionScope.user.lastName : ''}" />
                    <small id="lastNameError" class="text-danger"></small>
                </div>

                <!-- Email -->
                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" name="email" id="email" class="form-control"
                           value="${sessionScope.user.email != null ? sessionScope.user.email : ''}" />
                    <small id="emailError" class="text-danger"></small>
                </div>

                <!-- Số điện thoại -->
                <div class="mb-3">
                    <label class="form-label">Số điện thoại</label>
                    <input type="text" name="phone" id="phone" class="form-control"
                           value="${sessionScope.user.phone != null ? sessionScope.user.phone : ''}" />
                    <small id="phoneError" class="text-danger"></small>
                </div>

                <!-- Ngày tạo tài khoản (readonly) -->
                <div class="mb-3">
                    <label class="form-label">Ngày tạo tài khoản</label>
                    <input type="text" class="form-control" value="${sessionScope.user.dob}" readonly />
                </div>

                <button type="submit" class="btn btn-primary">Cập Nhật</button>
            </form>
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const form = document.getElementById("profileForm");
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

                    // Regex
                    const nameRegex = /^[A-Za-zÀ-Ỹà-ỹ\s]+$/;
                    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                    const phoneRegex = /^[0-9]{10}$/;

                    // Kiểm tra họ (bắt buộc)
                    if (firstName.value.trim() === "") {
                        firstNameError.textContent = "Vui lòng nhập họ!";
                        isValid = false;
                    } else if (!nameRegex.test(firstName.value.trim())) {
                        firstNameError.textContent = "Họ không hợp lệ (chỉ chứa chữ cái và khoảng trắng)!";
                        isValid = false;
                    } else {
                        firstNameError.textContent = "";
                    }

                    // Kiểm tra tên (bắt buộc)
                    if (lastName.value.trim() === "") {
                        lastNameError.textContent = "Vui lòng nhập tên!";
                        isValid = false;
                    } else if (!nameRegex.test(lastName.value.trim())) {
                        lastNameError.textContent = "Tên không hợp lệ (chỉ chứa chữ cái và khoảng trắng)!";
                        isValid = false;
                    } else {
                        lastNameError.textContent = "";
                    }

                    // Kiểm tra email (bắt buộc)
                    if (email.value.trim() === "") {
                        emailError.textContent = "Vui lòng nhập email!";
                        isValid = false;
                    } else if (!emailRegex.test(email.value.trim())) {
                        emailError.textContent = "Email không hợp lệ!";
                        isValid = false;
                    } else {
                        emailError.textContent = "";
                    }

                    // Kiểm tra số điện thoại (bắt buộc)
                    if (phone.value.trim() === "") {
                        phoneError.textContent = "Vui lòng nhập số điện thoại!";
                        isValid = false;
                    } else if (!phoneRegex.test(phone.value.trim())) {
                        phoneError.textContent = "Số điện thoại phải có đúng 10 chữ số!";
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
