package vn.hcmute.webpr330479.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.hcmute.webpr330479.models.User;
import vn.hcmute.webpr330479.services.UserService;
import vn.hcmute.webpr330479.services.impl.UserServiceImpl;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String fullName = request.getParameter("fullName").trim();
        String email = request.getParameter("email").trim();
        String phone = request.getParameter("phone").trim();
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        request.setAttribute("fullName", fullName);
        request.setAttribute("email", email);
        request.setAttribute("phone", phone);
        request.setAttribute("username", username);

        if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty()
                || username.isEmpty() || password.isEmpty()) {
            forwardWithMessage(request, response, "Vui lòng nhập đầy đủ thông tin.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            forwardWithMessage(request, response, "Xác nhận mật khẩu không khớp.");
            return;
        }

        if (userService.existsByUsername(username)) {
            forwardWithMessage(request, response, "Tài khoản đã tồn tại.");
            return;
        }

        if (userService.existsByEmail(email)) {
            forwardWithMessage(request, response, "Email đã tồn tại.");
            return;
        }

        if (userService.existsByPhone(phone)) {
            forwardWithMessage(request, response, "Số điện thoại đã tồn tại.");
            return;
        }

        User user = new User(username, password, fullName, email, phone);
        userService.register(user);

        response.sendRedirect(request.getContextPath()
                + "/session/login?registered=true");
    }

    private void forwardWithMessage(HttpServletRequest request,
            HttpServletResponse response, String message)
            throws ServletException, IOException {

        request.setAttribute("message", message);
        request.getRequestDispatcher("/views/register.jsp").forward(request, response);
    }
}
