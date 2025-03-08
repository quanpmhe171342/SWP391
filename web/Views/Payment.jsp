<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <title>Payment</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                ${error}
            </div>
        </c:if>
        <div class="container mt-5">
            <h1 class="mb-4">Payment Details</h1>
            <div class="table-responsive">
                <table class="table table-bordered table-striped">
                    <thead class="table-dark">
                        <tr>
                            <th>Product</th>
                            <th class="text-center">Quantity</th>
                            <th class="text-end">Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${orderDetails}" var="detail">
                            <tr>
                                <td>
                                    <div><strong>${detail.productName}</strong></div>
                                    <small class="text-muted">${detail.briefInformation}</small>
                                </td>
                                <td class="text-center">${detail.quantity}</td>
                                <td class="text-end">
                                    <div>
                                        <fmt:formatNumber value="${detail.price}" 
                                                          type="currency" pattern="#,###₫"/>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="d-flex justify-content-end mt-4">
                <h4>Total Price: 
                    <span class="text-success">
                        <fmt:formatNumber value="${totalAmount}" 
                                          type="currency" pattern="#,###₫"/>
                    </span>
                </h4>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
