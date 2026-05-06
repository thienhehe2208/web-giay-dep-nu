package dao;

import model.Cart;
import model.CartItem;
import model.DBContext;
import model.Product;

import java.sql.*;
import java.util.*;

/**
 * DAO cho bảng `carts` và `cart_items`
 * Mỗi user đã đăng nhập có một cart trong DB;
 * khách vãng lai dùng session (xử lý ở Servlet).
 */
public class CartDAO {

    // ===================== CART =====================

    /**
     * Lấy cart hiện tại của user, tạo mới nếu chưa có
     */
    public static Cart getOrCreateCart(int userId) {
        String sel = "SELECT id, user_id, created_at FROM carts WHERE user_id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sel)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Cart(rs.getInt("id"), rs.getInt("user_id"), rs.getTimestamp("created_at"));
                }
            }
            // Chưa có => tạo mới
            String ins = "INSERT INTO carts (user_id) VALUES (?)";
            try (PreparedStatement ps2 = conn.prepareStatement(ins, Statement.RETURN_GENERATED_KEYS)) {
                ps2.setInt(1, userId);
                ps2.executeUpdate();
                try (ResultSet keys = ps2.getGeneratedKeys()) {
                    if (keys.next()) {
                        return new Cart(keys.getInt(1), userId, null);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ===================== CART ITEMS =====================

    /**
     * Lấy tất cả items trong cart, kèm thông tin sản phẩm
     */
    public static List<CartItem> getItems(int cartId) {
        List<CartItem> list = new ArrayList<>();
        String sql =
            "SELECT ci.id, ci.cart_id, ci.product_id, ci.size, ci.quantity, " +
            "       p.category_id, p.name, p.price, p.discount_price, p.image, " +
            "       p.description, p.is_new, p.is_bestseller " +
            "FROM cart_items ci " +
            "JOIN products p ON ci.product_id = p.id " +
            "WHERE ci.cart_id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CartItem item = new CartItem(
                        rs.getInt("id"),
                        rs.getInt("cart_id"),
                        rs.getInt("product_id"),
                        rs.getObject("size") != null ? rs.getInt("size") : null,
                        rs.getInt("quantity")
                    );
                    Product p = new Product();
                    p.setId(rs.getInt("product_id"));
                    p.setCategoryId(rs.getInt("category_id"));
                    p.setName(rs.getString("name").trim());
                    p.setPrice(rs.getInt("price"));
                    int dp = rs.getInt("discount_price");
                    p.setDiscountPrice(rs.wasNull() ? null : dp);
                    p.setImage(rs.getString("image").trim());
                    p.setDescription(rs.getString("description"));
                    p.setNew(rs.getBoolean("is_new"));
                    p.setBestseller(rs.getBoolean("is_bestseller"));
                    item.setProduct(p);
                    list.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Thêm sản phẩm vào giỏ hàng.
     * Nếu đã có (cùng product_id + size) thì tăng quantity.
     */
    public static void addItem(int cartId, int productId, Integer size, int quantity) {
        String check = "SELECT id, quantity FROM cart_items WHERE cart_id=? AND product_id=? AND (size=? OR (size IS NULL AND ? IS NULL))";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(check)) {
            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            if (size != null) { ps.setInt(3, size); ps.setInt(4, size); }
            else { ps.setNull(3, Types.INTEGER); ps.setNull(4, Types.INTEGER); }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Cập nhật số lượng
                    int newQty = rs.getInt("quantity") + quantity;
                    String upd = "UPDATE cart_items SET quantity=? WHERE id=?";
                    try (PreparedStatement ps2 = conn.prepareStatement(upd)) {
                        ps2.setInt(1, newQty);
                        ps2.setInt(2, rs.getInt("id"));
                        ps2.executeUpdate();
                    }
                    return;
                }
            }
            // Thêm mới
            String ins = "INSERT INTO cart_items (cart_id, product_id, size, quantity) VALUES (?,?,?,?)";
            try (PreparedStatement ps2 = conn.prepareStatement(ins)) {
                ps2.setInt(1, cartId);
                ps2.setInt(2, productId);
                if (size != null) ps2.setInt(3, size); else ps2.setNull(3, Types.INTEGER);
                ps2.setInt(4, quantity);
                ps2.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cập nhật số lượng item
     */
    public static void updateQuantity(int itemId, int quantity) {
        String sql = quantity <= 0
            ? "DELETE FROM cart_items WHERE id=?"
            : "UPDATE cart_items SET quantity=? WHERE id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (quantity <= 0) {
                ps.setInt(1, itemId);
            } else {
                ps.setInt(1, quantity);
                ps.setInt(2, itemId);
            }
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Xoá một item khỏi giỏ hàng
     */
    public static void removeItem(int itemId) {
        String sql = "DELETE FROM cart_items WHERE id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, itemId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Xoá toàn bộ giỏ hàng sau khi thanh toán
     */
    public static void clearCart(int cartId) {
        String sql = "DELETE FROM cart_items WHERE cart_id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Đếm tổng số lượng sản phẩm trong giỏ
     */
    public static int countItems(int cartId) {
        String sql = "SELECT COALESCE(SUM(quantity),0) FROM cart_items WHERE cart_id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
