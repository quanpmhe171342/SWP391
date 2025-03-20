<%-- 
    Document   : addSlider
    Created on : Mar 19, 2025, 9:47:14 AM
    Author     : NV200
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            body {
    font-family: Arial, sans-serif;
}

.container {
    width: 50%;
    margin: auto;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h2 {
    text-align: center;
    margin-bottom: 20px;
}

.form-control {
    width: 100%;
    padding: 8px;
    margin: 8px 0;
    border: 1px solid #ccc;
    border-radius: 5px;
}

.btn {
    padding: 10px 15px;
    border: none;
    cursor: pointer;
    border-radius: 5px;
    margin-right: 10px;
}

.btn-success {
    background-color: #28a745;
    color: white;
}

.btn-success:hover {
    background-color: #218838;
}

.btn-secondary {
    background-color: #6c757d;
    color: white;
}

.btn-secondary:hover {
    background-color: #5a6268;
}

        </style>
    </head>
    <body>
    <div class="container">
        <h2>Thêm Slider</h2>

        <form action="AddSlider" method="post">
            <label>Tiêu đề:</label>
            <input type="text" name="title" class="form-control" required>

            <label>Ảnh (URL):</label>
            <input type="text" name="image" class="form-control" required>

            <label>Mô tả:</label>
            <textarea name="description" class="form-control" required></textarea>

            <label>Trạng thái:</label>
            <select name="status" class="form-control">
                <option value="1">Hiển thị</option>
                <option value="0">Ẩn</option>
            </select>

            <label>Product Variant ID:</label>
            <input list="productVariantList" name="productVariantId" class="form-control" required id="productVariantInput">

            <datalist id="productVariantList">
                <c:forEach var="variant" items="${pv}">
                    <option value="${variant.variantId}"></option>
                </c:forEach>
            </datalist>

            <button type="submit" class="btn btn-success">Thêm Slider</button>
            <a href="/SliderManager" class="btn btn-secondary">Hủy</a>
        </form>
    </div>

    <!-- JavaScript kiểm tra trước khi gửi -->
    <script>
    document.addEventListener("DOMContentLoaded", function () {
        const input = document.getElementById("productVariantInput");
        const datalist = document.getElementById("productVariantList");

        input.addEventListener("change", function () {
            let valid = false;
            const options = datalist.getElementsByTagName("option");

            for (let option of options) {
                if (input.value === option.value) {
                    valid = true;
                    break;
                }
            }

            if (!valid) {
                alert("Lỗi: Vui lòng chọn Product Variant ID hợp lệ!");
                input.value = ""; // Xóa giá trị nhập sai
                input.focus();
            }
        });
    });
    </script>
</body>
</html>
