package Model;

import java.sql.Date;

public class Product {
    private int product_ID;
    private String product_name;
    private Double original_price;
    private Double sale_price;
    private String product_description;
    private String brief_information;
    private CategoryProduct category;
    private Date createDate;
    private Double import_price;
    private boolean status;

    public Product() {
    }

    public Product(int product_ID, String product_name, Double original_price, Double sale_price, String product_description,
                   String brief_information, CategoryProduct category, Date createDate, Double import_price, boolean status) {
        this.product_ID = product_ID;
        this.product_name = product_name;
        this.original_price = original_price;
        this.sale_price = sale_price;
        this.product_description = product_description;
        this.brief_information = brief_information;
        this.category = category;
        this.createDate = createDate;
        this.import_price = import_price;
        this.status = status;
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

    public Double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(Double original_price) {
        this.original_price = original_price;
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

    public CategoryProduct getCategory() {
        return category;
    }

    public void setCategory(CategoryProduct category) {
        this.category = category;
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

    @Override
    public String toString() {
        return "Product{" +
                "product_ID=" + product_ID +
                ", product_name='" + product_name + '\'' +
                ", original_price=" + original_price +
                ", sale_price=" + sale_price +
                ", product_description='" + product_description + '\'' +
                ", brief_information='" + brief_information + '\'' +
                ", category=" + category +
                ", createDate=" + createDate +
                ", import_price=" + import_price +
                ", status=" + status +
                '}';
    }
}
