/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */
public class CategoryBlog {
    int categoryBlogID;
    String categoryBlogName;

    public CategoryBlog() {
    }

    public CategoryBlog(int categoryBlogID, String categoryBlogName) {
        this.categoryBlogID = categoryBlogID;
        this.categoryBlogName = categoryBlogName;
    }

    public int getCategoryBlogID() {
        return categoryBlogID;
    }

    public void setCategoryBlogID(int categoryBlogID) {
        this.categoryBlogID = categoryBlogID;
    }

    public String getCategoryBlogName() {
        return categoryBlogName;
    }

    public void setCategoryBlogName(String categoryBlogName) {
        this.categoryBlogName = categoryBlogName;
    }
    
    
}
