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
import vn.hcmute.webpr330479.utils.EmailUtil;
import vn.hcmute.webpr330479.utils.FormValidationUtil;
import vn.hcmute.webpr330479.utils.OtpUtil;

@WebServlet("/verify-reset-otp")
public class VerifyResetOtpController
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final int
            OTP_EXPIRE_MINUTES = 5;

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (!hasResetRequest(session)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/forgot-password");

            return;
        }

        request.setAttribute(
                "email",
                session.getAttribute(
                        "resetEmail"));

        request.getRequestDispatcher(
                "/views/verify-reset-otp.jsp")
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

        if (!hasResetRequest(session)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/forgot-password");

            return;
        }

        String action =
                request.getParameter("action");

        if ("resend".equals(action)) {

            resendOtp(
                    request,
                    response,
                    session);

            return;
        }

        String inputOtp =
                request.getParameter("otp");

        String savedOtp =
                (String)
                        session.getAttribute(
                                "resetOtp");

        LocalDateTime expiry =
                (LocalDateTime)
                        session.getAttribute(
                                "resetOtpExpiry");

        request.setAttribute(
                "email",
                session.getAttribute(
                        "resetEmail"));

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
                            + "Vui lòng gửi lại.");

            return;
        }

        if (!savedOtp.equals(inputOtp)) {

            forwardWithMessage(
                    request,
                    response,
                    "Mã OTP không đúng.");

            return;
        }

        session.setAttribute(
                "resetVerifiedUserId",
                session.getAttribute(
                        "resetUserId"));

        session.removeAttribute(
                "resetOtp");

        session.removeAttribute(
                "resetOtpExpiry");

        response.sendRedirect(
                request.getContextPath()
                        + "/reset-password");
    }

    private void resendOtp(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session)
            throws ServletException, IOException {

        String email =
                (String)
                        session.getAttribute(
                                "resetEmail");

        String otp =
                OtpUtil.generateOtp();

        try {

            EmailUtil.sendOtp(
                    email,
                    otp,
                    "Quên mật khẩu");

            session.setAttribute(
                    "resetOtp",
                    otp);

            session.setAttribute(
                    "resetOtpExpiry",
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
                email);

        request.getRequestDispatcher(
                "/views/verify-reset-otp.jsp")
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
                "/views/verify-reset-otp.jsp")
                .forward(
                        request,
                        response);
    }

    private boolean hasResetRequest(
            HttpSession session) {

        return session != null
                && session.getAttribute(
                        "resetUserId") != null
                && session.getAttribute(
                        "resetEmail") != null;
    }
}