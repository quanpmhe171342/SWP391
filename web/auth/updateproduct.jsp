<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cập Nhật Sản Phẩm</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container mt-5">
            <h2 class="text-center mb-4">Cập Nhật Sản Phẩm</h2>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">
                    ${error}
                </div>
            </c:if>

            <c:if test="${not empty success}">
                <div class="alert alert-success">
                    ${success}
                </div>
            </c:if>

            <form action="updateproduct" method="post">
                <input type="hidden" name="productId" value="${product.productID}">

                <div class="mb-3">
                    <label class="form-label">Tên Sản Phẩm</label>
                    <input type="text" class="form-control" name="product_name" value="${product.productName}" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Giá Bán</label>
                    <input type="number" class="form-control" name="original_price" value="${product.original_price}" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Mô Tả</label>
                    <textarea class="form-control" name="description" rows="3" required>${product.product_description}</textarea>
                </div>

                <div class="mb-3">
                    <label class="form-label">Thông Tin</label>
                    <textarea class="form-control" name="information" rows="3" required>${product.brief_information}</textarea>
                </div>

                <div class="mb-3">
                    <label class="form-label">Danh Mục</label>
                    <select class="form-select" name="category_id" required>
                        <c:forEach var="category" items="${categories}">
                            <option value="${category.category_productID}" ${category.category_productID == product.categoryProductID ? 'selected' : ''}>${category.category_name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label">Giá Nhập</label>
                    <input type="number" class="form-control" name="import_price" value="${product.import_price}" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Trạng Thái</label>
                    <select class="form-select" name="status" required>
                        <option value="1" ${product.status ? 'selected' : ''}>Còn hàng</option>
                        <option value="2" ${!product.status ? 'selected' : ''}>Hết hàng</option>
                    </select>
                </div>

                <h4 class="mt-4">Các Variants Hiện Tại</h4>
                <c:if test="${empty variants}">
                    <p>Chưa có variant nào.</p>
                </c:if>
                <c:if test="${not empty variants}">
                    <ul class="list-group mb-4">
                        <c:forEach var="v" items="${variants}">
                            <li class="list-group-item">
                                Size: ${v.sizeName} | Màu: ${v.colorName} | Số lượng: ${v.stock} | Ảnh: <a href="${v.imageURL}" target="_blank">Xem ảnh</a>
                            </li>
                        </c:forEach>
                    </ul>
                    <p><em>Các variants cũ sẽ được xoá và thêm mới theo thông tin bên dưới.</em></p>
                </c:if>

                <h4 class="mt-4">Thêm Các Variants Mới</h4>
                <div id="variantContainer">
                    <div class="row variant-row mb-3">
                        <div class="col-md-2">
                            <select class="form-select" name="size" required>
                                <c:forEach var="size" items="${sizes}">
                                    <option value="${size.sizeID}">${size.sizeName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <select class="form-select" name="color" required>
                                <c:forEach var="color" items="${colors}">
                                    <option value="${color.colorID}">${color.colorName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <input type="number" class="form-control" name="stock" placeholder="Số lượng" required>
                        </div>
                        <div class="col-md-4">
                            <input type="text" class="form-control" name="imageURL" placeholder="Link ảnh" required>
                        </div>
                        <div class="col-md-2">
                            <button type="button" class="btn btn-danger remove-variant">Xóa</button>
                        </div>
                    </div>
                </div>
                <div class="d-flex justify-content-start mb-4">
                    <button type="button" id="addVariantBtn" class="btn btn-secondary">Thêm Variant</button>
                </div>

                <div class="d-flex justify-content-center">
                    <button type="submit" class="btn btn-primary">Cập Nhật Sản Phẩm</button>
                </div>
            </form>
        </div>

        <script>
            document.getElementById('addVariantBtn').addEventListener('click', function () {
                const container = document.getElementById('variantContainer');
                const variantRow = container.querySelector('.variant-row');
                const newRow = variantRow.cloneNode(true);
                newRow.querySelectorAll('input').forEach(input => input.value = '');
                container.appendChild(newRow);
            });

            document.getElementById('variantContainer').addEventListener('click', function (e) {
                if (e.target.classList.contains('remove-variant')) {
                    const rows = document.querySelectorAll('.variant-row');
                    if (rows.length > 1) {
                        e.target.closest('.variant-row').remove();
                    } else {
                        alert('Cần ít nhất 1 variant!');
                    }
                }
            });
        </script>
    </body>
</html>
