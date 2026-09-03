<%@ page
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html lang="vi">

<head>

    <meta charset="UTF-8">

    <title>Trang Session</title>

</head>

<body>

    <h1>
        Đăng nhập Session thành công
    </h1>

    <p>
        Xin chào:
        <strong>
            ${sessionScope.account.fullName}
        </strong>
    </p>

    <p>
        Tài khoản:
        ${sessionScope.account.username}
    </p>

    <p>
        Email:
        ${sessionScope.account.email}
    </p>

    <p>
        Số điện thoại:
        ${sessionScope.account.phone}
    </p>

    <p>
        Chọn chức năng trên thanh menu phía trên.
    </p>

</body>

</html>