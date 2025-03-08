<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Báo cáo doanh thu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <div class="container mt-4">
        <!-- Form chọn thời gian -->
        <form class="row g-3 mb-4">
            <div class="col-auto">
                <label>Từ ngày:</label>
                <input type="date" name="startDate" class="form-control" 
                       value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd"/>">
            </div>
            <div class="col-auto">
                <label>Đến ngày:</label>
                <input type="date" name="endDate" class="form-control"
                       value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>">
            </div>
            <div class="col-auto">
                <button type="submit" class="btn btn-primary mt-4">Xem báo cáo</button>
            </div>
        </form>

        <!-- Tổng quan -->
        <div class="row mb-4">
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Tổng doanh thu</h5>
                        <p class="card-text">
                            <fmt:formatNumber value="${totalRevenue}" type="currency"/>
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Tổng đơn hàng</h5>
                        <p class="card-text">${totalOrders}</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Tổng sản phẩm bán ra</h5>
                        <p class="card-text">${totalProducts}</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Biểu đồ doanh thu theo ngày -->
        <div class="row mb-4">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Doanh thu theo ngày</h5>
                        <canvas id="revenueChart"></canvas>
                    </div>
                </div>
            </div>
        </div>
    <script>
        // Biểu đồ doanh thu theo ngày
        const ctx = document.getElementById('revenueChart').getContext('2d');
        new Chart(ctx, {
            type: 'line',
            data: {
                labels: [
                    <c:forEach items="${dailyReports}" var="report">
                        '<fmt:formatDate value="${report.date}" pattern="dd/MM"/>',
                    </c:forEach>
                ],
                datasets: [{
                    label: 'Doanh thu',
                    data: [
                        <c:forEach items="${dailyReports}" var="report">
                            ${report.revenue},
                        </c:forEach>
                    ],
                    borderColor: 'rgb(75, 192, 192)',
                    tension: 0.1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    </script>
</body>
</html>
