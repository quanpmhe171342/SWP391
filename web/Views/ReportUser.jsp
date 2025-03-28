<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thống kê khách hàng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-4">
            <!-- Form lọc thời gian -->
            <div class="card mb-4">
                <div class="card-body">
                    <form class="row g-3" method="GET">
                        <div class="col-md-4">
                            <label class="form-label">Từ ngày</label>
                            <input type="date" name="startDate" class="form-control" 
                                   value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd"/>">
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Đến ngày</label>
                            <input type="date" name="endDate" class="form-control" 
                                   value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>">
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">&nbsp;</label>
                            <button type="submit" class="btn btn-primary w-100">Xem thống kê</button>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Biểu đồ -->
            <div class="card">
                <div class="card-body">
                    <canvas id="myChart" style="width:100%;max-width:800px"></canvas>
                </div>
            </div>

            <!-- Bảng dữ liệu -->
            <div class="card mt-4">
                <div class="card-body">
                    <h5 class="card-title">Chi tiết số liệu</h5>
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Ngày</th>
                                    <th>Số khách hàng mới</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${dates}" var="date" varStatus="loop">
                                    <tr>
                                        <td>${date}</td>
                                        <td>${customers[loop.index]}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <script>
            const dates = [
                <c:forEach items="${dates}" var="date" varStatus="loop">
                    '${date}'${!loop.last ? ',' : ''}
                </c:forEach>
            ];
            
            const customers = [
                <c:forEach items="${customers}" var="count" varStatus="loop">
                    ${count}${!loop.last ? ',' : ''}
                </c:forEach>
            ];

            new Chart("myChart", {
                type: "bar",
                data: {
                    labels: dates,
                    datasets: [{
                        label: 'Số lượng khách hàng mới',
                        backgroundColor: "rgba(75, 192, 192, 0.2)",
                        borderColor: "rgba(75, 192, 192, 1)",
                        borderWidth: 1,
                        data: customers
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: {
                            beginAtZero: true,
                            ticks: {
                                stepSize: 1
                            }
                        }
                    },
                    plugins: {
                        legend: {
                            display: true,
                            position: 'top'
                        },
                        title: {
                            display: true,
                            text: 'Thống kê khách hàng mới theo ngày'
                        }
                    }
                }
            });
        </script>
    </body>
</html>
