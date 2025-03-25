<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng Nhập</title>
        <!-- Bootstrap 5 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <style>
            .logo-container {
                position: absolute;
                top: 20px;
                left: 20px;
            }
            .logo-container img {
                height: 25px;
                width: auto;
            }
        </style>
    </head>
    <body>
        <!-- Logo -->
        <div class="logo-container">
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
                                        <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Đăng Nhập</p>

                                        <c:if test="${not empty errorMessage}">
                                            <div class="alert alert-danger text-center">${errorMessage}</div>
                                        </c:if>

                                        <form method="POST" action="${pageContext.request.contextPath}/login">
                                            <div class="mb-4">
                                                <label class="form-label" for="username">Tên đăng nhập</label>
                                                <input type="text" id="username" name="username" class="form-control" />
                                            </div>

                                            <div class="mb-4">
                                                <label class="form-label" for="password">Mật khẩu</label>
                                                <input type="password" id="password" name="password" class="form-control" />
                                            </div>

                                            <div class="d-flex justify-content-between align-items-center mb-4">
                                                <a href="<%= request.getContextPath() %>/sendresetlink" class="text-body">Quên mật khẩu?</a>
                                            </div>

                                            <div class="d-flex justify-content-center">
                                                <button type="submit" class="btn btn-primary btn-lg">Đăng Nhập</button>
                                            </div>
                                        </form>

                                        <div class="text-center mt-3">
                                            <p>Nếu chưa có tài khoản, hãy <a href="<%= request.getContextPath() %>/register" class="fw-bold">đăng ký ở đây</a>.</p>
                                        </div>
                                    </div>

                                    <div class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">
                                        <img src="<%= request.getContextPath() %>/img/photo/register.jpg" alt="Hình ảnh minh họa đăng nhập" class="img-fluid">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
