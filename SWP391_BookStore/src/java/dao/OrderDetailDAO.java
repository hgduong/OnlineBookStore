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
import java.util.ArrayList;
import java.util.List;
import model.Order;
import model.OrderDetail;

/**
 *
 * @author TungPham
 */
public class OrderDetailDAO {

    private final static String ADD_ORDERDETAIL = "INSERT INTO OrderDetails (OrderId, BookId, Quantity, TotalPrice) VALUES (?, ?, ?, ?);";
    private final static String GET_ORDERDETAIL_BY_ORDERID = "SELECT OrderDetailId, OrderId, od.BookId, b.Title, Quantity, TotalPrice FROM OrderDetails od JOIN Book b ON od.bookId = b.bookId WHERE OrderId = ?;";
    
    public String saveOrderDetail(OrderDetail orderDetail) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();

            stm = connection.prepareStatement(ADD_ORDERDETAIL);
            stm.setInt(1, orderDetail.getOrderId());
            stm.setInt(2, orderDetail.getBookId());
            stm.setInt(3, orderDetail.getQuantity());
            stm.setDouble(4, orderDetail.getTotalPrice());
            
            stm.execute();
            return "add-done";
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
        return "add-false";
    }
    
    public List<OrderDetail> getOrderDetailsByOrderId(int id) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        List<OrderDetail> list = new ArrayList<>();
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_ORDERDETAIL_BY_ORDERID);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                OrderDetail od = new OrderDetail();
                od.setOrderDetailId(rs.getInt("OrderDetailId"));  
                od.setOrderId(rs.getInt("OrderId"));
                od.setBookId(rs.getInt("BookId"));
                od.setBookName(rs.getString("Title"));
                od.setQuantity(rs.getInt("quantity"));
                od.setTotalPrice(rs.getFloat("TotalPrice"));
                list.add(od);
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
        return list;
    }
}
