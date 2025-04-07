/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CartDAO;
import dao.CartItemDAO;
import dao.OrderDAO;
import dao.OrderDetailDAO;
import dao.PaymentDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Cart;
import model.CartItem;
import model.Order;
import model.OrderDetail;
import model.Payment;

/**
 *
 * @author TungPham
 */
@WebServlet(name = "PaymentController", urlPatterns = {"/payment-banking"})
public class PaymentBankingServlet extends HttpServlet {

    OrderDAO orderDAO = new OrderDAO();
    OrderDetailDAO orderdetailDAO = new OrderDetailDAO();
    CartDAO cartDAO = new CartDAO();
    CartItemDAO cartItemDAO = new CartItemDAO();
    PaymentDAO paymentDAO = new PaymentDAO();
            /**
             * Processes requests for both HTTP <code>GET</code> and
             * <code>POST</code> methods.
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
            out.println("<title>Servlet PaymentController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PaymentController at " + request.getContextPath() + "</h1>");
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
        RequestDispatcher rq = request.getRequestDispatcher("payment-banking.jsp");
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
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        Cart cart = cartDAO.getCartByAccountID(account.getAccountId());
        Order order = (Order) session.getAttribute("order");
        int totalQuantity = (int) session.getAttribute("totalQuantity");
        request.setAttribute("buyerName", account.getFullname());
        request.setAttribute("quantity", totalQuantity);
        request.setAttribute("totalPrice", order.getTotalPrice());
        request.setAttribute("address", order.getAddress());
        List<CartItem> items = cartItemDAO.getCartItemByCartID(cart.getCartId());
        order.setStatus("PAYMENT PROCESSING");
        orderDAO.saveOrder(order);
        for (CartItem cartItem : items) {
            OrderDetail detail = new OrderDetail();
            detail.setBookId(cartItem.getBookId());
            detail.setOrderId(order.getOrderId());
            detail.setQuantity(cartItem.getQuantity());
            detail.setTotalPrice(cartItem.getPrice());
            orderdetailDAO.saveOrderDetail(detail);
        }
        cartItemDAO.deleteAllCartItemByCartId(cart.getCartId());
        String transactionCode = request.getParameter("transactionCode");
        Payment payment = new Payment();
        payment.setAccountId(account.getAccountId());
        payment.setOrderId(order.getOrderId());
        payment.setPaymentType("Banking");
        payment.setTotalAmount((float) order.getTotalPrice());
        payment.setTransactionCode(transactionCode);
        payment.setStatus("PAYMENT PROCESSING");
        paymentDAO.addPayment(payment);
        RequestDispatcher rq = request.getRequestDispatcher("thanks.jsp");
        rq.forward(request, response);
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
