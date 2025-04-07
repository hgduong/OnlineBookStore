/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.dbcontext.DatabaseFactory;
import dao.dbcontext.MySqlDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Book;
import model.Category;

/**
 *
 * @author TungPham
 */
public class CategoryDAO {
    private static final String GET_ALL_CATE = "SELECT CategoryId, CategoryName from categories";
    private static final String GET_CATEGORIES = "SELECT CategoryId, CategoryName, Description, Status FROM Categories ORDER BY CategoryId";
    private static final String GET_CATEGORY_BY_ID = "SELECT CategoryId, CategoryName, Description, Status FROM Categories WHERE CategoryId = ?";
    private static final String ADD_CATEGORY = "INSERT INTO Categories (CategoryId, CategoryName, Description, Status) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_CATEGORY = "UPDATE Categories SET CategoryName = ?, Description = ?, Status = ? WHERE CategoryId = ?";
    private static final String CHECK_CATEGORY_ID_EXISTS = "SELECT COUNT(*) FROM Categories WHERE CategoryId = ?";
    private static final String CHECK_CATEGORY_NAME_EXISTS = "SELECT COUNT(*) FROM Categories WHERE CategoryName = ?";
    private static final String SEARCH_CATEGORY_BY_NAME = "SELECT CategoryId, CategoryName, Description, Status FROM Categories WHERE CategoryName LIKE ? ORDER BY CategoryId";
    
    public List<Category> getCates() {
        List<Category> cates = new ArrayList<>();
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_ALL_CATE);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Category cate = new Category();
                cate.setCategoryId(rs.getInt("CategoryId"));
                cate.setCategoryName(rs.getString("CategoryName"));
                cates.add(cate);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
//            System.out.println("closed connection::::::::::");
            try {
                if (stm != null) {
                    stm.close();

                }
                if (database != null && connection != null) {
                    connection.close();
                }

            } catch (SQLException sqlEx) {
                // TODO Auto-generated catch block
                sqlEx.printStackTrace();
            }
        }
        return cates;
    }
    
    
    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_CATEGORIES);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("CategoryId"));
                category.setCategoryName(rs.getString("CategoryName"));
                category.setDescription(rs.getString("Description"));
                category.setStatus(rs.getString("Status"));
                categories.add(category);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            closeResources(stm, connection, database);
        }
        return categories;
    }

    public Category getCategoryById(int categoryId) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        Category category = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_CATEGORY_BY_ID);
            stm.setInt(1, categoryId);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                category = new Category();
                category.setCategoryId(rs.getInt("CategoryId"));
                category.setCategoryName(rs.getString("CategoryName"));
                category.setDescription(rs.getString("Description"));
                category.setStatus(rs.getString("Status"));
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            closeResources(stm, connection, database);
        }
        return category;
    }

    public boolean addCategory(Category category) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(ADD_CATEGORY);
            stm.setInt(1, category.getCategoryId());
            stm.setString(2, category.getCategoryName());
            stm.setString(3, category.getDescription());
            stm.setString(4, category.getStatus()); // Set the status
            int rowsInserted = stm.executeUpdate();
            return rowsInserted > 0; // Return true if at least one row was inserted
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return false; // Return false if an exception occurred
        } finally {
            closeResources(stm, connection, database);
        }
    }

    public boolean updateCategory(Category category) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(UPDATE_CATEGORY);
            stm.setString(1, category.getCategoryName());
            stm.setString(2, category.getDescription());
            stm.setString(3, category.getStatus()); // Set the status
            stm.setInt(4, category.getCategoryId());

            int rowsUpdated = stm.executeUpdate();
            return rowsUpdated > 0; // Return true if at least one row was updated
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return false; // Return false if an exception occurred
        } finally {
            closeResources(stm, connection, database);
        }
    }

    public boolean categoryIdExists(int categoryId) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        ResultSet rs = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(CHECK_CATEGORY_ID_EXISTS);
            stm.setInt(1, categoryId);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if the count is > 0
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            closeResources(stm, connection, database, rs);
        }
        return false;
    }

    public boolean categoryNameExists(String categoryName) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        ResultSet rs = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(CHECK_CATEGORY_NAME_EXISTS);
            stm.setString(1, categoryName);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if the count is > 0
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            closeResources(stm, connection, database, rs);
        }
        return false;
    }
    
    public List<Category> searchCategoryName(String searchName) {
    List<Category> categories = new ArrayList<>();
    MySqlDatabase database = null;
    PreparedStatement stm = null;
    Connection connection = null;

    try {
        database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
        connection = database.createConnection();
        stm = connection.prepareStatement(SEARCH_CATEGORY_BY_NAME);
        stm.setString(1, "%" + searchName + "%");
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            Category category = new Category();
            category.setCategoryId(rs.getInt("CategoryId"));
            category.setCategoryName(rs.getString("CategoryName"));
            category.setDescription(rs.getString("Description"));
            category.setStatus(rs.getString("Status"));
            categories.add(category);
        }
    } catch (SQLException sqlEx) {
        sqlEx.printStackTrace();
    } finally {
        closeResources(stm, connection, database, null);
    }
    return categories;
}

    // Helper method to close resources
    private void closeResources(PreparedStatement stm, Connection connection, MySqlDatabase database, ResultSet rs) {
         try {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (connection != null && database != null) {
                database.closeConnection(connection);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }
    
    private void closeResources(PreparedStatement stm, Connection connection, MySqlDatabase database) {
        closeResources(stm, connection, database, null);
    }

}
