/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package global;

/**
 *
 * @author TungPham
 */
public class Comm {
    public static String driver = "com.mysql.cj.jdbc.Driver";
    public static String db_url = "jdbc:mysql://127.0.0.1:3306/BookStore?sslMode=REQUIRED";
    public static String db_username = "root";
    public static String db_password = "123456";

    public static float round2decimal(float number){
        int result = Math.round(number*100);
        float result1 = (float)result/100;
        return result1;
    }
}
