<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Thống kê sản phẩm</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="AdminHeader.jsp" />
    <jsp:include page="AdminSidebar.jsp" />
    <div class="container" style="margin-top: -39%">
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
                <div class="row">
                    <div class="col-md-6">
                        <canvas id="soldChart" style="width:100%;max-width:600px"></canvas>
                    </div>
                    <div class="col-md-6">
                        <canvas id="revenueChart" style="width:100%;max-width:600px"></canvas>
                    </div>
                </div>
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
                                <th>Sản phẩm</th>
                                <th>Số lượng bán</th>
                                <th>Doanh thu</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${products}" var="product">
                                <tr>
                                    <td>${product.productName}</td>
                                    <td>${product.totalSold}</td>
                                    <td><fmt:formatNumber value="${product.revenue}" type="currency"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script>
        const productNames = [
            <c:forEach items="${products}" var="product" varStatus="loop">
                "${product.productName}"${!loop.last ? ',' : ''}
            </c:forEach>
        ];
        
        const totalSold = [
            <c:forEach items="${products}" var="product" varStatus="loop">
                ${product.totalSold}${!loop.last ? ',' : ''}
            </c:forEach>
        ];
        
        const revenue = [
            <c:forEach items="${products}" var="product" varStatus="loop">
                ${product.revenue}${!loop.last ? ',' : ''}
            </c:forEach>
        ];

        const barColors = [
            "#b91d47", "#00aba9", "#2b5797", "#e8c3b9", "#1e7145",
            "#c91d47", "#10aba9", "#3b5797", "#f8c3b9", "#2e7145"
        ];
        new Chart("soldChart", {
            type: "pie",
            data: {
                labels: productNames,
                datasets: [{
                    backgroundColor: barColors,
                    data: totalSold
                }]
            },
            options: {
                title: {
                    display: true,
                    text: "Thống kê số lượng bán"
                }
            }
        });

        new Chart("revenueChart", {
            type: "pie",
            data: {
                labels: productNames,
                datasets: [{
                    backgroundColor: barColors,
                    data: revenue
                }]
            },
            options: {
                title: {
                    display: true,
                    text: "Thống kê doanh thu"
                }
            }
        });
    </script>
</body>
</html>
