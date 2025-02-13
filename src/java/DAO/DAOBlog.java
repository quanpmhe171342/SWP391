/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Blog;
import Model.CategoryBlog;
import Model.Feedback;
import Model.FeedbackBlog;
import Model.Role;
import Model.User;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author admin
 */
public class DAOBlog extends DBContext {
    public List<Blog> getBlogPaginated(int page, int pageSize) {
    List<Blog> paginatedBlogs = new ArrayList<>();
    int offset = (page - 1) * pageSize;
    
    try {
        String sql = "SELECT * FROM blog ORDER BY BlogID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, offset);
        pstm.setInt(2, pageSize);
        
        ResultSet rs = pstm.executeQuery();
        
        while (rs.next()) {
            CategoryBlog cp = new CategoryBlog(
                        rs.getInt("CategoryBlogID"),
                        null);
              Blog  b = new Blog(
                        rs.getInt("BlogID"),
                        rs.getString("Title"),
                        rs.getString("image"),
                        rs.getString("Content"),
                        cp,
                        rs.getDate("CreatedAt")
                );
            paginatedBlogs.add(b);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return paginatedBlogs;
}
public int getTotalBlogCount() {
    int count = 0;
    try {
        String sql = "SELECT COUNT(*) FROM blog";
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);
        
        if (rs.next()) {
            count = rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return count;
}
public List<Blog> getListByPage(List<Blog> list, int start, int end) {
        List<Blog> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }
    public List<Blog> getBlog() {
        List<Blog> product = new ArrayList<>();
        try {
            String query = "SELECT \n"
                    + "    b.BlogID, \n"
                    + "    b.Title, \n"
                    + "    b.image, \n"
                    + "    b.Content, \n"
                    + "    b.CategoryBlogID, \n"
                    + "    c.CategoryName, \n"
                    + "    b.CreatedAt\n"
                    + "FROM \n"
                    + "    Blog b\n"
                    + "INNER JOIN \n"
                    + "    CategoryBlog c ON b.CategoryBlogID = c.CategoryBlogID; ";
            PreparedStatement stm = conn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CategoryBlog cp = new CategoryBlog(
                        rs.getInt("CategoryBlogID"),
                        rs.getString("CategoryName"));
                Blog p = new Blog(
                        rs.getInt("BlogID"),
                        rs.getString("Title"),
                        rs.getString("image"),
                        rs.getString("Content"),
                        cp,
                        rs.getDate("CreatedAt")
                );
                product.add(p); // Add blog to the list
            }
        } catch (SQLException e) {
            System.out.print(e);
        }
        return product;
    }
    
    public Blog getBlogByid(int id) {
        Blog b = null;
        try {
            String query = "SELECT \n" +
"                        b.BlogID,\n" +
"                       b.Title,\n" +
"                        b.Content , b.image,\n" +
"                        b.CategoryBlogID,\n" +
"                       b.CreatedAt \n" +
"                      \n" +
"                   \n" +
"                    FROM Blog b\n" +
"                \n" +
"                    where b.BlogID=" + id;
            PreparedStatement stm = conn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CategoryBlog cp = new CategoryBlog(
                        rs.getInt("CategoryBlogID"),
                        null);
                b = new Blog(
                        rs.getInt("BlogID"),
                        rs.getString("Title"),
                        rs.getString("image"),
                        rs.getString("Content"),
                        cp,
                        rs.getDate("CreatedAt")
                );
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return b;
    }

    public Blog getBlogNewest() {
        Blog b = null;
        try {
            String query = "SELECT b.*, cb.CategoryName\n"
                    + "FROM [dbo].[Blog] b\n"
                    + "INNER JOIN [dbo].[CategoryBlog] cb ON b.CategoryBlogID = cb.CategoryBlogID\n"
                    + "WHERE b.[CreatedAt] = (SELECT MAX([CreatedAt]) FROM [dbo].[Blog]);";
            PreparedStatement stm = conn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CategoryBlog cp = new CategoryBlog(
                        rs.getInt("CategoryBlogID"),
                        rs.getString("CategoryName"));
                b = new Blog(
                        rs.getInt("BlogID"),
                        rs.getString("Title"),
                        rs.getString("image"),
                        rs.getString("Content"),
                        cp,
                        rs.getDate("CreatedAt")
                );
            }
        } catch (Exception e) {
        }
        return b;
    }

    public List<Feedback> getfBlogByid(int id) {
    List<Feedback> list = new ArrayList<>();
    try {
        String query = "SELECT " +
                "b.BlogID, b.Title, b.image, b.Content AS BlogContent, " +
                "b.CategoryBlogID, b.CreatedAt AS BlogCreatedAt, " +
                "f.FeedbackID, f.Content AS FeedbackContent, f.CreatedAt AS FeedbackCreatedAt, " +
                "u.UserID, u.first_name, u.last_name, u.email, u.username, u.avatar, u.roleID " +
                "FROM Blog b " +
                "INNER JOIN FeedbackBlog fb ON b.BlogID = fb.BlogID " +
                "INNER JOIN Feedback f ON fb.FeedbackId = f.FeedbackID " +
                "INNER JOIN Users u ON f.UserID = u.UserID " +
                "WHERE b.BlogID = ?";
        
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1, id); // Prevent SQL injection
        ResultSet rs = stm.executeQuery();
        
        while (rs.next()) {
            CategoryBlog category = new CategoryBlog(rs.getInt("CategoryBlogID"), null);
            Blog blog = new Blog(
                rs.getInt("BlogID"),
                rs.getString("Title"),
                rs.getString("image"),
                rs.getString("BlogContent"),
                category,
                rs.getDate("BlogCreatedAt")
            );

            Role role = new Role(rs.getInt("roleID"), null);
            User user = new User(
                rs.getInt("UserID"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("username"),
                null,
                null,
                null,
                true,
                null,
                rs.getString("avatar"),
                role
            );

            Feedback feedback = new Feedback(
                rs.getInt("FeedbackID"),
                user,
                rs.getString("FeedbackContent"),
                rs.getDate("FeedbackCreatedAt")
            );

            list.add(feedback); // Add feedback to the list
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

public static void main(String[] args) {
    DAOBlog daoBlog = new DAOBlog();
//    List<Feedback> feedbackList = daoBlog.getfBlogByid(2);
//
//    for (Feedback feedback : feedbackList) {
//        System.out.println(feedback);
//    }
    System.out.println(daoBlog.getBlogByid(3));
}

}
