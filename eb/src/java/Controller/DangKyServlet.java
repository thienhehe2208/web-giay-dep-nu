package controller;

import dao.UserDAO;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Servlet xử lý đăng ký tài khoản mới
 * URL: /register  (POST)
 */
@WebServlet(name = "DangKyServlet", urlPatterns = {"/register"})
public class DangKyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("DangKy.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String accountName = request.getParameter("account_name");
        String username    = request.getParameter("username");
        String email       = request.getParameter("email");
        String phone       = request.getParameter("phone");
        String address     = request.getParameter("address");
        String password    = request.getParameter("password");
        String confirm     = request.getParameter("confirm_password");

        // Validate
        if (isEmpty(accountName) || isEmpty(username) || isEmpty(email)
         || isEmpty(password) || isEmpty(confirm)) {
            request.setAttribute("error", "Vui lòng điền đầy đủ các trường bắt buộc.");
            request.getRequestDispatcher("DangKy.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirm)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp.");
            request.getRequestDispatcher("DangKy.jsp").forward(request, response);
            return;
        }

        if (UserDAO.isUsernameExists(username.trim())) {
            request.setAttribute("error", "Tên đăng nhập đã được sử dụng.");
            request.getRequestDispatcher("DangKy.jsp").forward(request, response);
            return;
        }

        if (UserDAO.isEmailExists(email.trim())) {
            request.setAttribute("error", "Email đã được đăng ký.");
            request.getRequestDispatcher("DangKy.jsp").forward(request, response);
            return;
        }

        User newUser = new User();
        newUser.setAccountName(accountName.trim());
        newUser.setUsername(username.trim());
        newUser.setEmail(email.trim());
        newUser.setPhone(phone == null ? "" : phone.trim());
        newUser.setAddress(address == null ? "" : address.trim());
        newUser.setPassword(password);   // Lưu ý: dự án học – thực tế cần hash (BCrypt)

        boolean ok = UserDAO.register(newUser);

        if (ok) {
            request.setAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
            request.getRequestDispatcher("DangNhap.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Đăng ký thất bại, vui lòng thử lại.");
            request.getRequestDispatcher("DangKy.jsp").forward(request, response);
        }
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
