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
import model.Author;
import model.Category;

/**
 *
 * @author TungPham
 */
public class AuthorDAO {
    private static final String GET_AUTHORS = "SELECT AuthorId, AuthorName, DoB, Description FROM Author ORDER BY AuthorId";
    private static final String GET_AUTHOR_BY_ID = "SELECT AuthorId, AuthorName, DoB, Description FROM Author WHERE AuthorId = ?";
    private static final String ADD_AUTHOR = "INSERT INTO Author (AuthorId, AuthorName, DoB, Description) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_AUTHOR = "UPDATE Author SET AuthorName = ?, DoB = ?, Description = ? WHERE AuthorId = ?";
    private static final String DELETE_AUTHOR = "DELETE FROM Author WHERE AuthorId = ?";
    private static final String SEARCH_AUTHORS_BY_NAME = "SELECT AuthorId, AuthorName, DoB, Description FROM Author WHERE AuthorName LIKE ? ORDER BY AuthorId";
    private static final String CHECK_AUTHOR_ID_EXISTS = "SELECT COUNT(*) FROM Author WHERE AuthorId = ?";
    private static final String CHECK_AUTHOR_NAME_EXISTS = "SELECT COUNT(*) FROM Author WHERE AuthorName = ?";
    public List<Author> getAuthors() {
        List<Author> authors = new ArrayList<>();
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_AUTHORS);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Author author = new Author();
                author.setAuthorId(rs.getInt("AuthorId"));
                author.setName(rs.getString("AuthorName"));
                author.setDob(rs.getDate("DoB"));
                author.setBiography(rs.getString("Description"));
                authors.add(author);
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
        return authors;
    }
    
    public Author getAuthorById(int authorId) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        Author author = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_AUTHOR_BY_ID);
            stm.setInt(1, authorId);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                author = new Author();
                author.setAuthorId(rs.getInt("AuthorId"));
                author.setName(rs.getString("AuthorName"));
                author.setDob(rs.getDate("DoB"));
                author.setBiography(rs.getString("Description"));
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
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
        return author;
    }

    public boolean addAuthor(Author author) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(ADD_AUTHOR);
            stm.setInt(1, author.getAuthorId());
            stm.setString(2, author.getName());
            stm.setDate(3, new java.sql.Date(author.getDob().getTime())); // Convert java.util.Date to java.sql.Date
            stm.setString(4, author.getBiography());

            int rowsInserted = stm.executeUpdate();
            return rowsInserted > 0; // Return true if at least one row was inserted
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return false; // Return false if an exception occurred
        } finally {
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
    }

    public boolean updateAuthor(Author author) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(UPDATE_AUTHOR);
            stm.setString(1, author.getName());
            stm.setDate(2, new java.sql.Date(author.getDob().getTime())); // Convert java.util.Date to java.sql.Date
            stm.setString(3, author.getBiography());
            stm.setInt(4, author.getAuthorId());

            int rowsUpdated = stm.executeUpdate();
            return rowsUpdated > 0; // Return true if at least one row was updated
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return false; // Return false if an exception occurred
        } finally {
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
    }

    

    public List<Author> searchAuthorsByName(String searchName) {
        List<Author> authors = new ArrayList<>();
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(SEARCH_AUTHORS_BY_NAME);
            stm.setString(1, "%" + searchName + "%"); // Use LIKE for partial matches
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Author author = new Author();
                author.setAuthorId(rs.getInt("AuthorId"));
                author.setName(rs.getString("AuthorName"));
                author.setDob(rs.getDate("DoB"));
                author.setBiography(rs.getString("Description"));
                authors.add(author);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
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
        return authors;
    }

    public boolean authorIdExists(int authorId) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        ResultSet rs = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(CHECK_AUTHOR_ID_EXISTS);
            stm.setInt(1, authorId);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if the count is > 0
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
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
        return false;
    }

    public boolean authorNameExists(String authorName) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        ResultSet rs = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(CHECK_AUTHOR_NAME_EXISTS);
            stm.setString(1, authorName);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if the count is > 0
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
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
        return false;
    }

}
