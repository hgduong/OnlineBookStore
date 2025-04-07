package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import dao.dbcontext.DatabaseFactory;
import dao.dbcontext.MySqlDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;

@WebServlet("/EditProfileServlet")
public class EditProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Account account = (Account) request.getSession().getAttribute("account");
        if (account == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        MySqlDatabase database = null;
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();

                request.setAttribute("accountId", account.getAccountId());
                request.setAttribute("fullname", account.getFullname());
                request.setAttribute("email", account.getEmail());
                request.setAttribute("phone", account.getPhone());
                request.setAttribute("dob", account.getDob());
                request.setAttribute("gender", account.getGender());
            

            // Lấy địa chỉ chính
            stm = connection.prepareStatement("SELECT * FROM Address WHERE AccountId = ? AND isMain = 1 LIMIT 1");
            stm.setInt(1, rs.getInt("AccountId"));
            rs = stm.executeQuery();

            if (rs.next()) {
                request.setAttribute("street", rs.getString("Street"));
                request.setAttribute("ward", rs.getString("Ward"));
                request.setAttribute("city", rs.getString("City"));
            }

            request.getRequestDispatcher("editprofile.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        

        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String street = request.getParameter("street");
        String ward = request.getParameter("ward");
        String city = request.getParameter("city");
        String addNewAddress = request.getParameter("addNewAddress"); // Checkbox cho thêm địa chỉ mới

        if (!phone.matches("^\\d{10,11}$")) {
            request.getSession().setAttribute("errorMessage", "Số điện thoại không hợp lệ!");
            response.sendRedirect("editprofile.jsp");
            return;
        }

        MySqlDatabase database = null;
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();

            int accountId = account.getAccountId();

            // Cập nhật thông tin tài khoản nếu có thay đổi
            stm = connection.prepareStatement("UPDATE Account SET Fullname = ?, Email = ?, Phone = ?, DoB = ?, Gender = ? WHERE AccountId = ?");
            stm.setString(1, fullname);
            stm.setString(2, email);
            stm.setString(3, phone);
            stm.setString(4, dob);
            stm.setString(5, gender);
            stm.setInt(6, accountId);
            stm.executeUpdate();

            // Kiểm tra xem địa chỉ đã tồn tại chưa
            stm = connection.prepareStatement("SELECT AddressId FROM Address WHERE AccountId = ? AND isMain = 1");
            stm.setInt(1, accountId);
            rs = stm.executeQuery();

            if (rs.next()) {
                // Cập nhật địa chỉ chính nếu có thay đổi
                stm = connection.prepareStatement("UPDATE Address SET Street = ?, Ward = ?, City = ? WHERE AccountId = ? AND isMain = 1");
                stm.setString(1, street);
                stm.setString(2, ward);
                stm.setString(3, city);
                stm.setInt(4, accountId);
                stm.executeUpdate();
            }

            // Thêm địa chỉ mới nếu được chọn
            if (addNewAddress != null) {
                String newStreet = request.getParameter("newStreet");
                String newWard = request.getParameter("newWard");
                String newCity = request.getParameter("newCity");

                stm = connection.prepareStatement("INSERT INTO Address (AccountId, isMain, Street, Ward, City) VALUES (?, 0, ?, ?, ?)");
                stm.setInt(1, accountId);
                stm.setString(2, newStreet);
                stm.setString(3, newWard);
                stm.setString(4, newCity);
                stm.executeUpdate();
            }

            request.getSession().setAttribute("successMessage", "Cập nhật thành công!");
            response.sendRedirect("editprofile.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Lỗi hệ thống!");
            response.sendRedirect("editprofile.jsp");
        }
    }
}
