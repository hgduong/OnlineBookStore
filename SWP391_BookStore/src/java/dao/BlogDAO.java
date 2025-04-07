/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import model.Blog;
import dao.dbcontext.DatabaseFactory;
import dao.dbcontext.MySqlDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TungPham
 */

public class BlogDAO {

    private Connection connection;

    public BlogDAO() {
        try {
            MySqlDatabase database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Blog> getAllBlogs() {
        List<Blog> blogs = new ArrayList<>();
        String sql = "SELECT * FROM Blog ORDER BY CreatedAt DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String createdAt = (rs.getTimestamp("CreatedAt") != null) ? rs.getTimestamp("CreatedAt").toString() : null;
                String updatedAt = (rs.getTimestamp("UpdatedAt") != null) ? rs.getTimestamp("UpdatedAt").toString() : null;
                blogs.add(new Blog(
                        rs.getInt("BlogId"),
                        rs.getString("Title"),
                        rs.getInt("AuthorId"),
                        rs.getString("Description"),
                        rs.getString("Image"),
                        rs.getString("Content"),
                        createdAt,
                        updatedAt,
                        rs.getString("Status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blogs;
    }

    public Blog getBlogById(int id) {
        String sql = "SELECT * FROM Blog WHERE BlogId=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Blog(
                        rs.getInt("BlogId"),
                        rs.getString("Title"),
                        rs.getInt("AuthorId"),
                        rs.getString("Description"),
                        rs.getString("Image"),
                        rs.getString("Content"),
                        rs.getString("CreatedAt"),
                        rs.getString("UpdatedAt"),
                        rs.getString("Status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateBlog(Blog blog) {
        String sql = "UPDATE Blog SET Title = ?, Description = ?, Content = ?, Image = ?, Status = ?, UpdatedAt = NOW() WHERE BlogId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, blog.getTitle());
            stmt.setString(2, blog.getDescription());
            stmt.setString(3, blog.getContent());
            stmt.setString(4, blog.getImage());

            String status = (blog.getStatus() == null || blog.getStatus().isEmpty()) ? "Published" : blog.getStatus();
            stmt.setString(5, status);
            stmt.setInt(6, blog.getBlogId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBlog(int blogId) {
        String sql = "DELETE FROM Blog WHERE BlogId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, blogId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Blog> searchBlogs(String keyword) {
        List<Blog> blogs = new ArrayList<>();
        String sql = "SELECT * FROM Blog WHERE Title LIKE ? OR Description LIKE ? OR Title LIKE ? OR AuthorId LIKE ? OR Content LIKE ? OR CreatedAt LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");
            ps.setString(4, "%" + keyword + "%");
            ps.setString(5, "%" + keyword + "%");
            ps.setString(6, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                blogs.add(new Blog(rs.getInt("BlogId"), rs.getString("Title"), rs.getInt("AuthorId"), rs.getString("Description"), rs.getString("Image"), rs.getString("Content"), rs.getString("Status"), rs.getString("CreatedAt"), rs.getString("UpdatedAt")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blogs;
    }

    public boolean addBlog(Blog blog) {
        String sql = "INSERT INTO Blog (Title, AuthorId, Description, Image, Content, CreatedAt, Status) VALUES (?, ?, ?, ?, ?, NOW(), ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, blog.getTitle());
            stmt.setInt(2, blog.getAuthorId());
            stmt.setString(3, blog.getDescription());
            stmt.setString(4, blog.getImage());
            stmt.setString(5, blog.getContent());

            // Nếu status là null, đặt thành "Published"
            String status = (blog.getStatus() == null || blog.getStatus().isEmpty()) ? "Published" : blog.getStatus();
            stmt.setString(6, status);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
