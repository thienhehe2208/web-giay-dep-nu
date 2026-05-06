package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Model liên hệ - khớp với bảng `contacts` trong CSDL
 * Các cột: id, name, email, phone, message, created_at
 */
public class Contact implements Serializable {

    private int id;
    private String name;
    private String email;
    private String phone;
    private String message;
    private Timestamp createdAt;

    public Contact() {}

    public Contact(String name, String email, String phone, String message) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.message = message;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
