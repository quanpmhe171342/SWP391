/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author d
 */
public class OrderDetail {
    private int orderDetailID;
    private int orderId;
    private int productId;
    private int quantity;
    private double price;

    public OrderDetail() {
    }

    public OrderDetail(int orderDetailID, int orderId, int productId, int quantity, double price) {
        this.orderDetailID = orderDetailID;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderDetail{" + "orderDetailID=" + orderDetailID + ", orderId=" + orderId + ", productId=" + productId + ", quantity=" + quantity + ", price=" + price + '}';
    }
    
}
