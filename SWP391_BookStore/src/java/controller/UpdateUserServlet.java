/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Role;

/**
 *
 * @author TungPham
 */
@WebServlet(name = "UpdateUserServlet", urlPatterns = {"/UpdateUserServlet"})
public class UpdateUserServlet extends HttpServlet {

    /**
     * Handles the HTTP POST method for updating user role and status
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            // Get parameters from request
            String accountIdStr = request.getParameter("accountId");
            String roleIdStr = request.getParameter("roleId");
            String status = request.getParameter("status");
            
            // Validate parameters
            if (accountIdStr == null || roleIdStr == null || status == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"success\": false, \"message\": \"Missing required parameters\"}");
                return;
            }
            
            // Parse IDs
            int accountId = Integer.parseInt(accountIdStr);
            int roleId = Integer.parseInt(roleIdStr);
            
            // Create DAO
            AccountDAO accountDAO = new AccountDAO();
            
            // Get current account
            Account account = accountDAO.getAccountById(accountId);
            
            if (account == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"success\": false, \"message\": \"Account not found\"}");
                return;
            }
            
            // Update account fields
            Role role = new Role();
            role.setRoleId(roleId);
            account.setRole(role);
            account.setRoleId(roleId);
            account.setStatus(status);
            
            // Save updates
            boolean updateSuccess = accountDAO.updateAccount(account);
            
            if (updateSuccess) {
                out.print("{\"success\": true, \"message\": \"User updated successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"success\": false, \"message\": \"Failed to update user\"}");
            }
            
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"success\": false, \"message\": \"Invalid ID format\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
        } finally {
            out.close();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "UpdateUserServlet - Handles updating user role and status";
    }
}