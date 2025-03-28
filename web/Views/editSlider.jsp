<%-- 
    Document   : editSlider
    Created on : Mar 19, 2025, 1:54:56 PM
    Author     : NV200
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa Slider</title>
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
        <h2>Chỉnh sửa Slider</h2>
        <form action="UpdateSlider" method="post">
            <input type="hidden" name="sliderID" value="${slider.sliderID}">

            <label>Tiêu đề:</label>
            <input type="text" name="title" class="form-control" required value="${slider.title}">

            <label>Ảnh (URL):</label>
            <input type="text" name="image" class="form-control" required value="${slider.image}">

            <label>Mô tả:</label>
            <textarea name="description" class="form-control" required>${slider.description}</textarea>

            <label>Trạng thái:</label>
            <select name="status" class="form-control">
                <option value="1" ${slider.status == 1 ? 'selected' : ''}>Hiển thị trong trang chủ</option>
                <option value="0" ${slider.status == 0 ? 'selected' : ''}>Ẩn khỏi trang chủ</option>
            </select>

            <label>Product Variant ID:</label>
            <input list="productVariantList" name="productVariantId" class="form-control" required id="productVariantInput"
                   value="${slider.productVariantID.variantID}">

            <datalist id="productVariantList">
                <c:forEach var="variant" items="${pv}">
                    <option value="${variant.variantId}" ${slider.productVariantID.variantID == variant.variantId ? 'selected' : ''}>
                        ${variant.variantId}
                    </option>
                </c:forEach>
            </datalist>

            <button type="submit" class="btn btn-success">Lưu thay đổi</button>
            <a href="/SliderManager" class="btn btn-secondary">Hủy</a>
        </form>
    </div>

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
                    input.value = "";
                    input.focus();
                }
            });
        });
    </script>
</body>
</html>