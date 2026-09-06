package vn.hcmute.webpr330479.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.hcmute.webpr330479.services.ProductService;
import vn.hcmute.webpr330479.services.impl.ProductServiceImpl;

@WebServlet("/product")
public class ProductController
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final int
            PAGE_SIZE = 6;

    private final ProductService productService =
            new ProductServiceImpl();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        long totalItems =
                productService.count();

        int totalPages =
                (int)
                        Math.ceil(
                                (double) totalItems
                                        / PAGE_SIZE);

        int page =
                parsePage(
                        request.getParameter(
                                "page"));

        if (totalPages > 0
                && page > totalPages) {

            page = totalPages;
        }

        request.setAttribute(
                "products",
                productService
                        .getPage(
                                page,
                                PAGE_SIZE));

        request.setAttribute(
                "currentPage",
                page);

        request.setAttribute(
                "totalPages",
                totalPages);

        request.setAttribute(
                "totalItems",
                totalItems);

        request.getRequestDispatcher(
                "/views/product.jsp")
                .forward(
                        request,
                        response);
    }

    private int parsePage(
            String value) {

        if (value == null
                || value.isBlank()) {

            return 1;
        }

        try {

            int page =
                    Integer.parseInt(
                            value);

            return Math.max(
                    page,
                    1);

        } catch (NumberFormatException exception) {

            return 1;
        }
    }
}