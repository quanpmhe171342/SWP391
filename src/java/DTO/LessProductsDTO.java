/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author NV200
 */
public class LessProductsDTO {

    private int productId;
    private String productName;
    private int totalSold;
    private double revenue;

    public LessProductsDTO() {
    }

    public LessProductsDTO(int productId, String productName, int totalSold, double revenue) {
        this.productId = productId;
        this.productName = productName;
        this.totalSold = totalSold;
        this.revenue = revenue;
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

    public int getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(int totalSold) {
        this.totalSold = totalSold;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    @Override
    public String toString() {
        return "LessProductsDTO{" + "productId=" + productId + ", productName=" + productName + ", totalSold=" + totalSold + ", revenue=" + revenue + '}';
    }
    
}
