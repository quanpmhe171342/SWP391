/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.sql.Date;

/**
 *
<<<<<<< HEAD
 * @author d
=======
 * @author NV200
>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05
 */
public class ReportProductDTO {
    private int productId;
    private String productName;
    private int totalSold;
    private double revenue;
    private Date period;

    public ReportProductDTO() {
    }

    public ReportProductDTO(int productId, String productName, int totalSold, double revenue, Date period) {
        this.productId = productId;
        this.productName = productName;
        this.totalSold = totalSold;
        this.revenue = revenue;
        this.period = period;
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

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return "ReportProductDTO{" + "productId=" + productId + ", productName=" + productName + ", totalSold=" + totalSold + ", revenue=" + revenue + ", period=" + period + '}';
    }
    
}
