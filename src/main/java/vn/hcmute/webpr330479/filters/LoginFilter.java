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
import vn.hcmute.webpr330479.models.User;
import vn.hcmute.webpr330479.services.UserService;
import vn.hcmute.webpr330479.services.impl.UserServiceImpl;

@WebFilter(urlPatterns = {
        "/admin/*",
        "/profile"
})
public class LoginFilter implements Filter {

    private static final String COOKIE_USERNAME =
            "cookie_username";

    private final UserService userService =
            new UserServiceImpl();

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request =
                (HttpServletRequest) servletRequest;

        HttpServletResponse response =
                (HttpServletResponse) servletResponse;

        HttpSession session =
                request.getSession(false);

        // Đã đăng nhập bằng Session
        if (session != null
                && session.getAttribute("account") != null) {

            filterChain.doFilter(
                    request,
                    response);

            return;
        }

        // Session hết nhưng còn Remember Cookie
        String username =
                getUsernameFromCookie(request);

        if (username != null) {

            User user =
                    userService.getByUsername(username);

            if (user != null) {

                HttpSession newSession =
                        request.getSession(true);

                newSession.setAttribute(
                        "account",
                        user);

                newSession.setMaxInactiveInterval(
                        30 * 60);

                filterChain.doFilter(
                        request,
                        response);

                return;
            }
        }

        response.sendRedirect(
                request.getContextPath() + "/login");
    }

    private String getUsernameFromCookie(
            HttpServletRequest request) {

        Cookie[] cookies =
                request.getCookies();

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {

            if (COOKIE_USERNAME.equals(
                    cookie.getName())) {

                return cookie.getValue();
            }
        }

        return null;
    }
}