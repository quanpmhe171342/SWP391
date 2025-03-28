<%-- 
    Document   : staffDashboard
    Created on : Mar 10, 2025, 5:29:20 PM
    Author     : NV200
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Navbar Thống Kê</title>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: Arial, sans-serif;
            }

            /* Thanh Navbar */
            .navbar {
                position: fixed;
                background-color: #fff;
                display: flex;
                justify-content: center;
                align-items: center;
                padding: 15px;
                position: fixed;
                width: 100%;
                top: 0;
                left: 0;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
                z-index: 1000;
            }

            /* Danh sách menu */
            .nav-links {
                list-style: none;
                display: flex;
                gap: 20px;
            }

            /* Các mục menu có khung vuông */
            .nav-links li {
                width: 120px;
                height: 50px;
                display: flex;
                justify-content: center;
                align-items: center;
                background-color: #444;
                border-radius: 10px;
                transition: 0.3s;
            }

            /* Văn bản bên trong */
            .nav-links a {
                color: white;
                text-decoration: none;
                font-size: 16px;
                text-align: center;
                width: 100%;
                height: 100%;
                display: flex;
                justify-content: center;
                align-items: center;
            }

            /* Hover hiệu ứng */
            .nav-links li:hover {
                background-color: #ff6600;
            }

            /* Responsive cho điện thoại */
            @media (max-width: 768px) {
                .navbar {
                    flex-direction: column;
                    align-items: center;
                }

                .nav-links {
                    flex-direction: column;
                    gap: 10px;
                }

                .nav-links li {
                    width: 100px;
                    height: 40px;
                }
            }
            .content {
                position: relative;
                top: 80px; /* Đẩy nội dung xuống dưới */
            }
        </style>
    </head>
    <body>

        <nav class="navbar">
            <ul class="nav-links">
                <li><a href="${pageContext.request.contextPath}/user/report"> Thống kê khách hàng mới</a></li>
                <li><a href="${pageContext.request.contextPath}/report/product">Doanh thu bán hàng</a></li>
                <li><a href="${pageContext.request.contextPath}/report/old/customer">Tỉ lệ khách hàng quay lại</a></li>
                <li><a href="${pageContext.request.contextPath}/SliderManager">Quản lí slider</a></li>
            </ul>
        </nav>

    </body>
</html>
