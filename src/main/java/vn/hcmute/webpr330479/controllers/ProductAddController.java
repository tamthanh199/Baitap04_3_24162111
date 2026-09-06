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

@WebServlet("/admin/product/add")
@MultipartConfig(
        fileSizeThreshold =
                1024 * 1024,
        maxFileSize =
                5 * 1024 * 1024,
        maxRequestSize =
                25 * 1024 * 1024)
public class ProductAddController
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

        loadCategories(
                request);

        request.getRequestDispatcher(
                "/views/admin/add-product.jsp")
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

        request.setAttribute(
                "name",
                name);

        request.setAttribute(
                "description",
                description);

        request.setAttribute(
                "price",
                priceValue);

        request.setAttribute(
                "selectedCategoryId",
                categoryIdValue);

        if (name.isEmpty()
                || priceValue.isEmpty()
                || categoryIdValue.isEmpty()) {

            forwardWithMessage(
                    request,
                    response,
                    "Vui lòng nhập tên, giá "
                    + "và danh mục sản phẩm.");

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
                        "Danh mục không tồn tại.");

                return;
            }

            Part imagePart =
                    request.getPart(
                            "image");

            String image =
                    FileUploadUtil
                            .saveProductImage(
                                    imagePart);

            Product product =
                    new Product(
                            name,
                            description,
                            price,
                            image,
                            category);

            productService.insert(
                    product);

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/product/list");

        } catch (NumberFormatException exception) {

            forwardWithMessage(
                    request,
                    response,
                    "Giá hoặc danh mục "
                    + "không hợp lệ.");

        } catch (IOException exception) {

            forwardWithMessage(
                    request,
                    response,
                    exception.getMessage());
        }
    }

    private String trim(
            String value) {

        return value == null
                ? ""
                : value.trim();
    }

    private void loadCategories(
            HttpServletRequest request) {

        request.setAttribute(
                "categories",
                categoryService.getAll());
    }

    private void forwardWithMessage(
            HttpServletRequest request,
            HttpServletResponse response,
            String message)
            throws ServletException, IOException {

        request.setAttribute(
                "message",
                message);

        loadCategories(
                request);

        request.getRequestDispatcher(
                "/views/admin/add-product.jsp")
                .forward(
                        request,
                        response);
    }
}