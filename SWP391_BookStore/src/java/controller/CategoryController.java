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
import java.util.List;

/**
 *
 * @author admin
 */
@WebServlet("/CategoryController")
public class CategoryController extends HttpServlet {

     private CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("search".equals(action)) {
            searchCategories(request, response);
        } else {
            listCategories(request, response);
        }
    }

    private void listCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryDAO.getCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("categoryList.jsp").forward(request, response);
    }

    private void searchCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchTerm = request.getParameter("searchTerm");
        List<Category> categories = categoryDAO.searchCategoryName(searchTerm);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("categoryList.jsp").forward(request, response);
    }
}
