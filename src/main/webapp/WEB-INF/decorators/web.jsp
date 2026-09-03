<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>

<html lang="vi">

<head>

    <meta charset="UTF-8">

    <meta
        name="viewport"
        content="width=device-width, initial-scale=1.0">

    <title>
        <sitemesh:write property="title"/>
    </title>

    <sitemesh:write property="head"/>

</head>

<body>

    <div>

        <%@ include file="/commons/web/header.jsp" %>

    </div>

    <main style="
        max-width: 900px;
        margin: 0 auto;
        min-height: 500px;
        padding: 20px;
    ">

        <sitemesh:write property="body"/>

    </main>

    <div>

        <%@ include file="/commons/web/footer.jsp" %>

    </div>

</body>

</html>