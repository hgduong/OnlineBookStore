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
public class SqlServerDataBase implements IDataBase {

    private static SqlServerDataBase install = null;

    @Override
    public Connection createConnection() {
        return null;
    }

    @Override
    public void closeConnection(Connection con) throws SQLException {
        con.close();
    }

}
