package model;

import java.io.Serializable;

/**
 * Model sản phẩm - khớp với bảng `products` trong CSDL
 * Các cột: id, category_id, name, price, discount_price, image, description, is_new, is_bestseller
 */
public class Product implements Serializable {

    private int id;
    private int categoryId;
    private String name;
    private int price;
    private Integer discountPrice;   // có thể NULL
    private String image;
    private String description;
    private boolean isNew;
    private boolean isBestseller;

    // Dùng để JOIN với bảng categories
    private String categoryName;

    public Product() {}

    public Product(int id, int categoryId, String name, int price, Integer discountPrice,
                   String image, String description, boolean isNew, boolean isBestseller) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.discountPrice = discountPrice;
        this.image = image;
        this.description = description;
        this.isNew = isNew;
        this.isBestseller = isBestseller;
    }

    /** Giá hiển thị: discount_price nếu có, ngược lại dùng price */
    public int getDisplayPrice() {
        return (discountPrice != null) ? discountPrice : price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public Integer getDiscountPrice() { return discountPrice; }
    public void setDiscountPrice(Integer discountPrice) { this.discountPrice = discountPrice; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isNew() { return isNew; }
    public void setNew(boolean isNew) { this.isNew = isNew; }

    public boolean isBestseller() { return isBestseller; }
    public void setBestseller(boolean isBestseller) { this.isBestseller = isBestseller; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}
