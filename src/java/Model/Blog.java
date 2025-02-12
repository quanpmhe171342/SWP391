/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class Blog {
    int blogID;
    String title;
    String image;
    String content;
    CategoryBlog categoryBlogID;
    Date createAt;

    public Blog() {
    }

    public Blog(int blogID, String title, String image, String content, CategoryBlog categoryBlogID, Date createAt) {
        this.blogID = blogID;
        this.title = title;
        this.image = image;
        this.content = content;
        this.categoryBlogID = categoryBlogID;
        this.createAt = createAt;
    }

    public int getBlogID() {
        return blogID;
    }

    public void setBlogID(int blogID) {
        this.blogID = blogID;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CategoryBlog getCategoryBlogID() {
        return categoryBlogID;
    }

    public void setCategoryBlogID(CategoryBlog categoryBlogID) {
        this.categoryBlogID = categoryBlogID;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "Blog{" + "blogID=" + blogID + ", title=" + title + ", image=" + image + ", content=" + content + ", categoryBlogID=" + categoryBlogID + ", createAt=" + createAt + '}';
    }

    
    
    
}
