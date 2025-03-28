<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="vi">
    <head>
        <title>Trang Đăng Ký</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<<<<<<< HEAD
=======
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
>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05
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
                                        

<<<<<<< HEAD
                                        
                                        
                                        
                                        
                                        <!--<form class="mx-1 mx-md-4" action="../register" method="POST">-->
                                        <form method="POST" action="${pageContext.request.contextPath}/register">

                                            <div class="d-flex flex-row align-items-center mb-4">
                                                <i class="fas fa-user fa-lg me-3 fa-fw"></i>
                                                <div class="form-outline flex-fill mb-0">
                                                    <input type="text" id="username" name="username" class="form-control" />
                                                    <label class="form-label" for="username">Tên đăng nhập</label>
                                                </div>
                                            </div>

                                            <div class="d-flex flex-row align-items-center mb-4">
                                                <i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
                                                <div class="form-outline flex-fill mb-0">
                                                    <input type="text" id="email" name="email" class="form-control" />
                                                    <label class="form-label" for="email">Địa chỉ email</label>
                                                    <!--<small id="newEmailError" class="text-danger"></small>  Hiển thị lỗi cho email mới -->
                                                    <small id="emailError" class="text-danger"></small> 
                                                </div>
=======
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
>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05
                                            </div>

                                            <div class="d-flex flex-row align-items-center mb-4">
                                                <i class="fas fa-lock fa-lg me-3 fa-fw"></i>
                                                <div class="form-outline flex-fill mb-0">
                                                    <input type="password" id="password" name="password" class="form-control" />
                                                    <label class="form-label" for="password">Mật khẩu</label>
                                                    <small id="passwordError" class="text-danger"></small> <!-- Hiển thị lỗi -->
                                                </div>
                                            </div>

                                            <div class="d-flex flex-row align-items-center mb-4">
                                                <i class="fas fa-key fa-lg me-3 fa-fw"></i>
                                                <div class="form-outline flex-fill mb-0">
                                                    <input type="password" id="repeatPassword" name="repeatPassword" class="form-control" />
                                                    <label class="form-label" for="repeatPassword">Nhập lại mật khẩu</label>
                                                    <small id="repeatPasswordError" class="text-danger"></small> <!-- Hiển thị lỗi -->
                                                </div>
                                            </div>

                                            <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                                                
                                                <button type="submit" class="btn btn-primary btn-lg">Đăng Ký</button>
                                                
                                                
                                            </div>
                                        </form>
                                        
                                        
                                        
                                        
                                        
                                        

<<<<<<< HEAD
                                        <div class="text-center">
                                            <p>Nếu đã có tài khoản, hãy <a href="SWP391/auth/login.jsp" class="fw-bold">đăng nhập ở đây</a>.</p>
=======
                                        <div class="text-center mt-3">
                                            <p>Nếu đã có tài khoản, hãy <a href="<%= request.getContextPath() %>/login" class="fw-bold">đăng nhập ở đây</a>.</p>
>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05
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
<<<<<<< HEAD
                const email = document.getElementById("email");
                const password = document.getElementById("password");
                const repeatPassword = document.getElementById("repeatPassword");

                const emailError = document.getElementById("emailError");
                const passwordError = document.getElementById("passwordError");
                const repeatPasswordError = document.getElementById("repeatPasswordError");

                form.addEventListener("submit", function (event) {
                    let isValid = true;

                    // Kiểm tra username (4-20 ký tự, chỉ chứa chữ cái, số, gạch dưới)
                    const username = document.getElementById("username");
                    const usernameError = document.createElement("small");
                    usernameError.classList.add("text-danger");

                    // Nếu chưa có phần tử nhỏ hiển thị lỗi, thêm nó vào sau input
                    if (!username.nextElementSibling || !username.nextElementSibling.classList.contains("text-danger")) {
                        username.parentNode.appendChild(usernameError);
                    }

                    const usernameRegex = /^[a-zA-Z0-9_]{4,20}$/;
                    if (!usernameRegex.test(username.value.trim())) {
                        usernameError.textContent = "Tên đăng nhập chỉ được chứa chữ cái, số, gạch dưới (4-20 ký tự).";
=======
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
>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05
                        isValid = false;
                    } else {
                        usernameError.textContent = "";
                    }
<<<<<<< HEAD
                    // Kiểm tra định dạng email
=======

>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05
                    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                    if (!emailRegex.test(email)) {
                        emailError.textContent = "Email không hợp lệ!";
                        isValid = false;
                    } else {
                        emailError.textContent = "";
                    }

<<<<<<< HEAD
                    // Kiểm tra mật khẩu (ít nhất 8 ký tự, 1 chữ hoa, 1 số)
                    const passwordRegex = /^(?=.*[A-Z])(?=.*\d).{8,}$/;
                    if (!passwordRegex.test(password.value.trim())) {
                        passwordError.textContent = "Mật khẩu phải có ít nhất 8 ký tự, bao gồm ít nhất 1 chữ hoa và 1 số.";
=======
                    const passwordRegex = /^(?=.*[A-Z])(?=.*\d).{8,}$/;
                    if (!passwordRegex.test(password)) {
                        passwordError.textContent = "Mật khẩu tối thiểu 8 ký tự, gồm ít nhất 1 chữ hoa và 1 số.";
>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05
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
                        event.preventDefault(); // Ngăn chặn gửi form nếu có lỗi
                    }
                });
            });

        </script>
    </body>
</html>