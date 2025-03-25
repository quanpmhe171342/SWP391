<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="vi">
    <head>
        <title>Trang Đăng Ký</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <style>
            .logo-top {
                position: absolute;
                top: 20px;
                left: 20px;
            }
            .logo-top img {
                height: 40px;
            }
        </style>
    </head>
    <body>
        <div class="logo-top">
            <a href="<%= request.getContextPath() %>/HomePage">
                <img src="<%= request.getContextPath() %>/img/logo.png" alt="Logo">
            </a>
        </div>

        <section class="vh-100" style="background-color: #eee;">
            <div class="container h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-lg-12 col-xl-11">
                        <div class="card text-black" style="border-radius: 25px;">
                            <div class="card-body p-md-5">
                                <div class="row justify-content-center">
                                    <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">
                                        <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Đăng Ký</p>

                                        <c:if test="${not empty successMessage}">
                                            <div class="alert alert-success text-center">${successMessage}</div>
                                        </c:if>
                                        <c:if test="${not empty errorMessage}">
                                            <div class="alert alert-danger text-center">${errorMessage}</div>
                                        </c:if>

                                        <form method="POST" action="${pageContext.request.contextPath}/register">
                                            <div class="mb-4">
                                                <label class="form-label" for="username">Tên đăng nhập</label>
                                                <input type="text" id="username" name="username" class="form-control" value="${param.username}" />
                                                <small id="usernameError" class="text-danger"></small>
                                            </div>

                                            <div class="mb-4">
                                                <label class="form-label" for="email">Địa chỉ email</label>
                                                <input type="text" id="email" name="email" class="form-control" value="${param.email}" />
                                                <small id="emailError" class="text-danger"></small>
                                            </div>

                                            <div class="mb-4">
                                                <label class="form-label" for="password">Mật khẩu</label>
                                                <input type="password" id="password" name="password" class="form-control"/>
                                                <small id="passwordError" class="text-danger"></small>
                                            </div>

                                            <div class="mb-4">
                                                <label class="form-label" for="repeatPassword">Nhập lại mật khẩu</label>
                                                <input type="password" id="repeatPassword" name="repeatPassword" class="form-control"/>
                                                <small id="repeatPasswordError" class="text-danger"></small>
                                            </div>

                                            <div class="d-flex justify-content-center">
                                                <button type="submit" class="btn btn-primary btn-lg">Đăng Ký</button>
                                            </div>
                                        </form>

                                        <div class="text-center mt-3">
                                            <p>Nếu đã có tài khoản, hãy <a href="<%= request.getContextPath() %>/login" class="fw-bold">đăng nhập ở đây</a>.</p>
                                        </div>
                                    </div>

                                    <div class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">
                                        <img src="<%= request.getContextPath() %>/img/photo/register.jpg" alt="Hình ảnh minh họa" class="img-fluid">
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
                const form = document.querySelector("form");
                form.addEventListener("submit", function (event) {
                    let isValid = true;

                    const username = document.getElementById("username").value.trim();
                    const email = document.getElementById("email").value.trim();
                    const password = document.getElementById("password").value.trim();
                    const repeatPassword = document.getElementById("repeatPassword").value.trim();

                    const usernameError = document.getElementById("usernameError");
                    const emailError = document.getElementById("emailError");
                    const passwordError = document.getElementById("passwordError");
                    const repeatPasswordError = document.getElementById("repeatPasswordError");

                    const usernameRegex = /^[a-zA-Z0-9_]{4,20}$/;
                    if (!usernameRegex.test(username)) {
                        usernameError.textContent = "Tên đăng nhập từ 4-20 ký tự, chỉ chứa chữ cái, số, gạch dưới.";
                        isValid = false;
                    } else {
                        usernameError.textContent = "";
                    }

                    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                    if (!emailRegex.test(email)) {
                        emailError.textContent = "Email không hợp lệ!";
                        isValid = false;
                    } else {
                        emailError.textContent = "";
                    }

                    const passwordRegex = /^(?=.*[A-Z])(?=.*\d).{8,}$/;
                    if (!passwordRegex.test(password)) {
                        passwordError.textContent = "Mật khẩu tối thiểu 8 ký tự, gồm ít nhất 1 chữ hoa và 1 số.";
                        isValid = false;
                    } else {
                        passwordError.textContent = "";
                    }

                    if (password !== repeatPassword) {
                        repeatPasswordError.textContent = "Mật khẩu nhập lại không khớp!";
                        isValid = false;
                    } else {
                        repeatPasswordError.textContent = "";
                    }

                    if (!isValid) {
                        event.preventDefault();
                    }
                });
            });
        </script>
    </body>
</html>
