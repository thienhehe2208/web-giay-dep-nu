package dao;

import model.Contact;
import model.DBContext;

import java.sql.*;

/**
 * DAO cho bảng `contacts`
 */
public class ContactDAO {

    /**
     * Lưu tin nhắn liên hệ vào CSDL
     * @return true nếu thành công
     */
    public static boolean save(Contact contact) {
        String sql = "INSERT INTO contacts (name, email, phone, message) VALUES (?,?,?,?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, contact.getName());
            ps.setString(2, contact.getEmail());
            ps.setString(3, contact.getPhone());
            ps.setString(4, contact.getMessage());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
