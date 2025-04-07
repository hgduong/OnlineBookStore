/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.AuthorDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Author;
import model.Author;

/**
 *
 * @author admin
 */
@WebServlet("/AuthorEditController")
public class AuthorEditController extends HttpServlet {
   
    private AuthorDAO authorDAO = new AuthorDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showEditForm(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateAuthor(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int authorId = Integer.parseInt(request.getParameter("authorId"));
        Author author = authorDAO.getAuthorById(authorId);
        request.setAttribute("author", author);
        request.getRequestDispatcher("authorEdit.jsp").forward(request, response);
    }

    private void updateAuthor(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int authorId = Integer.parseInt(request.getParameter("authorId"));
            String name = request.getParameter("name");
            String dobString = request.getParameter("dob");
            String biography = request.getParameter("biography");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust format as needed
            Date dob = dateFormat.parse(dobString);

            Author author = new Author(authorId, name, dob, biography);
            boolean success = authorDAO.updateAuthor(author);
            
            if (biography == null || biography.length() > 255 || biography.isEmpty()) {
                request.setAttribute("errorMessage", "Biography must be between 1 and 255 characters.");
                request.getRequestDispatcher("authorEdit.jsp").forward(request, response);
                return;
            }
            
            if (success) {
                response.sendRedirect("AuthorController"); // Redirect to list authors
            } else {
                // Handle the error
                request.setAttribute("errorMessage", "Failed to update author.");
                request.getRequestDispatcher("authorEdit.jsp").forward(request, response);
            }
        } catch (ParseException | NumberFormatException e) {
            Logger.getLogger(AuthorEditController.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("errorMessage", "Invalid data. Please check your input.");
            request.getRequestDispatcher("authorEdit.jsp").forward(request, response);
        }
    }
}
