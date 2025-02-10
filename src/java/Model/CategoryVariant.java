/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author phuan
 */
public class CategoryVariant {
    int CategoryVariantID;
    CategoryProduct cp;
    String NameVariantCate;

   

    public CategoryVariant() {
    }

    public CategoryVariant(int CategoryVariantID, CategoryProduct cp, String NameVariantCate) {
        this.CategoryVariantID = CategoryVariantID;
        this.cp = cp;
        this.NameVariantCate = NameVariantCate;
    }

    public int getCategoryVariantID() {
        return CategoryVariantID;
    }

    public void setCategoryVariantID(int CategoryVariantID) {
        this.CategoryVariantID = CategoryVariantID;
    }

    public CategoryProduct getCp() {
        return cp;
    }

    public void setCp(CategoryProduct cp) {
        this.cp = cp;
    }

    public String getNameVariantCate() {
        return NameVariantCate;
    }

    public void setNameVariantCate(String NameVariantCate) {
        this.NameVariantCate = NameVariantCate;
    }

    @Override
    public String toString() {
        return "CategoryVariant{" + "CategoryVariantID=" + CategoryVariantID + ", cp=" + cp + ", NameVariantCate=" + NameVariantCate + '}';
    }

   
}
