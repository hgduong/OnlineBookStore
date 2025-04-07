package controller;

import dao.DiscountDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Discount;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/DiscountAddController")
public class DiscountAddController extends HttpServlet {

    private DiscountDAO discountDAO = new DiscountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("discountAdd.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(request.getParameter("startDate"));
            Date endDate = dateFormat.parse(request.getParameter("endDate"));
            double discountPercent = Double.parseDouble(request.getParameter("discountPercent"));
            String status = request.getParameter("status");

            Discount discount = new Discount(startDate, endDate, discountPercent, status);
            boolean success = discountDAO.addDiscount(discount);

            if (success) {
                response.sendRedirect("DiscountController"); // Redirect to list
            } else {
                request.setAttribute("errorMessage", "Failed to add discount.");
                request.getRequestDispatcher("discountAdd.jsp").forward(request, response);
            }
        } catch (ParseException | NumberFormatException e) {
            Logger.getLogger(DiscountAddController.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("errorMessage", "Invalid data. Please check your input.");
            request.getRequestDispatcher("discountAdd.jsp").forward(request, response);
        }
    }
}