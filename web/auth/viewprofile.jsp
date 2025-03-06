<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Model.User" %>
<%@ page session="true" %>

<%! 
    // Hàm kiểm tra giá trị null hoặc rỗng
    public String checkNull(String value) {
        return (value == null || value.trim().isEmpty()) ? "Chưa có thông tin" : value;
    }
%>

<%
    // Lấy user từ session
    User user = (User) session.getAttribute("user");

    // Kiểm tra nếu chưa đăng nhập thì chuyển hướng về trang login
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hồ Sơ Cá Nhân</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .profile-card {
            border-radius: 25px;
            padding: 30px;
            max-width: 800px;
            background: #fff;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }
        .profile-info {
            font-size: 18px;
        }
        .profile-info p {
            margin-bottom: 15px;
        }
        .profile-image img {
            width: 250px;
            height: 250px;
            object-fit: cover;
            border-radius: 15px;
            border: 3px solid #ddd;
        }
    </style>
</head>
<body style="background-color: #eee;">
    <div class="container vh-100 d-flex justify-content-center align-items-center">
        <div class="profile-card row w-100">
            <!-- Cột trái: Thông tin -->
            <div class="col-md-7 d-flex flex-column justify-content-center">
                <h2 class="mb-4">Hồ Sơ Cá Nhân</h2>
                <div class="profile-info">
                    <p><strong>Tên đăng nhập:</strong> <%= checkNull(user.getUsername()) %></p>
                    <p><strong>Họ:</strong> <%= checkNull(user.getFirstName()) %></p>
                    <p><strong>Tên:</strong> <%= checkNull(user.getLastName()) %></p>
                    <p><strong>Email:</strong> <%= checkNull(user.getEmail()) %></p>
                    <p><strong>Số điện thoại:</strong> <%= checkNull(user.getPhone()) %></p>
                </div>
                <a href="editprofile.jsp" class="btn btn-primary mt-3">Chỉnh sửa hồ sơ</a>
            </div>
            <!-- Cột phải: Ảnh đại diện -->
            <div class="col-md-5 d-flex justify-content-center align-items-center">
                <div class="profile-image">
                    <img src="<%= request.getContextPath() %>/img/photo/User.jpg" alt="Ảnh đại diện">

                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
