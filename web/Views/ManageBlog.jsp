<%-- 
    Document   : ManageBlog
    Created on : Mar 11, 2025, 7:50:23 AM
    Author     : admin
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tạo Bài Viết</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        form { max-width: 500px; margin: auto; }
        label, input, textarea, select { display: block; width: 100%; margin-bottom: 10px; }
        input, textarea, select { padding: 8px; }
        input[type="submit"] { background-color: #28a745; color: white; border: none; cursor: pointer; }
    </style>
</head>
<body>
    <h2>Tạo Bài Viết Mới</h2>
    <form action="BlogServlet" method="post">
        <label>Tiêu đề:</label>
        <input type="text" name="title" required>
        <label>Nội dung:</label>
        <textarea name="content" required></textarea>
        <label>Tác giả:</label>
        <input type="text" name="author" required>
        <label>Trạng thái:</label>
        <select name="status">
            <option value="Draft">Bản nháp</option>
            <option value="Published">Đã xuất bản</option>
        </select>
        <input type="submit" value="Tạo">
    </form>
</body>
</html>

<!-- edit.jsp -->

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh Sửa Bài Viết</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        form { max-width: 500px; margin: auto; }
        label, input, textarea, select { display: block; width: 100%; margin-bottom: 10px; }
        input, textarea, select { padding: 8px; }
        input[type="submit"] { background-color: #007bff; color: white; border: none; cursor: pointer; }
    </style>
</head>
<body>
    <h2>Chỉnh Sửa Bài Viết</h2>
    <form action="BlogServlet" method="post">
        <input type="hidden" name="blogID" value="${blog.blogID}">
        <label>Tiêu đề:</label>
        <input type="text" name="title" value="${blog.title}" required>
        <label>Nội dung:</label>
        <textarea name="content" required>${blog.content}</textarea>
        <label>Tác giả:</label>
        <input type="text" name="author" value="${blog.author}" required>
        <label>Trạng thái:</label>
        <select name="status">
            <option value="Draft" ${blog.status == "Draft" ? "selected" : ""}>Bản nháp</option>
            <option value="Published" ${blog.status == "Published" ? "selected" : ""}>Đã xuất bản</option>
        </select>
        <input type="submit" value="Cập nhật">
    </form>
</body>
</html>

<!-- list.jsp -->

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh Sách Bài Viết</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        a { text-decoration: none; margin-right: 10px; }
    </style>
</head>
<body>
    <h2>Danh Sách Bài Viết</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>Tiêu đề</th>
            <th>Tác giả</th>
            <th>Trạng thái</th>
            <th>Ngày tạo</th>
            <th>Hành động</th>
        </tr>
        <c:forEach var="blog" items="${blogList}">
            <tr>
                <td>${blog.blogID}</td>
                <td>${blog.title}</td>
                <td>${blog.author}</td>
                <td>${blog.status}</td>
                <td>${blog.createdAt}</td>
                <td>
                    <a href="edit.jsp?id=${blog.blogID}">Chỉnh sửa</a>
                    <a href="delete.jsp?id=${blog.blogID}" style="color: red;">Xóa</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <a href="create.jsp" style="background-color: #28a745; color: white; padding: 8px 12px; border-radius: 5px;">Tạo bài viết mới</a>
</body>
</html>
