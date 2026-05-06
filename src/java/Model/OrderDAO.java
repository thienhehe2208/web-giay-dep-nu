package dao;

import model.DBContext;
import model.CartItem;
import java.sql.*;
import java.util.*;

public class OrderDAO {

    public static int createOrder(Integer userId, String customerName,
                                   String customerPhone, String customerAddress,
                                   long totalMoney, List<CartItem> items) {
        String sqlOrder = "INSERT INTO orders (user_id, customer_name, customer_phone, " +
                          "customer_address, total_money, status) VALUES (?, ?, ?, ?, ?, 'Chờ xử lý')";
        String sqlDetail = "INSERT INTO order_details (order_id, product_id, price, quantity) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false);
            int orderId = -1;

            try (PreparedStatement ps = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS)) {
                if (userId != null) ps.setInt(1, userId);
                else ps.setNull(1, Types.INTEGER);
                ps.setString(2, customerName);
                ps.setString(3, customerPhone);
                ps.setString(4, customerAddress);
                ps.setLong(5, totalMoney);
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) orderId = keys.getInt(1);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlDetail)) {
                for (CartItem item : items) {
                    ps.setInt(1, orderId);
                    ps.setInt(2, item.getProductId());
                    ps.setInt(3, item.getProduct().getDisplayPrice());
                    ps.setInt(4, item.getQuantity());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            conn.commit();
            return orderId;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}