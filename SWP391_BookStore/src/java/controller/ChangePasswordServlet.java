package controller;

import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import dao.dbcontext.DatabaseFactory;
import dao.dbcontext.MySqlDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        String currentPassword = request.getParameter("currentpassword");
        String newPassword = request.getParameter("newpassword");
        String confirmPassword = request.getParameter("confirmpassword");


        // Kiểm tra xác nhận mật khẩu
        if (!newPassword.equals(confirmPassword)) {
            request.getSession().setAttribute("errorMessage", "⚠️ Mật khẩu mới không khớp!");
            response.sendRedirect("changepassword.jsp");
            return;
        }
        // checkdieu kien pass
        if (!isValidPassword(newPassword)) {
            request.getSession().setAttribute("errorMessage", "⚠️ Mật khẩu phải từ 8-16 ký tự, có chữ hoa, chữ thường, số và ký tự đặc biệt!");
            response.sendRedirect("changepassword.jsp");
            return;
        }

        MySqlDatabase database = null;
        Connection connection = null;
        PreparedStatement getPassStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet rs = null;

        try {
            // Tạo kết nối database
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();

            // Truy vấn mật khẩu cũ từ database
            String getPassQuery = "SELECT Password FROM Account WHERE Username = ?";
            getPassStmt = connection.prepareStatement(getPassQuery);
            getPassStmt.setString(1, username);
            rs = getPassStmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("Password");

                // Kiểm tra mật khẩu cũ
                if (!BCrypt.checkpw(currentPassword, hashedPassword)) {
                    request.getSession().setAttribute("errorMessage", "⚠️ Mật khẩu hiện tại không đúng!");
                    response.sendRedirect("changepassword.jsp");
                    return;
                }

                // Mã hóa mật khẩu mới
                String newHashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

                // Cập nhật mật khẩu mới
                String updateQuery = "UPDATE Account SET Password = ? WHERE Username = ?";
                updateStmt = connection.prepareStatement(updateQuery);
                updateStmt.setString(1, newHashedPassword);
                updateStmt.setString(2, username);
                int rowsUpdated = updateStmt.executeUpdate();

                if (rowsUpdated > 0) {
                    request.getSession().setAttribute("successMessage", "✅ Đổi mật khẩu thành công! Đang chuyển về hồ sơ...");
                    response.sendRedirect("changepassword.jsp");
                } else {
                    request.getSession().setAttribute("errorMessage", "⚠️ Lỗi khi cập nhật mật khẩu!");
                    response.sendRedirect("changepassword.jsp");
                }
            } else {
                request.getSession().setAttribute("errorMessage", "⚠️ Không tìm thấy tài khoản!");
                response.sendRedirect("changepassword.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "⚠️ Lỗi hệ thống!");
            response.sendRedirect("changepassword.jsp");
        } finally {
            // Đóng tài nguyên
            try {
                if (rs != null) rs.close();
                if (getPassStmt != null) getPassStmt.close();
                if (updateStmt != null) updateStmt.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$");
    }
}
