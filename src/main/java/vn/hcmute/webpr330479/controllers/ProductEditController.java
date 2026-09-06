package vn.hcmute.webpr330479.controllers;

import java.io.IOException;
import java.math.BigDecimal;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.hcmute.webpr330479.models.Category;
import vn.hcmute.webpr330479.models.Product;
import vn.hcmute.webpr330479.services.CategoryService;
import vn.hcmute.webpr330479.services.ProductService;
import vn.hcmute.webpr330479.services.impl.CategoryServiceImpl;
import vn.hcmute.webpr330479.services.impl.ProductServiceImpl;
import vn.hcmute.webpr330479.utils.FileUploadUtil;

@WebServlet("/admin/product/edit")
@MultipartConfig(
        fileSizeThreshold =
                1024 * 1024,
        maxFileSize =
                5 * 1024 * 1024,
        maxRequestSize =
                25 * 1024 * 1024)
public class ProductEditController
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ProductService productService =
            new ProductServiceImpl();

    private final CategoryService categoryService =
            new CategoryServiceImpl();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Product product =
                getProduct(
                        request);

        if (product == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/product/list");

            return;
        }

        request.setAttribute(
                "product",
                product);

        request.setAttribute(
                "categories",
                categoryService.getAll());

        request.getRequestDispatcher(
                "/views/admin/edit-product.jsp")
                .forward(
                        request,
                        response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding(
                "UTF-8");

        Product product =
                getProduct(
                        request);

        if (product == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/product/list");

            return;
        }

        String name =
                trim(
                        request.getParameter(
                                "name"));

        String description =
                trim(
                        request.getParameter(
                                "description"));

        String priceValue =
                trim(
                        request.getParameter(
                                "price"));

        String categoryIdValue =
                trim(
                        request.getParameter(
                                "categoryId"));

        if (name.isEmpty()
                || priceValue.isEmpty()
                || categoryIdValue.isEmpty()) {

            forwardWithMessage(
                    request,
                    response,
                    product,
                    "Vui lòng nhập đầy đủ thông tin.");

            return;
        }

        try {

            BigDecimal price =
                    new BigDecimal(
                            priceValue);

            int categoryId =
                    Integer.parseInt(
                            categoryIdValue);

            if (price.compareTo(
                    BigDecimal.ZERO) < 0) {

                forwardWithMessage(
                        request,
                        response,
                        product,
                        "Giá phải lớn hơn "
                        + "hoặc bằng 0.");

                return;
            }

            Category category =
                    categoryService
                            .getById(
                                    categoryId);

            if (category == null) {

                forwardWithMessage(
                        request,
                        response,
                        product,
                        "Danh mục không tồn tại.");

                return;
            }

            String oldImage =
                    product.getImage();

            Part imagePart =
                    request.getPart(
                            "image");

            String newImage =
                    FileUploadUtil
                            .saveProductImage(
                                    imagePart);

            product.setName(
                    name);

            product.setDescription(
                    description);

            product.setPrice(
                    price);

            product.setCategory(
                    category);

            if (newImage != null) {

                product.setImage(
                        newImage);
            }

            productService.update(
                    product);

            if (newImage != null
                    && oldImage != null
                    && !oldImage.isBlank()) {

                try {

                    FileUploadUtil
                            .deleteProductImage(
                                    oldImage);

                } catch (IOException exception) {

                    System.err.println(
                            "Không thể xóa ảnh cũ: "
                            + oldImage);
                }
            }

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/product/list");

        } catch (NumberFormatException exception) {

            forwardWithMessage(
                    request,
                    response,
                    product,
                    "Giá hoặc danh mục "
                    + "không hợp lệ.");

        } catch (IOException exception) {

            forwardWithMessage(
                    request,
                    response,
                    product,
                    exception.getMessage());
        }
    }

    private Product getProduct(
            HttpServletRequest request) {

        String idValue =
                request.getParameter(
                        "id");

        if (idValue == null
                || idValue.isBlank()) {

            return null;
        }

        try {

            return productService
                    .getById(
                            Integer.parseInt(
                                    idValue));

        } catch (NumberFormatException exception) {

            return null;
        }
    }

    private String trim(
            String value) {

        return value == null
                ? ""
                : value.trim();
    }

    private void forwardWithMessage(
            HttpServletRequest request,
            HttpServletResponse response,
            Product product,
            String message)
            throws ServletException, IOException {

        product.setName(
                trim(
                        request.getParameter(
                                "name")));

        product.setDescription(
                trim(
                        request.getParameter(
                                "description")));

        String priceValue =
                trim(
                        request.getParameter(
                                "price"));

        try {

            if (!priceValue.isEmpty()) {

                product.setPrice(
                        new BigDecimal(
                                priceValue));
            }

        } catch (NumberFormatException ignored) {
        }

        request.setAttribute(
                "product",
                product);

        request.setAttribute(
                "selectedCategoryId",
                trim(
                        request.getParameter(
                                "categoryId")));

        request.setAttribute(
                "categories",
                categoryService.getAll());

        request.setAttribute(
                "message",
                message);

        request.getRequestDispatcher(
                "/views/admin/edit-product.jsp")
                .forward(
                        request,
                        response);
    }
}