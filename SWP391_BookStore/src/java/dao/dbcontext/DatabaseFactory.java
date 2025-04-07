/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.dbcontext;

import java.sql.SQLException;

/**
 *
 * @author TungPham
 */
public class DatabaseFactory {
    private final static int SQL_SERVER = 0;
    private final static int MYSQL = 1;

    public static IDataBase createDataBase(int sgdb) {
        if (sgdb == MYSQL) {
            try {
                return MySqlDatabase.getInstall();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                System.out.println("Error occurse when init SqlserverDatabase: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
        return new SqlServerDataBase();
    }
}
