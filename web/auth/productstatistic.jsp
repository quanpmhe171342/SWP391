<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Map.Entry" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>Thống kê sản phẩm</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <!-- Include Sidebar -->
                <div class="col-md-3">
                    <jsp:include page="sidebar.jsp" />
                </div>

                <div class="col-md-6">
                    <div class="container mt-5">
                        <h2 class="text-center mb-4">Thống kê sản phẩm</h2>


                        <div class="row text-center">
                            <div class="col-md-3">
                                <div class="card shadow-sm">
                                    <div class="card-body">
                                        <h5 class="card-title text-primary">Tổng Số Sản Phẩm</h5>
                                        <p class="fs-3 fw-bold">${totalProducts}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="card shadow-sm">
                                    <div class="card-body">
                                        <h5 class="card-title text-primary">Số Sản Phẩm Còn Hàng</h5>
                                        <p class="fs-3 fw-bold">${ProductInStock}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="card shadow-sm">
                                    <div class="card-body">
                                        <h5 class="card-title text-danger">Số Sản Phẩm Sắp Hết Hàng</h5>
                                        <p class="fs-3 fw-bold">${lowStockProducts}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="card shadow-sm">
                                    <div class="card-body">
                                        <h5 class="card-title text-danger">Số Sản Phẩm Hết Hàng</h5>
                                        <p class="fs-3 fw-bold">${OutOfProduct}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="card shadow-sm">
                                    <div class="card-body">
                                        <h5 class="card-title text-muted">Tổng Số Hàng Tồn Kho </h5>
                                        <p class="fs-3 fw-bold">${allStock}</p>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <h3 class="text-center mt-5">Số lượng sản phẩm theo danh mục</h3>
                        <div class="table-responsive mt-3">
                            <table class="table table-bordered table-hover">
                                <thead class="table-dark">
                                    <tr>
                                        <th>Danh mục</th>
                                        <th>Số lượng</th>
                                    </tr>
                                </thead>
                                <%
        Map<String, Integer> categoryStats = (Map<String, Integer>) request.getAttribute("productsByCategory");
                                %>

                                <tbody>
                                    <%
                                        if (categoryStats != null && !categoryStats.isEmpty()) {
                                            for (Map.Entry<String, Integer> entry : categoryStats.entrySet()) {
                                    %>
                                    <tr>
                                        <td><%= entry.getKey() %></td>
                                        <td><%= entry.getValue() %></td>
                                    </tr>
                                    <%
                                            }
                                        } else {
                                    %>
                                    <tr>
                                        <td colspan="2" class="text-center text-muted">Không có dữ liệu</td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>

                        <div class="container d-flex justify-content-center mt-5">
                            <div class="card p-4 shadow-lg" style="max-width: 500px; width: 100%;">
                                <h4 class="text-center text-primary">📊 Bộ Lọc Doanh Thu</h4>

                                <form action="productstatistic" method="GET">
                                    <div class="mb-3">
                                        <label for="filterType" class="form-label fw-bold">Lọc theo:</label>
                                        <select name="filterType" id="filterType" class="form-select rounded-pill">
                                            <option value="day">Ngày</option>
                                            <option value="month">Tháng</option>
                                        </select>
                                    </div>

                                    <div class="mb-3">
                                        <input type="date" name="selectedDate" id="selectedDate" class="form-control rounded-pill">
                                        <input type="month" name="selectedMonth" id="selectedMonth" class="form-control rounded-pill" style="display: none;">
                                    </div>

                                    <div class="text-center">
                                        <button type="submit" class="btn btn-primary rounded-pill px-4">🔍 Lọc</button>
                                    </div>
                                </form>

                                <hr>

                                <div class="mt-3">
                                    <h5 class="text-primary">📦 Tổng số sản phẩm bán ra:</h5>
                                    <p class="fs-3 fw-bold text-center">${totalSoldProducts}</p>

                                    <h5 class="text-success">💰 Tổng doanh thu:</h5>
                                    <p class="fs-3 fw-bold text-center">${totalRevenue} VNĐ</p>
                                </div>
                            </div>
                        </div>

                        <div class="d-flex justify-content-center">
                            <div style="width: 250px; height: 250px; margin-top: 20px">
                                <canvas id="categoryPieChart"></canvas>
                            </div>
                        </div>

                    </div>
                    <<script>
                        document.getElementById("filterType").addEventListener("change", function () {
                            var filterType = this.value;
                            document.getElementById("selectedDate").style.display = (filterType === "day") ? "block" : "none";
                            document.getElementById("selectedMonth").style.display = (filterType === "month") ? "block" : "none";
                        });
                    </script>
                    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>



                    <script>
                        document.addEventListener("DOMContentLoaded", function () {
                        // Dữ liệu danh mục từ request
                        var categoryStats = {
                        <% 
                                    if (categoryStats != null && !categoryStats.isEmpty()) { 
                                        for (Entry<String, Integer> entry : categoryStats.entrySet()) {
                        %>
                        "<%= entry.getKey() %>": <%= entry.getValue() %>,
                        <% 
                                        }
                                    } 
                        %>
                        };
                                // Tách dữ liệu thành labels và values
                                var labels = Object.keys(categoryStats);
                                var dataValues = Object.values(categoryStats);
                                // Màu sắc cho biểu đồ
                                var backgroundColors = [
                                        'rgba(255, 99, 132, 0.6)',
                                        'rgba(54, 162, 235, 0.6)',
                                        'rgba(255, 206, 86, 0.6)',
                                        'rgba(75, 192, 192, 0.6)',
                                        'rgba(153, 102, 255, 0.6)',
                                        'rgba(255, 159, 64, 0.6)'
                                ];
                                // Tạo biểu đồ tròn nhỏ
                                var ctx = document.getElementById('categoryPieChart').getContext('2d');
                                new Chart(ctx, {
                                type: 'doughnut', // Biểu đồ tròn dạng nhỏ
                                        data: {
                                        labels: labels,
                                                datasets: [{
                                                data: dataValues,
                                                        backgroundColor: backgroundColors,
                                                        borderWidth: 1
                                                }]
                                        },
                                        options: {
                                        responsive: true,
                                                maintainAspectRatio: false,
                                                plugins: {
                                                legend: {
                                                position: 'bottom', // Đưa chú thích xuống dưới
                                                }
                                                }
                                        }
                                });
                        });
                    </script>

                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

                    </body>
                    </html>
