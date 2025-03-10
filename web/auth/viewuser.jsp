<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, Model.User" %>
<%
    List<User> userList = (List<User>) request.getAttribute("userList");
    if (userList == null) {
        userList = new java.util.ArrayList<>();
    }

    // Lấy giá trị role đã chọn từ servlet
    int selectedRole = (request.getAttribute("selectedRole") != null) ? (int) request.getAttribute("selectedRole") : 0;
%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản lý Người Dùng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <div class="container mt-5">
            <h2 class="text-center mb-4">Quản lý Người Dùng</h2>

            <!-- Dropdown Lọc Người Dùng -->
            <div class="d-flex justify-content-between mb-3">
                <label for="roleFilter" class="fw-bold">Lọc theo vai trò:</label>
                <select id="roleFilter" class="form-select w-25" onchange="filterUsers()">
                    <option value="all" <%= (selectedRole == 0) ? "selected" : "" %>>Tất cả</option>
                    <option value="2" <%= (selectedRole == 2) ? "selected" : "" %>>Nhân viên</option>
                    <option value="3" <%= (selectedRole == 3) ? "selected" : "" %>>Khách hàng</option>
                </select>
            </div>
            <%
                String message = (String) session.getAttribute("message");
                if (message != null) {
            %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <%= message %>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <%
                    session.removeAttribute("message"); // Xóa thông báo sau khi hiển thị
                }
            %>
            <!-- Bảng hiển thị danh sách user -->
            <table class="table table-bordered table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>Số</th>
                        <th>Họ</th>
                        <th>Tên</th>
                        <th>Email</th>
                        <th>Số điện thoại</th>
                        <th>Tên đăng nhập</th>
                        <th>Vai trò</th>
                        <th>Chỉnh sửa</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (userList.isEmpty()) { %>
                    <tr>
                        <td colspan="9" class="text-center">Không có dữ liệu</td>
                    </tr>
                    <% } else { %>
                    <% for (User user : userList) { %>
                    <tr class="user-row" data-role="<%= user.getRoleId() %>">
                        <td><%= user.getUserId() %></td>
                        <td><%= user.getFirstName() %></td>
                        <td><%= user.getLastName() %></td>
                        <td><%= user.getEmail() %></td>
                        <td><%= user.getPhone() %></td>
                        <td><%= user.getUsername() %></td>
                        <td><%= user.getRoleId() == 2 ? "Nhân viên" : "Khách hàng" %></td>
                        <td>
                            <a href="edituser?userId=<%= user.getUserId() %>" class="btn btn-warning btn-sm">Sửa</a>
                            <a href="deleteuser?userId=<%= user.getUserId() %>" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc muốn xóa người dùng này không?');">Xóa</a>

                        </td>
                    </tr>
                    <% } %>
                    <% } %>
                </tbody>

            </table>
        </div>

        <script>
            function filterUsers() {
                let role = document.getElementById("roleFilter").value;
                let url = window.location.pathname + (role === "all" ? "" : "?role=" + role);
                window.location.href = url;
            }
        </script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
