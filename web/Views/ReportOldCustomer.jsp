<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thống kê khách hàng</title>
        <script src="https://cdn.plot.ly/plotly-latest.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body style="margin-top: 100px;">
        <jsp:include page="staffDashboard.jsp"></jsp:include>
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

            const data = [{
                x: customers,
                y: dates,
                type: "bar",
                orientation: "h",
                marker: {
                    color: "rgba(75, 192, 192, 0.6)",
                    line: {
                        color: "rgba(75, 192, 192, 1)",
                        width: 1
                    }
                }
            }];

            const layout = {
                title: "Thống kê khách hàng mới theo ngày",
                xaxis: {
                    title: "Số lượng khách hàng",
                    tickmode: "linear",
                    tick0: 0,
                    dtick: 1
                },
                yaxis: {
                    title: "Ngày",
                    automargin: true
                },
                margin: {
                    l: 100,
                    r: 20,
                    t: 40,
                    b: 40
                },
                height: Math.max(400, dates.length * 25), // Điều chỉnh chiều cao theo số lượng ngày
                bargap: 0.2
            };

            const config = {
                responsive: true,
                displayModeBar: false
            };

            Plotly.newPlot("myPlot", data, layout, config);
        </script>
    </body>
</html>
