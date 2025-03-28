/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author hieum
 */
public class NewProductVariant {
    private int variantID;
    private Product product;
    private Size size;
    private Color color;
    private int stock;
    private String imageURL;

    public NewProductVariant() {
    }

    public NewProductVariant(int variantID, Product product, Size size, Color color, int stock, String imageURL) {
        this.variantID = variantID;
        this.product = product;
        this.size = size;
        this.color = color;
        this.stock = stock;
        this.imageURL = imageURL;
    }

    public int getVariantID() {
        return variantID;
    }

    public void setVariantID(int variantID) {
        this.variantID = variantID;
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
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    
    
}
