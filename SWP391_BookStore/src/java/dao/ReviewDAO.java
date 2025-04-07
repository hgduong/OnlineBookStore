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
import model.Book;
import model.Category;
import model.Review;

/**
 *
 * @author TungPham
 */
public class ReviewDAO {

    private static String GET_REVIEWS_BY_BOOKID = "SELECT r.ReviewId, r.AccountID, r.BookId, r.Rating, r.Comment, r.ReviewDate, r.Status, a.Fullname "
            + "FROM review r "
            + "JOIN Account a ON r.AccountID = a.AccountId "
            + "WHERE r.BookId = ? order by ReviewDate desc;";
    private static String INSERT_REVIEW_TO_BOOK = "INSERT INTO Review (BookId, AccountId, Rating, Comment, ReviewDate, Status) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";
    
    private static String DELETE_REVIEW = "DELETE FROM Review WHERE ReviewId = ?;";
    public List<Review> getReviewByBookId(int id) {
        List<Review> list = new ArrayList<>();
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        Book book = new Book();
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_REVIEWS_BY_BOOKID);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Review review = new Review();
                review.setReviewId(rs.getInt("ReviewId"));
                review.setBookId(rs.getInt("BookId"));
                review.setRating(rs.getInt("Rating"));
                review.setComment(rs.getString("Comment"));
                review.setReviewDate(rs.getDate("ReviewDate"));
                review.setStatus(rs.getString("Status"));
                review.setFullName(rs.getString("Fullname"));
                list.add(review);

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
        return list;
    }
    
    public void insertReviewtoBook(Review review) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(INSERT_REVIEW_TO_BOOK);
            stm.setInt(1, review.getBookId());
            stm.setInt(2, review.getAccountId());
            stm.setInt(3, review.getRating());
            stm.setString(4, review.getComment());
            stm.setDate(5, new java.sql.Date(review.getReviewDate().getTime()));
            stm.setString(6, review.getStatus());
            stm.execute();
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
    }
    
    public void deleteReview(int id) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(DELETE_REVIEW);
            stm.setInt(1, id);
            
            stm.execute();
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
    }
    
    
}
