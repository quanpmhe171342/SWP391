/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.ProductVariant;

/**
 *
 * @author Admin
 */
public class Slider {
    int sliderID;
    String title;
    String image;
    int status;
    String description;
    ProductVariant productVariantID;

    public Slider() {
    }

    public Slider(int sliderID, String title, String image, int status, String description) {
        this.sliderID = sliderID;
        this.title = title;
        this.image = image;
        this.status = status;
        this.description = description;
    }

    public Slider(int sliderID, String title, String image, int status, String description, ProductVariant productVariantID) {
        this.sliderID = sliderID;
        this.title = title;
        this.image = image;
        this.status = status;
        this.description = description;
        this.productVariantID = productVariantID;
    }

    public ProductVariant getProductVariantID() {
        return productVariantID;
    }

    public void setProductVariantID(ProductVariant productVariantID) {
        this.productVariantID = productVariantID;
    }

   
    

    public int getSliderID() {
        return sliderID;
    }

    public void setSliderID(int sliderID) {
        this.sliderID = sliderID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Slider{" + "sliderID=" + sliderID + ", title=" + title + ", image=" + image + ", status=" + status + ", description=" + description + '}';
    }
    
    
}
