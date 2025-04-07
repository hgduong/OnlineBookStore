package controller;

/**
 *
 * @author DuongNHH
 */
import dao.AccountDAO;
import dao.CartDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Cart;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private AccountDAO accountDAO = new AccountDAO();
    private CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String rememberedUsername = "";
        String rememberedPassword = "";

        if (session != null) {
            rememberedUsername = (String) session.getAttribute("rememberedUsername");
            rememberedPassword = (String) session.getAttribute("rememberedPassword");
        }

        request.setAttribute("username", "");
        request.setAttribute("password", "");
        RequestDispatcher rq = request.getRequestDispatcher("Login.jsp");
        rq.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        String contextPath = request.getContextPath();
        Account account = accountDAO.Login(username, password);

        if (account != null) {
            HttpSession session = request.getSession();
            session.setAttribute("accountId", account.getAccountId());
            session.setAttribute("username", username);
            session.setAttribute("account", account);
            if ("true".equals(remember)) {
                session.setAttribute("rememberedUsername", username);
                session.setAttribute("rememberedPassword", password); // Lưu mật khẩu vào session
                session.setMaxInactiveInterval(7 * 24 * 60 * 60); // Session lưu trong 7 ngày
            } else {
                session.removeAttribute("rememberedUsername");
                session.removeAttribute("rememberedPassword");
            }

            if (account.getRoleId() == 2) {
                Cart cart = cartDAO.getCartByAccountID(account.getAccountId());
                if (cart == null) {
                    cartDAO.createCartForUser(account.getAccountId());
                }
                response.sendRedirect(contextPath + "/homepage");
            } else if (account.getRoleId() == 3) {
                response.sendRedirect(contextPath + "/manager.jsp");
            } else {
                response.sendRedirect(contextPath + "/ListServlet");
            }
        } else {
            Account tempAccount = accountDAO.getAccountByUsername(username);
            if (tempAccount != null && !"ACTIVE".equalsIgnoreCase(tempAccount.getStatus())) {
                request.setAttribute("errorMessage", "Tài khoản của bạn không hoạt động!");
            } else {
                request.setAttribute("errorMessage", "Sai tài khoản hoặc mật khẩu!");
            }

            RequestDispatcher rq = request.getRequestDispatcher("Login.jsp");
            rq.forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet xử lý đăng nhập";
    }
}
