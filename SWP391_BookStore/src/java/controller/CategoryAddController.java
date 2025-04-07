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

@WebServlet("/CategoryAddController")
public class CategoryAddController extends HttpServlet {

    private CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("categoryAdd.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        addCategory(request, response);
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            String categoryName = request.getParameter("categoryName");
            String description = request.getParameter("description");

             //Check if name and id exists
            if (categoryDAO.categoryIdExists(categoryId)){
                request.setAttribute("errorMessage", "CategoryId already exists.");
                request.getRequestDispatcher("categoryAdd.jsp").forward(request, response);
                return;
            }

            if (categoryDAO.categoryNameExists(categoryName)){
                request.setAttribute("errorMessage", "Category Name already exists.");
                request.getRequestDispatcher("categoryAdd.jsp").forward(request, response);
                return;
            }
            
            if (description == null || description.length() > 255 || description.isEmpty()) {
                request.setAttribute("errorMessage", "Description must be between 1 and 255 characters.");
                request.getRequestDispatcher("categoryAdd.jsp").forward(request, response);
                return;
            }

            Category category = new Category(categoryId, categoryName, description);
            boolean success = categoryDAO.addCategory(category);

            if (success) {
                response.sendRedirect("CategoryController"); // Redirect to list categories
            } else {
                // Handle the error
                request.setAttribute("errorMessage", "Failed to add category.");
                request.getRequestDispatcher("categoryAdd.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
             Logger.getLogger(CategoryAddController.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("errorMessage", "Invalid data. Please check your input.");
            request.getRequestDispatcher("categoryAdd.jsp").forward(request, response);
        }
    }}