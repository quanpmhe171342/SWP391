<%-- 
    Document   : EditProduct
    Created on : Feb 11, 2025, 12:41:21 AM
    Author     : phuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Thêm Sản Phẩm</title>
        <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
        <link href="css/sb-admin-2.min.css" rel="stylesheet">
        <link href="vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
        <style>
            #content-wrapper {
                background-color: #f8f9fc;
            }
            .product-form-container {
                background: #fff;
                border-radius: 8px;
                box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
                margin: 1.5rem;
                padding: 1.5rem;
            }
            .product-form {
                display: grid;
                gap: 1.5rem;
            }
            .input-group {
                display: flex;
                flex-direction: column;
                gap: 0.5rem;
            }
            .product-form label {
                font-weight: 600;
                color: #2c3e50;
                font-size: 0.95rem;
            }
            .product-form input[type="text"],
            .product-form input[type="number"],
            .product-form select {
                padding: 0.75rem;
                border: 1px solid #e2e8f0;
                border-radius: 6px;
                font-size: 1rem;
                width: 100%;
            }
            .color-options {
                display: grid;
                gap: 1rem;
                margin-top: 1rem;
            }
            .color-section {
                background-color: #f8fafc;
                border-radius: 6px;
                border: 1px solid #e2e8f0;
                padding: 1rem;
            }
            .color-group {
                display: grid;
                gap: 1rem;
                margin-bottom: 1rem;
            }
            .size-options {
                display: grid;
                gap: 0.5rem;
                margin-top: 0.5rem;
            }
            .size-group {
                display: flex;
                align-items: center;
                gap: 1rem;
            }
            .size-group input[type="number"] {
                width: 100px;
            }
            input[type="file"] {
                width: 100%;
                padding: 0.5rem;
                border: 1px dashed #cbd5e0;
                border-radius: 4px;
            }
            .submit-btn {
                margin-top: 2rem;
                padding: 1rem 2rem;
                background-color: #4e73df;
                color: white;
                border: none;
                border-radius: 6px;
                font-weight: 600;
                width: 100%;
            }
            .submit-btn:hover {
                background-color: #2e59d9;
            }
            input:disabled {
                background-color: #f1f5f9;
                cursor: not-allowed;
                opacity: 0.7;
            }
        </style>
    </head>
    <body id="page-top">
        <div id="wrapper">

            <jsp:include page="sideadmin.jsp"></jsp:include>
                <div id="content-wrapper" class="d-flex flex-column">
                    <div id="content">
                    <jsp:include page="header.jsp"></jsp:include>
                        <div class="container-fluid">
                            <div class="page-header d-sm-flex align-items-center justify-content-between mb-4">
                                <h1 class="page-title">Sửa Sản Phẩm</h1>
                            </div>

                            <div class="product-form-container">
                                <form id="productForm" class="product-form" method="post" action="MangeProduct" enctype="multipart/form-data">
                                <c:set value="${product}" var="p"></c:set>
                                <input type="hidden" id="productName" name="productID" value="${p.product.product_ID}" required>
                                    <div class="input-group">
                                        <label for="productName">Tên sản phẩm:</label>
                                        <input type="text" id="productName" name="productName" value="${p.product.product_name}" required>
                                </div>

                                <div class="input-group">
                                    <label for="category">Loại sản phẩm:</label>
                                    <select id="category" name="category">
                                        <c:forEach items="${cate}" var="c">
                                            <option value="${c.category_productID}" ${c.category_productID == p.product.ct.category_productID ? 'selected' : ''}>${c.category_name}</option>
                                        </c:forEach>

                                    </select>
                                </div>


                                <div class="input-group">
                                    <label for="originalPrice">Giá gốc:</label>
                                    <input type="number" id="originalPrice" name="originalPrice" min="0" value="${p.product.original_Price}" required>
                                </div>

                                <div class="input-group">
                                    <label for="salePrice">Giá sale:</label>
                                    <input type="number" id="salePrice" name="salePrice" min="0" value="${p.product.sale_price}" required>
                                </div>
                                <div class="input-group">
                                    <label for="productName">Chi tiết :</label>
                                    <input type="text" id="productName" name="description" value="${p.product.product_description}" required>
                                </div>

                                <div class="input-group">
                                    <label for="productName">Thông tin ngắn :</label>
                                    <input type="text" id="productName" name="brief"  value="${p.product.brief_information}" required>
                                </div>
                                <div class="input-group">
                                    <label>Màu sắc, kích thước và số lượng:</label>
                                    <div class="color-options">
                                        <c:forEach items="${color}" var="c">
                                            <div class="color-section">
                                                <div class="color-group">
                                                    <div>
                                                        <%-- Kiểm tra xem màu hiện tại có trong colorProduct không --%>
                                                        <c:set var="isSelected" value="false" />
                                                        <c:forEach items="${colorProduct}" var="cp">
                                                            <c:if test="${c.color_id == cp.color.color_id}">
                                                                <c:set var="isSelected" value="true" />
                                                            </c:if>
                                                        </c:forEach>

                                                        <input type="checkbox" id="color-${c.color_id}" 
                                                               name="color" 
                                                               value="${c.color_id}"
                                                               ${isSelected ? 'checked' : ''}>
                                                        <label for="color-${c.color_id}">${c.color_Name}</label>
                                                    </div>

                                                    <div class="size-options">
                                                        <c:forEach items="${sizes}" var="size">
                                                            <div class="size-group">
                                                                <label for="qty-${c.color_id}-${size.size_id}">Size ${size.size_name}:</label>
                                                                <c:set var="stockValue" value="0"/>
                                                                <c:forEach items="${colorProduct}" var="cp">
                                                                    <c:if test="${ cp.size.size_id == size.size_id && cp.color.color_id == c.color_id}">
                                                                        <c:set var="stockValue" value="${cp.stock}"/>
                                                                    </c:if>

                                                                </c:forEach>
                                                               <input type="hidden" value="${size.size_id}" name="sizes">
                                                                <input type="number" id="qty-${c.color_id}-${size.size_id}" 
                                                                       name="quantity-${c.color_id}-${size.size_id}" 
                                                                       min="0" value="${stockValue}" disabled>
                                                            </div>
                                                        </c:forEach>

                                                    </div>
                                                </div>

                                                <input type="file" id="img-${c.color_id}" 
                                                       name="image-${c.color_id}" 
                                                       accept="image/*"  disabled>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                                <input type="hidden" name="action" value="editProduct">
                                <button type="submit" class="submit-btn">Sửa sản phẩm</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            document.getElementById('category').addEventListener('change', function () {
                const categoryId = this.value;
                console.log('Sending categoryId:', categoryId); // Debugging log

                if (!categoryId) {
                    console.warn('No category selected.');
                    return;
                }

                // Gửi request đến Servlet
                fetch('MangeProduct', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: new URLSearchParams({categoryId: categoryId}) // Đảm bảo gửi đúng format
                })
                        .then(response => {
                            console.log('Response status:', response.status);
                            if (!response.ok) {
                                return response.text().then(text => {
                                    console.error('Error response:', text);
                                    throw new Error(`HTTP error! Status: ${response.status}`);
                                });
                            }
                            return response.json();
                        })
                        .then(sizes => {
                            console.log('Received sizes:', sizes);

                            if (!Array.isArray(sizes)) {
                                throw new Error("Invalid response format: Expected an array");
                            }

                            document.querySelectorAll('.size-options').forEach(container => {
                                const colorSection = container.closest('.color-section');
                                const colorId = colorSection ? colorSection.getAttribute('data-color-id') : "unknown";

                                // Xóa nội dung cũ
                                container.innerHTML = '';
                                console.log("Sizes array:", sizes);
                                sizes.map(size => console.log("Processing size:", size.size_name));

                                sizes.map(size => {
                                    const div = document.createElement("div");
                                    div.classList.add("size-group");
                                    const label = document.createElement("label");
                                    label.setAttribute("for", `qty-${colorId}-${size.size_id}`);
                                    label.textContent = "Size: " + size.size_name;
                                    const input = document.createElement("input");
                                    input.type = "number";
                                    input.id = `qty-${colorId}-${size.size_id}`;
                                    input.name = `quantity-${colorId}-${size.size_id}`;
                                    input.min = "0";
                                    input.value = "0";
                                    input.disabled = true;
                                    div.appendChild(label);
                                    div.appendChild(input);
                                    container.appendChild(div);
                                });
                            });

                        })
                        .catch(error => {
                            console.error('Full error:', error);
                        });

                console.log(document.querySelectorAll('.size-options'));
            });


// Xử lý checkbox màu sắc
            document.querySelectorAll("input[name='color']").forEach(checkbox => {
                checkbox.addEventListener("change", function () {
                    const colorSection = this.closest('.color-section');
                    const qtyInputs = colorSection.querySelectorAll("input[type='number']");
                    const fileInput = colorSection.querySelector("input[type='file']");

                    qtyInputs.forEach(input => {
                        input.disabled = !this.checked;
                    });

                    if (fileInput) {
                        fileInput.disabled = !this.checked;
                    }
                });
            });

// Xử lý sự kiện submit form
            document.getElementById("productForm").addEventListener("submit", function (e) {

                const originalPrice = parseFloat(document.getElementById("originalPrice").value);
                const salePrice = parseFloat(document.getElementById("salePrice").value);

                if (salePrice > originalPrice) {
                    alert("Giá sale không thể cao hơn giá gốc!");
                    return;
                }

                const selectedColors = document.querySelectorAll("input[name='color']:checked");
                if (selectedColors.length === 0) {
                    alert("Vui lòng chọn ít nhất một màu sắc!");
                    return;
                }

                let formValid = true;
                selectedColors.forEach(colorCheckbox => {
                    const colorSection = colorCheckbox.closest('.color-section');
                    const colorName = colorSection.querySelector('label').textContent;
                    const qtyInputs = colorSection.querySelectorAll("input[type='number']");
                    const fileInput = colorSection.querySelector("input[type='file']");


                    
                });

                if (formValid) {
                    alert("Sản phẩm đã được sửa thành công!");
                }
            });

        </script>
    </body>
</html>