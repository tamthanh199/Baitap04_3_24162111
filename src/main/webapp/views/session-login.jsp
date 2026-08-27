<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập bằng Session</title>
</head>
<body>
    <h1>Đăng nhập bằng Session</h1>

    <p style="color: green;">${successMessage}</p>
    <p style="color: red;">${message}</p>

    <form action="${pageContext.request.contextPath}/session/login" method="post">
        <p>
            <label>Tài khoản:</label><br>
            <input type="text" name="username" required>
        </p>

        <p>
            <label>Mật khẩu:</label><br>
            <input type="password" name="password" required>
        </p>

        <button type="submit">Đăng nhập</button>
        <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
    </form>
</body>
</html>