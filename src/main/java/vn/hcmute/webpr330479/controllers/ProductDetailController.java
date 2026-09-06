package vn.hcmute.webpr330479.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.hcmute.webpr330479.models.Product;
import vn.hcmute.webpr330479.services.ProductService;
import vn.hcmute.webpr330479.services.impl.ProductServiceImpl;

@WebServlet("/product/detail")
public class ProductDetailController
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ProductService productService =
            new ProductServiceImpl();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String idValue =
                request.getParameter(
                        "id");

        if (idValue == null
                || idValue.isBlank()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/product");

            return;
        }

        try {

            Product product =
                    productService
                            .getById(
                                    Integer.parseInt(
                                            idValue));

            if (product == null) {

                response.sendError(
                        HttpServletResponse
                                .SC_NOT_FOUND);

                return;
            }

            request.setAttribute(
                    "product",
                    product);

            request.getRequestDispatcher(
                    "/views/product-detail.jsp")
                    .forward(
                            request,
                            response);

        } catch (NumberFormatException exception) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/product");
        }
    }
}