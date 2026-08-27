<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Sửa Category</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 30px;
        }

        .current-image {
            display: block;
            width: 160px;
            height: 110px;
            object-fit: cover;
            margin: 10px 0;
            border: 1px solid #cccccc;
        }

        .message {
            color: red;
        }
    </style>
</head>

<body>
    <h1>Sửa Category</h1>

    <p class="message">${message}</p>

    <form action="${pageContext.request.contextPath}/admin/category/edit"
          method="post"
          enctype="multipart/form-data">

        <input type="hidden" name="id" value="${category.id}">

        <p>
            <label>Tên danh mục:</label><br>
            <input type="text"
                   name="name"
                   value="${category.name}"
                   required>
        </p>

        <p>
            <label>Ảnh hiện tại:</label><br>

            <c:choose>
                <c:when test="${not empty category.icon}">
                    <img class="current-image"
                         src="${pageContext.request.contextPath}/image?fname=${category.icon}"
                         alt="${category.name}">
                </c:when>

                <c:otherwise>
                    Chưa có ảnh
                </c:otherwise>
            </c:choose>
        </p>

        <p>
            <label>Chọn ảnh mới:</label><br>
            <input type="file"
                   name="icon"
                   accept=".jpg,.jpeg,.png,.gif,.webp">
        </p>

        <button type="submit">Lưu thay đổi</button>

        <a href="${pageContext.request.contextPath}/admin/category/list">
            Quay lại
        </a>
    </form>
</body>
</html>