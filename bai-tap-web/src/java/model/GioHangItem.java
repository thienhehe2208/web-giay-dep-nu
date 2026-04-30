package model;
public class GioHangItem {
    private Product product;
    private String size;
    private int quantity;

   public GioHangItem(Product product, String size, int quantity) {
    this.product = product; // ❗ cực quan trọng
    this.size = size;
    this.quantity = quantity;
}
    public double getTotal() {
    return product.getPrice() * quantity;
}

    public Product getProduct() {
        return product;
    }

    public String getSize() {
        return size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}