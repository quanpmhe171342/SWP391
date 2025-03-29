/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author d
 */
import java.sql.Date;

public class Order {

    private int orderID;
    private int userID;
    private Date orderDate;
    private String status;
    private int saleID;
    private String paymentMethod;

    public Order() {
    }

    public Order(int orderID, int userID, Date orderDate, String status, int saleID, String paymentMethod) {
        this.orderID = orderID;
        this.userID = userID;
        this.orderDate = orderDate;
        this.status = status;
        this.saleID = saleID;
        this.paymentMethod = paymentMethod;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSaleID() {
        return saleID;
    }

    public void setSaleID(int saleID) {
        this.saleID = saleID;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "Order{" + "orderID=" + orderID + ", userID=" + userID + ", orderDate=" + orderDate + ", status=" + status + ", saleID=" + saleID + ", paymentMethod=" + paymentMethod + '}';
    }

}
