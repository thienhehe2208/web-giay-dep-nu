package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import model.Product;
import model.ProductDAO;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action     = request.getParameter("action");
        String categoryId = request.getParameter("categoryId");
        String keyword    = request.getParameter("keyword");

        ProductDAO dao = new ProductDAO();

        // 1. XEM CHI TIẾT SẢN PHẨM
        if ("detail".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Product product = dao.getById(id);
                if (product == null) {
                    response.sendRedirect("index.jsp");
                    return;
                }
                request.setAttribute("product", product);
                request.getRequestDispatcher("Product.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("index.jsp");
            }

        // 2. LỌC THEO DANH MỤC
        } else if (categoryId != null && !categoryId.isEmpty()) {
            try {
                int catId = Integer.parseInt(categoryId);
                List<Product> list = dao.getByCategory(catId);
                request.setAttribute("list", list);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("index.jsp");
            }

        // 3. TÌM KIẾM
        } else if (keyword != null && !keyword.trim().isEmpty()) {
            List<Product> list = dao.search(keyword);
            request.setAttribute("list", list);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        // 4. TẤT CẢ SẢN PHẨM
        } else {
            List<Product> list = dao.getAllProducts();
            request.setAttribute("list", list);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
