<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="Model.User" %>

<%
    User user = (User) session.getAttribute("user");

    // Nếu user chưa đăng nhập, chuyển hướng về trang login
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/adminlogin");
        return;
    }

    int roleID = user.getRoleId();
%>

<!-- Sidebar -->
<div class="d-flex flex-column flex-shrink-0 p-3 bg-dark text-white" style="width: 250px; height: 100vh;">
    <a href="<%= request.getContextPath() %>/" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none">
        <span class="fs-4">Trang Quản Lý</span>
    </a>
    <hr>
    <ul class="nav nav-pills flex-column mb-auto">
        <% if (roleID == 1) { %>
            <li class="nav-item">
                <a href="<%= request.getContextPath() %>/viewstaff" class="nav-link text-white">👨‍💼 Quản lý Nhân Viên</a>
            </li>
            <li>
                <a href="<%= request.getContextPath() %>/viewuser" class="nav-link text-white">👥 Quản lý Khách Hàng</a>
            </li>
            <li>
                <a href="<%= request.getContextPath() %>/listproduct" class="nav-link text-white">📦 Quản lý Sản Phẩm</a>
            </li>
            <li>
                <a href="<%= request.getContextPath() %>/productstatistic" class="nav-link text-white">📊 Thống Kê Sản Phẩm</a>
            </li>
        <% } else if (roleID == 2) { %>
            <li>
                <a href="<%= request.getContextPath() %>/viewuser" class="nav-link text-white">👥 Quản lý Khách Hàng</a>
            </li>
            <li>
                <a href="<%= request.getContextPath() %>/listproduct" class="nav-link text-white">📦 Quản lý Sản Phẩm</a>
            </li>
            <li>
                <a href="<%= request.getContextPath() %>/productstatistic" class="nav-link text-white">📊 Thống Kê Sản Phẩm</a>
            </li>
            <li>
                <a href="<%= request.getContextPath() %>/viewprofile" class="nav-link text-white">🧑 Xem Thông Tin Cá Nhân</a>
            </li>
            <li>
                <a href="<%= request.getContextPath() %>/changepassword" class="nav-link text-white">🔑 Đổi Mật Khẩu</a>
            </li>
        <% } %>
    </ul>
    <hr>
    <div class="dropdown">
        <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle" id="dropdownUser1" data-bs-toggle="dropdown" aria-expanded="false">
            <strong>Tài khoản</strong>
        </a>
        <ul class="dropdown-menu dropdown-menu-dark text-small shadow">
            <li><a class="dropdown-item" href="<%= request.getContextPath() %>/adminlogin">Đăng xuất</a></li>
        </ul>
    </div>
</div>
