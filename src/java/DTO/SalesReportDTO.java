/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.sql.Date;

/**
 *
 * @author d
 */
public class SalesReportDTO {
    private Date date;
    private int totalOrders;
    private int totalProducts;
    private double revenue;

    public SalesReportDTO() {
    }

    public SalesReportDTO(Date date, int totalOrders, int totalProducts, double revenue) {
        this.date = date;
        this.totalOrders = totalOrders;
        this.totalProducts = totalProducts;
        this.revenue = revenue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    @Override
    public String toString() {
        return "SalesReportDTO{" + "date=" + date + ", totalOrders=" + totalOrders + ", totalProducts=" + totalProducts + ", revenue=" + revenue + '}';
    }
    
}
