package model;

import java.io.Serializable;

/**
 * Model chi tiết giỏ hàng - khớp với bảng `cart_items` trong CSDL
 * Các cột: id, cart_id, product_id, size, quantity
 * Có thêm trường product (JOIN) để hiển thị thông tin sản phẩm
 */
public class CartItem implements Serializable {

    private int id;
    private int cartId;
    private int productId;
    private Integer size;
    private int quantity;

    // JOIN với bảng products để hiển thị
    private Product product;

    public CartItem() {}

    public CartItem(int id, int cartId, int productId, Integer size, int quantity) {
        this.id = id;
        this.cartId = cartId;
        this.productId = productId;
        this.size = size;
        this.quantity = quantity;
    }

    /** Tổng tiền của dòng này */
    public int getLineTotal() {
        if (product == null) return 0;
        return product.getDisplayPrice() * quantity;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCartId() { return cartId; }
    public void setCartId(int cartId) { this.cartId = cartId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}
