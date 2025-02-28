/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */
public class NewProductVariant {
    int VariantID;
    NewProduct product;
    Size size;
    Color color;
    int stock;
    String image;

    public NewProductVariant() {
    }

    public NewProductVariant(int VariantID, NewProduct product, Size size, Color color, int stock, String image) {
        this.VariantID = VariantID;
        this.product = product;
        this.size = size;
        this.color = color;
        this.stock = stock;
        this.image = image;
    }

    public int getVariantID() {
        return VariantID;
    }

    public void setVariantID(int VariantID) {
        this.VariantID = VariantID;
    }

    public NewProduct getProduct() {
        return product;
    }

    public void setProduct(NewProduct product) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "NewProductVariant{" + "VariantID=" + VariantID + ", product=" + product + ", size=" + size + ", color=" + color + ", stock=" + stock + ", image=" + image + '}';
    }
    
    
}
