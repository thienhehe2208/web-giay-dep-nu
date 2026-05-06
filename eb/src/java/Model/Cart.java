package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Model giỏ hàng - khớp với bảng `carts` trong CSDL
 * Các cột: id, user_id, created_at
 */
public class Cart implements Serializable {

    private int id;
    private int userId;
    private Timestamp createdAt;

    public Cart() {}

    public Cart(int id, int userId, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
