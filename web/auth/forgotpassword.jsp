<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>Quên mật khẩu</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body>
        <section class="vh-100 d-flex align-items-center justify-content-center bg-light">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-6 col-lg-5">
                        <div class="card shadow-lg p-4">
                            <h2 class="text-center mb-4">Quên mật khẩu</h2>

                            <!-- Hiển thị thông báo lỗi -->
                            <c:if test="${not empty message}">
                                <div class="alert alert-danger text-center">${message}</div>
                            </c:if>

                            <!-- Hiển thị link reset mật khẩu -->
                            <c:if test="${not empty resetLink}">
                                <div class="alert alert-success text-center">
                                    <a href="${resetLink}" class="text-success">${resetLink}</a>
                                </div>
                            </c:if>

                            <form action="<c:url value='/sendresetlink' />" method="post">
                                <div class="mb-3">
                                    <label class="form-label">Nhập tên đăng nhập:</label>
                                    <input type="text" class="form-control" name="username" required>
                                </div>
                                <button type="submit" class="btn btn-primary w-100">Gửi yêu cầu</button>
                            </form>

                            <div class="text-center mt-3">
                                <a href="<c:url value='/login' />" class="text-decoration-none">Quay lại đăng nhập</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>
