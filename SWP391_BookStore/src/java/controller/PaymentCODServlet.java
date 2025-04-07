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
import java.util.List;
import java.util.Random;
import model.Account;
import model.Cart;
import model.CartItem;
import model.Order;
import model.OrderDetail;
import model.Payment;
import service.EmailService;

/**
 *
 * @author TungPham
 */
@WebServlet(name = "PaymentCODServlet", urlPatterns = {"/payment-cod"})
public class PaymentCODServlet extends HttpServlet {

    OrderDAO orderDAO = new OrderDAO();
    OrderDetailDAO orderdetailDAO = new OrderDetailDAO();
    CartDAO cartDAO = new CartDAO();
    CartItemDAO cartItemDAO = new CartItemDAO();
    PaymentDAO paymentDAO = new PaymentDAO();
    EmailService emailService = new EmailService();

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
            out.println("<title>Servlet PaymentCODServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PaymentCODServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        String email = account.getEmail();
        String otp = generateOTP();
        session.setAttribute("otp", otp);
        session.setAttribute("otpTime", System.currentTimeMillis());
        session.setAttribute("email", email);
        emailService.sendOTP(email, otp);
        RequestDispatcher rq = request.getRequestDispatcher("cod-email-verify.jsp");
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

        String storedOtp = (String) session.getAttribute("otp");
        Long otpTime = (Long) session.getAttribute("otpTime");
        String enteredOtp = request.getParameter("otp");

        if (storedOtp != null && otpTime != null) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - otpTime > 60000) { // Check nếu quá 1 phút
                response.getWriter().write("expired");
            } else
                if (storedOtp.equals(enteredOtp)) { // Check OTP đúng
                int totalQuantity = (int) session.getAttribute("totalQuantity");
                request.setAttribute("buyerName", account.getFullname());
                request.setAttribute("quantity", totalQuantity);
                request.setAttribute("totalPrice", order.getTotalPrice());
                request.setAttribute("address", order.getAddress());
                order.setStatus("MAIL CONFIRMED");
                List<CartItem> items = cartItemDAO.getCartItemByCartID(cart.getCartId());
                orderDAO.saveOrder(order);

                for (CartItem cartItem : items) {
                    OrderDetail detail = new OrderDetail();
                    detail.setBookId(cartItem.getBookId());
                    detail.setOrderId(order.getOrderId());
                    detail.setQuantity(cartItem.getQuantity());
                    detail.setTotalPrice(cartItem.getPrice());
                    orderdetailDAO.saveOrderDetail(detail);
                }
                String transactionCode = request.getParameter("transactionCode");
                Payment payment = new Payment();
                payment.setAccountId(account.getAccountId());
                payment.setOrderId(order.getOrderId());
                payment.setPaymentType("COD");
                payment.setTotalAmount((float) order.getTotalPrice());
                payment.setTransactionCode(transactionCode);
                payment.setStatus("MAIL CONFIRMED");
                paymentDAO.addPayment(payment);
                cartItemDAO.deleteAllCartItemByCartId(cart.getCartId());
                response.getWriter().write("success");
            } else { // OTP sai
                response.getWriter().write("invalid");
            }
        } else {
            response.getWriter().write("error");
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

    private static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
