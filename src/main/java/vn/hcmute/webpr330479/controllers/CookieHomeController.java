package vn.hcmute.webpr330479.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cookie/home")
public class CookieHomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String COOKIE_USERNAME = "cookie_username";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = getUsernameFromCookie(request);

        if (username == null) {
            response.sendRedirect(request.getContextPath() + "/cookie/login");
            return;
        }

        request.setAttribute("username", username);
        request.getRequestDispatcher("/views/cookie-home.jsp").forward(request, response);
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