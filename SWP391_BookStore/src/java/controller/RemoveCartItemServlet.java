/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookDAO;
import dao.CartDAO;
import dao.CartItemDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Book;
import model.Cart;
import model.CartItem;

/**
 *
 * @author TungPham
 */
@WebServlet(name = "RemoveCartItemServlet", urlPatterns = {"/remove-cartitem"})
public class RemoveCartItemServlet extends HttpServlet {

    CartItemDAO cartItemDAO = new CartItemDAO();
    CartDAO cartDAO = new CartDAO();
    BookDAO bookDAO = new BookDAO();

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
            out.println("<title>Servlet RemoveCartItemServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RemoveCartItemServlet at " + request.getContextPath() + "</h1>");
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
        String cartIdParam = request.getParameter("cartId");
        String bookIdParam = request.getParameter("bookId");

        int cartId = Integer.parseInt(cartIdParam);
        int bookId = Integer.parseInt(bookIdParam);

        boolean success = cartItemDAO.deleteCartItem(cartId, bookId);    
        List<CartItem> cartItems = cartItemDAO.getCartItemByCartID(cartId);
        float totalPrice = 0;
        int totalQuantity = 0;
        for (CartItem item : cartItems) {
            totalPrice += item.getPrice();
            totalQuantity+= item.getQuantity();
        }
        cartDAO.saveCart(cartId, totalPrice, totalQuantity);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

         String json = String.format("{\"success\": %b, \"newTotalPrice\": %.2f}", success, totalPrice);

        PrintWriter out = response.getWriter();
        out.write(json);
        out.flush();
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
