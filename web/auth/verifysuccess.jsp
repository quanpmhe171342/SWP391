<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Kích hoạt thành công</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="d-flex align-items-center justify-content-center vh-100 bg-light">
    <div class="text-center p-4 bg-white shadow rounded">
        <h2 class="text-success">🎉 Kích hoạt tài khoản thành công!</h2>
        <p class="mt-3">Hãy chuyển đến trang đăng nhập để tiếp tục nhé.</p>
        <a href="<%= request.getContextPath() %>/login" class="btn btn-primary mt-3">Đến trang Đăng nhập</a>
    </div>
</body>
</html>
