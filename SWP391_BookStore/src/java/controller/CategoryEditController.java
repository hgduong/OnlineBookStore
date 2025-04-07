/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

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

/**
 *
 * @author admin
 */
@WebServlet("/CategoryEditController")
public class CategoryEditController extends HttpServlet {

    private CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showEditForm(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        updateCategory(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        Category category = categoryDAO.getCategoryById(categoryId);
        request.setAttribute("category", category);
        request.getRequestDispatcher("categoryEdit.jsp").forward(request, response);
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            String categoryName = request.getParameter("categoryName");
            String description = request.getParameter("description");
            String status = request.getParameter("status"); //Get the status from the form.

            Category category = new Category(categoryId, categoryName, description);
            category.setStatus(status);

            boolean success = categoryDAO.updateCategory(category);
            
            if (description == null || description.length() > 255 || description.isEmpty()) {
                request.setAttribute("errorMessage", "Description must be between 1 and 255 characters.");
                request.getRequestDispatcher("categoryEdit.jsp").forward(request, response);
                return;
            }
            if (success) {
                response.sendRedirect("CategoryController"); // Redirect to list categories
            } else {
                // Handle the error
                request.setAttribute("errorMessage", "Failed to update category.");
                request.getRequestDispatcher("categoryEdit.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            Logger.getLogger(CategoryEditController.class.getName()).log(Level.SEVERE, null, e);
             request.setAttribute("errorMessage", "Invalid data. Please check your input.");
            request.getRequestDispatcher("categoryEdit.jsp").forward(request, response);
        }
    }
}