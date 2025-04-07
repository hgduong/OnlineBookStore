/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dao.AddressDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import model.Address;

/**
 *
 * @author TungPham
 */
@WebServlet(name = "AddAddressServlet", urlPatterns = {"/addressservlet"})
public class AddAddressServlet extends HttpServlet {

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
        processRequest(request, response);
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
        int accountId = (int) request.getSession().getAttribute("accountId");
        String street = request.getParameter("street");
        String ward = request.getParameter("ward");
        String city = request.getParameter("city");

        Address address = new Address();
        address.setAccountId(accountId);
        address.setStreet(street);
        address.setWard(ward);
        address.setCity(city);
        address.setIsMain(false);
        AddressDAO addressDAO = new AddressDAO();
        boolean success = addressDAO.addAddress(address);

        if (success) {
            // Phương thức này chỉ chuẩn bị request, không thực hiện chuyển hướng
            // request.getRequestDispatcher(contextPath+"/userprofileservlet");

            // Sử dụng sendRedirect để chuyển hướng sau khi thêm thành công
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/userprofileservlet");
        } else {
            // Xử lý khi thêm không thành công
            request.setAttribute("error", "Failed to add address");
            request.getRequestDispatcher("/addnewaddress.jsp").forward(request, response);
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
