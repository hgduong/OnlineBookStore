/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dao.AddressDAO;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Account;
import model.Address;
import model.Cart;
import model.CartItem;
import model.Order;
import service.ShippingService;

/**
 *
 * @author TungPham
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {

    CartDAO cartDAO = new CartDAO();
    CartItemDAO cartItemDAO = new CartItemDAO();
    AddressDAO addressDAO = new AddressDAO();

    ShippingService shippingService = new ShippingService();

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
            out.println("<title>Servlet CheckoutServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckoutServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession(false);

        // Kiểm tra nếu session hoặc account trong session là null
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        Account account = (Account) session.getAttribute("account");
        List<Address> addresses = addressDAO.getAddressesByAccountId(account.getAccountId());
        Address main = null;
        for (Address address : addresses) {
            if (address.isIsMain()) {
                main = address;
                break;
            }
        }
        Cart cart = cartDAO.getCartByAccountID(account.getAccountId());
        List<CartItem> items = cartItemDAO.getCartItemByCartID(cart.getCartId());
        if (items.isEmpty()) {
            response.sendRedirect("book-list");  // ✅ Dùng redirect thay vì forward
            return;
        }
        Order order = new Order();
        order.setAccountId(account.getAccountId());
        order.setCartId(cart.getCartId());
        if (order.getAddressId() != 0) {
            Address selectedAddress = addressDAO.getAddressById(order.getAddressId());
            if (selectedAddress != null) { //check null
                main = selectedAddress; // Ghi đè defaultAddress nếu có addressId

            }
        }
        int totalQuantity = 0;
        for (CartItem cartItem : items) {
            totalQuantity += cartItem.getQuantity();
        }
        session.setAttribute("totalQuantity", totalQuantity);
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        Date deliveryTime = calendar.getTime();
        order.setDeliveryTime(deliveryTime);
        order.setAddressId(main.getAddressId());
        order.setAddress(main);
        order.setOrderDate(new Date());
        float totalPrice = 0;
        float shipFee = 0;
        float finalPrice = 0;

        for (CartItem cartItem : items) {
            totalPrice += cartItem.getPrice();
        }
        session.setAttribute("totalPrice", totalPrice);
        shipFee = shippingService.getShipFee(main.getWard());
        finalPrice = totalPrice + shipFee;
        order.setTotalPrice(finalPrice);
        session.setAttribute("order", order);
        request.setAttribute("addresses", addresses);
        request.setAttribute("totalPrice", totalPrice);
        request.setAttribute("shippingPrice", shipFee);
        request.setAttribute("finalPrice", finalPrice);
        request.setAttribute("currentAddress", main);
        request.setAttribute("items", items);
        RequestDispatcher rq = request.getRequestDispatcher("checkout.jsp");
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
        String addressIdStr = request.getParameter("addressId");
        if (addressIdStr != null && !addressIdStr.isEmpty()) {
            int addressId = Integer.parseInt(addressIdStr);
            Address address = addressDAO.getAddressById(addressId);
            HttpSession session = request.getSession();
            Order order = (Order) session.getAttribute("order");
            float totalPrice = (float) session.getAttribute("totalPrice");
            float shippingFee = shippingService.getShipFee(address.getWard());
            float finalPrice = totalPrice + shippingFee;
            if (order != null) {
                order.setAddressId(addressId);
                order.setAddress(address);
                order.setTotalPrice(finalPrice);
                session.setAttribute("order", order);
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("shippingPrice", shippingFee);
            responseData.put("finalPrice", finalPrice);

            Gson gson = new Gson();
            String json = gson.toJson(responseData);

            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
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
