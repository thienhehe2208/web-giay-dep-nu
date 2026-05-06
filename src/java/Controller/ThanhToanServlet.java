package controller;

import dao.CartDAO;
import model.CartItem;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Servlet xử lý thanh toán
 *
 *  GET  /checkout          -> hiển thị trang thanh toán
 *  POST /checkout          -> xác nhận đặt hàng
 */
@WebServlet(name = "ThanhToanServlet", urlPatterns = {"/checkout"})
public class ThanhToanServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Phải đăng nhập mới được vào trang thanh toán
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int cartId = getCartId(request);
        List<CartItem> items = CartDAO.getItems(cartId);

        if (items.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        long total = items.stream().mapToLong(CartItem::getLineTotal).sum();

        request.setAttribute("cartItems", items);
        request.setAttribute("cartTotal", total);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/ThanhToan.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String fullName   = request.getParameter("full_name");
        String phone      = request.getParameter("phone");
        String address    = request.getParameter("address");
        String payMethod  = request.getParameter("pay_method");

        // Validate
        if (isBlank(fullName) || isBlank(phone) || isBlank(address)) {
            int cartId = getCartId(request);
            List<CartItem> items = CartDAO.getItems(cartId);
            long total = items.stream().mapToLong(CartItem::getLineTotal).sum();
            request.setAttribute("cartItems", items);
            request.setAttribute("cartTotal", total);
            request.setAttribute("user", user);
            request.setAttribute("error", "Vui lòng điền đầy đủ thông tin giao hàng.");
            request.getRequestDispatcher("/ThanhToan.jsp").forward(request, response);
            return;
        }

        // TODO: Lưu order vào CSDL khi có bảng orders
        // Ở đây ta xử lý đơn giản: xoá giỏ hàng sau khi đặt thành công
        int cartId = getCartId(request);
        CartDAO.clearCart(cartId);

        // Reset cartCount
        request.getSession().setAttribute("cartCount", 0);

        // Truyền thông tin ra trang xác nhận
        request.setAttribute("customerName", fullName);
        request.setAttribute("customerPhone", phone);
        request.setAttribute("customerAddress", address);
        request.setAttribute("payMethod", payMethod);
        request.getRequestDispatcher("/DatHangThanhCong.jsp").forward(request, response);
    }

    private int getCartId(HttpServletRequest request) {
        Integer cartId = (Integer) request.getSession().getAttribute("cartId");
        return (cartId != null) ? cartId : 0;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
