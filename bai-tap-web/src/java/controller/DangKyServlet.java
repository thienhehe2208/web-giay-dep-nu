package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import model.DBConnection;

@WebServlet(name = "DangKyServlet", urlPatterns = {"/register"})
public class DangKyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String accountName = request.getParameter("account_name");
        String username    = request.getParameter("username");
        String email       = request.getParameter("email");
        String phone       = request.getParameter("phone");
        String address     = request.getParameter("address");
        String password    = request.getParameter("password");

        // Mã hóa MD5
        String md5Pass = toMD5(password);

        PrintWriter out = response.getWriter();

        try {
            Connection conn = DBConnection.getConnection();

            // Kiểm tra username hoặc email đã tồn tại chưa
            String checkSql = "SELECT id FROM users WHERE username = ? OR email = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setString(1, username);
            checkPs.setString(2, email);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                out.println("<h3>Tên đăng nhập hoặc email đã tồn tại!</h3>");
                out.println("<a href='DangKy.jsp'>Quay lại</a>");
                return;
            }

            // Thêm user mới vào database
            String sql = "INSERT INTO users (account_name, username, email, phone, address, password) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, accountName);
            ps.setString(2, username);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, address);
            ps.setString(6, md5Pass);
            ps.executeUpdate();

            out.println("<h3>Đăng ký thành công!</h3>");
            out.println("<a href='DangNhap.jsp'>Đăng nhập ngay</a>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Lỗi hệ thống, vui lòng thử lại!</h3>");
        }
    }

    private String toMD5(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return input;
        }
    }
}
