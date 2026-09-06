<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="jakarta.tags.core" %>

<header style="
    background-color: #eeeeee;
    padding: 15px;
    margin-bottom: 20px;
">

    <h2>
        WEBPR330479 - Servlet CRUD MVC
    </h2>

    <nav>

        <a href="${pageContext.request.contextPath}/home">
            Trang chủ
        </a>

        &nbsp; | &nbsp;

        <a href="${pageContext.request.contextPath}/product">
            Sản phẩm
        </a>

        &nbsp; | &nbsp;

        <c:choose>

            <c:when test="${not empty sessionScope.account}">

                <span>

                    Xin chào,

                    <strong>
                        ${sessionScope.account.fullName}
                    </strong>

                </span>

                &nbsp; | &nbsp;

                <a href="${pageContext.request.contextPath}/profile">
                    Profile
                </a>

                &nbsp; | &nbsp;

                <a href="${pageContext.request.contextPath}/admin/category/list">
                    Category
                </a>

                &nbsp; | &nbsp;

                <a href="${pageContext.request.contextPath}/admin/product/list">
                    Product CRUD
                </a>

                &nbsp; | &nbsp;

                <a href="${pageContext.request.contextPath}/logout">
                    Đăng xuất
                </a>

            </c:when>

            <c:otherwise>

                <a href="${pageContext.request.contextPath}/login">
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