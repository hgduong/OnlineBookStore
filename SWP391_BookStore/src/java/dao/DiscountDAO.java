package dao;

import dao.dbcontext.DatabaseFactory;
import dao.dbcontext.MySqlDatabase;
import model.Discount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountDAO {

    private static final String GET_DISCOUNTS = "SELECT DiscountId, StartDate, EndDate, DiscountPercent, Status FROM Discounts ORDER BY DiscountId";
    private static final String GET_DISCOUNT_BY_ID = "SELECT DiscountId, StartDate, EndDate, DiscountPercent, Status FROM Discounts WHERE DiscountId = ?";
    private static final String ADD_DISCOUNT = "INSERT INTO Discounts (StartDate, EndDate, DiscountPercent, Status) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_DISCOUNT = "UPDATE Discounts SET StartDate = ?, EndDate = ?, DiscountPercent = ?, Status = ? WHERE DiscountId = ?";
    private static final String DELETE_DISCOUNT = "DELETE FROM Discounts WHERE DiscountId = ?";

    public List<Discount> getDiscounts() {
        List<Discount> discounts = new ArrayList<>();
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_DISCOUNTS);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Discount discount = new Discount();
                discount.setDiscountId(rs.getInt("DiscountId"));
                discount.setStartDate(rs.getDate("StartDate"));
                discount.setEndDate(rs.getDate("EndDate"));
                discount.setDiscountPercent(rs.getDouble("DiscountPercent"));
                discount.setStatus(rs.getString("Status"));
                discounts.add(discount);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            closeResources(stm, connection, database, null);
        }
        return discounts;
    }

    public Discount getDiscountById(int discountId) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        Discount discount = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_DISCOUNT_BY_ID);
            stm.setInt(1, discountId);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                discount = new Discount();
                discount.setDiscountId(rs.getInt("DiscountId"));
                discount.setStartDate(rs.getDate("StartDate"));
                discount.setEndDate(rs.getDate("EndDate"));
                discount.setDiscountPercent(rs.getDouble("DiscountPercent"));
                discount.setStatus(rs.getString("Status"));
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            closeResources(stm, connection, database, null);
        }
        return discount;
    }

    public boolean addDiscount(Discount discount) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(ADD_DISCOUNT);
             stm.setDate(1, new java.sql.Date(discount.getStartDate().getTime()));
            stm.setDate(2, new java.sql.Date(discount.getEndDate().getTime()));
            stm.setDouble(3, discount.getDiscountPercent());
            stm.setString(4, discount.getStatus());

            int rowsInserted = stm.executeUpdate();
            return rowsInserted > 0; // Return true if at least one row was inserted
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return false; // Return false if an exception occurred
        } finally {
            closeResources(stm, connection, database, null);
        }
    }

    public boolean updateDiscount(Discount discount) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(UPDATE_DISCOUNT);
            stm.setDate(1, new java.sql.Date(discount.getStartDate().getTime()));
            stm.setDate(2, new java.sql.Date(discount.getEndDate().getTime()));
            stm.setDouble(3, discount.getDiscountPercent());
            stm.setString(4, discount.getStatus());
            stm.setInt(5, discount.getDiscountId());

            int rowsUpdated = stm.executeUpdate();
            return rowsUpdated > 0; // Return true if at least one row was updated
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return false; // Return false if an exception occurred
        } finally {
            closeResources(stm, connection, database, null);
        }
    }

    public boolean deleteDiscount(int discountId) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(DELETE_DISCOUNT);
            stm.setInt(1, discountId);

            int rowsDeleted = stm.executeUpdate();
            return rowsDeleted > 0; // Return true if at least one row was deleted
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return false; // Return false if an exception occurred
        } finally {
            closeResources(stm, connection, database, null);
        }
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
}