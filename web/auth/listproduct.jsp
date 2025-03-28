<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh sách sản phẩm</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body class="bg-light">

        <div class="container-fluid">
            <div class="row">
                <!-- Include Sidebar -->
                <div class="col-md-3">
                    <jsp:include page="sidebar.jsp" />
                </div>

                <div class="col-md-5">
                    <div class="container mt-4">
                        <h2 class="mb-3 text-center">Danh Sách Sản Phẩm</h2>
                        <!-- Hiển thị thông báo thành công -->
                        <c:if test="${not empty successMessage}">
                            <div class="alert alert-success">
                                ${successMessage}
                            </div>
                        </c:if>

                        <!-- Hiển thị thông báo lỗi -->
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger">
                                ${errorMessage}
                            </div>
                        </c:if>

                        <c:if test="${not empty param.message}">
                            <div class="alert alert-success" role="alert">
                                ${param.message}
                            </div>
                        </c:if>

                        <!-- Thêm sản phẩm & Lọc -->
                        <div class="row mb-4">
                            <div class="col-md-9">
                                <form id="filterForm" method="GET" action="${pageContext.request.contextPath}/listproduct" class="row g-3">
                                    <!-- Lọc theo danh mục -->
                                    <div class="col-md-6">
                                        <label for="category_id" class="form-label">Danh Mục</label>
                                        <select class="form-select" id="category_id" name="category_id">
                                            <option value="">Tất cả</option>
                                            <c:forEach var="category" items="${categories}">
                                                <option value="${category.category_productID}" ${category.category_productID == category_id ? 'selected' : ''}>
                                                    ${category.category_name}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <!-- Lọc theo trạng thái -->
                                    <div class="col-md-3">
                                        <label for="statusFilter" class="form-label">Trạng Thái</label>
                                        <select class="form-select" id="statusFilter" name="status">
                                            <option value="">Tất cả</option>
                                            <option value="1" ${param.status == '1' ? 'selected' : ''}>Còn hàng</option>
                                            <option value="0" ${param.status == '0' ? 'selected' : ''}>Hết hàng</option>
                                        </select>
                                    </div>

                                    <!-- Nút lọc -->
                                    <div class="col-md-3 d-flex align-items-end">
                                        <button type="submit" class="btn btn-primary w-100">Lọc</button>
                                    </div>
                                </form>
                            </div>

                            <!-- Nút Thêm Sản Phẩm ở bên phải -->
                            <div class="col-md-3 d-flex align-items-end">
                                <a href="<%= request.getContextPath() %>/addproduct" class="btn btn-primary w-100">+ Thêm sản phẩm</a>
                            </div>
                        </div>

                        <table class="table table-striped table-hover">
                            <thead class="table-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Tên sản phẩm</th>
                                    <th>Danh mục</th>
                                    <th>Cỡ</th>
                                    <th>Màu</th>
                                    <th>Số Lượng</th>
                                    <th>Mô tả</th>
                                    <th>Thông tin</th>
                                    <th>Giá Bán</th>
                                    <th>Giá Nhập</th>
                                    <th>Trạng thái</th>
                                    <th>Hình ảnh</th>
                                    <th>Ngày tạo</th>
                                    <th>Sửa</th>
                                    <th>Xoá</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="p" items="${productList}">
                                    <tr>
                                        <td>${p.product_ID}</td>
                                        <td>${p.product_name}</td>
                                        <td>${p.category.category_name}</td>
                                        <td>${p.variants[0].size}</td>
                                        <td>${p.variants[0].color}</td>
                                        <td>${p.variants[0].stock}</td>
                                        <td>${p.product_description}</td>
                                        <td>${p.brief_information}</td>
                                        <td>${p.original_price}</td>
                                        <td>${p.import_price}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${p.status}">
                                                    <span class="badge bg-success">Còn hàng</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-danger">Hết hàng</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty p.imageURL}">
                                                    <img src="${p.imageURL}" alt="Ảnh sản phẩm" width="100">
                                                </c:when>
                                                <c:otherwise>
                                                    Chưa có ảnh
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${p.createDate}</td>
                                        <td><a href="updateproduct?product_ID=${p.product_ID}" class="btn btn-warning btn-sm">Sửa</a></td>
                                        <td>
                                            <a href="deleteproduct?product_ID=${p.product_ID}" class="btn btn-danger btn-sm" 
                                               onclick="return confirm('Bạn có chắc muốn xoá sản phẩm này?');">
                                                Xoá
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
