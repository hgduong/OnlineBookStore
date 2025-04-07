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
import model.Category;

/**
 *
 * @author TungPham
 */
public class CartDAO {

    private static final String GET_CART_BY_ACCOUNTID = "select * from Cart where AccountId = ?";

    private static final String GET_CART_BY_ID = "select * from Cart where CartId = ?";

    private static final String ADD_NEW_CART_FOR_USER = "INSERT INTO Cart (AccountId, TotalAmount, TotalQuantity) "
            + "VALUES (?, ?, ?);";

    private static final String UPDATE_CART = "UPDATE Cart "
            + "SET TotalAmount = ?, TotalQuantity = ? "
            + "WHERE CartId = ?;";

    public Cart getCartByAccountID(int accountId) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        Cart cart = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_CART_BY_ACCOUNTID);
            stm.setInt(1, accountId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                cart = new Cart();
                cart.setCartId(rs.getInt("CartId"));
                cart.setAccountId(rs.getInt("AccountId"));
                cart.setTotalAmount(rs.getFloat("TotalAmount"));
                cart.setTotalQuantity(rs.getInt("TotalQuantity"));
            }
            return cart;
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
        return cart;
    }

    public Cart getCartByID(int cartId) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        Cart cart = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_CART_BY_ID);
            stm.setInt(1, cartId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                cart = new Cart();
                cart.setCartId(rs.getInt("CartId"));
                cart.setAccountId(rs.getInt("AccountId"));
                cart.setTotalAmount(rs.getFloat("TotalAmount"));
                cart.setTotalQuantity(rs.getInt("TotalQuantity"));
            }
            return cart;
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
        return cart;
    }

    public boolean createCartForUser(int accountId) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        Cart cart = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(ADD_NEW_CART_FOR_USER);
            stm.setInt(1, accountId);
            stm.setFloat(2, 0f);
            stm.setInt(3, 0);
            stm.execute();

            return true;
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
        return false;
    }

    public boolean saveCart(int cartId, float totalPrice, int totalQuantity) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(UPDATE_CART);
            stm.setFloat(1, totalPrice);
            stm.setInt(2, totalQuantity);
            stm.setInt(3, cartId);
            stm.execute();
            return true;
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
