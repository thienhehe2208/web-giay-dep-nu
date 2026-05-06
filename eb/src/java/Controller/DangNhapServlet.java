package controller;

import dao.UserDAO;
import dao.CartDAO;
import model.Cart;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Servlet xử lý đăng nhập
 * URL: /login  (POST)
 */
@WebServlet(name = "DangNhapServlet", urlPatterns = {"/login"})
public class DangNhapServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // GET -> hiển thị form đăng nhập
        request.getRequestDispatcher("DangNhap.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate đầu vào
        if (username == null || username.trim().isEmpty()
         || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
            request.getRequestDispatcher("jsp/DangNhap.jsp").forward(request, response);
            return;
        }

        User user = UserDAO.login(username.trim(), password.trim());

        if (user == null) {
            request.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng.");
            request.getRequestDispatcher("jsp/DangNhap.jsp").forward(request, response);
            return;
        }

        // Đăng nhập thành công -> lưu session
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        // Lấy / tạo cart trong DB cho user
        Cart cart = CartDAO.getOrCreateCart(user.getId());
        session.setAttribute("cartId", cart.getId());
        session.setAttribute("cartCount", CartDAO.countItems(cart.getId()));

        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}
