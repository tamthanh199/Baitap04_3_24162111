<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thêm Category</title>
</head>
<body>
    <h1>Thêm Category</h1>

    <p style="color: red;">${message}</p>

    <form action="${pageContext.request.contextPath}/admin/category/add"
          method="post"
          enctype="multipart/form-data">

        <p>
            <label>Tên danh mục:</label><br>
            <input type="text" name="name" value="${name}" required>
        </p>

        <p>
            <label>Ảnh đại diện:</label><br>
            <input type="file" name="icon" accept=".jpg,.jpeg,.png,.gif,.webp">
        </p>

        <button type="submit">Thêm</button>
        <a href="${pageContext.request.contextPath}/admin/category/list">Quay lại</a>
    </form>
</body>
</html>