package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Model người dùng - khớp với bảng `users` trong CSDL
 * Các cột: id, account_name, username, email, phone, address, password, created_at
 */
public class User implements Serializable {

    private int id;
    private String accountName;
    private String username;
    private String email;
    private String phone;
    private String address;
    private String password;
    private Timestamp createdAt;

    public User() {}

    public User(int id, String accountName, String username, String email,
                String phone, String address, String password, Timestamp createdAt) {
        this.id = id;
        this.accountName = accountName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
