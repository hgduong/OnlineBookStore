package controller;

/**
 *
 * @author DuongNHH
 */
import dao.AddressDAO;
import dao.dbcontext.DatabaseFactory;
import dao.dbcontext.MySqlDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import model.Address;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name = "RegisterServlet1", urlPatterns = {"/register1"})
public class RegisterServlet1 extends HttpServlet {

    AddressDAO addressDAO = new AddressDAO();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy dữ liệu từ form
        String fullname = request.getParameter("Fullname");
        String username = request.getParameter("Username");
        String email = request.getParameter("Email");
        String password = request.getParameter("Password");
        String confirmPassword = request.getParameter("ConfirmPassword");
        String dob = request.getParameter("DoB");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("Gender");
        String roleIdStr = request.getParameter("Role");
        
        String street = request.getParameter("street");
        String ward = request.getParameter("Ward");
        // dieu kien cac truong
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,16}$";
        // Kiểm tra độ dài của các trường nhập vào 
        if (fullname.length() > 50 || username.length() > 50 || email.length() > 50 || password.length() > 50 || phone.length() > 50 || gender.length() > 50) {
            request.setAttribute("errorMessage", "Dữ liệu nhập vào không được quá 50 ký tự.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

        // Kiểm tra xem các trường có toàn dấu cách không
        if (fullname.trim().isEmpty() || username.trim().isEmpty() || email.trim().isEmpty()
                || password.trim().isEmpty() || confirmPassword.trim().isEmpty()
                || phone.trim().isEmpty() || gender.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Không được nhập toàn dấu cách.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

        // email, username khong chua dau cach
        String noSpaceNoAccentPattern = "^[a-zA-Z0-9@._-]+$"; 

        if (!username.matches(noSpaceNoAccentPattern) || !email.matches(noSpaceNoAccentPattern) || !phone.matches("^[0-9]+$")) {
            request.setAttribute("errorMessage", "Username, Email và Số điện thoại không được chứa dấu cách hoặc ký tự đặc biệt.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

        // Mặc định RoleId là 2 (User)
        int roleId = 2;
        if (roleIdStr != null && !roleIdStr.isEmpty()) {
            try {
                roleId = Integer.parseInt(roleIdStr);
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "RoleId không hợp lệ.");
                request.getRequestDispatcher("Register.jsp").forward(request, response);
                return;
            }
        }

        // Kiểm tra mật khẩu 
        if (!password.matches(passwordPattern)) {
            request.setAttribute("errorMessage", "Mật khẩu phải có 8-16 ký tự, chứa ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu nhập lại 
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Mật khẩu xác nhận không khớp.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

        // Mã hóa mật khẩu bằng BCrypt
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Kết nối Database
            MySqlDatabase database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            conn = database.createConnection();

            // Kiểm tra RoleId có tồn tại trong bảng Role không
            String checkRoleSQL = "SELECT RoleId FROM Role WHERE RoleId = ?";
            stmt = conn.prepareStatement(checkRoleSQL);
            stmt.setInt(1, roleId);
            rs = stmt.executeQuery();

            if (!rs.next()) {  // Nếu RoleId không tồn tại
                request.setAttribute("errorMessage", "RoleId không hợp lệ. Liên hệ admin.");
                request.getRequestDispatcher("Register.jsp").forward(request, response);
                return;
            }
            rs.close();
            stmt.close();

            // Lấy AccountId lớn nhất và tăng lên 1
            String getMaxIdSQL = "SELECT COALESCE(MAX(AccountId), 0) + 1 FROM Account;";
            stmt = conn.prepareStatement(getMaxIdSQL);
            rs = stmt.executeQuery();
            int newAccountId = rs.next() ? rs.getInt(1) : 1; // Nếu database rỗng thì bắt đầu từ 1
            rs.close();
            stmt.close();

            // Chèn dữ liệu vào bảng Account
            String insertSQL = "INSERT INTO Account (AccountId, Username, Password, RoleId, Fullname, Phone, DoB, Gender, Email, CreatedAt, UpdatedAt, Status) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW(), 'ACTIVE')";
            stmt = conn.prepareStatement(insertSQL);
            stmt.setInt(1, newAccountId);
            stmt.setString(2, username);
            stmt.setString(3, hashedPassword);
            stmt.setInt(4, roleId);
            stmt.setString(5, fullname);
            stmt.setString(6, phone);
            stmt.setDate(7, (dob != null && !dob.isEmpty()) ? Date.valueOf(dob) : null);
            stmt.setString(8, gender);
            stmt.setString(9, email);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                response.sendRedirect("Login.jsp");
            } else {
                request.setAttribute("errorMessage", "Đăng ký thất bại. Vui lòng thử lại.");
                request.getRequestDispatcher("Register.jsp").forward(request, response);
            }
            
            Address address = new Address();
            address.setAccountId(newAccountId);
            address.setIsMain(true);
            address.setStreet(street);
            address.setWard(ward);
            addressDAO.addAddress(address);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi database: " + e.getMessage());
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet xử lý đăng ký người dùng";
    }
}
