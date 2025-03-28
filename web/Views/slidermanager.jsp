<%-- 
    Document   : slidermanager
    Created on : Mar 19, 2025, 8:14:27 AM
    Author     : NV200
--%>

<!DOCTYPE html>
<html lang="en">
    <head>

        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <title>Slider Management</title>
        <link rel="stylesheet" href="css/slider.css" type="text/css">

        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <style>
            .table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 10px;
            }
            .container {
                max-width: 90%;
                margin: auto;
            }
            .table th, .table td {
                border: 1px solid #ddd;
                text-align: center;
                vertical-align: middle;
                padding: 10px;
                word-wrap: break-word;
                white-space: pre-wrap;
            }
            .table td:nth-child(2), .table th:nth-child(2),
            .table td:nth-child(4), .table th:nth-child(4),
            .table td:nth-child(5), .table th:nth-child(5) {
                text-align: center;
                vertical-align: middle;
            }
            .table img {
                height: 350px;
                width: 900px;

                border-radius: 5px;
                display: block;
                margin: auto;

            }
            .table thead {
                background-color: #f8f9fa;
            }

            .btn {
                padding: 6px 12px;
                font-size: 14px;
                border-radius: 5px;
                transition: all 0.3s ease;
                margin-bottom: 5px;
            }
            .btn-primary {
                background-color: #007bff;
                border: none;
            }
            .btn-primary:hover {
                background-color: #0056b3;
            }
            .btn-danger {
                background-color: #dc3545;
                border: none;
            }
            .btn-danger:hover {
                background-color: #c82333;
            }
            .btn-success {
                background-color: #28a745;
                border: none;
            }
            .btn-success:hover {
                background-color: #218838;
            }
            .btn-delete {
                background-color: #ff0000;
                border: none;
            }
            .btn-delete:hover {
                background-color: #cc0000;
            }
            .table tbody tr:hover {
                background-color: #f1f1f1;
            }
            h2 {
                text-align: center;
                font-weight: bold;
                margin-bottom: 20px;
            }
            td {
                text-align: center;
                vertical-align: middle;
            }

            .status-text {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100%;
                width: 100%;
                text-align: center;
                white-space: nowrap; /* Ngăn chữ bị xuống dòng */
            }


        </style>
    </head>
    <body style="margin-top: 100px;">
        <jsp:include page="staffDashboard.jsp"></jsp:include>
        <div class="container mt-4">
            <h2 class="mb-4">Danh Sách Slider</h2>
            <a href="/LoadProductVariant" class="btn btn-success mb-3">Thêm Slider</a>
            <table class="table table-bordered">
                <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Tiêu đề</th>
                        <th>Ảnh</th>
                        <th>Mô tả</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="slider" items="${sliders}">
                        <tr>
                            <td>${slider.sliderID}</td>
                            <td>${slider.title}</td>
                            <td><img src="${slider.image}" ></td>
                            <td>${slider.description}</td>
                            <td>
                                <div class="status-text">
                                    <c:choose>
                                        <c:when test="${slider.status == 1}">Hiển thị trong trang chủ</c:when>
                                        <c:otherwise>Ẩn khỏi trang chủ</c:otherwise>
                                    </c:choose>
                                </div>
                            </td>
                            <td>
                                <a href="LoadSliderById?id=${slider.sliderID}" class="btn btn-primary btn-sm">Sửa</a>
                                <a href="HidenSlider?id=${slider.sliderID}" class="btn btn-danger btn-sm btn-hide">Ẩn</a>
                                <a href="DeleteSlider?id=${slider.sliderID}" class="btn btn-delete btn-sm btn-delete-slider">Xóa</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                // Xử lý nút "Ẩn"
                document.querySelectorAll(".btn-hide").forEach(function (button) {
                    button.addEventListener("click", function (event) {
                        event.preventDefault();
                        let url = new URL(this.href);
                        let sliderId = url.searchParams.get("id");

                        if (sliderId && confirm("Bạn có chắc muốn ẩn slider này không?")) {
                            window.location.href = this.href; // Chỉ chuyển hướng sau khi xác nhận
                        }
                    });
                });

                // Xử lý nút "Xóa"
                document.querySelectorAll(".btn-delete-slider").forEach(function (button) {
                    button.addEventListener("click", function (event) {
                        event.preventDefault();
                        let url = new URL(this.href);
                        let sliderId = url.searchParams.get("id");

                        if (sliderId && confirm("Bạn có chắc muốn xóa vĩnh viễn slider này không?")) {
                            window.location.href = this.href; // Chỉ chuyển hướng sau khi xác nhận
                        }
                    });
                });
            });
        </script>

    </body>
</html>
