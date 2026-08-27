<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Servlet CRUD MVC</title>
</head>
<body>
    <h1>Servlet CRUD MVC</h1>

    <h2>Chọn chức năng</h2>

    <p>
        <a href="${pageContext.request.contextPath}/register">
            Đăng ký tài khoản
        </a>
    </p>

    <p>
        <a href="${pageContext.request.contextPath}/session/login">
            Đăng nhập bằng Session
        </a>
    </p>

    <p>
        <a href="${pageContext.request.contextPath}/cookie/login">
            Đăng nhập bằng Cookie
        </a>
    </p>

    <p>
        <a href="${pageContext.request.contextPath}/admin/category/list">
            Quản lý Category
        </a>
    </p>
</body>
</html>