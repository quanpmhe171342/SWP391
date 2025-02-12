/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.Product to edit this template
 */
package Model;

/**
 *
 * @author phuan
 */
public class Product {

    int product_ID;
    String product_name;
    Double original_Price;
    Double sale_price;
    String product_description;
    String brief_information;
    CategoryProduct ct;

      public Product(){
      };

    public Product(int product_ID, String product_name, Double original_Price, Double sale_price, String product_description, String brief_information, CategoryProduct ct) {
        this.product_ID = product_ID;
        this.product_name = product_name;
        this.original_Price = original_Price;
        this.sale_price = sale_price;
        this.product_description = product_description;
        this.brief_information = brief_information;
        this.ct = ct;
    }

    public int getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(int product_ID) {
        this.product_ID = product_ID;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Double getOriginal_Price() {
        return original_Price;
    }

    public void setOriginal_Price(Double original_Price) {
        this.original_Price = original_Price;
    }

    public Double getSale_price() {
        return sale_price;
    }

    public void setSale_price(Double sale_price) {
        this.sale_price = sale_price;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getBrief_information() {
        return brief_information;
    }

    public void setBrief_information(String brief_information) {
        this.brief_information = brief_information;
    }

    public CategoryProduct getCt() {
        return ct;
    }

    public void setCt(CategoryProduct ct) {
        this.ct = ct;
    }

   
   

}
