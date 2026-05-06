package controller;

import dao.CategoryDAO;
import dao.ProductDAO;
import model.Category;
import model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Servlet hiển thị danh sách sản phẩm
 *
 *  /products              -> tất cả sản phẩm
 *  /products?category=3   -> theo danh mục
 *  /products?search=abc   -> tìm kiếm
 *  /products/new          -> hàng mới
 *  /products/bestseller   -> bán chạy
 *  /products/sale         -> giảm giá
 *  /product?id=5          -> chi tiết sản phẩm
 */
@WebServlet(name = "ProductServlet", urlPatterns = {"/products", "/products/*", "/product"})
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();   // null, /new, /bestseller, /sale
        String categoryParam = request.getParameter("category");
        String searchParam   = request.getParameter("search");
        String idParam       = request.getParameter("id");

        List<Product> products;
        String pageTitle;

        // Chi tiết sản phẩm
        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);
            Product p = ProductDAO.findById(id);
            request.setAttribute("product", p);
            request.setAttribute("categories", CategoryDAO.getAll());
            request.getRequestDispatcher("/ProductDetail.jsp").forward(request, response);
            return;
        }

        // Lọc theo loại
        if ("/new".equals(pathInfo)) {
            products = ProductDAO.getNewProducts();
            pageTitle = "Hàng Mới";
        } else if ("/bestseller".equals(pathInfo)) {
            products = ProductDAO.getBestsellers();
            pageTitle = "Bán Chạy";
        } else if ("/sale".equals(pathInfo)) {
            products = ProductDAO.getSaleProducts();
            pageTitle = "Giảm Giá";
        } else if (categoryParam != null && !categoryParam.isEmpty()) {
            int catId = Integer.parseInt(categoryParam);
            products = ProductDAO.getByCategory(catId);
            Category cat = CategoryDAO.findById(catId);
            pageTitle = (cat != null) ? cat.getName() : "Sản phẩm";
        } else if (searchParam != null && !searchParam.isEmpty()) {
            products = ProductDAO.search(searchParam);
            pageTitle = "Kết quả tìm kiếm: " + searchParam;
        } else {
            products = ProductDAO.getAll();
            pageTitle = "Tất Cả Sản Phẩm";
        }

        request.setAttribute("products", products);
        request.setAttribute("pageTitle", pageTitle);
        request.setAttribute("categories", CategoryDAO.getAll());
        request.getRequestDispatcher("/Product.jsp").forward(request, response);
    }
}
