
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <script>
        function updateQuantity(productId, newQuantity, cartId) {
            if (newQuantity <= 0) {
                removeFromCart(cartId);
                return;
            }
            fetch("/SWP391/product/cart?action=update&pid=" + productId + "&quantity=" + newQuantity + "&cid="+cartId, {
                method: 'POST'
            })
            .then(response => {
                if (response.ok) {
                    window.location.reload();
                } else {
                    response.text().then(text => alert(text));
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Có lỗi xảy ra khi cập nhật giỏ hàng');
            });
        }

        function removeFromCart(productId) {
            if (confirm('Bạn có chắc muốn xóa sản phẩm này khỏi giỏ hàng?')) {
                fetch("/SWP391/product/cart?action=delete&pid="+productId, {
                    method: 'POST'
                })
                .then(response => {
                    if (response.ok) {
                        window.location.reload();
                    } else {
                        response.text().then(text => alert(text));
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Có lỗi xảy ra khi xóa sản phẩm khỏi giỏ hàng');
                });
            }
        }
    </script>
    </head>
    <body>
        <section class="h-100 h-custom" style="background-color: #eee;">
            <div class="container py-5 h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col">
                        <div class="card">
                            <div class="card-body p-4">
                                <c:if test="${hasQuantityError}">
                                    <div class="alert alert-danger" role="alert">
                                        <h5 class="alert-heading">Không thể thanh toán!</h5>
                                        <ul class="mb-0">
                                            <c:forEach items="${errorMessages}" var="error">
                                                <li>${error}</li>
                                                </c:forEach>
                                        </ul>
                                    </div>
                                </c:if>
                                <div class="row">
                                    <div class="col-lg-7">
                                        <h5 class="mb-3"><a href="#!" class="text-body"><i
                                                    class="fas fa-long-arrow-alt-left me-2"></i>Continue shopping</a></h5>
                                        <hr>
                                        <div class="d-flex justify-content-between align-items-center mb-4">
                                            <div>
                                                <p class="mb-1">Shopping cart</p>
                                                <p class="mb-0">You have 4 items in your cart</p>
                                            </div>
                                            <div>
                                                <p class="mb-0"><span class="text-muted">Sort by:</span> <a href="#!"
                                                                                                            class="text-body">price <i class="fas fa-angle-down mt-1"></i></a></p>
                                            </div>
                                        </div>
                                        <c:forEach var="cart" items="${cartList}">
                                            <div class="card mb-3 border-0 shadow-sm">
                                                <div class="card-body">
                                                    <div class="row align-items-center">
                                                        <!-- Product Image -->
                                                        <div class="col-md-2 col-3">
                                                            <img src="${cart.imageUrl}" 
                                                                 class="img-fluid rounded" 
                                                                 alt="${cart.productName}" 
                                                                 style="max-width: 80px;">
                                                        </div>
                                                        <!-- Product Details -->
                                                        <div class="col-md-4 col-9">
                                                            <input type="hidden" name="productId" value="${cart.cartId}">
                                                            <h5 class="mb-2">${cart.productName}</h5>
                                                            <div class="row g-2">
                                                                <!-- Size Dropdown -->
                                                                <div class="col-6">
                                                                    <select class="form-select form-select-sm" 
                                                                            >               
                                                                        <c:forEach var="size" items="${cart.sizes}">
                                                                            <option value="${size.size_id}" 
                                                                                    >
                                                                                ${size.size_name}
                                                                            </option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </div>

                                                                <!-- Color Dropdown -->
                                                                <div class="col-6">
                                                                    <select class="form-select form-select-sm" 
                                                                            >
                                                                        <c:forEach var="color" items="${cart.colors}">
                                                                            <option value="${color.color_id}" 
                                                                                    >
                                                                                ${color.color_Name}
                                                                            </option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!-- Quantity -->
                                                        <div class="col-md-2 col-4 mt-3 mt-md-0">
                                                            <div class="input-group input-group-sm">
                                                                <button class="btn btn-outline-secondary" 
                                                                        type="button" 
                                                                        onclick="updateQuantity(${cart.productId}, parseInt(this.nextElementSibling.value) - 1, ${cart.cartId})">
                                                                    <i class="fas fa-minus"></i>
                                                                </button>
                                                                <input type="number" 
                                                                       class="form-control text-center" 
                                                                       value="${cart.quantity}" 
                                                                       min="0"
                                                                       onchange="updateQuantity(${cart.productId}, this.value, ${cart.cartId})">
                                                                <button class="btn btn-outline-secondary" 
                                                                        type="button" 
                                                                        onclick="updateQuantity(${cart.productId}, parseInt(this.previousElementSibling.value) + 1, ${cart.cartId})">
                                                                    <i class="fas fa-plus"></i>
                                                                </button>
                                                            </div>
                                                        </div>

                                                        <!-- Price -->
                                                        <div class="col-md-2 col-4 text-end mt-3 mt-md-0">
                                                            <h6 class="mb-0 fw-bold">
                                                                <fmt:formatNumber value="${cart.originalPrice}" type="currency" pattern="#,###₫" />
                                                            </h6>
                                                        </div>

                                                        <!-- Remove Button -->
                                                        <div class="col-md-2 col-4 text-end mt-3 mt-md-0">
                                                            <button class="btn btn-link text-danger" 
                                                                    onclick="removeFromCart(${cart.cartId})" 
                                                                    title="Remove item">
                                                                <i class="fas fa-trash-alt"></i>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                        <hr class="my-4">
                                        <form action="/SWP391/ProductList/Cart" method="post">
                                            <div class="mb-3">
                                                <label for="amount" class="form-label">Số tiền (VND)</label>
                                                <input type="hidden" id="amount" name="amount" value="<fmt:formatNumber value='${requestScope.sumPrice}' type='currency' pattern='#,###₫' />">
                                            </div>
                                            <div><fmt:formatNumber value='${requestScope.sumPrice}' type='currency' pattern='#,###₫' /></div>                         
                                            <div class="d-grid">
                                                <button type="submit" class="btn btn-primary" 
                                                        <c:if test="${hasQuantityError}">disabled</c:if>>
                                                            Thanh toán
                                                        </button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            
    </body>
</html>
