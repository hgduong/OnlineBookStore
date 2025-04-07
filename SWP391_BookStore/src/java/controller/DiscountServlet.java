package controller;

import dao.DiscountDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Discount;

import java.io.IOException;
import java.util.List;

@WebServlet("/DiscountController")
public class DiscountServlet extends HttpServlet {

    private DiscountDAO discountDAO = new DiscountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Discount> discounts = discountDAO.getDiscounts();
        request.setAttribute("discounts", discounts);
        request.getRequestDispatcher("discountList.jsp").forward(request, response);
    }
}