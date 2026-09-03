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

@WebServlet(urlPatterns = {"/profile"})
public class ProfileController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(
                    request.getContextPath() + "/session/login"
            );
            return;
        }

        User sessionUser = (User) session.getAttribute("account");

        User user = userService.getById(sessionUser.getId());

        request.setAttribute("user", user);

        request.getRequestDispatcher("/views/profile.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(
                    request.getContextPath() + "/session/login"
            );
            return;
        }

        User sessionUser = (User) session.getAttribute("account");

        User user = userService.getById(sessionUser.getId());

        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");

        user.setFullName(fullName);
        user.setPhone(phone);

        userService.update(user);

        session.setAttribute("account", user);

        response.sendRedirect(
                request.getContextPath()
                + "/profile?success=true"
        );
    }
}