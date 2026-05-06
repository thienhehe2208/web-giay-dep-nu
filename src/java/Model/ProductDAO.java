package dao;

import model.DBContext;
import model.Product;

import java.sql.*;
import java.util.*;

/**
 * DAO cho bảng `products`
 * JOIN với bảng categories để lấy tên danh mục
 */
public class ProductDAO {

    private static final String BASE_SQL =
        "SELECT p.id, p.category_id, p.name, p.price, p.discount_price, " +
        "       p.image, p.description, p.is_new, p.is_bestseller, c.name AS category_name " +
        "FROM products p " +
        "LEFT JOIN categories c ON p.category_id = c.id ";

    /** Lấy tất cả sản phẩm */
    public static List<Product> getAll() {
        return query(BASE_SQL + "ORDER BY p.id", null);
    }

    /** Lấy sản phẩm mới (is_new = 1) */
    public static List<Product> getNewProducts() {
        return query(BASE_SQL + "WHERE p.is_new = 1 ORDER BY p.id", null);
    }

    /** Lấy sản phẩm bán chạy (is_bestseller = 1) */
    public static List<Product> getBestsellers() {
        return query(BASE_SQL + "WHERE p.is_bestseller = 1 ORDER BY p.id", null);
    }

    /** Lấy sản phẩm giảm giá (discount_price IS NOT NULL) */
    public static List<Product> getSaleProducts() {
        return query(BASE_SQL + "WHERE p.discount_price IS NOT NULL ORDER BY p.id", null);
    }

    /** Lấy sản phẩm theo danh mục */
    public static List<Product> getByCategory(int categoryId) {
        String sql = BASE_SQL + "WHERE p.category_id = ? ORDER BY p.id";
        return query(sql, ps -> ps.setInt(1, categoryId));
    }

    /** Tìm sản phẩm theo id */
    public static Product findById(int id) {
        String sql = BASE_SQL + "WHERE p.id = ?";
        List<Product> result = query(sql, ps -> ps.setInt(1, id));
        return result.isEmpty() ? null : result.get(0);
    }

    /** Tìm kiếm sản phẩm theo từ khóa */
    public static List<Product> search(String keyword) {
        String sql = BASE_SQL + "WHERE p.name LIKE ? ORDER BY p.id";
        return query(sql, ps -> ps.setString(1, "%" + keyword + "%"));
    }

    // ---- helper ----

    @FunctionalInterface
    interface ParamSetter {
        void set(PreparedStatement ps) throws SQLException;
    }

    private static List<Product> query(String sql, ParamSetter setter) {
        List<Product> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (setter != null) setter.set(ps);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private static Product mapRow(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setCategoryId(rs.getInt("category_id"));
        p.setName(rs.getString("name").trim());
        p.setPrice(rs.getInt("price"));
        int dp = rs.getInt("discount_price");
        p.setDiscountPrice(rs.wasNull() ? null : dp);
        p.setImage(rs.getString("image").trim());
        p.setDescription(rs.getString("description"));
        p.setNew(rs.getBoolean("is_new"));
        p.setBestseller(rs.getBoolean("is_bestseller"));
        p.setCategoryName(rs.getString("category_name"));
        return p;
    }
}
