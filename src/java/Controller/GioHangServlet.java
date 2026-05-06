package controller;

import dao.CartDAO;
import dao.ProductDAO;
import model.Cart;
import model.CartItem;
import model.Product;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Servlet quản lý giỏ hàng
 *
 *  GET  /cart              -> xem giỏ hàng
 *  GET  /cart?action=add&id=5&size=38&qty=1     -> thêm sản phẩm
 *  GET  /cart?action=remove&itemId=3            -> xoá item
 *  POST /cart?action=update                     -> cập nhật số lượng
 */
@WebServlet(name = "GioHangServlet", urlPatterns = {"/cart"})
public class GioHangServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            handleAdd(request, response);
        } else if ("remove".equals(action)) {
            handleRemove(request, response);
        } else {
            // Xem giỏ hàng
            showCart(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("update".equals(action)) {
            handleUpdate(request, response);
        } else {
            showCart(request, response);
        }
    }

    // ---- Xem giỏ hàng ----
    private void showCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int cartId = getCartId(request, response);
        List<CartItem> items = CartDAO.getItems(cartId);

        long total = items.stream().mapToLong(CartItem::getLineTotal).sum();

        request.setAttribute("cartItems", items);
        request.setAttribute("cartTotal", total);
        request.getRequestDispatcher("/GioHang.jsp").forward(request, response);
    }

    // ---- Thêm sản phẩm ----
    private void handleAdd(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String idParam   = request.getParameter("id");
        String sizeParam = request.getParameter("size");
        String qtyParam  = request.getParameter("qty");

        if (idParam == null) { response.sendRedirect("index.jsp"); return; }

        int productId = Integer.parseInt(idParam);
        Integer size  = (sizeParam != null && !sizeParam.isEmpty()) ? Integer.parseInt(sizeParam) : null;
        int qty       = (qtyParam != null && !qtyParam.isEmpty()) ? Integer.parseInt(qtyParam) : 1;

        // Kiểm tra sản phẩm tồn tại
        Product p = ProductDAO.findById(productId);
        if (p == null) { response.sendRedirect("index.jsp"); return; }

        int cartId = getCartId(request, response);
        CartDAO.addItem(cartId, productId, size, qty);

        // Cập nhật cartCount trong session
        HttpSession session = request.getSession();
        session.setAttribute("cartCount", CartDAO.countItems(cartId));

        // Redirect về trang trước
        String referer = request.getHeader("Referer");
        response.sendRedirect(referer != null ? referer : "index.jsp");
    }

    // ---- Xoá item ----
    private void handleRemove(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String itemIdParam = request.getParameter("itemId");
        if (itemIdParam != null) {
            CartDAO.removeItem(Integer.parseInt(itemIdParam));
            int cartId = getCartId(request, response);
            request.getSession().setAttribute("cartCount", CartDAO.countItems(cartId));
        }
        response.sendRedirect(request.getContextPath() + "/cart");
    }

    // ---- Cập nhật số lượng ----
    private void handleUpdate(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String[] itemIds = request.getParameterValues("itemId");
        String[] quantities = request.getParameterValues("quantity");

        if (itemIds != null && quantities != null) {
            for (int i = 0; i < itemIds.length; i++) {
                int itemId = Integer.parseInt(itemIds[i]);
                int qty    = Integer.parseInt(quantities[i]);
                CartDAO.updateQuantity(itemId, qty);
            }
        }

        int cartId = getCartId(request, response);
        request.getSession().setAttribute("cartCount", CartDAO.countItems(cartId));
        response.sendRedirect(request.getContextPath() + "/cart");
    }

    /**
     * Lấy cart_id từ session.
     * Nếu user đã đăng nhập dùng cart DB, nếu chưa tạo cart tạm (guest cart).
     */
    private int getCartId(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Integer cartId = (Integer) session.getAttribute("cartId");

        if (cartId == null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                Cart cart = CartDAO.getOrCreateCart(user.getId());
                cartId = cart.getId();
            } else {
                // Khách vãng lai: tạo cart với user_id = NULL
                // (cần thêm cột user_id nullable trong carts, đã có sẵn trong SQL)
                try {
                    java.sql.Connection conn = model.DBContext.getConnection();
                    java.sql.PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO carts (user_id) VALUES (NULL)",
                        java.sql.Statement.RETURN_GENERATED_KEYS);
                    ps.executeUpdate();
                    java.sql.ResultSet keys = ps.getGeneratedKeys();
                    if (keys.next()) cartId = keys.getInt(1);
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    cartId = 0;
                }
            }
            session.setAttribute("cartId", cartId);
        }
        return cartId;
    }
}
