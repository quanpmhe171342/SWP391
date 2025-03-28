<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Thống kê trạng thái đơn hàng</title>
    <script src="https://cdn.plot.ly/plotly-latest.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <!-- Form lọc -->
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
                <div id="myPlot" style="width:100%;max-width:900px"></div>
            </div>
        </div>
    </div>

    <script>
        // Chuẩn bị dữ liệu cho từng trạng thái
        const dates = [
            <c:forEach items="${states}" var="state" varStatus="loop">
                '<fmt:formatDate value="${state.period}" pattern="dd/MM/yyyy"/>'${!loop.last ? ',' : ''}
            </c:forEach>
        ];

        const orderCounts = [
            <c:forEach items="${states}" var="state" varStatus="loop">
                ${state.orderCount}${!loop.last ? ',' : ''}
            </c:forEach>
        ];

        const totalAmounts = [
            <c:forEach items="${states}" var="state" varStatus="loop">
                ${state.totalAmount}${!loop.last ? ',' : ''}
            </c:forEach>
        ];

        // Định nghĩa dữ liệu cho biểu đồ
        const orderData = {
            x: dates,
            y: orderCounts,
            name: 'Số đơn hàng',
            mode: 'lines+markers',
            line: {
                color: 'rgb(75, 192, 192)',
                width: 2
            },
            marker: {
                size: 8
            }
        };

        const amountData = {
            x: dates,
            y: totalAmounts,
            name: 'Doanh thu',
            mode: 'lines+markers',
            line: {
                color: 'rgb(255, 99, 132)',
                width: 2
            },
            marker: {
                size: 8
            },
            yaxis: 'y2'
        };

        const layout = {
            title: 'Thống kê đơn hàng theo trạng thái',
            xaxis: {
                title: '',
                tickangle: -45
            },
            yaxis: {
                title: 'Số đơn hàng',
                rangemode: 'tozero',
                titlefont: {color: 'rgb(75, 192, 192)'},
                tickfont: {color: 'rgb(75, 192, 192)'}
            },
            yaxis2: {
                title: 'Doanh thu (VNĐ)',
                titlefont: {color: 'rgb(255, 99, 132)'},
                tickfont: {color: 'rgb(255, 99, 132)'},
                overlaying: 'y',
                side: 'right',
                rangemode: 'tozero'
            },
            legend: {
                orientation: 'h',
                yanchor: 'bottom',
                y: -0.3,
                xanchor: 'center',
                x: 0.5
            },
            margin: {
                l: 50,
                r: 50,
                b: 100,
                t: 30,
                pad: 4
            },
            hovermode: 'x unified'
        };

        Plotly.newPlot('myPlot', [orderData, amountData], layout, {
            responsive: true,
            displayModeBar: false
        });

        let showingOrders = true;
        function toggleData() {
            const data = showingOrders ? [amountData] : [orderData];
            const newLayout = {
                ...layout,
                yaxis: {
                    ...layout.yaxis,
                    title: showingOrders ? 'Doanh thu (VNĐ)' : 'Số đơn hàng'
                }
            };
            Plotly.react('myPlot', data, newLayout);
            showingOrders = !showingOrders;
        }
    </script>

    <!-- Thêm nút chuyển đổi -->
    <div class="container mt-3">
        <button onclick="toggleData()" class="btn btn-primary">
            Chuyển đổi hiển thị
        </button>
    </div>

    <!-- Bảng dữ liệu chi tiết -->
    <div class="container mt-4">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Chi tiết số liệu</h5>
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Ngày</th>
                                <th>Trạng thái</th>
                                <th>Số đơn hàng</th>
                                <th>Doanh thu</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${states}" var="state">
                                <tr>
                                    <td><fmt:formatDate value="${state.period}" pattern="dd/MM/yyyy"/></td>
                                    <td>${state.status}</td>
                                    <td>${state.orderCount}</td>
                                    <td><fmt:formatNumber value="${state.totalAmount}" type="currency"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
