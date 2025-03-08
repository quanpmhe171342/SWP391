/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author admin
 */
public class CategoryBlog {
    int CategoryBlogID;
    String CategoryName;

    public CategoryBlog() {
    }

    public CategoryBlog(int CategoryBlogID, String CategoryName) {
        this.CategoryBlogID = CategoryBlogID;
        this.CategoryName = CategoryName;
    }

    public int getCategoryBlogID() {
        return CategoryBlogID;
    }

    public void setCategoryBlogID(int CategoryBlogID) {
        this.CategoryBlogID = CategoryBlogID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }
    
}
