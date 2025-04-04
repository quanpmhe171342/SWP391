<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thống kê sản phẩm</title>
        <script src="https://cdn.plot.ly/plotly-latest.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
<<<<<<< HEAD
    <body>
=======
    <body style="margin-top: 100px;">
        <jsp:include page="staffDashboard.jsp"></jsp:include>
>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05
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
                    <div id="myPlot" style="width:100%;max-width:900px"></div>
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
                                    <th>Tên sản phẩm</th>
<<<<<<< HEAD
                                    <th>Số lượng bán</th>
=======
                                    <th>Số lượng sản phẩm bán ra</th>
>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05
                                    <th>Doanh thu</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${reports}" var="report">
                                    <tr>
                                        <td><fmt:formatDate value="${report.period}" pattern="dd/MM/yyyy"/></td>
                                        <td>${report.productName}</td>
                                        <td>${report.totalSold}</td>
<<<<<<< HEAD
                                        <td><fmt:formatNumber value="${report.revenue}" type="currency"/></td>
=======
                                        <td><fmt:formatNumber value="${report.revenue}"/> VND</td>
>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05
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
                <c:forEach items="${reports}" var="report" varStatus="loop">
                    '<fmt:formatDate value="${report.period}" pattern="dd/MM/yyyy"/>'${!loop.last ? ',' : ''}
                </c:forEach>
            ];
            
            const totalSold = [
                <c:forEach items="${reports}" var="report" varStatus="loop">
                    ${report.totalSold}${!loop.last ? ',' : ''}
                </c:forEach>
            ];

            const revenue = [
                <c:forEach items="${reports}" var="report" varStatus="loop">
                    ${report.revenue}${!loop.last ? ',' : ''}
                </c:forEach>
            ];

            const data = [{
                x: dates,
                y: totalSold,
<<<<<<< HEAD
                name: 'Số lượng bán',
=======
                name: 'Số lượng sản phẩm bán ra',
>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05
                type: "bar",
                marker: {
                    color: "rgba(75, 192, 192, 0.6)",
                    line: {
                        color: "rgba(75, 192, 192, 1)",
                        width: 1
                    }
                }
            },
            {
                x: dates,
                y: revenue,
                name: 'Doanh thu',
                type: 'scatter',
                yaxis: 'y2',
                line: {
                    color: 'rgb(255, 99, 132)'
                }
            }];

            const layout = {
                title: "Thống kê bán hàng theo ngày",
                xaxis: {
                    title: "Ngày",
                    tickangle: -45
                },
                yaxis: {
<<<<<<< HEAD
                    title: "Số lượng bán",
=======
                    title: "Số lượng sản phẩm bán ra",
>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05
                    titlefont: {color: 'rgb(75, 192, 192)'},
                    tickfont: {color: 'rgb(75, 192, 192)'}
                },
                yaxis2: {
                    title: 'Doanh thu',
                    titlefont: {color: 'rgb(255, 99, 132)'},
                    tickfont: {color: 'rgb(255, 99, 132)'},
                    overlaying: 'y',
                    side: 'right'
                },
                margin: {
                    l: 50,
                    r: 50,
                    t: 40,
                    b: 100
                },
                height: Math.max(400, dates.length * 25),
                bargap: 0.2,
                showlegend: true
            };

            const config = {
                responsive: true,
                displayModeBar: false
            };

            Plotly.newPlot("myPlot", data, layout, config);
        </script>
    </body>
</html>
