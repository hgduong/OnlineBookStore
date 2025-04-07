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
import model.Order;
import model.Payment;

/**
 *
 * @author TungPham
 */
public class PaymentDAO {

    private static final String ADD_NEW_PAYMENT = "INSERT INTO Payment (AccountId, PaymentType, TransactionCode, OrderId, TotalAmount, Status) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String GET_PAYMENTS = "SELECT p.PaymentId, p.AccountId, a.Fullname, p.PaymentType, p.TransactionCode, "
            + "p.OrderId, p.TotalAmount, p.Status "
            + "FROM Payment p "
            + "JOIN Account a ON p.AccountId = a.AccountId";

    private static final String GET_PAYMENT_BY_ID = "SELECT * FROM Payment WHERE paymentId = ? ";
    private static final String GET_PAYMENT_BY_ORDERID = "SELECT * FROM Payment WHERE orderId = ? ";
    private static final String UPDATE_STATUS_PAYMENT = "UPDATE Payment SET Status = ? WHERE PaymentId = ?";

    public boolean addPayment(Payment payment) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;

        try {

            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(ADD_NEW_PAYMENT, Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, payment.getAccountId());
            stm.setString(2, payment.getPaymentType());
            stm.setString(3, payment.getTransactionCode());
            stm.setInt(4, payment.getOrderId());
            stm.setFloat(5, payment.getTotalAmount());
            stm.setString(6, payment.getStatus());
            stm.execute();

            try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    payment.setPaymentId(generatedKeys.getInt(1));
                    return true;
                } else {
                    throw new SQLException("Inserting payment failed, no ID obtained.");
                }
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

    public List<Payment> getPayments() {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        List<Payment> list = new ArrayList<>();
        try {

            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_PAYMENTS);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getInt("PaymentId"));
                payment.setAccountId(rs.getInt("AccountId"));
                payment.setAccountName(rs.getString("FullName"));
                payment.setPaymentType(rs.getString("PaymentType"));
                payment.setTransactionCode(rs.getString("TransactionCode"));
                payment.setOrderId(rs.getInt("OrderId"));
                payment.setTotalAmount(rs.getFloat("TotalAmount"));
                payment.setStatus(rs.getString("Status"));
                list.add(payment);
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

    public Payment getPaymentById(int id) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        Payment payment = null;
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_PAYMENT_BY_ID);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                payment = new Payment();
                payment.setPaymentId(rs.getInt("PaymentId"));
                payment.setAccountId(rs.getInt("AccountId"));
                payment.setPaymentType(rs.getString("PaymentType"));
                payment.setTransactionCode(rs.getString("TransactionCode"));
                payment.setOrderId(rs.getInt("OrderId"));
                payment.setTotalAmount(rs.getFloat("TotalAmount"));
                payment.setStatus(rs.getString("Status"));
            }
            return payment;

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
        return payment;
    }
    
    public Payment getPaymentByOrderId(int id) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        Payment payment = null;
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_PAYMENT_BY_ORDERID);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                payment = new Payment();
                payment.setPaymentId(rs.getInt("PaymentId"));
                payment.setAccountId(rs.getInt("AccountId"));
                payment.setPaymentType(rs.getString("PaymentType"));
                payment.setTransactionCode(rs.getString("TransactionCode"));
                payment.setOrderId(rs.getInt("OrderId"));
                payment.setTotalAmount(rs.getFloat("TotalAmount"));
                payment.setStatus(rs.getString("Status"));
            }
            return payment;

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
        return payment;
    }

    public boolean updateStatusPayment(String status, int paymentId) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(UPDATE_STATUS_PAYMENT);
            stm.setString(1, status);
            stm.setInt(2, paymentId);
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
