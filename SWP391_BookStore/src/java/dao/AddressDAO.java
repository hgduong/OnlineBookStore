/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.dbcontext.DatabaseFactory;
import dao.dbcontext.MySqlDatabase;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Address;

/**
 *
 * @author TungPham
 */
public class AddressDAO {

    private final static String GET_ADDRESS_BY_ACCOUNTID = "select addressId, isMain, Street, Ward, City from Address where accountId = ?";
    private final static String GET_ADDRESS_BY_ID = "select addressId, isMain, Street, Ward, City from Address where addressId = ?";
    private final static String ADD_ADDRESS = "INSERT INTO Address (AccountId, isMain, Street, Ward, City) VALUES (?, ?, ?, ?, ?);";
    public List<Address> getAddressesByAccountId(int accountId) {
        List<Address> addresses = new ArrayList<>();
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_ADDRESS_BY_ACCOUNTID);
            stm.setInt(1, accountId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Address address = new Address();
                address.setAddressId(rs.getInt("AddressId"));
                address.setIsMain(rs.getBoolean("isMain"));
                address.setStreet(rs.getString("Street"));
                address.setWard(rs.getString("Ward"));
                address.setCity(rs.getString("City"));
                addresses.add(address);
            }
            return addresses;
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
        return addresses;
    }

    public Address getAddressById(int addressId) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        Address address = null;
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_ADDRESS_BY_ID);
            stm.setInt(1, addressId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                address = new Address();
                address.setAddressId(rs.getInt("AddressId"));
                address.setIsMain(rs.getBoolean("isMain"));
                address.setStreet(rs.getString("Street"));
                address.setWard(rs.getString("Ward"));
                address.setCity(rs.getString("City"));
                return address;
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
        return address;
    }
    
    public boolean addAddress(Address address) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(ADD_ADDRESS);//AccountId, isMain, Street, Ward, City
            stm.setInt(1, address.getAccountId());
            int isMain = 1;
            if(!address.isIsMain()){
                isMain = 0;
            }
            stm.setInt(2, isMain);
            stm.setString(3, address.getStreet());
            stm.setString(4, address.getWard());
            stm.setString(5, "Ha Noi");
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
    
}
