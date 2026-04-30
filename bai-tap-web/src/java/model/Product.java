package model;

import java.io.Serializable;

public class Product implements Serializable {

    private int id;
    private int categoryId;
    private String sku;
    private String name;
    private double price;
    private double discountPrice;
    private String image;
    private String description;
    private int isNew;
    private int isBestseller;

    public Product() {}

    public Product(int id, String name, String image, double price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getDiscountPrice() { return discountPrice; }
    public void setDiscountPrice(double discountPrice) { this.discountPrice = discountPrice; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getIsNew() { return isNew; }
    public void setIsNew(int isNew) { this.isNew = isNew; }

    public int getIsBestseller() { return isBestseller; }
    public void setIsBestseller(int isBestseller) { this.isBestseller = isBestseller; }
}
