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
public class ReportStateDTO {
    private Date period;
    private String status;
    private int orderCount;
    private double totalAmount;

    public ReportStateDTO() {
    }

    public ReportStateDTO(Date period, String status, int orderCount, double totalAmount) {
        this.period = period;
        this.status = status;
        this.orderCount = orderCount;
        this.totalAmount = totalAmount;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "ReportStateDTO{" + "period=" + period + ", status=" + status + ", orderCount=" + orderCount + ", totalAmount=" + totalAmount + '}';
    }
    
}
