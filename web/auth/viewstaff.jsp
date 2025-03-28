<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, Model.User" %>
<%
    List<User> userList = (List<User>) request.getAttribute("userList");
    if (userList == null) {
        userList = new java.util.ArrayList<>();
    }
%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Danh sách nhân viên</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        
            <div class="container-fluid">
                <div class="row">
                    <!-- Include Sidebar -->
                    <div class="col-md-3">
                        <jsp:include page="sidebar.jsp" />
                    </div>
                    
                     <div class="col-md-9">
                    <h2 class="text-center mb-4">Danh sách nhân viên</h2>

                    <div class="mb-3 text-end">
                        <a href="<%= request.getContextPath() %>/addstaff" class="btn btn-success">Thêm nhân viên</a>
                    </div>

                    <table class="table table-bordered table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Họ</th>
                                <th>Tên</th>
                                <th>Email</th>
                                <th>SĐT</th>
                                <th>Tên đăng nhập</th>
                                <th>Ngày tạo</th>
                                <th>Trạng thái</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (userList.isEmpty()) { %>
                            <tr>
                                <td colspan="9" class="text-center">Không có nhân viên</td>
                            </tr>
                            <% } else { %>
                            <% for (User user : userList) { %>
                            <tr>
                                <td><%= user.getUserId() %></td>
                                <td><%= user.getFirstName() == null ? "" : user.getFirstName() %></td>
                                <td><%= user.getLastName() == null ? "" : user.getLastName() %></td>
                                <td><%= user.getEmail() == null ? "" : user.getEmail() %></td>
                                <td><%= user.getPhone() == null ? "" : user.getPhone() %></td>
                                <td><%= user.getUsername() == null ? "" : user.getUsername() %></td>
                                <td><%= (user.getDob() != null) ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(user.getDob()) : "N/A" %></td>
                                <td>
                                    <form action="updateStatus" method="post">
                                        <input type="hidden" name="userId" value="<%= user.getUserId() %>">
                                        <select name="isActive" class="form-select" onchange="this.form.submit()">
                                            <option value="1" <%= user.isIsActive() ? "selected" : "" %>>Kích hoạt</option>
                                            <option value="0" <%= !user.isIsActive() ? "selected" : "" %>>Khóa</option>
                                        </select>
                                    </form>
                                </td>
                                <td>
                                    <a href="edituser?username=<%= user.getUsername() %>" class="btn btn-warning btn-sm">Sửa</a>
                                    <a href="deleteuser?username=<%= user.getUsername() %>" class="btn btn-danger btn-sm" onclick="return confirm('Xóa nhân viên này?');">Xóa</a>
                                </td>
                            </tr>
                            <% } %>
                            <% } %>
                        </tbody>
                    </table>
                </div>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                </body>
                </html>
