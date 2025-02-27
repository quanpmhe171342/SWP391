<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng Nhập Nội Bộ</title>
        <!-- Bootstrap 5 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f8f9fa;
            }
            .login-container {
                max-width: 450px;
                background: white;
                padding: 40px;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
        </style>
    </head>
    <body>
        <section class="vh-100 d-flex justify-content-center align-items-center">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-6 col-lg-5">
                        <div class="login-container">
                            <div class="text-center">
                                <p class="h1 fw-bold mb-4">Đăng Nhập Nội Bộ</p>
                            </div>
                            <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
                            <% if (errorMessage != null) { %>
                            <div class="alert alert-danger" role="alert">
                                <%= errorMessage %>
                            </div>
                            <% } %>

                            <form method="POST" action="${pageContext.request.contextPath}/adminlogin">
                                <!-- Email input -->
                                <div class="form-outline mb-3">
                                    <input type="text" id="username" name="username" class="form-control" placeholder="Nhập username" required />
                                    <label class="form-label" for="username">Tên đăng nhập</label>
                                </div>

                                <!-- Mật khẩu -->
                                <div class="form-outline mb-3">
                                    <input type="password" id="password" name="password" class="form-control" placeholder="Nhập mật khẩu" required />
                                    <label class="form-label" for="password">Mật khẩu</label>
                                </div>

                                <div class="d-flex justify-content-between align-items-center">
                                    <!-- Checkbox -->
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" id="rememberMe" />
                                        <label class="form-check-label" for="rememberMe">Nhớ tài khoản</label>
                                    </div>
                                    <a href="#" class="text-primary">Quên mật khẩu?</a>
                                </div>

                                <div class="text-center mt-4">
                                    <button type="submit" class="btn btn-primary w-100">Đăng nhập</button>
                                </div>


                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- Bootstrap 5 JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
