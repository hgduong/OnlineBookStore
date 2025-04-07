/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.dbcontext.DatabaseFactory;
import dao.dbcontext.MySqlDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Address;
import model.Author;
import model.Book;
import model.Category;
import model.Role;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author TungPham
 */
public class AccountDAO {

    private static final String LOGIN = "select * from account where Username =? and password= ?";
    private static final String UPDATE_ACCOUNT = "UPDATE account SET status = ?, fullname = ?, username = ?, email = ?, phone = ?, roleId = ? WHERE accountId = ?";

    public Account login(String username, String password) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        Account account = null;
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(LOGIN);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                account = new Account();
                account.setAccountId(rs.getInt("AccountId"));
                account.setStatus(rs.getString("Status"));
                account.setFullname(rs.getString("Fullname"));
                account.setUsername(rs.getString("Username"));
                account.setEmail(rs.getString("Email"));
                account.setPhone(rs.getString("Phone"));
                account.setRoleId(rs.getInt("RoleId"));
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
        return account;
    }

    public Account Login(String username, String password) {
        Account account = null;
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        ResultSet rs = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement("SELECT * FROM Account WHERE username = ?");
            stm.setString(1, username);
            rs = stm.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("Password");
                String status = rs.getString("Status"); // Lấy trạng thái tài khoản

                if (!"Active".equalsIgnoreCase(status)) {
                    return null; // Nếu tài khoản không hoạt động, trả về null
                }

                // Kiểm tra mật khẩu nhập vào có đúng với mật khẩu đã mã hóa không
                if (BCrypt.checkpw(password, hashedPassword)) {
                    account = new Account();
                    account.setAccountId(rs.getInt("AccountId"));
                    account.setStatus(rs.getString("Status"));
                    account.setFullname(rs.getString("Fullname"));
                    account.setUsername(rs.getString("Username"));
                    account.setEmail(rs.getString("Email"));
                    account.setPhone(rs.getString("Phone"));
                    account.setRoleId(rs.getInt("RoleId"));
                    return account;
                }
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
        }
        return null;
    }

    public Account getAccountByUsername(String username) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        ResultSet rs = null;
        Account account = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement("SELECT * FROM Account WHERE username = ?");
            stm.setString(1, username);
            rs = stm.executeQuery();

            if (rs.next()) {
                account = new Account();
                account.setAccountId(rs.getInt("AccountId"));
                account.setUsername(rs.getString("Username"));
                account.setStatus(rs.getString("Status")); // Lấy trạng thái tài khoản
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
        }
        return account;
    }

    public Account getUserProfile(int accountId) {
        Account account = null;
        List<Address> addresses = new ArrayList<>();
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;

        String query = "SELECT a.*, r.RoleName, r.Status AS RoleStatus, "
                + "addr.AddressId, addr.Street, addr.Ward, addr.City, addr.isMain "
                + "FROM Account a "
                + "JOIN Role r ON a.RoleId = r.RoleId "
                + "LEFT JOIN Address addr ON a.AccountId = addr.AccountId "
                + "WHERE a.AccountId = ?";

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(query);
            stm.setInt(1, accountId);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                if (account == null) {
                    Role role = new Role(
                            rs.getInt("RoleId"),
                            rs.getString("RoleName"),
                            rs.getString("RoleStatus")
                    );

                    account = new Account();
                    account.setAccountId(rs.getInt("AccountId"));
                    account.setUsername(rs.getString("Username"));
                    account.setPassword(rs.getString("Password"));
                    account.setRole(role);
                    account.setFullname(rs.getString("Fullname"));
                    account.setPhone(rs.getString("Phone"));
                    account.setDob(rs.getDate("Dob"));
                    account.setGender(rs.getString("Gender"));
                    account.setEmail(rs.getString("Email"));
                    account.setCreatedAt(rs.getDate("CreatedAt"));
                    account.setUpdatedAt(rs.getDate("UpdatedAt"));
                    account.setStatus(rs.getString("Status"));
                }

                int addressId = rs.getInt("AddressId");
                if (addressId > 0) {
                    Address address = new Address(
                            addressId,
                            accountId,
                            rs.getBoolean("isMain"),
                            rs.getString("Street"),
                            rs.getString("Ward"),
                            rs.getString("City")
                    );
                    addresses.add(address);
                }
            }

            if (account != null) {
                account.setAddresses(addresses); // Gán danh sách địa chỉ vào tài khoản
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (database != null && connection != null) {
                    connection.close();
                }
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
        }
        return account;
    }

    public boolean updateUserProfile(Account account) {
        MySqlDatabase database = null;
        Connection connection = null;
        PreparedStatement stm = null;
        boolean updated = false;

        String updateAccountQuery = "UPDATE Account SET Fullname = ?, Email = ?, Phone = ?, Dob = ?, Gender = ?, UpdatedAt = NOW() WHERE AccountId = ?";
        String updateAddressQuery = "UPDATE Address SET Street = ?, Ward = ?, City = ? WHERE AccountId = ? AND isMain = 1";

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            connection.setAutoCommit(false); // Bắt đầu transaction

            // Cập nhật thông tin tài khoản
            stm = connection.prepareStatement(updateAccountQuery);
            stm.setString(1, account.getFullname());
            stm.setString(2, account.getEmail());
            stm.setString(3, account.getPhone());
            stm.setDate(4, new java.sql.Date(account.getDob().getTime()));
            stm.setString(5, account.getGender());
            stm.setInt(6, account.getAccountId());
            stm.executeUpdate();
            stm.close();

            // Cập nhật địa chỉ chính (nếu có)
            if (account.getAddresses() != null && !account.getAddresses().isEmpty()) {
                Address mainAddress = account.getAddresses().get(0); // Lấy địa chỉ chính
                stm = connection.prepareStatement(updateAddressQuery);
                stm.setString(1, mainAddress.getStreet());
                stm.setString(2, mainAddress.getWard());
                stm.setString(3, mainAddress.getCity());
                stm.setInt(4, account.getAccountId());
                stm.executeUpdate();
                stm.close();
            }

            connection.commit(); // Xác nhận transaction
            updated = true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback(); // Hoàn tác nếu lỗi
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return updated;
    }

    public Account getAccountById(int accountId) {
        Account account = null;
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        ResultSet rs = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement("SELECT * FROM Account WHERE AccountId = ?");
            stm.setInt(1, accountId);
            rs = stm.executeQuery();

            if (rs.next()) {
                account = new Account();
                account.setAccountId(rs.getInt("AccountId"));
                account.setStatus(rs.getString("Status"));
                account.setFullname(rs.getString("Fullname"));
                account.setUsername(rs.getString("Username"));
                account.setEmail(rs.getString("Email"));
                account.setPhone(rs.getString("Phone"));
                account.setRoleId(rs.getInt("RoleId"));
                return account;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
        }
        return null;
    }


    public boolean updateAccount(Account account) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        boolean success = false;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();

            stm = connection.prepareStatement(UPDATE_ACCOUNT);

            stm.setString(1, account.getStatus());
            stm.setString(2, account.getFullname());
            stm.setString(3, account.getUsername());
            stm.setString(4, account.getEmail());
            stm.setString(5, account.getPhone());
            stm.setInt(6, account.getRoleId());
            stm.setInt(7, account.getAccountId());

            int rowsAffected = stm.executeUpdate();

            if (rowsAffected > 0) {
                success = true;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (database != null && connection != null) {
                    connection.close();
                }
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
        }

        return success;
    }
}
