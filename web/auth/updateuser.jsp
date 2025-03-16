<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
    <head>
        <title>Cập Nhật Thông Tin Nhân Viên</title>
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
                                        <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Cập Nhật Nhân Viên</p>
                                        <form id="updateForm" method="POST" action="${pageContext.request.contextPath}/updatestaff">
                                            
                                            <div class="mb-4">
                                                <label class="form-label" for="username">Tên đăng nhập</label>
                                                <input type="text" id="username" name="username" class="form-control" value="${user.username}" readonly />
                                            </div>

                                            <div class="mb-4">
                                                <label class="form-label" for="oldPassword">Mật khẩu cũ</label>
                                                <input type="password" id="oldPassword" name="oldPassword" class="form-control" required />
                                                <small id="oldPasswordError" class="error-text"></small>
                                            </div>

                                            <div class="mb-4 row">
                                                <div class="col-md-6">
                                                    <label class="form-label" for="firstName">Họ</label>
                                                    <input type="text" id="firstName" name="firstName" class="form-control" value="${user.firstName}" required />
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="form-label" for="lastName">Tên</label>
                                                    <input type="text" id="lastName" name="lastName" class="form-control" value="${user.lastName}" required />
                                                </div>
                                            </div>
                                            
                                            <div class="mb-4">
                                                <label class="form-label" for="email">Email</label>
                                                <input type="email" id="email" name="email" class="form-control" value="${user.email}" required />
                                            </div>

                                            <div class="mb-4">
                                                <label class="form-label" for="phone">Số Điện Thoại</label>
                                                <input type="tel" id="phone" name="phone" class="form-control" value="${user.phone}" required />
                                            </div>

                                            <div class="d-flex justify-content-center mb-3">
                                                <button type="submit" class="btn btn-primary btn-lg">Cập Nhật</button>
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
    </body>
</html>
