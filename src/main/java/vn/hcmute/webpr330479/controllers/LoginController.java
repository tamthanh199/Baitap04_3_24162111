package vn.hcmute.webpr330479.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.hcmute.webpr330479.models.User;
import vn.hcmute.webpr330479.services.UserService;
import vn.hcmute.webpr330479.services.impl.UserServiceImpl;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String COOKIE_USERNAME = "cookie_username";
    private static final int COOKIE_MAX_AGE = 30 * 60;

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Đã đăng nhập bằng Session
        if (session != null
                && session.getAttribute("account") != null) {

            response.sendRedirect(
                    request.getContextPath() + "/home");

            return;
        }

        // Kiểm tra Cookie "Remember me"
        String username = getUsernameFromCookie(request);

        if (username != null) {

            User user = userService.getByUsername(username);

            if (user != null) {

                session = request.getSession(true);

                session.setAttribute("account", user);

                session.setMaxInactiveInterval(30 * 60);

                response.sendRedirect(
                        request.getContextPath() + "/home");

                return;
            }

            // Cookie tồn tại nhưng user không còn trong database
            deleteRememberCookie(request, response);
        }

        // Thông báo sau khi đăng ký thành công
        if ("true".equals(request.getParameter("registered"))) {

            request.setAttribute(
                    "successMessage",
                    "Đăng ký thành công. Vui lòng đăng nhập.");
        }

        request.getRequestDispatcher("/views/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        username = username == null
                ? ""
                : username.trim();

        password = password == null
                ? ""
                : password;

        boolean rememberMe = "on".equals(remember);

        request.setAttribute("username", username);

        if (username.isEmpty() || password.isEmpty()) {

            request.setAttribute(
                    "message",
                    "Vui lòng nhập tài khoản và mật khẩu.");

            request.getRequestDispatcher("/views/login.jsp")
                    .forward(request, response);

            return;
        }

        User user = userService.login(username, password);

        if (user == null) {

            request.setAttribute(
                    "message",
                    "Tài khoản hoặc mật khẩu không đúng.");

            request.getRequestDispatcher("/views/login.jsp")
                    .forward(request, response);

            return;
        }

        // Luôn tạo Session khi đăng nhập thành công
        HttpSession session = request.getSession(true);

        session.setAttribute("account", user);

        session.setMaxInactiveInterval(30 * 60);

        // Chỉ lưu Cookie khi chọn Remember me
        if (rememberMe) {

            saveRememberCookie(
                    request,
                    response,
                    user.getUsername());

        } else {

            deleteRememberCookie(
                    request,
                    response);
        }

        response.sendRedirect(
                request.getContextPath() + "/home");
    }

    private String getUsernameFromCookie(
            HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {

            if (COOKIE_USERNAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

    private void saveRememberCookie(
            HttpServletRequest request,
            HttpServletResponse response,
            String username) {

        Cookie cookie =
                new Cookie(COOKIE_USERNAME, username);

        cookie.setMaxAge(COOKIE_MAX_AGE);

        cookie.setPath(getCookiePath(request));

        cookie.setHttpOnly(true);

        response.addCookie(cookie);
    }

    private void deleteRememberCookie(
            HttpServletRequest request,
            HttpServletResponse response) {

        Cookie cookie =
                new Cookie(COOKIE_USERNAME, "");

        cookie.setMaxAge(0);

        cookie.setPath(getCookiePath(request));

        cookie.setHttpOnly(true);

        response.addCookie(cookie);
    }

    private String getCookiePath(
            HttpServletRequest request) {

        String contextPath =
                request.getContextPath();

        if (contextPath == null
                || contextPath.isEmpty()) {

            return "/";
        }

        return contextPath;
    }
}
