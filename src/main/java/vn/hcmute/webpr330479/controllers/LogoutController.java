package vn.hcmute.webpr330479.controllers;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String COOKIE_USERNAME =
            "cookie_username";

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        // Xóa Session
        HttpSession session =
                request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        // Xóa Cookie Remember me
        Cookie cookie =
                new Cookie(COOKIE_USERNAME, "");

        cookie.setMaxAge(0);

        String contextPath =
                request.getContextPath();

        cookie.setPath(
                contextPath == null
                        || contextPath.isEmpty()
                        ? "/"
                        : contextPath);

        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        response.sendRedirect(
                request.getContextPath() + "/login");
    }
}