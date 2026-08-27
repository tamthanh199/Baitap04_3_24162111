package vn.hcmute.webpr330479.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.hcmute.webpr330479.models.User;
import vn.hcmute.webpr330479.services.UserService;
import vn.hcmute.webpr330479.services.impl.UserServiceImpl;

@WebServlet("/cookie/login")
public class CookieLoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String COOKIE_USERNAME = "cookie_username";
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (getUsernameFromCookie(request) != null) {
            response.sendRedirect(request.getContextPath() + "/cookie/home");
            return;
        }

        request.getRequestDispatcher("/views/cookie-login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username").trim();
        String password = request.getParameter("password");

        User user = userService.login(username, password);

        if (user == null) {
            request.setAttribute("message", "Tài khoản hoặc mật khẩu không đúng.");
            request.getRequestDispatcher("/views/cookie-login.jsp").forward(request, response);
            return;
        }

        Cookie cookie = new Cookie(COOKIE_USERNAME, user.getUsername());
        cookie.setMaxAge(30 * 60);
        cookie.setPath(request.getContextPath());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        response.sendRedirect(request.getContextPath() + "/cookie/home");
    }

    private String getUsernameFromCookie(HttpServletRequest request) {
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
}