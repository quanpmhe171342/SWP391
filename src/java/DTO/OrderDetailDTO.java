/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author d
 */
public class OrderDetailDTO {

    private int orderDetailID;
    private int orderId;
    private int productId;
    private String productName;
    private double originalPrice;
    private double salePrice;
    private String productDescription;
    private String briefInformation;
    private int quantity;
    private double price;
    private double totalPrice;

    public OrderDetailDTO() {
    }

    
    public OrderDetailDTO(int orderDetailID, int orderId, int productId, String productName,
            double originalPrice, double salePrice, String productDescription,
            String briefInformation, int quantity, double price) {
        this.orderDetailID = orderDetailID;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.originalPrice = originalPrice;
        this.salePrice = salePrice;
        this.productDescription = productDescription;
        this.briefInformation = briefInformation;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = quantity * price;
    }

    // Getters and Setters
    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = quantity * price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        this.totalPrice = quantity * price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
