<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="Model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Home Test Page</title>
</head>
<body>
    <h1>Welcome to the Home Test Page!</h1>

    <% 
        // Kiểm tra xem user đã đăng nhập chưa
        Object userObj = session.getAttribute("user");
        if (userObj == null) { 
    %>
        <a href="../auth/register.jsp">Register</a>
        <a href="../auth/login.jsp">Login</a>
    <% } else { 
        User user = (User) userObj;
    %>
        <p>Welcome, <%= user.getUsername() %>!</p>
        <!-- Nút thay đổi mật khẩu -->
        <a href="../changepassword">Change Password</a>
    <% } %>
</body>
</html>
