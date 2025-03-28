<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Tạo Tài Khoản Nhân Viên</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .error-text { color: red; font-size: 14px; }
        </style>
    </head>
    <body>
        <section class="vh-100" style="background-color: #eee;">
            <div class="container h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-lg-12 col-xl-11">
                        <div class="card text-black" style="border-radius: 25px;">
                            <div class="card-body p-md-5">
                                <div class="row justify-content-center">
                                    <div class="col-md-10 col-lg-6 col-xl-5">
                                        <p class="text-center h1 fw-bold mb-4 mx-1 mx-md-4 mt-4">Thêm Nhân Viên</p>

                                        <c:if test="${not empty successMessage}">
                                            <div class="alert alert-success text-center" role="alert">
                                                ${successMessage}
                                            </div>
                                        </c:if>
                                        <c:if test="${not empty errorMessage}">
                                            <div class="alert alert-danger text-center" role="alert">
                                                ${errorMessage}
                                            </div>
                                        </c:if>

                                        <form id="registerForm" method="POST" action="${pageContext.request.contextPath}/addstaff">
                                            
                                            <div class="mb-4">
                                                <label class="form-label" for="username">Tên đăng nhập</label>
                                                <input type="text" id="username" name="username" class="form-control" required />
                                                <small id="usernameError" class="error-text"></small>
                                            </div>

                                            <div class="mb-4 row">
                                                <div class="col-md-6">
                                                    <label class="form-label" for="firstName">Họ</label>
                                                    <input type="text" id="firstName" name="firstName" class="form-control" required />
                                                    <small id="firstNameError" class="error-text"></small>
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="form-label" for="lastName">Tên</label>
                                                    <input type="text" id="lastName" name="lastName" class="form-control" required />
                                                    <small id="lastNameError" class="error-text"></small>
                                                </div>
                                            </div>
                                            
                                            <div class="mb-4">
                                                <label class="form-label" for="email">Email</label>
                                                <input type="email" id="email" name="email" class="form-control" required />
                                                <small id="emailError" class="error-text"></small>
                                            </div>

                                            <div class="mb-4">
                                                <label class="form-label" for="phone">Số Điện Thoại</label>
                                                <input type="tel" id="phone" name="phone" class="form-control" required />
                                                <small id="phoneError" class="error-text"></small>
                                            </div>

                                            <div class="mb-4">
                                                <label class="form-label" for="password">Mật khẩu</label>
                                                <input type="password" id="password" name="password" class="form-control" required />
                                                <small id="passwordError" class="error-text"></small>
                                            </div>

                                            <div class="mb-4">
                                                <label class="form-label" for="repeatPassword">Nhập lại mật khẩu</label>
                                                <input type="password" id="repeatPassword" name="repeatPassword" class="form-control" required />
                                                <small id="repeatPasswordError" class="error-text"></small>
                                            </div>

                                            <div class="d-flex justify-content-center mb-3">
                                                <button type="submit" class="btn btn-primary btn-lg">Tạo Tài Khoản</button>
                                            </div>

                                            <div class="d-flex justify-content-center">
                                                <a href="${pageContext.request.contextPath}/viewstaff" class="btn btn-secondary btn-lg">Quay lại danh sách nhân viên</a>
                                            </div>

                                        </form>
                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
        
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const form = document.getElementById("registerForm");

                form.addEventListener("submit", function (event) {
                    let isValid = true;

                    function showError(input, message) {
                        const errorElement = document.getElementById(input.id + "Error");
                        errorElement.textContent = message;
                    }

                    function clearError(input) {
                        document.getElementById(input.id + "Error").textContent = "";
                    }

                    // Kiểm tra username (4-20 ký tự, chỉ chữ cái, số, gạch dưới)
                    const username = document.getElementById("username");
                    const usernameRegex = /^[a-zA-Z0-9_]{4,20}$/;
                    if (!usernameRegex.test(username.value.trim())) {
                        showError(username, "Tên đăng nhập phải có 4-20 ký tự, chỉ gồm chữ cái, số và _");
                        isValid = false;
                    } else {
                        clearError(username);
                    }

                    // Kiểm tra họ & tên (chỉ chữ cái, tối thiểu 2 ký tự)
                    const nameRegex = /^[A-Za-zÀ-ỹ\s]{2,}$/;
                    const firstName = document.getElementById("firstName");
                    const lastName = document.getElementById("lastName");

                    if (!nameRegex.test(firstName.value.trim())) {
                        showError(firstName, "Họ phải có ít nhất 2 ký tự và chỉ chứa chữ cái.");
                        isValid = false;
                    } else {
                        clearError(firstName);
                    }

                    if (!nameRegex.test(lastName.value.trim())) {
                        showError(lastName, "Tên phải có ít nhất 2 ký tự và chỉ chứa chữ cái.");
                        isValid = false;
                    } else {
                        clearError(lastName);
                    }

                    // Kiểm tra email
                    const email = document.getElementById("email");
                    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                    if (!emailRegex.test(email.value.trim())) {
                        showError(email, "Email không hợp lệ!");
                        isValid = false;
                    } else {
                        clearError(email);
                    }

                    // Kiểm tra số điện thoại (chỉ đúng 10 chữ số)
                    const phone = document.getElementById("phone");
                    const phoneRegex = /^[0-9]{10}$/;
                    if (!phoneRegex.test(phone.value.trim())) {
                        showError(phone, "Số điện thoại phải có đúng 10 chữ số.");
                        isValid = false;
                    } else {
                        clearError(phone);
                    }

                    // Kiểm tra mật khẩu (ít nhất 8 ký tự, 1 chữ hoa, 1 số)
                    const password = document.getElementById("password");
                    const passwordRegex = /^(?=.*[A-Z])(?=.*\d).{8,}$/;
                    if (!passwordRegex.test(password.value.trim())) {
                        showError(password, "Mật khẩu phải có ít nhất 8 ký tự, gồm ít nhất 1 chữ hoa và 1 số.");
                        isValid = false;
                    } else {
                        clearError(password);
                    }

                    // Kiểm tra nhập lại mật khẩu
                    const repeatPassword = document.getElementById("repeatPassword");
                    if (password.value !== repeatPassword.value) {
                        showError(repeatPassword, "Mật khẩu nhập lại không khớp!");
                        isValid = false;
                    } else {
                        clearError(repeatPassword);
                    }

                    if (!isValid) event.preventDefault();
                });
            });
        </script>
    </body>
</html>
