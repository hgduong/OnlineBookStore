/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.BookDAO;
import java.util.ArrayList;
import java.util.List;
import model.Book;

/**
 *
 * @author TungPham
 */
public class BookService {

    public List<Book> getBooksByPrice(List<Book> books, int min, int max) {
        List<Book> bbs = new ArrayList<>();
        for (Book book : books) {
            if (book.getPrice() >= min && book.getPrice() <= max) {
                bbs.add(book);
            }
        }
        return bbs;
    }

    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();
        BookService thiss = new BookService();
        List<Book> books = new ArrayList<>();
        books = bookDAO.getBooks();
        for (Book book : books) {
            System.out.println("");
            System.out.print(book.getBookId() + ", ");
            System.out.print(book.getTitle() + ", ");
            System.out.print(book.getPrice() + ", ");
            System.out.print(book.getAuthor().getName() + ", ");
            System.out.print(book.getCategory().getCategoryName());
        }
        books = bookDAO.getListPaginBook(9, 0);
        int minPrice = 0;
        int maxPrice = 320000;
        int totalBooks = bookDAO.getTotalCountOfBook();
        int offset = 0;
        int page = 1;
        if (minPrice > 0 || maxPrice < 600000) {
            offset = (page - 1) * 9;
            books = bookDAO.getBooks();
            books = thiss.getBooksByPrice(books, minPrice, maxPrice);
            List<Book> subList = new ArrayList<>();
            if (books.isEmpty()) {

            } else if (books.size() < 9) {
                subList = books.subList(0, books.size() - 1);
            } else {
                subList = books.subList(offset, 9 * page);
            }

            books = subList;
            totalBooks = books.size();
        }
//        List<Book> bookss = thiss.getBooksByPrice(books, 225000, 235000);
//        
//        
        System.out.println("aaaaaaaaaaaaaaa");
        for (Book book : books) {
            System.out.println("");
            System.out.print(book.getBookId() + ", ");
            System.out.print(book.getPrice() + ", ");
        }
    }
}
