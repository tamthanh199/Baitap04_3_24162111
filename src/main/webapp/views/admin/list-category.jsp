<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý Category</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 30px;
        }

        h1 {
            color: #333;
        }

        table {
            border-collapse: collapse;
            width: 850px;
        }

        th,
        td {
            border: 1px solid #999;
            padding: 10px;
            text-align: center;
        }

        th {
            background-color: #eeeeee;
        }

        td.category-name {
            text-align: left;
        }

        .category-image {
            width: 120px;
            height: 80px;
            object-fit: cover;
            border: 1px solid #cccccc;
        }

        a {
            margin-right: 8px;
        }

        .add-link {
            display: inline-block;
            margin-bottom: 15px;
        }
    </style>
</head>

<body>
    <h1>Danh sách Category</h1>

    <a class="add-link"
       href="${pageContext.request.contextPath}/admin/category/add">
        Thêm danh mục
    </a>

    <table>
        <thead>
            <tr>
                <th>STT</th>
                <th>ID</th>
                <th>Tên danh mục</th>
                <th>Ảnh</th>
                <th>Thao tác</th>
            </tr>
        </thead>

        <tbody>
            <c:forEach items="${categories}"
                       var="category"
                       varStatus="status">

                <tr>
                    <td>${status.index + 1}</td>
                    <td>${category.id}</td>

                    <td class="category-name">
                        ${category.name}
                    </td>

                    <td>
                        <c:choose>
                            <c:when test="${not empty category.icon}">
                                <img class="category-image"
                                     src="${pageContext.request.contextPath}/image?fname=${category.icon}"
                                     alt="${category.name}">
                            </c:when>

                            <c:otherwise>
                                Chưa có ảnh
                            </c:otherwise>
                        </c:choose>
                    </td>

                    <td>
                        <a href="${pageContext.request.contextPath}/admin/category/edit?id=${category.id}">
                            Sửa
                        </a>

                        <a href="${pageContext.request.contextPath}/admin/category/delete?id=${category.id}"
                           onclick="return confirm('Bạn có chắc muốn xóa danh mục này?');">
                            Xóa
                        </a>
                    </td>
                </tr>

            </c:forEach>
        </tbody>
    </table>

    <p>
        <a href="${pageContext.request.contextPath}/session/login">
            Login Session
        </a>

        <a href="${pageContext.request.contextPath}/cookie/login">
            Login Cookie
        </a>

        <a href="${pageContext.request.contextPath}/register">
            Đăng ký
        </a>
    </p>
</body>
</html>