/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AuthorDAO;
import dao.BookDAO;
import dao.CategoryDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Author;
import model.Book;
import model.Category;
import service.BookService;

/**
 *
 * @author TungPham
 */
@WebServlet(name = "BookListServlet", urlPatterns = {"/book-list"})
public class BookListServlet extends HttpServlet {

    BookDAO bookDAO = new BookDAO();
    CategoryDAO cateDAO = new CategoryDAO();
    AuthorDAO authorDAO = new AuthorDAO();
    BookService bookService = new BookService();

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
        int page = 1; //dèfault
        int recordsPerPage = 9;
        List<Book> books = new ArrayList<>();
        List<Category> cates = new ArrayList<>();
        List<Author> authors = new ArrayList<>();
        String searchName = request.getParameter("searchName");
        String categoryIdStr = request.getParameter("categoryId");
        String authorIdStr = request.getParameter("authorId");
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");
        int minPrice = 0;
        int maxPrice = 600000;
        Integer categoryId = null;
        Integer authorId = null;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        try {
            if (minPriceStr != null && !minPriceStr.isEmpty() && !minPriceStr.equals("NaN")) {
                minPrice = Integer.parseInt(minPriceStr);
            }
            if (maxPriceStr != null && !maxPriceStr.isEmpty() && !maxPriceStr.equals("NaN")) {
                maxPrice = Integer.parseInt(maxPriceStr);
            }
        } catch (NumberFormatException e) {
            minPrice = 0;
            maxPrice = 600000;
        }

        if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
            try {
                categoryId = Integer.parseInt(categoryIdStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                categoryId = null;
            }
        }

        if (authorIdStr != null && !authorIdStr.isEmpty()) {
            try {
                authorId = Integer.parseInt(authorIdStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                authorId = null;
            }
        }

        try {
            int offset = (page - 1) * recordsPerPage;
            int totalBooks = 0;
            if (searchName != null && !searchName.trim().isEmpty()) {
                books = bookDAO.searchBookByName(searchName, recordsPerPage, offset);
                totalBooks = bookDAO.getTotalCountOfBookSearch(searchName);
                request.setAttribute("searchName", searchName);
            } else if (categoryId != null) {
                books = bookDAO.filterBookByCategory(categoryId, recordsPerPage, offset);
                totalBooks = bookDAO.getTotalCountOfBookCate(categoryId);
                request.setAttribute("categoryId", categoryId);
            } else if (authorId != null) {
                books = bookDAO.filterBookByAuthor(authorId, recordsPerPage, offset);
                totalBooks = bookDAO.getTotalCountOfBookAuthor(authorId);
                request.setAttribute("authorId", authorId);
            } else {
                books = bookDAO.getListPaginBook(recordsPerPage, offset);
                totalBooks = bookDAO.getTotalCountOfBook();
                if (minPrice > 0 || maxPrice < 600000) {
                    offset = (page - 1) * recordsPerPage;
                    books = bookDAO.getBooks();
                    books = bookService.getBooksByPrice(books, minPrice, maxPrice);
                    totalBooks = books.size();
                    List<Book> subList = new ArrayList<>();
                    if (books.isEmpty()) {
                    } else if (books.size() < 9) {
                        subList = books.subList(0, books.size() - 1);
                    } else {
                        subList = books.subList(offset, recordsPerPage * page);
                    }

                    books = subList;
                    request.setAttribute("minPrice", minPrice);
                    request.setAttribute("maxPrice", maxPrice);
                }
            }

            int totalPages = (int) Math.ceil((double) totalBooks / recordsPerPage);
            cates = cateDAO.getCates();
            authors = authorDAO.getAuthors();

            request.setAttribute("books", books);
            request.setAttribute("cates", cates);
            request.setAttribute("authors", authors);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);

            RequestDispatcher rd = request.getRequestDispatcher("/books.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
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
        processRequest(request, response);
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
