package vn.hcmute.webpr330479.controllers;

import java.io.IOException;
import java.time.LocalDateTime;

import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.hcmute.webpr330479.models.User;
import vn.hcmute.webpr330479.services.UserService;
import vn.hcmute.webpr330479.services.impl.UserServiceImpl;
import vn.hcmute.webpr330479.utils.EmailUtil;
import vn.hcmute.webpr330479.utils.OtpUtil;

@WebServlet("/forgot-password")
public class ForgotPasswordController
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final int
            OTP_EXPIRE_MINUTES = 5;

    private final UserService userService =
            new UserServiceImpl();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
                "/views/forgot-password.jsp")
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

        String email =
                request.getParameter(
                        "email");

        email =
                email == null
                        ? ""
                        : email.trim();

        request.setAttribute(
                "email",
                email);

        if (email.isEmpty()) {

            request.setAttribute(
                    "message",
                    "Vui lòng nhập email.");

            request.getRequestDispatcher(
                    "/views/forgot-password.jsp")
                    .forward(
                            request,
                            response);

            return;
        }

        User user =
                userService
                        .getByEmail(
                                email);

        if (user == null) {

            request.setAttribute(
                    "message",
                    "Không tìm thấy tài khoản "
                    + "với email này.");

            request.getRequestDispatcher(
                    "/views/forgot-password.jsp")
                    .forward(
                            request,
                            response);

            return;
        }

        String otp =
                OtpUtil.generateOtp();

        try {

            EmailUtil.sendOtp(
                    email,
                    otp,
                    "Quên mật khẩu");

        } catch (MessagingException exception) {

            request.setAttribute(
                    "message",
                    "Không gửi được OTP: "
                            + exception.getMessage());

            request.getRequestDispatcher(
                    "/views/forgot-password.jsp")
                    .forward(
                            request,
                            response);

            return;
        }

        HttpSession session =
                request.getSession(true);

        session.setAttribute(
                "resetUserId",
                user.getId());

        session.setAttribute(
                "resetEmail",
                user.getEmail());

        session.setAttribute(
                "resetOtp",
                otp);

        session.setAttribute(
                "resetOtpExpiry",
                LocalDateTime
                        .now()
                        .plusMinutes(
                                OTP_EXPIRE_MINUTES));

        session.removeAttribute(
                "resetVerifiedUserId");

        response.sendRedirect(
                request.getContextPath()
                        + "/verify-reset-otp");
    }
}