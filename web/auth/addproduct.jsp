<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Thêm Sản Phẩm</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js"></script>
    </head>
    <body class="bg-light">

        <div class="container-fluid">
            <div class="row">
                <!-- Include Sidebar -->
                <div class="col-md-3">
                    <jsp:include page="sidebar.jsp" />
                </div>

                <div class="col-md-9">
                    <div class="container mt-5">
                        <h2 class="text-center mb-4">Thêm Sản Phẩm</h2>


                        <!-- Hiển thị thông báo lỗi nếu có -->
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">
                                ${error}
                            </div>
                        </c:if>

                        <!-- Hiển thị thông báo thành công nếu có -->
                        <c:if test="${not empty success}">
                            <div class="alert alert-success">
                                ${success}
                            </div>
                        </c:if>
                        <!-- Form thêm sản phẩm -->
                        <form id="productForm" action="addproduct" method="post">

                            <div class="mb-3">
                                <label for="product_name" class="form-label">Tên Sản Phẩm</label>
                                <input type="text" class="form-control" id="product_name" name="product_name" value="${not empty product_name ? product_name : ''}" required>
                                <span class="error text-danger" id="errorName"></span>
                            </div>

                            <div class="mb-3">
                                <label for="category_id" class="form-label">Danh Mục</label>
                                <select class="form-select" id="category_id" name="category_id" required>
                                    <c:forEach var="category" items="${categories}">
                                        <option value="${category.category_productID}" ${category.category_productID == category_id ? 'selected' : ''}>
                                            ${category.category_name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="description" class="form-label">Mô Tả</label>
                                <textarea class="form-control" id="description" name="description" rows="3" required>${not empty description ? description : ''}</textarea>
                                <span class="error text-danger" id="errorDescription"></span>
                            </div>

                            <div class="mb-3">
                                <label for="information" class="form-label">Thông Tin</label>
                                <textarea class="form-control" id="information" name="information" rows="3" required>${not empty information ? information : ''}</textarea>
                                <span class="error text-danger" id="errorInformation"></span>
                            </div>

                            <div class="mb-3">
                                <label for="original_price" class="form-label">Giá Bán</label>
                                <input type="number" class="form-control" id="original_price" name="original_price" value="${not empty original_price ? original_price : ''}" required>
                                <span class="error text-danger" id="errorOriginalPrice"></span>
                            </div>

                            <div class="mb-3">
                                <label for="import_price" class="form-label">Giá Nhập</label>
                                <input type="number" class="form-control" id="import_price" name="import_price" value="${not empty import_price ? import_price : ''}" required>
                                <span class="error text-danger" id="errorImportPrice"></span>
                            </div>

                            <div class="mb-3">
                                <label for="status" class="form-label">Trạng Thái</label>
                                <select class="form-select" id="status" name="status" required>
                                    <option value="1" ${status == '1' ? 'selected' : ''}>Còn hàng</option>
                                    <option value="2" ${status == '2' ? 'selected' : ''}>Hết hàng</option>
                                </select>
                            </div>

                            <h4 class="mt-4">Thông Tin Sản Phẩm - Các Variants</h4>

                            <div class="mb-3">
                                <label for="size" class="form-label">Kích Cỡ</label>
                                <select class="form-select" id="size" name="size" required>
                                    <c:forEach var="size" items="${sizes}">
                                        <option value="${size.sizeID}">${size.sizeName}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="color" class="form-label">Màu Sắc</label>
                                <select class="form-select" id="color" name="color" required>
                                    <c:forEach var="color" items="${colors}">
                                        <option value="${color.colorID}">${color.colorName}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="stock" class="form-label">Số Lượng</label>
                                <input type="number" class="form-control" id="stock" name="stock" value="${not empty stock ? stock : ''}" required>
                                <span class="error text-danger" id="errorStock"></span>
                            </div>

                            <div class="mb-3">
                                <label for="imageURL" class="form-label">Ảnh Sản Phẩm</label>
                                <textarea class="form-control" id="imageURL" name="imageURL" rows="3" required>${not empty imageURL ? imageURL : ''}</textarea>
                                <span class="error text-danger" id="errorImage"></span>
                            </div>
                            <div class="d-flex justify-content-center">
                                <button type="submit" class="btn btn-primary">Thêm Sản Phẩm</button>
                            </div>
                        </form>
                    </div>
                    <script>
                        document.getElementById('category_id').addEventListener('change', function () {
                            const categoryId = this.value;
                            fetch('getsize?category_id=' + categoryId)
                                    .then(response => response.json())
                                    .then(data => {
                                        const sizeSelect = document.getElementById('size');
                                        sizeSelect.innerHTML = ''; // Xóa các option cũ
                                        data.forEach(size => {
                                            const option = document.createElement('option');
                                            option.value = size.sizeID;
                                            option.textContent = size.sizeName;
                                            sizeSelect.appendChild(option);
                                        });
                                    })
                                    .catch(error => console.error('Error:', error));
                        });
                    </script>
                    <script>
                        document.getElementById("productForm").addEventListener("submit", function (event) {
                            let isValid = true;

                            // Lấy giá trị từ input
                            let productName = document.getElementById("product_name").value.trim();
                            let description = document.getElementById("description").value.trim();
                            let information = document.getElementById("information").value.trim();
                            let imageURL = document.getElementById("imageURL").value.trim();
                            let importPrice = parseFloat(document.getElementById("import_price").value);
                            let originalPrice = parseFloat(document.getElementById("original_price").value);
                            let stock = parseInt(document.getElementById("stock").value);

                            // Xóa lỗi cũ
                            document.querySelectorAll(".error").forEach(e => e.innerText = "");

                            // Kiểm tra các trường không được để trống
                            if (!productName) {
                                document.getElementById("errorName").innerText = "Vui lòng nhập tên sản phẩm.";
                                isValid = false;
                            }
                            if (!description) {
                                document.getElementById("errorDescription").innerText = "Vui lòng nhập mô tả.";
                                isValid = false;
                            }
                            if (!information) {
                                document.getElementById("errorInformation").innerText = "Vui lòng nhập thông tin.";
                                isValid = false;
                            }
                            if (!imageURL) {
                                document.getElementById("errorImage").innerText = "Vui lòng nhập đường dẫn ảnh.";
                                isValid = false;
                            }

                            // Kiểm tra số phải là số dương
                            if (isNaN(importPrice) || importPrice <= 0) {
                                document.getElementById("errorImportPrice").innerText = "Giá nhập phải là số dương.";
                                isValid = false;
                            }
                            if (isNaN(originalPrice) || originalPrice <= 0) {
                                document.getElementById("errorOriginalPrice").innerText = "Giá bán phải là số dương.";
                                isValid = false;
                            }
                            if (!isNaN(importPrice) && !isNaN(originalPrice) && importPrice >= originalPrice) {
                                document.getElementById("errorOriginalPrice").innerText = "Giá bán phải lớn hơn giá nhập.";
                                isValid = false;
                            }
                            if (isNaN(stock) || stock < 0) {
                                document.getElementById("errorStock").innerText = "Số lượng phải là số dương.";
                                isValid = false;
                            }

                            // Nếu có lỗi thì ngăn submit
                            if (!isValid) {
                                event.preventDefault();
                            }
                        });
                    </script>
                    </body>

                    </html>
