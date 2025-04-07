package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import dao.AuthorDAO;
import dao.BookDAO;
import dao.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import model.Author;
import model.Book;
import model.Category;

/**
 *
 * @author TungPham
 */
@WebServlet(urlPatterns = {"/add-book"})
public class AddBookSerlet extends HttpServlet {

    AuthorDAO authorDAO = new AuthorDAO();
    CategoryDAO categoryDAO = new CategoryDAO();
    BookDAO bookDAO = new BookDAO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddBookSerlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddBookSerlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Author> authors = authorDAO.getAuthors(); // Thay thế bằng phương thức DAO thực tế
        List<Category> categories = categoryDAO.getCategories(); // Thay thế bằng phương thức DAO thực tế

        request.setAttribute("authors", authors);
        request.setAttribute("categories", categories);

        request.getRequestDispatcher("add-book.jsp").forward(request, response); // Trỏ đến file JSP của bạn
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String authorIdStr = request.getParameter("authorId");
        String quantityStr = request.getParameter("quantity"); // Đổi tên param thành quantity như trong JSP
        String priceStr = request.getParameter("price");
        String description = request.getParameter("description");
        String releaseDateStr = request.getParameter("releaseDate");
        String nxb = request.getParameter("nxb");
        String categoryIdStr = request.getParameter("categoryId");

        Book newBook = new Book();
        newBook.setTitle(title);

        newBook.setAuthorId(Integer.parseInt(authorIdStr)); 

        newBook.setStock(Integer.parseInt(quantityStr)); 
        newBook.setPrice(Float.parseFloat(priceStr));
        newBook.setDescription(description);
        newBook.setPublishedDate(java.sql.Date.valueOf(releaseDateStr));
        newBook.setPublisher(nxb);

        newBook.setCategory(Integer.parseInt(categoryIdStr));
        bookDAO.addBook(newBook);
        
        response.sendRedirect("mbooklist");

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
