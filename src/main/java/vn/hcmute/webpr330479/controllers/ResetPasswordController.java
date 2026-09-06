package vn.hcmute.webpr330479.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.hcmute.webpr330479.models.User;
import vn.hcmute.webpr330479.services.UserService;
import vn.hcmute.webpr330479.services.impl.UserServiceImpl;
import vn.hcmute.webpr330479.utils.FormValidationUtil;

@WebServlet("/reset-password")
public class ResetPasswordController
        extends HttpServlet {

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
                || session.getAttribute(
                        "resetVerifiedUserId") == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/forgot-password");

            return;
        }

        request.getRequestDispatcher(
                "/views/reset-password.jsp")
                .forward(
                        request,
                        response);
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
                || session.getAttribute(
                        "resetVerifiedUserId") == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/forgot-password");

            return;
        }

        String password =
                request.getParameter("password");

        String confirmPassword =
                request.getParameter(
                        "confirmPassword");

        String validationMessage =
                FormValidationUtil
                        .validateResetPassword(
                                password,
                                confirmPassword);

        if (validationMessage != null) {

            request.setAttribute(
                    "message",
                    validationMessage);

            request.getRequestDispatcher(
                    "/views/reset-password.jsp")
                    .forward(
                            request,
                            response);

            return;
        }

        int userId =
                (Integer)
                        session.getAttribute(
                                "resetVerifiedUserId");

        User user =
                userService.getById(userId);

        if (user == null) {

            clearResetSession(session);

            response.sendRedirect(
                    request.getContextPath()
                            + "/forgot-password");

            return;
        }

        user.setPassword(password);

        userService.update(user);

        clearResetSession(session);

        response.sendRedirect(
                request.getContextPath()
                        + "/login?reset=true");
    }

    private void clearResetSession(
            HttpSession session) {

        session.removeAttribute(
                "resetUserId");

        session.removeAttribute(
                "resetEmail");

        session.removeAttribute(
                "resetOtp");

        session.removeAttribute(
                "resetOtpExpiry");

        session.removeAttribute(
                "resetVerifiedUserId");
    }
}