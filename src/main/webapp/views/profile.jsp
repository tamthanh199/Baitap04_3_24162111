<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Profile</title>
</head>

<body>

    <h1>Thông tin cá nhân</h1>

    <% if ("true".equals(request.getParameter("success"))) { %>
        <p style="color: green;">
            Cập nhật thông tin thành công.
        </p>
    <% } %>

    <form
        action="${pageContext.request.contextPath}/profile"
        method="post">

        <div>
            <label>Tài khoản:</label>
            <input
                type="text"
                value="${user.username}"
                readonly>
        </div>

        <br>

        <div>
            <label>Email:</label>
            <input
                type="email"
                value="${user.email}"
                readonly>
        </div>

        <br>

        <div>
            <label>Họ và tên:</label>
            <input
                type="text"
                name="fullName"
                value="${user.fullName}"
                required>
        </div>

        <br>

        <div>
            <label>Số điện thoại:</label>
            <input
                type="text"
                name="phone"
                value="${user.phone}">
        </div>

        <br>

        <button type="submit">
            Cập nhật
        </button>

    </form>

    <br>

    <a href="${pageContext.request.contextPath}/session/home">
        Quay về trang Session
    </a>

</body>
</html>