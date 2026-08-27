package vn.hcmute.webpr330479.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.hcmute.webpr330479.models.User;
import vn.hcmute.webpr330479.services.UserService;
import vn.hcmute.webpr330479.services.impl.UserServiceImpl;

@WebServlet("/session/login")
public class SessionLoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("account") != null) {
            response.sendRedirect(request.getContextPath() + "/session/home");
            return;
        }

        if ("true".equals(request.getParameter("registered"))) {
            request.setAttribute("successMessage",
                    "Đăng ký thành công. Vui lòng đăng nhập.");
        }

        request.getRequestDispatcher("/views/session-login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username").trim();
        String password = request.getParameter("password");

        if (username.isEmpty() || password.isEmpty()) {
            request.setAttribute("message", "Vui lòng nhập tài khoản và mật khẩu.");
            request.getRequestDispatcher("/views/session-login.jsp").forward(request, response);
            return;
        }

        User user = userService.login(username, password);

        if (user == null) {
            request.setAttribute("message", "Tài khoản hoặc mật khẩu không đúng.");
            request.getRequestDispatcher("/views/session-login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("account", user);
        session.setMaxInactiveInterval(30 * 60);

        response.sendRedirect(request.getContextPath() + "/session/home");
    }
}
