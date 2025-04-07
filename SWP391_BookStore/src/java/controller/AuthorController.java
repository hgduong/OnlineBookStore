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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Author;
import model.Author;

/**
 *
 * @author admin
 */

@WebServlet("/AuthorController")
public class AuthorController extends HttpServlet {
   
    private AuthorDAO authorDAO = new AuthorDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("search".equals(action)) {
            searchAuthors(request, response);
        } else {
            listAuthors(request, response); // Default action: list authors
        }
    }

    private void listAuthors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Author> authors = authorDAO.getAuthors();
        request.setAttribute("authors", authors);
        request.getRequestDispatcher("authorList.jsp").forward(request, response);
    }

    private void searchAuthors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchTerm = request.getParameter("searchTerm");
        List<Author> authors = authorDAO.searchAuthorsByName(searchTerm);
        request.setAttribute("authors", authors);
        request.getRequestDispatcher("authorList.jsp").forward(request, response);
    }
}
