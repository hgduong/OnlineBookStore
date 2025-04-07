package controller;

import dao.CategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/CategoryStatusController")
public class CategoryStatusController extends HttpServlet {

    private CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String redirectURL = "CategoryController"; // Default URL
        String errorMessage = null;

        try {
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            Category category = categoryDAO.getCategoryById(categoryId);

            if (category == null) {
                errorMessage = "Category not found.";
            } else {
                String currentStatus = category.getStatus();
                String newStatus = (currentStatus.equals("Active")) ? "Suspended" : "Active";
                category.setStatus(newStatus);

                boolean success = categoryDAO.updateCategory(category);  // Database
                if (!success) {
                    errorMessage = "Failed to change status.";
                }
            }
        } catch (NumberFormatException e) {
            Logger.getLogger(CategoryStatusController.class.getName()).log(Level.SEVERE, null, e);
            errorMessage = "Invalid category ID.";
        }

        if (errorMessage != null) {
            request.getSession().setAttribute("errorMessage", errorMessage);
        }

        response.sendRedirect(redirectURL);
    }

}