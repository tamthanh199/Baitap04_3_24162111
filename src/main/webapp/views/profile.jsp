<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">

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

    <% if (request.getAttribute("message") != null) { %>

        <p style="color: red;">
            ${message}
        </p>

    <% } %>

    <c:if test="${not empty user.images}">

        <p>Ảnh đại diện hiện tại:</p>

        <img
            src="${pageContext.request.contextPath}/profile-image?fname=${user.images}"
            alt="Avatar"
            width="150"
            height="150">

        <br><br>

    </c:if>

    <form
        action="${pageContext.request.contextPath}/profile"
        method="post"
        enctype="multipart/form-data">

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

        <div>

            <label>Ảnh đại diện:</label>

            <input
                type="file"
                name="image"
                accept="image/jpeg,image/png,image/gif,image/webp">

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