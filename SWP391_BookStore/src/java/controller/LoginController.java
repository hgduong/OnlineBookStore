/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AccountDAO;
import dao.CartDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Cart;

/**
 *
 * @author TungPham
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login1"})
public class LoginController extends HttpServlet {

    AccountDAO accountDAO = new AccountDAO();
    CartDAO cartDAO = new CartDAO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rq = request.getRequestDispatcher("login.jsp");
        rq.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String errorMessage = "";
        String contextPath = request.getContextPath();
        Account account = accountDAO.login(username, password);
        if (account == null) {
            errorMessage = "Sai tên tài khoản hoặc mật khẩu";
            request.setAttribute("username", username);
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher rq = request.getRequestDispatcher("login.jsp");
            rq.forward(request, response);
        } else if (account.getStatus().equals("inactive")) {
            errorMessage = "Tài khoản đã bị vô hiệu hóa";
            request.setAttribute("username", username);
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher rq = request.getRequestDispatcher("login.jsp");
            rq.forward(request, response);
        } else {

            HttpSession session = request.getSession();
            session.setAttribute("account", account);
            if (account.getRoleId() == 2) {
                Cart cart = cartDAO.getCartByAccountID(account.getAccountId());
                if (cart == null) {
                    cartDAO.createCartForUser(account.getAccountId());
                }
                response.sendRedirect(contextPath + "/homepage");
            } else {
                response.sendRedirect(contextPath + "/manager.jsp");
            }

        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
