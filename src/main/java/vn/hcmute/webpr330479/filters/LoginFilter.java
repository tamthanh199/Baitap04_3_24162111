package vn.hcmute.webpr330479.filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.hcmute.webpr330479.dao.UserDao;
import vn.hcmute.webpr330479.dao.impl.UserDaoImpl;

@WebFilter("/admin/*")
public class LoginFilter implements Filter {

    private static final String COOKIE_USERNAME = "cookie_username";

    private final UserDao userDao = new UserDaoImpl();

    @Override
    public void doFilter(ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession(false);

        // Cho phep neu da dang nhap bang Session.
        if (session != null && session.getAttribute("account") != null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Cho phep neu Cookie ton tai va username co trong database.
        String username = getUsernameFromCookie(request);

        if (username != null && userDao.getByUsername(username) != null) {
            filterChain.doFilter(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/home");
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