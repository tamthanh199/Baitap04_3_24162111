<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html lang="vi">

<head>

    <meta charset="UTF-8">

    <meta
        name="viewport"
        content="width=device-width, initial-scale=1">

    <title>
        <sitemesh:write property="title"/>
    </title>

    <!-- Bootstrap 5 -->
    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
        rel="stylesheet"
        integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
        crossorigin="anonymous">

    <style>

        body {
            background-color: #f5f7fb;
        }

        .app-main {
            min-height: calc(100vh - 140px);
        }

        .page-card {
            background: white;
            border-radius: 16px;
            padding: 24px;

            box-shadow:
                0 0.25rem 1rem
                rgba(0, 0, 0, 0.06);
        }

        img {
            max-width: 100%;
        }

    </style>

    <!-- Head của từng JSP -->
    <sitemesh:write property="head"/>

</head>

<body>

    <!-- HEADER dùng chung -->
    <%@ include file="/commons/web/header.jsp" %>


    <!-- NỘI DUNG CỦA TỪNG JSP -->
    <main class="app-main py-4">

        <div class="container">

            <sitemesh:write property="body"/>

        </div>

    </main>


    <!-- FOOTER dùng chung -->
    <%@ include file="/commons/web/footer.jsp" %>


    <!-- Bootstrap JS -->
    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
        crossorigin="anonymous">
    </script>


    <!-- Bootstrap Form Validation -->
    <script>

        (() => {

            'use strict';

            const forms =
                document.querySelectorAll(
                    '.needs-validation'
                );

            Array.from(forms)
                .forEach(form => {

                    form.addEventListener(
                        'submit',
                        event => {

                            if (!form.checkValidity()) {

                                event.preventDefault();
                                event.stopPropagation();

                            }

                            form.classList.add(
                                'was-validated'
                            );

                        },
                        false
                    );

                });

        })();

    </script>

</body>

</html>