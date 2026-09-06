package vn.hcmute.webpr330479.controllers;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.hcmute.webpr330479.models.Product;
import vn.hcmute.webpr330479.services.ProductService;
import vn.hcmute.webpr330479.services.impl.ProductServiceImpl;
import vn.hcmute.webpr330479.utils.FileUploadUtil;

@WebServlet("/admin/product/delete")
public class ProductDeleteController
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ProductService productService =
            new ProductServiceImpl();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String idValue =
                request.getParameter(
                        "id");

        if (idValue != null
                && !idValue.isBlank()) {

            try {

                int id =
                        Integer.parseInt(
                                idValue);

                Product product =
                        productService
                                .getById(
                                        id);

                if (product != null) {

                    String image =
                            product.getImage();

                    productService.delete(
                            id);

                    if (image != null
                            && !image.isBlank()) {

                        try {

                            FileUploadUtil
                                    .deleteProductImage(
                                            image);

                        } catch (IOException exception) {

                            System.err.println(
                                    "Không thể xóa ảnh: "
                                    + image);
                        }
                    }
                }

            } catch (NumberFormatException exception) {

                System.err.println(
                        "Product ID không hợp lệ.");
            }
        }

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/product/list");
    }
}