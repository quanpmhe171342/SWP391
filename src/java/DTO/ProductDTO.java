/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import Model.Color;
import Model.Size;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author d
 */
public class ProductDTO {

    private int cartId;
    private int productId;
    private String productName;
    private Double originalPrice;
    private Double salePrice;
    private String productDescription;
    private String briefInformation;
    private String imageUrl;
    private int quantity;
    private List<Size> sizes = new ArrayList<>();
    private List<Color> colors = new ArrayList<>();

    public ProductDTO() {
    }

    public ProductDTO(int cartId, int productId, String productName, Double originalPrice, Double salePrice, String productDescription, String briefInformation, String imageUrl, int quantity) {
        this.cartId = cartId;
        this.productId = productId;
        this.productName = productName;
        this.originalPrice = originalPrice;
        this.salePrice = salePrice;
        this.productDescription = productDescription;
        this.briefInformation = briefInformation;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getBriefInformation() {
        return briefInformation;
    }

    public void setBriefInformation(String briefInformation) {
        this.briefInformation = briefInformation;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Size> getSizes() {
        return sizes;
    }

    public void setSizes(List<Size> sizes) {
        this.sizes = sizes;
    }

    public List<Color> getColors() {
        return colors;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }

    @Override
    public String toString() {
        return "ProductDTO{" + "cartId=" + cartId + ", productId=" + productId + ", productName=" + productName + ", originalPrice=" + originalPrice + ", salePrice=" + salePrice + ", productDescription=" + productDescription + ", briefInformation=" + briefInformation + ", imageUrl=" + imageUrl + ", quantity=" + quantity + ", sizes=" + sizes + ", colors=" + colors + '}';
    }
}
