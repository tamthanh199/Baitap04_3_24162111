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
import vn.hcmute.webpr330479.utils.FormValidationUtil;
import vn.hcmute.webpr330479.utils.OtpUtil;

@WebServlet("/verify-otp")
public class VerifyOtpController
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

        HttpSession session =
                request.getSession(false);

        User pendingUser =
                getPendingUser(session);

        if (pendingUser == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/register");

            return;
        }

        request.setAttribute(
                "email",
                pendingUser.getEmail());

        request.getRequestDispatcher(
                "/views/verify-otp.jsp")
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

        User pendingUser =
                getPendingUser(session);

        if (pendingUser == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/register");

            return;
        }

        String action =
                request.getParameter("action");

        if ("resend".equals(action)) {

            resendOtp(
                    request,
                    response,
                    session,
                    pendingUser);

            return;
        }

        String inputOtp =
                request.getParameter("otp");

        String savedOtp =
                (String)
                        session.getAttribute(
                                "registrationOtp");

        LocalDateTime expiry =
                (LocalDateTime)
                        session.getAttribute(
                                "registrationOtpExpiry");

        request.setAttribute(
                "email",
                pendingUser.getEmail());

        String validationMessage =
                FormValidationUtil
                        .validateOtp(inputOtp);

        if (validationMessage != null) {

            forwardWithMessage(
                    request,
                    response,
                    validationMessage);

            return;
        }

        inputOtp = inputOtp.trim();

        if (savedOtp == null
                || expiry == null
                || LocalDateTime
                        .now()
                        .isAfter(expiry)) {

            forwardWithMessage(
                    request,
                    response,
                    "Mã OTP đã hết hạn. "
                            + "Vui lòng gửi lại OTP.");

            return;
        }

        if (!savedOtp.equals(inputOtp)) {

            forwardWithMessage(
                    request,
                    response,
                    "Mã OTP không đúng.");

            return;
        }

        if (!userService.register(
                pendingUser)) {

            clearRegistrationSession(
                    session);

            request.setAttribute(
                    "message",
                    "Thông tin tài khoản đã tồn tại. "
                            + "Vui lòng đăng ký lại.");

            request.getRequestDispatcher(
                    "/views/register.jsp")
                    .forward(
                            request,
                            response);

            return;
        }

        clearRegistrationSession(
                session);

        response.sendRedirect(
                request.getContextPath()
                        + "/login?registered=true");
    }

    private void resendOtp(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            User pendingUser)
            throws ServletException, IOException {

        String otp =
                OtpUtil.generateOtp();

        try {

            EmailUtil.sendOtp(
                    pendingUser.getEmail(),
                    otp,
                    "Kích hoạt tài khoản");

            session.setAttribute(
                    "registrationOtp",
                    otp);

            session.setAttribute(
                    "registrationOtpExpiry",
                    LocalDateTime
                            .now()
                            .plusMinutes(
                                    OTP_EXPIRE_MINUTES));

            request.setAttribute(
                    "successMessage",
                    "Đã gửi lại OTP.");

        } catch (MessagingException exception) {

            request.setAttribute(
                    "message",
                    "Không gửi lại được OTP: "
                            + exception.getMessage());
        }

        request.setAttribute(
                "email",
                pendingUser.getEmail());

        request.getRequestDispatcher(
                "/views/verify-otp.jsp")
                .forward(
                        request,
                        response);
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
                "/views/verify-otp.jsp")
                .forward(
                        request,
                        response);
    }

    private User getPendingUser(
            HttpSession session) {

        if (session == null) {
            return null;
        }

        return (User)
                session.getAttribute(
                        "pendingUser");
    }

    private void clearRegistrationSession(
            HttpSession session) {

        session.removeAttribute(
                "pendingUser");

        session.removeAttribute(
                "registrationOtp");

        session.removeAttribute(
                "registrationOtpExpiry");
    }
}