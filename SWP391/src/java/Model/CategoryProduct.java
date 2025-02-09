/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author phuan
 */
public class CategoryProduct {
    int Category_ProductID;
    String category_name;
    String category_description;
    String image;

    public CategoryProduct(int Category_ProductID, String category_name, String category_description, String image) {
        this.Category_ProductID = Category_ProductID;
        this.category_name = category_name;
        this.category_description = category_description;
        this.image = image;
    }

    public int getCategory_ProductID() {
        return Category_ProductID;
    }

    public void setCategory_ProductID(int Category_ProductID) {
        this.Category_ProductID = Category_ProductID;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_description() {
        return category_description;
    }

    public void setCategory_description(String category_description) {
        this.category_description = category_description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
}
