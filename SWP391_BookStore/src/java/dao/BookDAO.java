/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.dbcontext.DatabaseFactory;
import dao.dbcontext.MySqlDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Author;
import model.Book;
import model.Category;
import model.Review;

/**
 *
 * @author TungPham
 */
public class BookDAO {

    ReviewDAO reviewDAO = new ReviewDAO();
    private static final String GET_BOOKS = "SELECT b.BookId, b.Title, b.Stock, a.AuthorName, c.CategoryName, b.Price, b.ReleaseDate, b.Status "
            + "FROM Book b "
            + "JOIN Author a ON b.AuthorId = a.AuthorId "
            + "JOIN Categories c ON b.CategoryId = c.CategoryId "
            + "ORDER BY b.BookId desc";
    private static final String GET_BOOK_BY_ID = "SELECT b.BookId, b.Title, a.AuthorName, c.CategoryName, "
            + "b.Stock, b.Price, b.Description, b.ReleaseDate, b.NXB, b.Status "
            + "FROM Book b "
            + "JOIN Author a ON b.AuthorId = a.AuthorId "
            + "JOIN Categories c ON b.CategoryId = c.CategoryId "
            + "WHERE b.BookId = ?;";
    private static final String GET_BOOKS_INCLUDE_AUTHOR_CATE_PAGIN = "SELECT b.BookId, b.Title, a.AuthorName, c.CategoryName, b.Price, b.ReleaseDate, b.Status "
            + "FROM Book b "
            + "JOIN Author a ON b.AuthorId = a.AuthorId "
            + "JOIN Categories c ON b.CategoryId = c.CategoryId "
            + "order by BookId LIMIT ? OFFSET ?";
    private static final String GET_BOOKS_INCLUDE_AUTHOR_CATE_PAGIN_REV = "SELECT b.BookId, b.Title, a.AuthorName, c.CategoryName, b.Price, b.ReleaseDate, b.Status "
            + "FROM Book b "
            + "JOIN Author a ON b.AuthorId = a.AuthorId "
            + "JOIN Categories c ON b.CategoryId = c.CategoryId "
            + "order by BookId desc LIMIT ? OFFSET ?";
    private static final String GET_TOTAL_COUNT = "SELECT COUNT(*) AS total FROM book";

    private static final String SEARCH_BOOK_BY_NAME_PAGIN = "SELECT b.BookId, b.Title, a.AuthorName, c.CategoryName, b.Price, b.ReleaseDate, b.Status "
            + "FROM Book b "
            + "JOIN Author a ON b.AuthorId = a.AuthorId "
            + "JOIN Categories c ON b.CategoryId = c.CategoryId "
            + "WHERE Title LIKE ? order by BookId LIMIT ? OFFSET ?";

    private static final String SEARCH_BOOK_BY_CATE_PAGIN = "SELECT b.BookId, b.Title, a.AuthorName, c.CategoryName, b.Price, b.ReleaseDate, b.Status "
            + "FROM Book b "
            + "JOIN Author a ON b.AuthorId = a.AuthorId "
            + "JOIN Categories c ON b.CategoryId = c.CategoryId "
            + "WHERE b.CategoryId = ? "
            + "ORDER BY b.BookId "
            + "LIMIT ? OFFSET ?;";
    private static final String SEARCH_BOOK_BY_AUTHOR_PAGIN = "SELECT b.BookId, b.Title, a.AuthorName, c.CategoryName, b.Price, b.ReleaseDate, b.Status "
            + "FROM Book b "
            + "JOIN Author a ON b.AuthorId = a.AuthorId "
            + "JOIN Categories c ON b.CategoryId = c.CategoryId "
            + "WHERE b.AuthorId = ? "
            + "ORDER BY b.BookId "
            + "LIMIT ? OFFSET ?;";
    private static final String GET_TOTAL_COUNT_BOOK_SEARCH = "SELECT COUNT(*) AS total FROM book WHERE Title LIKE ?";
    private static final String GET_TOTAL_COUNT_BOOK_CATE = "SELECT COUNT(*) AS total FROM book WHERE CategoryId = ?";
    private static final String GET_TOTAL_COUNT_BOOK_AUTHOR = "SELECT COUNT(*) AS total FROM book WHERE AuthorId = ?";
    private static final String UPDATE_BOOKSTOCK = "UPDATE book SET stock = ? WHERE bookId = ?;";
    private static final String INSERT_BOOK = "INSERT INTO Book (Title, Price, Stock, Description, ReleaseDate, NXB, AuthorId, CategoryId, Status) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_BOOKS);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                Author author = new Author();
                Category cate = new Category();
                book.setBookId(rs.getInt("BookId"));
                book.setTitle(rs.getString("Title"));
                book.setPrice(rs.getFloat("Price"));
                author.setName(rs.getString("AuthorName"));
                cate.setCategoryName(rs.getString("CategoryName"));
                book.setStock(rs.getInt("Stock"));
                book.setAuthor(author);
                book.setCategory(cate);
                books.add(book);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
//            System.out.println("closed connection::::::::::");
            try {
                if (stm != null) {
                    stm.close();

                }
                if (database != null && connection != null) {
                    connection.close();
                }

            } catch (SQLException sqlEx) {
                // TODO Auto-generated catch block
                sqlEx.printStackTrace();
            }
        }
        System.out.println("yes");
        return books;
    }

    public Book getBookById(int id) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        Book book = new Book();
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_BOOK_BY_ID);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                Author author = new Author();
                Category cate = new Category();
                book.setBookId(rs.getInt("BookId"));
                book.setTitle(rs.getString("Title"));
                book.setPrice(rs.getFloat("Price"));
                book.setStock(rs.getInt("Stock"));
                book.setDescription(rs.getString("Description"));
                book.setPublishedDate(rs.getDate("ReleaseDate"));
                System.out.println("Debug - ReleaseDate from DB : " + book.getPublishedDate());
                book.setPublisher(rs.getString("NXB"));
                book.setStatus(rs.getString("Status"));
                author.setName(rs.getString("AuthorName"));
                cate.setCategoryName(rs.getString("CategoryName"));
                List<Review> list = reviewDAO.getReviewByBookId(id);
                book.setAuthor(author);
                book.setCategory(cate);
                book.setReviews(list);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
//            System.out.println("closed connection::::::::::");
            try {
                if (stm != null) {
                    stm.close();

                }
                if (database != null && connection != null) {
                    connection.close();
                }

            } catch (SQLException sqlEx) {
                // TODO Auto-generated catch block
                sqlEx.printStackTrace();
            }
        }
        return book;
    }

    public List<Book> getListPaginBook(int limit, int offset) {
        List<Book> books = new ArrayList<>();
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_BOOKS_INCLUDE_AUTHOR_CATE_PAGIN);
            stm.setInt(1, limit);
            stm.setInt(2, offset);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                Author author = new Author();
                Category cate = new Category();
                book.setBookId(rs.getInt("BookId"));
                book.setTitle(rs.getString("Title"));
                book.setPrice(rs.getFloat("Price"));
                author.setName(rs.getString("AuthorName"));
                cate.setCategoryName(rs.getString("CategoryName"));
                book.setAuthor(author);
                book.setCategory(cate);
                books.add(book);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
//            System.out.println("closed connection::::::::::");
            try {
                if (stm != null) {
                    stm.close();

                }
                if (database != null && connection != null) {
                    connection.close();
                }

            } catch (SQLException sqlEx) {
                // TODO Auto-generated catch block
                sqlEx.printStackTrace();
            }
        }
        return books;
    }

    public List<Book> getListPaginBookRev(int limit, int offset) {
        List<Book> books = new ArrayList<>();
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_BOOKS_INCLUDE_AUTHOR_CATE_PAGIN_REV);
            stm.setInt(1, limit);
            stm.setInt(2, offset);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                Author author = new Author();
                Category cate = new Category();
                book.setBookId(rs.getInt("BookId"));
                book.setTitle(rs.getString("Title"));
                book.setPrice(rs.getFloat("Price"));
                author.setName(rs.getString("AuthorName"));
                cate.setCategoryName(rs.getString("CategoryName"));
                book.setAuthor(author);
                book.setCategory(cate);
                books.add(book);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
//            System.out.println("closed connection::::::::::");
            try {
                if (stm != null) {
                    stm.close();

                }
                if (database != null && connection != null) {
                    connection.close();
                }

            } catch (SQLException sqlEx) {
                // TODO Auto-generated catch block
                sqlEx.printStackTrace();
            }
        }
        return books;
    }

    public int getTotalCountOfBook() {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_TOTAL_COUNT);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
//            System.out.println("closed connection::::::::::");
            try {
                if (stm != null) {
                    stm.close();

                }
                if (database != null && connection != null) {
                    connection.close();
                }

            } catch (SQLException sqlEx) {
                // TODO Auto-generated catch block
                sqlEx.printStackTrace();
            }
        }
        return 0;
    }

    public List<Book> searchBookByName(String searchName, int limit, int offset) {
        List<Book> books = new ArrayList<>();
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(SEARCH_BOOK_BY_NAME_PAGIN);
            stm.setString(1, "%" + searchName + "%");
            stm.setInt(2, limit);
            stm.setInt(3, offset);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                Author author = new Author();
                Category cate = new Category();
                book.setBookId(rs.getInt("BookId"));
                book.setTitle(rs.getString("Title"));
                book.setPrice(rs.getFloat("Price"));
                author.setName(rs.getString("AuthorName"));
                cate.setCategoryName(rs.getString("CategoryName"));
                book.setAuthor(author);
                book.setCategory(cate);
                books.add(book);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
//            System.out.println("closed connection::::::::::");
            try {
                if (stm != null) {
                    stm.close();

                }
                if (database != null && connection != null) {
                    connection.close();
                }

            } catch (SQLException sqlEx) {
                // TODO Auto-generated catch block
                sqlEx.printStackTrace();
            }
        }
        return books;
    }

    public int getTotalCountOfBookSearch(String searchName) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_TOTAL_COUNT_BOOK_SEARCH);
            stm.setString(1, "%" + searchName + "%");
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
//            System.out.println("closed connection::::::::::");
            try {
                if (stm != null) {
                    stm.close();

                }
                if (database != null && connection != null) {
                    connection.close();
                }

            } catch (SQLException sqlEx) {
                // TODO Auto-generated catch block
                sqlEx.printStackTrace();
            }
        }
        return 0;
    }

    public List<Book> filterBookByCategory(int id, int limit, int offset) {
        List<Book> books = new ArrayList<>();
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(SEARCH_BOOK_BY_CATE_PAGIN);
            stm.setInt(1, id);
            stm.setInt(2, limit);
            stm.setInt(3, offset);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                Author author = new Author();
                Category cate = new Category();
                book.setBookId(rs.getInt("BookId"));
                book.setTitle(rs.getString("Title"));
                book.setPrice(rs.getFloat("Price"));
                author.setName(rs.getString("AuthorName"));
                cate.setCategoryName(rs.getString("CategoryName"));
                book.setAuthor(author);
                book.setCategory(cate);
                books.add(book);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
//            System.out.println("closed connection::::::::::");
            try {
                if (stm != null) {
                    stm.close();

                }
                if (database != null && connection != null) {
                    connection.close();
                }

            } catch (SQLException sqlEx) {
                // TODO Auto-generated catch block
                sqlEx.printStackTrace();
            }
        }
        return books;
    }

    public List<Book> filterBookByAuthor(int id, int limit, int offset) {
        List<Book> books = new ArrayList<>();
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(SEARCH_BOOK_BY_AUTHOR_PAGIN);
            stm.setInt(1, id);
            stm.setInt(2, limit);
            stm.setInt(3, offset);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                Author author = new Author();
                Category cate = new Category();
                book.setBookId(rs.getInt("BookId"));
                book.setTitle(rs.getString("Title"));
                book.setPrice(rs.getFloat("Price"));
                author.setName(rs.getString("AuthorName"));
                cate.setCategoryName(rs.getString("CategoryName"));
                book.setAuthor(author);
                book.setCategory(cate);
                books.add(book);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
//            System.out.println("closed connection::::::::::");
            try {
                if (stm != null) {
                    stm.close();

                }
                if (database != null && connection != null) {
                    connection.close();
                }

            } catch (SQLException sqlEx) {
                // TODO Auto-generated catch block
                sqlEx.printStackTrace();
            }
        }
        return books;
    }

    public int getTotalCountOfBookCate(int categoryId) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_TOTAL_COUNT_BOOK_CATE);
            stm.setInt(1, categoryId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
//            System.out.println("closed connection::::::::::");
            try {
                if (stm != null) {
                    stm.close();

                }
                if (database != null && connection != null) {
                    connection.close();
                }

            } catch (SQLException sqlEx) {
                // TODO Auto-generated catch block
                sqlEx.printStackTrace();
            }
        }
        return 0;
    }

    public int getTotalCountOfBookAuthor(int authorId) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(GET_TOTAL_COUNT_BOOK_AUTHOR);
            stm.setInt(1, authorId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
//            System.out.println("closed connection::::::::::");
            try {
                if (stm != null) {
                    stm.close();

                }
                if (database != null && connection != null) {
                    connection.close();
                }

            } catch (SQLException sqlEx) {
                // TODO Auto-generated catch block
                sqlEx.printStackTrace();
            }
        }
        return 0;
    }

    public boolean updateBookStock(int stock, int bookId) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(UPDATE_BOOKSTOCK);
            stm.setInt(1, stock);
            stm.setInt(2, bookId);
            stm.executeUpdate();
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return false;
        } finally {
//            System.out.println("closed connection::::::::::");
            try {
                if (stm != null) {
                    stm.close();

                }
                if (database != null && connection != null) {
                    connection.close();
                }

            } catch (SQLException sqlEx) {
                // TODO Auto-generated catch block
                sqlEx.printStackTrace();
            }
        }

    }

    public boolean addBook(Book book) {
        MySqlDatabase database = null;
        PreparedStatement stm = null;
        Connection connection = null;
        boolean success = false;

        try {
            database = (MySqlDatabase) DatabaseFactory.createDataBase(1);
            connection = database.createConnection();
            stm = connection.prepareStatement(INSERT_BOOK);
            stm.setString(1, book.getTitle());
            stm.setDouble(2, book.getPrice());
            stm.setInt(3, book.getStock());
            stm.setString(4, book.getDescription());
            stm.setDate(5, (java.sql.Date) book.getPublishedDate());
            stm.setString(6, book.getPublisher());
            stm.setInt(7, book.getAuthorId());
            stm.setInt(8, book.getCategoryId());
            stm.setString(9, "ACTIVE");
            int rowsAffected = stm.executeUpdate();

            if (rowsAffected > 0) {
                success = true;
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace(); 
            return success;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
        }
        return success;
    }
//    public static void main(String[] args) {
//        BookDAO thisss = new BookDAO();
//        List<Book> book = new ArrayList<>();
//        book = thisss.filterBookByAuthor(1,);
//    }
}
