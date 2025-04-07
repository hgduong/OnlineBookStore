package controller;

import dao.BlogDAO;
import model.Blog;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/BlogServlet")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // Giới hạn upload file 5MB
public class BlogServlet extends HttpServlet {

    private BlogDAO blogDAO;

    @Override
    public void init() throws ServletException {
        blogDAO = new BlogDAO();
    }

    private void saveFile(Part filePart, String fileName) throws IOException {
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        filePart.write(uploadPath + File.separator + fileName);
    }

    private boolean isValidInput(String input, int maxWords) {
        if (input == null || input.trim().isEmpty()) {
            return false; // Không cho phép nhập toàn dấu cách
        }

        // Kiểm tra không có nhiều dấu cách liên tiếp
        if (input.matches(".*\\s{2,}.*")) {
            return false;
        }

        // Kiểm tra số lượng từ không vượt quá maxWords
        String[] words = input.trim().split("\\s+"); // Tách từ bằng dấu cách
        return words.length <= maxWords;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String content = request.getParameter("content");
        String errorMessage = null;
        int page = 1; // default
        int recordsPerPage = 9;
        //****************************************
        Integer role = (Integer) request.getSession().getAttribute("role");
        // Kiểm tra quyền và truyền thông báo nếu không có quyền
        if (role == null || role != 3) {
            request.setAttribute("errorMessage", "Bạn không có quyền thực hiện chức năng này.");
        }

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        if (!isValidInput(title, 1000) || !isValidInput(description, 1000) || !isValidInput(content, 1000)) {
            errorMessage = "Dữ liệu nhập vào không hợp lệ! Không được nhập toàn dấu cách, chứa nhiều dấu cách liên tiếp hoặc vượt quá 1000 từ.";
            request.getRequestDispatcher("editblog.jsp").forward(request, response);
            return;
        } else if ("update".equals(action) || "add".equals(action)) {
            try {

                String status = request.getParameter("status");
                int authorId = 1; // Thay thế bằng ID của tác giả hiện tại (ví dụ: lấy từ session)

                // Kiểm tra dữ liệu đầu vào
                if (title == null || title.isEmpty() || content == null || content.isEmpty()) {
                    response.sendRedirect("editBlog.jsp?error=4"); // Thiếu thông tin
                    return;
                }

                // Xử lý file ảnh nếu có
                Part filePart = request.getPart("image");
                String image = request.getParameter("existingImage"); // Giữ ảnh cũ nếu cập nhật

                if (filePart != null && filePart.getSize() > 0) {
                    image = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    saveFile(filePart, image); // Lưu ảnh mới vào server
                }

                if ("update".equals(action)) {
                    // Lấy blogId từ request
                    int blogId = Integer.parseInt(request.getParameter("blogId"));
                    Blog blog = new Blog(blogId, title, authorId, description, image, content, "", "", status);
                    boolean success = blogDAO.updateBlog(blog);

                    if (success) {
                        response.sendRedirect("BlogServlet?action=list&success=1");
                    } else {
                        response.sendRedirect("editBlog.jsp?blogId=" + blogId + "&error=1");
                    }
                } else if ("add".equals(action)) {
                    // Thêm blog mới
                    Blog newBlog = new Blog(0, title, authorId, description, image, content, "", "", status);
                    boolean success = blogDAO.addBlog(newBlog);

                    if (success) {
                        response.sendRedirect("BlogServlet?action=list&success=2");
                    } else {
                        response.sendRedirect("addBlog.jsp?error=1");
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect("editBlog.jsp?error=5");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("editBlog.jsp?error=2");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.equalsIgnoreCase("list")) {
            List<Blog> blogs = blogDAO.getAllBlogs();
            request.setAttribute("blogs", blogs);
            RequestDispatcher dispatcher = request.getRequestDispatcher("bloglist.jsp");
            dispatcher.forward(request, response);

        } else if ("edit".equals(action)) {
            int blogId = Integer.parseInt(request.getParameter("blogId"));
            Blog blog = blogDAO.getBlogById(blogId);

            if (blog == null) {
                response.sendRedirect("error.jsp?message=Blog not found");
                return;
            }

            request.setAttribute("blog", blog);
            RequestDispatcher dispatcher = request.getRequestDispatcher("updateblog.jsp");
            dispatcher.forward(request, response);

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            blogDAO.deleteBlog(id);
            response.sendRedirect("BlogServlet?action=list");

        } else if ("search".equals(action)) {
            String keyword = request.getParameter("keyword");
            List<Blog> blogs = blogDAO.searchBlogs(keyword);
            request.setAttribute("blogs", blogs);
            request.setAttribute("keyword", keyword);
            RequestDispatcher dispatcher = request.getRequestDispatcher("bloglist.jsp");
            dispatcher.forward(request, response);

        } else if ("view".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Blog blog = blogDAO.getBlogById(id);

            if (blog == null) {
                response.sendRedirect("BlogServlet?action=list");
                return;
            }

            request.setAttribute("blog", blog);
            RequestDispatcher dispatcher = request.getRequestDispatcher("blogdetail.jsp");
            dispatcher.forward(request, response);
        }
    }
}
