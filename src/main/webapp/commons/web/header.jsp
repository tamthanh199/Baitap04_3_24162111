<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<header style="
    background-color: #eeeeee;
    padding: 15px;
    margin-bottom: 20px;
">

    <h2>WEBPR330479 - User Profile</h2>

    <nav>

        <a href="${pageContext.request.contextPath}/">
            Trang chủ
        </a>

        &nbsp; | &nbsp;

        <c:choose>

            <c:when test="${not empty sessionScope.account}">

                <a href="${pageContext.request.contextPath}/session/home">
                    Session Home
                </a>

                &nbsp; | &nbsp;

                <a href="${pageContext.request.contextPath}/profile">
                    Profile
                </a>

                &nbsp; | &nbsp;

                <a href="${pageContext.request.contextPath}/admin/category/list">
                    Category
                </a>

                &nbsp; | &nbsp;

                <a href="${pageContext.request.contextPath}/session/logout">
                    Đăng xuất
                </a>

            </c:when>

            <c:otherwise>

                <a href="${pageContext.request.contextPath}/session/login">
                    Đăng nhập
                </a>

                &nbsp; | &nbsp;

                <a href="${pageContext.request.contextPath}/register">
                    Đăng ký
                </a>

            </c:otherwise>

        </c:choose>

    </nav>

</header>