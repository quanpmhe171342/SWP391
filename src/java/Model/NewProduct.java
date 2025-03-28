/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;

/**
 *
 * @author hieum
 */
public class NewProduct extends Product{
    private Date createDate;
    private Double import_price;
    private boolean status;

    public NewProduct() {
    }

    public NewProduct(Date createDate, Double import_price, boolean status) {
        this.createDate = createDate;
        this.import_price = import_price;
        this.status = status;
    }

    

    public NewProduct(Date createDate, Double import_price, boolean status, int product_ID, String product_name, Double original_price, Double sale_price, String product_description, String brief_information, CategoryProduct category) {
        super(product_ID, product_name, original_price, sale_price, product_description, brief_information, category);
        this.createDate = createDate;
        this.import_price = import_price;
        this.status = status;
    }

    public NewProduct(Date createDate) {
        this.createDate = createDate;
    }

    public NewProduct(Product product, Date date) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public NewProduct(Product product, java.util.Date createDate) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Double getImport_price() {
        return import_price;
    }

    public void setImport_price(Double import_price) {
        this.import_price = import_price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
    
}
