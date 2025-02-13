package Model;

import Model.CategoryBlog;
import java.util.Date;

public class Blog {
    private int blogID;
    private String title;
    private String image;
    private String content;
    private CategoryBlog category;
    private Date createdAt;

    public Blog() {
    }

    // Constructor
    public Blog(int blogID, String title, String image, String content, CategoryBlog category, Date createdAt) {
        this.blogID = blogID;
        this.title = title;
        this.image = image;
        this.content = content;
        this.category = category;
        this.createdAt = createdAt;
    }

    // Getters and Setters
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

    public CategoryBlog getCategory() {
        return category;
    }

    public void setCategory(CategoryBlog category) {
        this.category = category;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "blogID=" + blogID +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", content='" + content + '\'' +
                ", category=" + category +
                ", createdAt=" + createdAt +
                '}';
    }
}
