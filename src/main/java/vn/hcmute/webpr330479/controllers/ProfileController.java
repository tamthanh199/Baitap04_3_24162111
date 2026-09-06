package vn.hcmute.webpr330479.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import vn.hcmute.webpr330479.models.User;
import vn.hcmute.webpr330479.services.UserService;
import vn.hcmute.webpr330479.services.impl.UserServiceImpl;
import vn.hcmute.webpr330479.utils.FileUploadUtil;

@WebServlet(urlPatterns = {"/profile"})
@MultipartConfig(
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 6 * 1024 * 1024)
public class ProfileController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final UserService userService =
            new UserServiceImpl();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null
                || session.getAttribute("account") == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login");

            return;
        }

        User sessionUser =
                (User) session.getAttribute("account");

        User user =
                userService.getById(
                        sessionUser.getId());

        request.setAttribute(
                "user",
                user);

        request.getRequestDispatcher(
                "/views/profile.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session =
                request.getSession(false);

        if (session == null
                || session.getAttribute("account") == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login");

            return;
        }

        User sessionUser =
                (User) session.getAttribute("account");

        User user =
                userService.getById(
                        sessionUser.getId());

        String fullName =
                request.getParameter("fullName");

        String phone =
                request.getParameter("phone");

        String oldImage =
                user.getImages();

        try {

            Part imagePart =
                    request.getPart("image");

            String newImage =
                    FileUploadUtil
                            .saveProfileImage(
                                    imagePart);

            user.setFullName(fullName);

            user.setPhone(phone);

            if (newImage != null) {

                user.setImages(newImage);

                if (oldImage != null
                        && !oldImage.isBlank()) {

                    FileUploadUtil
                            .deleteProfileImage(
                                    oldImage);
                }
            }

            userService.update(user);

            // Cập nhật lại User trong Session
            session.setAttribute(
                    "account",
                    user);

            response.sendRedirect(
                    request.getContextPath()
                            + "/profile?success=true");

        } catch (IOException exception) {

            request.setAttribute(
                    "message",
                    exception.getMessage());

            request.setAttribute(
                    "user",
                    user);

            request.getRequestDispatcher(
                    "/views/profile.jsp")
                    .forward(request, response);
        }
    }
}