package vn.hcmute.webpr330479.controllers;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cookie/logout")
public class CookieLogoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String COOKIE_USERNAME = "cookie_username";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Cookie cookie = new Cookie(COOKIE_USERNAME, "");
        cookie.setMaxAge(0);
        cookie.setPath(request.getContextPath());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        response.sendRedirect(request.getContextPath() + "/cookie/login");
    }
}