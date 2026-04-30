package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import model.DBConnection;

@WebServlet("/LienHeServlet")
public class LienHeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String name    = request.getParameter("name");
        String email   = request.getParameter("email");
        String phone   = request.getParameter("phone");
        String message = request.getParameter("message");

        PrintWriter out = response.getWriter();

        if (name == null || name.isEmpty() || email == null || email.isEmpty()
                || message == null || message.isEmpty()) {
            out.println("Không được để trống!");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            // Tên bảng đúng: contacts, đủ cột: name, email, phone, message
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO contacts (name, email, phone, message) VALUES (?,?,?,?)");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, message);
            ps.executeUpdate();

            out.println("<h3>Gửi liên hệ thành công!</h3>");
            out.println("<a href='index.jsp'>Quay lại trang chủ</a>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi hệ thống, vui lòng thử lại!");
        }
    }
}
