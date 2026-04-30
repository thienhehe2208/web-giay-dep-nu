package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import model.DBConnection;
import model.User;

@WebServlet(name = "DangNhapServlet", urlPatterns = {"/login"})
public class DangNhapServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Mã hóa password bằng MD5 (giống lúc đăng ký)
        String md5Pass = toMD5(password);

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, md5Pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Đăng nhập thành công
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));

                HttpSession session = request.getSession();
                session.setAttribute("User", user);
                response.sendRedirect("index.jsp");
            } else {
                // Sai tài khoản hoặc mật khẩu
                PrintWriter out = response.getWriter();
                out.println("<h3>Sai tài khoản hoặc mật khẩu!</h3>");
                out.println("<a href='DangNhap.jsp'>Quay lại</a>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm mã hóa MD5
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
