package vn.hcmute.webpr330479.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.hcmute.webpr330479.models.Category;
import vn.hcmute.webpr330479.services.CategoryService;
import vn.hcmute.webpr330479.services.impl.CategoryServiceImpl;
import vn.hcmute.webpr330479.utils.FileUploadUtil;

@WebServlet("/admin/category/add")
@MultipartConfig(
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 6 * 1024 * 1024)
public class CategoryAddController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/views/admin/add-category.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");

        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("message", "Tên danh mục không được để trống.");
            request.getRequestDispatcher("/views/admin/add-category.jsp")
                    .forward(request, response);
            return;
        }

        try {
            Part iconPart = request.getPart("icon");
            String icon = FileUploadUtil.saveCategoryImage(iconPart);

            Category category = new Category(name.trim(), icon);
            categoryService.insert(category);

            response.sendRedirect(request.getContextPath() + "/admin/category/list");
        } catch (IOException exception) {
            request.setAttribute("name", name);
            request.setAttribute("message", exception.getMessage());
            request.getRequestDispatcher("/views/admin/add-category.jsp")
                    .forward(request, response);
        }
    }
}