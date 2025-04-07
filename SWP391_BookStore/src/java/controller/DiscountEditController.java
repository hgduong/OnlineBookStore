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

@WebServlet("/DiscountEditController")
public class DiscountEditController extends HttpServlet {

    private DiscountDAO discountDAO = new DiscountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showEditForm(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        updateDiscount(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int discountId = Integer.parseInt(request.getParameter("discountId"));
        Discount discount = discountDAO.getDiscountById(discountId);
        request.setAttribute("discount", discount);
        request.getRequestDispatcher("discountEdit.jsp").forward(request, response);
    }

    private void updateDiscount(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int discountId = Integer.parseInt(request.getParameter("discountId"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(request.getParameter("startDate"));
            Date endDate = dateFormat.parse(request.getParameter("endDate"));
            double discountPercent = Double.parseDouble(request.getParameter("discountPercent"));
            String status = request.getParameter("status");

            Discount discount = new Discount();
            discount.setDiscountId(discountId);
            discount.setStartDate(startDate);
            discount.setEndDate(endDate);
            discount.setDiscountPercent(discountPercent);
            discount.setStatus(status);

            boolean success = discountDAO.updateDiscount(discount);

            if (success) {
                response.sendRedirect("DiscountController"); // Redirect to list
            } else {
                request.setAttribute("errorMessage", "Failed to update discount.");
                request.getRequestDispatcher("discountEdit.jsp").forward(request, response);
            }
        } catch (ParseException | NumberFormatException e) {
            Logger.getLogger(DiscountEditController.class.getName()).log(Level.SEVERE, null, e);
             request.setAttribute("errorMessage", "Invalid data. Please check your input.");
            request.getRequestDispatcher("discountEdit.jsp").forward(request, response);
        }
    }
}