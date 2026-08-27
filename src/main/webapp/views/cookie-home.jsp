<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang Cookie</title>
</head>
<body>
    <h1>Đăng nhập Cookie thành công</h1>

    <p>
        Xin chào:
        <strong>${username}</strong>
    </p>

    <p>
        Cookie
        <code>cookie_username</code>
        đang được lưu trong trình duyệt.
    </p>

    <hr>

    <p>
        <a href="${pageContext.request.contextPath}/admin/category/list">
            Xem và quản lý Category
        </a>
    </p>

    <p>
        <a href="${pageContext.request.contextPath}/">
            Về trang chính
        </a>
    </p>

    <p>
        <a href="${pageContext.request.contextPath}/cookie/logout">
            Đăng xuất Cookie
        </a>
    </p>
</body>
</html>