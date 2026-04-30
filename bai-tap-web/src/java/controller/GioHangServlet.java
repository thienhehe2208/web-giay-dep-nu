package controller;

//import dao.ProductDAO;
import model.GioHangItem;
import model.Product;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
//import GioHangmodel.GioHangItem;
import model.ProductDAO;



@WebServlet("/cart")
public class GioHangServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    int productId = Integer.parseInt(request.getParameter("productId"));
String size = request.getParameter("size");
int quantity = Integer.parseInt(request.getParameter("quantity"));

HttpSession session = request.getSession();
List<GioHangItem> cart = (List<GioHangItem>) session.getAttribute("cart");

if (cart == null) {
    cart = new ArrayList<>();
}

ProductDAO dao = new ProductDAO();
Product product = dao.getById(productId);

// ❗ THÊM ĐOẠN NÀY
if (product == null) {
    response.sendRedirect("index.jsp");
    return;
}

boolean found = false;

    // Nếu đã có sản phẩm thì cộng số lượng
    for (GioHangItem item : cart) {
        if (item.getProduct().getId() == productId &&
            item.getSize().equals(size)) {

            item.setQuantity(item.getQuantity() + quantity);
            found = true;
            break;
        }
    }

    // Nếu chưa có thì thêm mới
    if (!found) {
        GioHangItem item = new GioHangItem(product, size, quantity);
        cart.add(item);
    }

    session.setAttribute("cart", cart);

    response.sendRedirect("index.jsp");
}
}