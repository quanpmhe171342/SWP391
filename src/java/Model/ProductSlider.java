/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author NV200
 */
public class ProductSlider {
    private int variantId;


    public ProductSlider() {
    }

    public ProductSlider(int variantId) {
        this.variantId = variantId;
       
    }

    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }

    @Override
    public String toString() {
        return "ProductSlider{" + "variantId=" + variantId + '}';
    }

   

   

   
    
}
