/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.dbcontext;

import global.Comm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author TungPham
 */
public class MySqlDatabase implements IDataBase{
    
    private static MySqlDatabase install = null;
    private MySqlDatabase() {
        createConnection();
    }

    public static MySqlDatabase getInstall() throws SQLException {
        if (install == null) {
            install = new MySqlDatabase();
        }
        return install;
    }
    
    @Override
    public Connection createConnection() {
         Connection connection = null;
        try {
            Class.forName(Comm.driver).newInstance();

            connection = DriverManager.getConnection(Comm.db_url, Comm.db_username,
                    Comm.db_password);
        } catch (InstantiationException iE) {
            iE.printStackTrace();

        } catch (IllegalAccessException iaE) {
            iaE.printStackTrace();

        } catch (ClassNotFoundException cnfEx) {
            cnfEx.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("init sqlServerConnection false: "
                    + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

    
    
}
