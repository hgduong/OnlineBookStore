package controller;

import dao.AuthorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Author;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Author;

@WebServlet("/AuthorAddController")
public class AuthorAddController extends HttpServlet {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private final AuthorDAO authorDAO = new AuthorDAO(); //Use final tag

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("authorAdd.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            addAuthor(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(AuthorAddController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addAuthor(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        String name = request.getParameter("name");
        String dobString = request.getParameter("dob");
        String biography = request.getParameter("biography");
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT); // Use constant for format
        Date dob = dateFormat.parse(dobString);
        try {
            //Validate here to prevent injection
            if (name == null || name.isEmpty() || dobString == null || dobString.isEmpty() || biography == null || biography.isEmpty()) {
                request.setAttribute("errorMessage", "All fields are required.");
                request.getRequestDispatcher("authorAdd.jsp").forward(request, response);
                return;
            }
            if (authorDAO.authorNameExists(name)) {
                request.setAttribute("errorMessage", "Author Name already exists.");
                request.setAttribute("dob", dob);
                request.setAttribute("authorName", name);
                request.setAttribute("biography", biography);
                request.getRequestDispatcher("authorAdd.jsp").forward(request, response);
                return;
            }

            if (biography == null || biography.length() > 255 || biography.isEmpty()) {
                request.setAttribute("errorMessage", "Biography must be between 1 and 255 characters.");
                request.setAttribute("dob", dob);
                request.setAttribute("authorName", name);
                request.setAttribute("biography", biography);
                request.getRequestDispatcher("authorAdd.jsp").forward(request, response);
                return;
            }

            Author author = new Author(0, name, dob, biography); //Id is now auto increment
            boolean success = authorDAO.addAuthor(author);

            if (success) {
                response.sendRedirect("AuthorController"); // Redirect to list authors
            } else {
                // Handle the error
                request.setAttribute("errorMessage", "Failed to add author.");
                request.setAttribute("dob", dob);
                request.setAttribute("authorName", name);
                request.setAttribute("biography", biography);
                request.getRequestDispatcher("authorAdd.jsp").forward(request, response);
            }
        } catch (ServletException e) {
            Logger.getLogger(AuthorAddController.class.getName()).log(Level.SEVERE, "Servlet exception", e);
            throw e; //Re-throw the exception
        } catch (Exception e) {
            Logger.getLogger(AuthorAddController.class.getName()).log(Level.SEVERE, "Some other exception", e);
        }
    }
}
