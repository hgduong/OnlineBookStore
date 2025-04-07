/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.OrderDAO;
import dao.PaymentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Order;
import model.Payment;

/**
 *
 * @author TungPham
 */
@WebServlet(name = "MOrderListServlet", urlPatterns = {"/list-order"})
public class MOrderListServlet extends HttpServlet {

    OrderDAO orderDAO = new OrderDAO();
    PaymentDAO paymentDAO = new PaymentDAO();

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MOrderListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MOrderListServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        List<Order> list = orderDAO.getOrders();
        request.setAttribute("orderList", list);
        request.getRequestDispatcher("morder-list.jsp").forward(request, response);
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
        String status = request.getParameter("status");
        String orderIdStr = request.getParameter("orderId");
        int orderId = Integer.parseInt(orderIdStr);
        boolean updateSuccess = orderDAO.updateStatusOrder(status, orderId);

        response.setContentType("application/json");
        if (updateSuccess) {
            Payment payment = paymentDAO.getPaymentByOrderId(orderId);
            if (payment.getPaymentType().equals("Banking")) {
                if (status.equals("PAYMENT PROCESSING")) {
                    payment.setStatus("PAYMENT PROCESSING");
                } else if (status.equals("PAYMENT COMPLETED") || status.equals("ORDER PREPARING") || status.equals("DELIVERING") || status.equals("COMPLETED")) {
                    payment.setStatus("PAYMENT COMPLETED");
                } else if (status.equals("PAYMENT FAILED")) {
                    payment.setStatus("PAYMENT FAILED");
                } else if (status.equals("RETURNED")) {
                    payment.setStatus("PAYMENT RETURNING");
                }
            } else if (payment.getPaymentType().equals("COD")) {
                if (status.equals("MAIL CONFIRMED")|| status.equals("ORDER PREPARING") || status.equals("DELIVERING")) {
                    payment.setStatus("MAIL CONFIRMED");
                } else if (status.equals("COMPLETED")) {
                    payment.setStatus("PAYMENT COMPLETED");
                } else if (status.equals("RETURNED")) {
                    payment.setStatus("PAYMENT RETURNING");
                }
            }

                    paymentDAO.updateStatusPayment(payment.getStatus(), payment.getPaymentId());
            response.getWriter().write("{\"success\": true}");
        } else {
            response.getWriter().write("{\"success\": false, \"message\": \"Failed to update status\"}");
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
