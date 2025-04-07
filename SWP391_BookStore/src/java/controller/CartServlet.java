/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookDAO;
import dao.CartDAO;
import dao.CartItemDAO;
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
import model.Account;
import model.Book;
import model.Cart;
import model.CartItem;

/**
 *
 * @author TungPham
 */
@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    CartDAO cartDAO = new CartDAO();
    CartItemDAO cartItemDAO = new CartItemDAO();
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
            out.println("<title>Servlet CartServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartServlet at " + request.getContextPath() + "</h1>");
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
        Cart cart = cartDAO.getCartByAccountID(account.getAccountId());
        List<CartItem> cartItems = cartItemDAO.getCartItemByCartID(cart.getCartId());
        float totalPrice = 0;
        for (CartItem item : cartItems) {
            totalPrice += item.getPrice();
        }
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("totalPrice", totalPrice);
        RequestDispatcher rq = request.getRequestDispatcher("cart1.jsp");
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
        String cartIdParam = request.getParameter("cartId");
        String bookIdParam = request.getParameter("bookId");
        String quantityParam = request.getParameter("quantity");
        
        int cartId = Integer.parseInt(cartIdParam);
        int bookId = Integer.parseInt(bookIdParam);
        int newQuantity = Integer.parseInt(quantityParam);
        
        CartItem cartItem = cartItemDAO.getCartItemByCartIDBookId(cartId, bookId);
        Cart cart = cartDAO.getCartByID(cartId);
        Book book = bookDAO.getBookById(bookId);

        
        float newPrice = newQuantity * cartItem.getBookPrice();

        int totalQuantity = cart.getTotalQuantity() - cartItem.getQuantity() +newQuantity;
        cartItemDAO.saveCartItem(book, cartId, newQuantity);
        List<CartItem> cartItems = cartItemDAO.getCartItemByCartID(cart.getCartId());
        float totalPrice = 0;
        for (CartItem item : cartItems) {
            totalPrice += item.getPrice();
        }
        cartDAO.saveCart(cartId, totalPrice, totalQuantity);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = "{\"newQuantity\": " + newQuantity
                + ", \"newPrice\": " + newPrice
                + ", \"newTotalPrice\": " + totalPrice + "}";

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
