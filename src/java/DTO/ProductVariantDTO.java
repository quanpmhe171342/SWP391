/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author d
 */
public class ProductVariantDTO {
    private int variantId;
    private int productID;
    private int sizeId;
    private int colorId;
    private int stock;
    private String imageURL;

    public ProductVariantDTO() {
    }

    public ProductVariantDTO(int variantId, int productID, int sizeId, int colorId, int stock, String imageURL) {
        this.variantId = variantId;
        this.productID = productID;
        this.sizeId = sizeId;
        this.colorId = colorId;
        this.stock = stock;
        this.imageURL = imageURL;
    }

    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
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

    @Override
    public String toString() {
        return "ProductVariantDTO{" + "variantId=" + variantId + ", productID=" + productID + ", sizeId=" + sizeId + ", colorId=" + colorId + ", stock=" + stock + ", imageURL=" + imageURL + '}';
    }
}
