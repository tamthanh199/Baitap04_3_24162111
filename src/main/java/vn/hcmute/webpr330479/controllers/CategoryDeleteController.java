package vn.hcmute.webpr330479.controllers;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.hcmute.webpr330479.models.Category;
import vn.hcmute.webpr330479.services.CategoryService;
import vn.hcmute.webpr330479.services.impl.CategoryServiceImpl;
import vn.hcmute.webpr330479.utils.FileUploadUtil;

@WebServlet("/admin/category/delete")
public class CategoryDeleteController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        String idParameter = request.getParameter("id");

        if (idParameter == null || idParameter.isBlank()) {
            response.sendRedirect(request.getContextPath()
                    + "/admin/category/list");
            return;
        }

        try {
            int id = Integer.parseInt(idParameter);
            Category category = categoryService.getById(id);

            if (category != null) {
                String icon = category.getIcon();

                categoryService.delete(id);

                if (icon != null && !icon.isBlank()) {
                    try {
                        FileUploadUtil.deleteCategoryImage(icon);
                    } catch (IOException exception) {
                        System.err.println(
                                "Khong the xoa file anh: " + icon);
                    }
                }
            }
        } catch (NumberFormatException exception) {
            System.err.println("Category ID khong hop le.");
        }

        response.sendRedirect(request.getContextPath()
                + "/admin/category/list");
    }
}
