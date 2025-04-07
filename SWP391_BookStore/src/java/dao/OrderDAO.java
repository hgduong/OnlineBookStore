/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.dbcontext.DatabaseFactory;
import dao.dbcontext.MySqlDatabase;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Address;
import model.Order;

/**
 *
 * @author TungPham
 */
public class OrderDAO {

    private static final String ADD_NEW_ORDER = "INSERT INTO bookstore.order (AccountId, CartId, TotalAmount, DeliveryTime, AddressId, CreatedAt, Status) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?);";

    private static final String UPDATE_ORDER = "UPDATE bookstore.order "
            + "SET AccountId = ?, CartId = ?, TotalAmount = ?, DeliveryTime = ?, AddressId = ?, CreatedAt = ?, Status = ? "
            + "WHERE orderId = ?;";

    private static final String GET_ORDER_BY_ID = "SELECT o.OrderId, o.AccountId, a.Fullname, a.Phone, o.TotalAmount, o.DeliveryTime, "
            + "o.AddressId, address.Street, address.Ward, address.City, o.CreatedAt, o.Status, "
            + "p.paymentType "
            + "FROM bookstore.order o "
            + "JOIN account a ON o.AccountId = a.AccountId "
            + "JOIN address ON o.addressId = address.AddressId "
            + "JOIN Payment p ON o.OrderId = p.OrderId "
            + "WHERE o.OrderId = ?";

    private static final String GET_ORDERS = "SELECT o.OrderId, o.AccountId, a.Fullname, a.Phone, o.TotalAmount, o.DeliveryTime, "
            + "o.AddressId, address.Street, address.Ward, address.City, o.CreatedAt, o.Status, "
            + "p.paymentType "
            + "FROM bookstore.order o "
            + "JOIN account a ON o.AccountId = a.AccountId "
            + "JOIN address ON o.addressId = address.AddressId "
            + "JOIN Payment p ON o.OrderId = p.OrderId;";

    private static final String GET_ORDER_BY_ACCOUNTID = "SELECT * from bookstore.order o where o.accountId =? order by OrderId desc";

    private static final String UPDATE_STATUS_ORDER = "UPDATE bookstore.order SET Status = ? WHERE OrderId = ?";

    public boolean saveOrder(Order order) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        Order orderCheck = new Order();
        try {
            orderCheck = this.getOrderById(order.getOrderId());
            if (orderCheck == null) {
                database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
                connection = database.createConnection();

                stm = connection.prepareStatement(ADD_NEW_ORDER, Statement.RETURN_GENERATED_KEYS);
                stm.setInt(1, order.getAccountId());
                stm.setInt(2, order.getCartId());
                stm.setDouble(3, order.getTotalPrice());
                java.util.Date utilDeliveryDate = order.getDeliveryTime();
                java.sql.Date sqlDeliveryDate = new java.sql.Date(utilDeliveryDate.getTime());
                stm.setDate(4, sqlDeliveryDate);
                stm.setInt(5, order.getAddressId());
                java.util.Date utilOrderDate = order.getOrderDate();
                java.sql.Date sqlOrderDate = new java.sql.Date(utilOrderDate.getTime());
                stm.setDate(6, sqlOrderDate);
                stm.setString(7, order.getStatus());
                stm.executeUpdate();
                try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        order.setOrderId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Inserting order failed, no ID obtained.");
                    }
                }
            } else {
                database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
                connection = database.createConnection();
                stm = connection.prepareStatement(UPDATE_ORDER);
                stm.setInt(1, order.getAccountId());
                stm.setInt(2, order.getCartId());
                stm.setDouble(3, order.getTotalPrice());
                java.util.Date utilDeliveryDate = order.getDeliveryTime();
                java.sql.Date sqlDeliveryDate = new java.sql.Date(utilDeliveryDate.getTime());
                stm.setDate(4, sqlDeliveryDate);
                stm.setInt(5, order.getAddressId());
                stm.setDate(6, (Date) order.getOrderDate());
                stm.setString(7, order.getStatus());
                stm.setInt(8, order.getOrderId());
                stm.execute();
            }

            return true;
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

    public Order getOrderById(int id) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        Order order = null;
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_ORDER_BY_ID);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
//orderId, o.AccountId, a.Fullname, a.Phone, TotalAmount, DeliveryTime, o.AddressId, address.Street, address.Ward, address.City ,o.CreatedAt, o.Status
                order = new Order();
                Address address = new Address();
                order.setOrderId(rs.getInt("orderId"));
                order.setAccountId(rs.getInt("accountId"));
                order.setFullName(rs.getString("fullName"));
                order.setPhone(rs.getString("phone"));
                order.setAddressId(rs.getInt("addressId"));
                address.setAddressId(rs.getInt("addressId"));
                address.setStreet(rs.getString("street"));
                address.setWard(rs.getString("ward"));
                address.setCity(rs.getString("city"));
                order.setAddress(address);
                order.setTotalPrice(rs.getFloat("TotalAmount"));
                order.setDeliveryTime(rs.getDate("DeliveryTime"));
                order.setOrderDate(rs.getDate("CreatedAt"));
                order.setStatus(rs.getString("status"));
                order.setPaymentType(rs.getString("paymentType"));
            }
            return order;
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
        return null;
    }

    public List<Order> getOrdersByAccountId(int id) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        List<Order> list = new ArrayList<>();
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_ORDER_BY_ACCOUNTID);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("OrderId"));
                order.setAccountId(rs.getInt("accountId"));
                order.setAddressId(rs.getInt("addressId"));
                order.setTotalPrice(rs.getFloat("TotalAmount"));
                order.setDeliveryTime(rs.getDate("DeliveryTime"));
                order.setOrderDate(rs.getDate("CreatedAt"));
                order.setStatus(rs.getString("status"));
                list.add(order);
            }
            return list;
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
        return null;
    }

    public List<Order> getOrders() {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        List<Order> list = new ArrayList<>();
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_ORDERS);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
//orderId, o.AccountId, a.Fullname, a.Phone, TotalAmount, DeliveryTime, o.AddressId, address.Street, address.Ward, address.City ,o.CreatedAt, o.Status
                Order order = new Order();
                Address address = new Address();
                order.setOrderId(rs.getInt("orderId"));
                order.setAccountId(rs.getInt("accountId"));
                order.setFullName(rs.getString("fullName"));
                order.setPhone(rs.getString("phone"));
                order.setAddressId(rs.getInt("addressId"));
                address.setAddressId(rs.getInt("addressId"));
                address.setStreet(rs.getString("street"));
                address.setWard(rs.getString("ward"));
                address.setCity(rs.getString("city"));
                order.setAddress(address);
                order.setTotalPrice(rs.getFloat("TotalAmount"));
                order.setDeliveryTime(rs.getDate("DeliveryTime"));
                order.setOrderDate(rs.getDate("CreatedAt"));
                order.setStatus(rs.getString("status"));
                order.setPaymentType(rs.getString("paymentType"));
                list.add(order);
            }
            return list;
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
        return null;
    }

    public boolean updateStatusOrder(String status, int orderId) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(UPDATE_STATUS_ORDER);
            stm.setString(1, status);
            stm.setInt(2, orderId);
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return false;
        } finally {
            try {
                if (stm != null) {
                    stm.close();

                }
                if (database != null && connection != null) {
                    connection.close();
                }

            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
        }
    } 
}
