package controller;

import dao.dbcontext.DatabaseFactory;
import dao.dbcontext.MySqlDatabase;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import model.Account;
import model.Role;

@WebServlet("/ListServlet")
public class UserListServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UserListServlet.class.getName());
    private static final int RECORDS_PER_PAGE = 10; // Số bản ghi trên mỗi trang

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Account> userList = new ArrayList<>();
        String sort = request.getParameter("sort");
        String search = request.getParameter("search");
        int page = 1;

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        int start = (page - 1) * RECORDS_PER_PAGE;
        int totalRecords = 0;
        int totalPages = 1;

        try {
            MySqlDatabase database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            try (Connection conn = database.createConnection()) {

                String countQuery = "SELECT COUNT(*) FROM Account WHERE 1=1";
                if (search != null && !search.trim().isEmpty()) {
                    countQuery += " AND (Fullname LIKE ? OR Email LIKE ? OR DoB LIKE ? OR Phone LIKE ? OR Gender LIKE ?)";
                }

                try (PreparedStatement countPs = conn.prepareStatement(countQuery)) {
                    if (search != null && !search.trim().isEmpty()) {
                        for (int i = 1; i <= 5; i++) {
                            countPs.setString(i, "%" + search + "%");
                        }
                    }
                    try (ResultSet rs = countPs.executeQuery()) {
                        if (rs.next()) {
                            totalRecords = rs.getInt(1);
                        }
                    }
                }

                // Tính tổng số trang
                totalPages = (int) Math.ceil((double) totalRecords / RECORDS_PER_PAGE);

                // Truy vấn lấy danh sách người dùng theo trang
                String sql = "SELECT a.AccountId, a.Fullname, a.DoB, a.Email, a.Phone, a.Gender, "
                        + "r.RoleId, r.RoleName, a.Status FROM Account a "
                        + "LEFT JOIN Role r ON a.RoleId = r.RoleId WHERE 1=1";

                if (search != null && !search.trim().isEmpty()) {
                    sql += " AND (Fullname LIKE ? OR Email LIKE ? OR DoB LIKE ? OR Phone LIKE ? OR Gender LIKE ?)";
                }

                if ("name".equals(sort)) {
                    sql += " ORDER BY Fullname";
                } else if ("dob".equals(sort)) {
                    sql += " ORDER BY DoB";
                } else {
                    sql += " ORDER BY AccountId";
                }

                sql += " LIMIT ? OFFSET ?"; // Phân trang

                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    int paramIndex = 1;
                    if (search != null && !search.trim().isEmpty()) {
                        for (int i = 1; i <= 5; i++) {
                            ps.setString(paramIndex++, "%" + search + "%");
                        }
                    }
                    ps.setInt(paramIndex++, RECORDS_PER_PAGE);
                    ps.setInt(paramIndex, start);

                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            Account user = new Account();
                            user.setAccountId(rs.getInt("AccountId"));
                            user.setFullname(Optional.ofNullable(rs.getString("Fullname")).orElse("N/A"));
                            user.setDob(rs.getDate("DoB"));
                            user.setEmail(Optional.ofNullable(rs.getString("Email")).orElse("N/A"));
                            user.setPhone(Optional.ofNullable(rs.getString("Phone")).orElse("N/A"));
                            user.setGender(Optional.ofNullable(rs.getString("Gender")).orElse("Khác"));

                            Role role = new Role();
                            int roleId = rs.getObject("RoleId") != null ? rs.getInt("RoleId") : -1;
                            role.setRoleId(roleId);
                            role.setRoleName(Optional.ofNullable(rs.getString("RoleName")).orElse("N/A"));

                            user.setRole(role);
                            user.setStatus(rs.getString("Status"));
                            userList.add(user);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving user list", e);
        }

        request.setAttribute("userList", userList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("userlist.jsp").forward(request, response);
    }
}
