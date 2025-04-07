package controller;

/**
 *
 * @author DuongNHH
 */
import dao.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Account;
import model.Address;
import java.sql.Date;

@WebServlet(name = "UserProfileServlet", urlPatterns = {"/userprofileservlet"})
public class UserProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        AccountDAO accountDAO = new AccountDAO();

        Integer accountId = (Integer) session.getAttribute("accountId");
        System.out.println("accountId từ session: " + accountId);

        if (accountId == null || accountId == 0) {
            response.sendRedirect("Login.jsp");
            return;
        }

        Account account = accountDAO.getUserProfile(accountId);
        request.setAttribute("account", account);

        String action = request.getParameter("action");
        if ("edit".equals(action)) {
            request.getRequestDispatcher("editprofile.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        AccountDAO accountDAO = new AccountDAO();

        Integer accountId = (Integer) session.getAttribute("accountId");
        if (accountId == null || accountId == 0) {
            response.sendRedirect("Login.jsp");
            return;
        }

        // Lấy tài khoản hiện tại từ DB
        Account account = accountDAO.getUserProfile(accountId);
        if (account == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        try {
            // Lấy thông tin từ form
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String dobStr = request.getParameter("dob");
            String gender = request.getParameter("gender");

            // Xử lý ngày sinh hợp lệ
             Date dob = (dobStr != null && !dobStr.isEmpty()) ? (java.sql.Date)Date.valueOf(dobStr) : (java.sql.Date)account.getDob();

            // Cập nhật dữ liệu nếu có thay đổi
            if (!fullname.equals(account.getFullname())) {
                account.setFullname(fullname);
            }
            if (!email.equals(account.getEmail())) {
                account.setEmail(email);
            }
            if (!phone.equals(account.getPhone())) {
                account.setPhone(phone);
            }
            if (!dob.equals(account.getDob())) {
                account.setDob(dob);
            }
            if (!gender.equals(account.getGender())) {
                account.setGender(gender);
            }
      

            boolean updateSuccess = accountDAO.updateUserProfile(account);
            if (updateSuccess) {
                Account updatedAccount = accountDAO.getUserProfile(accountId);
                session.setAttribute("account", updatedAccount);
                session.setAttribute("message", "Cập nhật thành công!");
                response.sendRedirect("userprofileservlet");
            } else {
                request.setAttribute("account", account);
                request.setAttribute("errorMessage", "Cập nhật thất bại. Vui lòng thử lại!");
                request.getRequestDispatcher("editprofile.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi xử lý dữ liệu! Vui lòng nhập đúng định dạng.");
            request.getRequestDispatcher("editprofile.jsp").forward(request, response);
        }

    }

    @Override
    public String getServletInfo() {
        return "User Profile Servlet";
    }
}
