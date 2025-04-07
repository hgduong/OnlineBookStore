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
import model.Cart;
import model.CartItem;

/**
 *
 * @author TungPham
 */
public class CartItemDAO {

    private final static String GET_CART_ITEM_BY_CARTID = "SELECT ci.CartId, ci.BookId, ci.Quantity, ci.Price, ci.isChecked, b.Price as bookPrice, b.Title "
            + "FROM CartItem AS ci "
            + "JOIN Book AS b ON ci.BookId = b.BookId "
            + "WHERE ci.CartId = ?;";

    private final static String GET_CART_ITEM_BY_CARTID_BOOKID = "SELECT ci.CartId, ci.BookId, ci.Quantity, ci.Price, ci.isChecked, b.Price as bookPrice, b.Title "
            + "FROM CartItem AS ci "
            + "JOIN Book AS b ON ci.BookId = b.BookId "
            + "WHERE ci.CartId = ? and ci.BookId =?;";

    private final static String CHECK_BOOK_EXIST = "SELECT * "
            + "FROM CartItem "
            + "WHERE CartId = ? AND BookId = ?;";

    private final static String ADD_CART_ITEM = "INSERT INTO CartItem (CartId, BookId, Quantity, Price) "
            + "VALUES (?, ?, ?, ?);";
    private final static String UPDATE_CART_ITEM = "UPDATE CartItem "
            + "SET CartId = ?, BookId = ?, Quantity = ?, Price = ? "
            + "WHERE CartId = ? AND BookId = ?;";

    private final static String DELETE_CART_ITEM = "DELETE FROM CartItem WHERE cartId =? and bookId = ?;";

    private final static String DELETE_CART_ITEM_BY_CARTID = "DELETE FROM CartItem WHERE cartId = ?;";

    public List<CartItem> getCartItemByCartID(int cartId) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        List<CartItem> items = new ArrayList<>();

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_CART_ITEM_BY_CARTID);
            stm.setInt(1, cartId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CartItem item = new CartItem();
                item.setBookId(rs.getInt("BookId"));
                item.setCartId(rs.getInt("CartId"));
                item.setPrice(rs.getFloat("Price"));
                item.setQuantity(rs.getInt("Quantity"));
                item.setBookPrice(rs.getFloat("bookPrice"));
                item.setBookName(rs.getString("Title"));
                items.add(item);
            }
            return items;
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
        return items;
    }

    public CartItem getCartItemByCartIDBookId(int cartId, int bookId) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        CartItem item = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_CART_ITEM_BY_CARTID_BOOKID);
            stm.setInt(1, cartId);
            stm.setInt(2, bookId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                item = new CartItem();
                item.setBookId(rs.getInt("BookId"));
                item.setCartId(rs.getInt("CartId"));
                item.setPrice(rs.getFloat("Price"));
                item.setQuantity(rs.getInt("Quantity"));
                item.setBookPrice(rs.getFloat("bookPrice"));
                item.setBookName(rs.getString("Title"));

            }
            return item;
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
        return item;
    }

    public boolean saveCartItem(Book book, int cartId, int quantity) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        PreparedStatement stmadd = null;
        PreparedStatement stmupdate = null;
        Connection connection = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(CHECK_BOOK_EXIST);
            stm.setInt(1, cartId);
            stm.setInt(2, book.getBookId());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setPrice(rs.getFloat("Price"));
                cartItem.setQuantity(rs.getInt("Quantity"));
                stmupdate = connection.prepareStatement(UPDATE_CART_ITEM);
                //"SET CartId = ?, BookId = ?, Quantity = ?, Price = ? "+ "WHERE CartId = ? AND BookId = ?;";
                stmupdate.setInt(1, cartId);
                stmupdate.setInt(2, book.getBookId());
                stmupdate.setInt(3, quantity);
                stmupdate.setDouble(4, quantity * book.getPrice());
                stmupdate.setInt(5, cartId);
                stmupdate.setInt(6, book.getBookId());
                int rowsAffected = stmupdate.executeUpdate();
                return rowsAffected > 0;
            } else {
                //CartId, BookId, Quantity, Price
                stmadd = connection.prepareStatement(ADD_CART_ITEM);
                stmadd.setInt(1, cartId);
                stmadd.setInt(2, book.getBookId());
                stmadd.setInt(3, quantity);
                stmadd.setDouble(4, quantity * book.getPrice());
                int rowsAffected = stmadd.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return false;

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

    public static void main(String[] args) {
        CartItemDAO thisss = new CartItemDAO();
        Book book = new Book();
        book.setBookId(20);
        book.setPrice(100000);
        boolean check = thisss.saveCartItem(book, 1, 2);
        System.out.println(check);
    }

    public boolean deleteCartItem(int cartId, int bookId) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(DELETE_CART_ITEM);
            stm.setInt(1, cartId);
            stm.setInt(2, bookId);
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return false;
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

    public void deleteAllCartItemByCartId(int cartId) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(DELETE_CART_ITEM_BY_CARTID);
            stm.setInt(1, cartId);
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
