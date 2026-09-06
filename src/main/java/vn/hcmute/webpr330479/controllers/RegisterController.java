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

@WebServlet("/register")
public class RegisterController
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
                "/views/register.jsp")
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

        response.setCharacterEncoding(
                "UTF-8");

        String fullName =
                trim(
                        request.getParameter(
                                "fullName"));

        String email =
                trim(
                        request.getParameter(
                                "email"));

        String phone =
                trim(
                        request.getParameter(
                                "phone"));

        String username =
                trim(
                        request.getParameter(
                                "username"));

        String password =
                request.getParameter(
                        "password");

        String confirmPassword =
                request.getParameter(
                        "confirmPassword");

        request.setAttribute(
                "fullName",
                fullName);

        request.setAttribute(
                "email",
                email);

        request.setAttribute(
                "phone",
                phone);

        request.setAttribute(
                "username",
                username);

        if (fullName.isEmpty()
                || email.isEmpty()
                || phone.isEmpty()
                || username.isEmpty()
                || password == null
                || password.isEmpty()) {

            forwardWithMessage(
                    request,
                    response,
                    "Vui lòng nhập đầy đủ thông tin.");

            return;
        }

        if (!password.equals(
                confirmPassword)) {

            forwardWithMessage(
                    request,
                    response,
                    "Xác nhận mật khẩu không khớp.");

            return;
        }

        if (userService
                .existsByUsername(
                        username)) {

            forwardWithMessage(
                    request,
                    response,
                    "Tài khoản đã tồn tại.");

            return;
        }

        if (userService
                .existsByEmail(
                        email)) {

            forwardWithMessage(
                    request,
                    response,
                    "Email đã tồn tại.");

            return;
        }

        if (userService
                .existsByPhone(
                        phone)) {

            forwardWithMessage(
                    request,
                    response,
                    "Số điện thoại đã tồn tại.");

            return;
        }

        User pendingUser =
                new User(
                        username,
                        password,
                        fullName,
                        email,
                        phone);

        String otp =
                OtpUtil.generateOtp();

        try {

            EmailUtil.sendOtp(
                    email,
                    otp,
                    "Kích hoạt tài khoản");

        } catch (MessagingException exception) {

            forwardWithMessage(
                    request,
                    response,
                    "Không gửi được OTP: "
                            + exception.getMessage());

            return;
        }

        HttpSession session =
                request.getSession(true);

        session.setAttribute(
                "pendingUser",
                pendingUser);

        session.setAttribute(
                "registrationOtp",
                otp);

        session.setAttribute(
                "registrationOtpExpiry",
                LocalDateTime
                        .now()
                        .plusMinutes(
                                OTP_EXPIRE_MINUTES));

        response.sendRedirect(
                request.getContextPath()
                        + "/verify-otp");
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
            String message)
            throws ServletException, IOException {

        request.setAttribute(
                "message",
                message);

        request.getRequestDispatcher(
                "/views/register.jsp")
                .forward(
                        request,
                        response);
    }
}