<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="Model.ProductVariant" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Cập nhật sản phẩm</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <!-- Include Sidebar -->
                <div class="col-md-3">
                    <jsp:include page="sidebar.jsp" />
                </div>

                <div class="col-md-9">
                    <div class="container mt-4">
                        <h2>Cập nhật sản phẩm</h2>

                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>

                        <c:if test="${not empty success}">
                            <div class="alert alert-success">${success}</div>
                        </c:if>


                        <form action="${pageContext.request.contextPath}/updateproduct" method="post" id="productForm" onsubmit="return validateForm()">


                            <input type="hidden" name="product_ID" value="${product.product_ID}">

                            <div class="mb-3">
                                <label for="product_name" class="form-label">Tên Sản Phẩm</label>
                                <input type="text" class="form-control" name="product_name" id="product_name" value="${product.product_name}" required>
                                <span class="error text-danger" id="errorName"></span>
                            </div>

                            <div class="mb-3">
                                <label for="original_price" class="form-label">Giá Bán</label>
                                <input type="number" class="form-control" name="original_price" id="original_price" step="0.01" value="${product.original_price}" required>
                                <span class="error text-danger" id="errorOriginalPrice"></span>
                            </div>

                            <div class="mb-3">
                                <label for="import_price" class="form-label">Giá Nhập</label>
                                <input type="number" class="form-control" name="import_price" id="import_price" step="0.01" value="${product.import_price}" required>
                                <span class="error text-danger" id="errorImportPrice"></span>
                            </div>

                            <div class="mb-3">
                                <<label for="product_description" class="form-label">Mô Tả</label>
                                <textarea class="form-control" name="product_description">${product.product_description}</textarea>
                                <span class="error text-danger" id="errorDescription"></span>
                            </div>

                            <div class="mb-3">
                                <label for="brief_information" class="form-label">Thông Tin</label>
                                <textarea class="form-control" name="brief_information" id="brief_information">${product.brief_information}</textarea>
                                <span class="error text-danger" id="errorInformation"></span>
                            </div>

                            <div class="mb-3">
                                <label for="category_id" class="form-label">Danh Mục</label>
                                <select class="form-select" name="category_id" id="category_id" required>
                                    <c:forEach var="category" items="${categories}">
                                        <option value="${category.category_productID}" 
                                                ${category.category_productID == product.category.category_productID ? 'selected' : ''}>
                                            ${category.category_name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="status" class="form-label">Trạng Thái</label>
                                <select class="form-select" name="status">
                                    <option value="1" ${product.status ? 'selected' : ''}>Hoạt động</option>
                                    <option value="0" ${!product.status ? 'selected' : ''}>Ngừng hoạt động</option>
                                </select>
                            </div>

                            <h4 class="mt-4">Sửa Variant</h4>

                            <c:if test="${empty productVariant}">
                                <div class="alert alert-warning">Sản phẩm này chưa có variant.</div>
                            </c:if>

                            <c:if test="${not empty productVariant}">
                                <input type="hidden" name="variantID" value="${productVariant.variantID}">
                                <div class="row variant-row mb-3">
                                    <div class="col-md-2">
                                        <label for="size" class="form-label">Kích Cỡ</label>
                                        <select class="form-select" name="size" required>
                                            <c:forEach var="size" items="${sizes}">
                                                <option value="${size.sizeID}" ${size.sizeID == productVariant.size.sizeID ? 'selected' : ''}>
                                                    ${size.sizeName}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="col-md-2">
                                        <label for="color" class="form-label">Màu Sắc</label>
                                        <select class="form-select" name="color" required>
                                            <c:forEach var="color" items="${colors}">
                                                <option value="${color.colorID}" ${color.colorID == productVariant.color.colorID ? 'selected' : ''}>
                                                    ${color.colorName}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="col-md-2">
                                        <label for="stock" class="form-label">Số Lượng</label>
                                        <input type="number" class="form-control" name="stock" id="stock" value="${productVariant.stock}" required>
                                        <span class="error text-danger" id="errorStock"></span>
                                    </div>

                                    <div class="col-md-4">
                                        <label for="imageURL" class="form-label">Ảnh Sản Phẩm</label>
                                        <input type="text" class="form-control" name="imageURL" id="imageURL" value="${productVariant.imageURL}" required>
                                        <span class="error text-danger" id="errorImage"></span>
                                    </div>
                                </div>
                            </c:if>

                            <button type="submit" class="btn btn-primary">Cập nhật</button>
                        </form>
                    </div>
                    <script>
                        function validateForm() {
                            let isValid = true;

                            
                            let product_name = document.getElementById("product_name").value.trim();
                            let product_description = document.getElementById("product_description").value.trim();
                            let brief_information = document.getElementById("brief_information").value.trim();
                            let imageURL = document.getElementById("imageURL").value.trim();
                            let import_price = parseFloat(document.getElementById("import_price").value);
                            let original_price = parseFloat(document.getElementById("original_price").value);
                            let stock = parseInt(document.getElementById("stock").value);

                            // Xóa lỗi cũ
                            document.querySelectorAll(".error").forEach(e => e.innerText = "");

                            // Kiểm tra các trường không được để trống
                            if (!product_name) {
                                document.getElementById("errorName").innerText = "Vui lòng nhập tên sản phẩm.";
                                isValid = false;
                            }
                            if (!product_description) {
                                document.getElementById("errorDescription").innerText = "Vui lòng nhập mô tả.";
                                isValid = false;
                            }
                            if (!brief_information) {
                                document.getElementById("errorInformation").innerText = "Vui lòng nhập thông tin ngắn.";
                                isValid = false;
                            }
                            if (!imageURL) {
                                document.getElementById("errorImage").innerText = "Vui lòng nhập đường dẫn ảnh.";
                                isValid = false;
                            }

                            // Kiểm tra số phải là số dương
                            if (isNaN(import_price) || import_price <= 0) {
                                document.getElementById("errorImportPrice").innerText = "Giá nhập phải là số dương.";
                                isValid = false;
                            }
                            if (isNaN(original_price) || original_price <= 0) {
                                document.getElementById("errorOriginalPrice").innerText = "Giá bán phải là số dương.";
                                isValid = false;
                            }
                            if (!isNaN(import_price) && !isNaN(original_price) && import_price >= original_price) {
                                document.getElementById("errorOriginalPrice").innerText = "Giá bán phải lớn hơn giá nhập.";
                                isValid = false;
                            }
                            if (isNaN(stock) || stock < 0) {
                                document.getElementById("errorStock").innerText = "Số lượng phải là số dương.";
                                isValid = false;
                            }

                            if (!isValid) {
                                event.preventDefault();
                            }
                        }
                    </script>
                </div>
            </div>
        </div>
    </body>
</html>
