package vn.hcmute.webpr330479.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.hcmute.webpr330479.connection.DBConnection;

@WebServlet("/test-db")
public class TestDatabaseController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");

        try (Connection connection = new DBConnection().getConnection();
                PrintWriter out = response.getWriter()) {

            out.println("<h1>Ket noi SQL Server thanh cong!</h1>");
            out.println("<p>Database: " + connection.getCatalog() + "</p>");
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            try (PrintWriter out = response.getWriter()) {
                out.println("<h1>Ket noi SQL Server that bai.</h1>");
                out.println("<pre>");
                exception.printStackTrace(out);
                out.println("</pre>");
            }
        }
    }
}