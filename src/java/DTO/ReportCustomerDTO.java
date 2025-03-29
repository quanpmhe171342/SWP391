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
public class ReportCustomerDTO {
    private Date period;
    private int customers;

    public ReportCustomerDTO() {
    }

    public ReportCustomerDTO(Date period, int customers) {
        this.period = period;
        this.customers = customers;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }

    public int getCustomers() {
        return customers;
    }

    public void setCustomers(int customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "ReportCustomerDTO{" + "period=" + period + ", customers=" + customers + '}';
    }
    
}
