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

@WebServlet("/admin/category/edit")
@MultipartConfig(
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 6 * 1024 * 1024)
public class CategoryEditController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String idParameter = request.getParameter("id");

        if (idParameter == null || idParameter.isBlank()) {
            response.sendRedirect(request.getContextPath()
                    + "/admin/category/list");
            return;
        }

        try {
            int id = Integer.parseInt(idParameter);
            Category category = categoryService.getById(id);

            if (category == null) {
                response.sendRedirect(request.getContextPath()
                        + "/admin/category/list");
                return;
            }

            request.setAttribute("category", category);
            request.getRequestDispatcher("/views/admin/edit-category.jsp")
                    .forward(request, response);

        } catch (NumberFormatException exception) {
            response.sendRedirect(request.getContextPath()
                    + "/admin/category/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idParameter = request.getParameter("id");
        String name = request.getParameter("name");

        if (idParameter == null || name == null
                || name.trim().isEmpty()) {
            request.setAttribute("message",
                    "Vui long nhap day du thong tin.");

            request.getRequestDispatcher("/views/admin/edit-category.jsp")
                    .forward(request, response);
            return;
        }

        int id = Integer.parseInt(idParameter);
        Category oldCategory = categoryService.getById(id);

        if (oldCategory == null) {
            response.sendRedirect(request.getContextPath()
                    + "/admin/category/list");
            return;
        }

        try {
            Part iconPart = request.getPart("icon");
            String oldIcon = oldCategory.getIcon();
            String newIcon = FileUploadUtil.saveCategoryImage(iconPart);

            oldCategory.setName(name.trim());

            if (newIcon != null) {
                oldCategory.setIcon(newIcon);
            }

            categoryService.update(oldCategory);

            if (newIcon != null && oldIcon != null
                    && !oldIcon.isBlank()) {
                FileUploadUtil.deleteCategoryImage(oldIcon);
            }

            response.sendRedirect(request.getContextPath()
                    + "/admin/category/list");

        } catch (IOException exception) {
            request.setAttribute("category", oldCategory);
            request.setAttribute("message", exception.getMessage());

            request.getRequestDispatcher("/views/admin/edit-category.jsp")
                    .forward(request, response);
        }
    }
}