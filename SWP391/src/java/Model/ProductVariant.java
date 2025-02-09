/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author phuan
 */
public class ProductVariant {
    int VariantID;
    Product product;
    Size size;
    Color color;
    int Stock;
    String image;

    public ProductVariant(int VariantID, Product product, Size size, Color color, int Stock, String image) {
        this.VariantID = VariantID;
        this.product = product;
        this.size = size;
        this.color = color;
        this.Stock = Stock;
        this.image = image;
    }

    public int getVariantID() {
        return VariantID;
    }

    public void setVariantID(int VariantID) {
        this.VariantID = VariantID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
}
