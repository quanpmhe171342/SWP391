/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author NV200
 */
public class NewSlider {
    private String title;
    private String image;
    private int status;
    private String description;
    private int productVariantId;

    public NewSlider() {
    }

    public NewSlider(String title, String image, int status, String description, int productVariantId) {
        this.title = title;
        this.image = image;
        this.status = status;
        this.description = description;
        this.productVariantId = productVariantId;
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

    public int getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(int productVariantId) {
        this.productVariantId = productVariantId;
    }

    @Override
    public String toString() {
        return "NewSlider{" + "title=" + title + ", image=" + image + ", status=" + status + ", description=" + description + ", productVariantId=" + productVariantId + '}';
    }
    
}
