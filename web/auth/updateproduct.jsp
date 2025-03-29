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
                                <small id="productNameError" class="text-danger"></small>
                            </div>

                            <div class="mb-3">
                                <label for="original_price" class="form-label">Giá Bán</label>
                                <input type="number" class="form-control" name="original_price" id="original_price" step="0.01" value="${product.original_price}" required>
                                <small id="originalPriceError" class="text-danger"></small>
                            </div>

                            <div class="mb-3">
                                <label for="import_price" class="form-label">Giá Nhập</label>
                                <input type="number" class="form-control" name="import_price" id="import_price" step="0.01" value="${product.import_price}" required>
                                <small id="importPriceError" class="text-danger"></small>
                            </div>

                            <div class="mb-3">
                                <label for="product_description" class="form-label">Mô Tả</label>
                                <textarea class="form-control" name="product_description">${product.product_description}</textarea>
                                <small id="descriptionError" class="text-danger"></small>
                            </div>

                            <div class="mb-3">
                                <label for="brief_information" class="form-label">Thông Tin</label>
                                <textarea class="form-control" name="brief_information" id="brief_information">${product.brief_information}</textarea>
                                <small id="informationError" class="text-danger"></small>
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
                                        <small id="stockError" class="text-danger"></small>
                                    </div>

                                    <div class="col-md-4">
                                        <label for="imageURL" class="form-label">Ảnh Sản Phẩm</label>
                                        <input type="text" class="form-control" name="imageURL" id="imageURL" value="${productVariant.imageURL}" required>
                                        <small id="imageURLError" class="text-danger"></small>
                                    </div>
                                </div>
                            </c:if>

                            <button type="submit" class="btn btn-primary mt-3">Cập nhật sản phẩm</button>
                        </form>
                    </div>

                </div>
            </div>
        </div>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const form = document.getElementById("updateProductForm");

                form.addEventListener("submit", function (event) {
                    let isValid = true;

                    const productName = document.getElementById("product_name").value.trim();
                    const description = document.getElementById("product_description").value.trim();
                    const information = document.getElementById("brief_information").value.trim();
                    const imageURL = document.getElementById("imageURL").value.trim();
                    const importPrice = document.getElementById("import_price").value.trim();
                    const originalPrice = document.getElementById("original_price").value.trim();
                    const stock = document.getElementById("stock").value.trim();

                    const productNameError = document.getElementById("productNameError");
                    const descriptionError = document.getElementById("descriptionError");
                    const informationError = document.getElementById("informationError");
                    const imageURLError = document.getElementById("imageURLError");
                    const importPriceError = document.getElementById("importPriceError");
                    const originalPriceError = document.getElementById("originalPriceError");
                    const stockError = document.getElementById("stockError");

                    const urlRegex = /^(https?:\/\/.*\.(?:png|jpg|jpeg|gif|webp))$/;
                    const priceRegex = /^\d+(\.\d{1,2})?$/; // Chấp nhận số thập phân 2 chữ số sau dấu chấm
                    const stockRegex = /^\d+$/; // Chỉ chấp nhận số nguyên

                    productNameError.textContent = productName.length > 0 ? "" : "Tên sản phẩm không được để trống!";
                    descriptionError.textContent = description.length > 0 ? "" : "Mô tả sản phẩm không được để trống!";
                    informationError.textContent = information.length > 0 ? "" : "Thông tin thêm không được để trống!";
                    imageURLError.textContent = urlRegex.test(imageURL) ? "" : "URL hình ảnh phải hợp lệ!";
                    importPriceError.textContent = priceRegex.test(importPrice) ? "" : "Giá nhập phải hợp lệ!";
                    originalPriceError.textContent = priceRegex.test(originalPrice) ? "" : "Giá gốc phải hợp lệ!";
                    stockError.textContent = stockRegex.test(stock) ? "" : "Số lượng tồn kho phải là số nguyên!";

                    isValid = productNameError.textContent === "" &&
                            descriptionError.textContent === "" &&
                            informationError.textContent === "" &&
                            imageURLError.textContent === "" &&
                            importPriceError.textContent === "" &&
                            originalPriceError.textContent === "" &&
                            stockError.textContent === "";

                    if (!isValid)
                        event.preventDefault();
                });
            });
        </script>
    </body>
</html>
