/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package global;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import model.Account;

@WebFilter("/*") // Vẫn chặn tất cả để kiểm tra
public class AuthorizationFilter implements Filter {

    // --- Định nghĩa các URL được phép ---

    // URL công khai (không cần đăng nhập)
    // Bao gồm cả các trang JSP và Servlet công khai
    private static final Set<String> PUBLIC_URLS = new HashSet<>(Arrays.asList(
            // Trang JSP công khai
            "/Login.jsp",
            "/login1.jsp", // Xem xét có cần cả 2 không
            "/Register.jsp",
            "/contact.jsp",
            "/faq.jsp",
            "/returnpolicy.jsp",
            "/bloglist.jsp",
            "/blogdetail.jsp",
            "/book-detail.jsp",
            "/index.jsp", // Có thể là trang chủ chung
            "/",          // Trang gốc

            // Servlet/Mapping công khai
            "/BlogServlet",
            "/book-detail", // Mapping cho book-detail.jsp?
            "/book-list",   // Mapping cho books.jsp?
            "/homepage",    // Mapping cho trang chủ công khai?
            "/login",       // Servlet xử lý đăng nhập
            "/logout",      // Servlet xử lý đăng xuất
            "/register",    // Servlet xử lý đăng ký cũ?
            "/register1"    // Servlet xử lý đăng ký mới?
    ));

    // Các tiền tố tài nguyên tĩnh
    private static final Set<String> STATIC_RESOURCE_PREFIXES = new HashSet<>(Arrays.asList(
            "/css/",
            "/js/",
            "/images/",
            "/fonts/",
            "/vendor/"
    ));

    // URLs chỉ dành riêng cho Admin (RoleId = 1)
    private static final Set<String> ADMIN_URLS = new HashSet<>(Arrays.asList(
            // JSP Admin
            "/userlist.jsp",

            // Servlet Admin
            "/UpdateUserServlet",
            "/ListServlet" // Servlet liệt kê user?
    ));

    // URLs dành cho Manager (RoleId = 2), Admin (1) cũng có thể truy cập
    private static final Set<String> MANAGER_URLS = new HashSet<>(Arrays.asList(
            // JSP Manager
            "/add-book.jsp", // Trùng với servlet /add-book của user? Xem xét lại logic
            "/addnewaddress.jsp",
            "/authorAdd.jsp",
            "/authorEdit.jsp",
            "/authorList.jsp",
            "/categoryAdd.jsp",
            "/categoryEdit.jsp",
            "/categoryList.jsp",
            "/discountAdd.jsp",
            "/discountEdit.jsp",
            "/discountList.jsp",
            "/manager.jsp",
            "/mbooklist.jsp",
            "/morder-list.jsp",
            "/mpayment-list.jsp",
            "/updateblog.jsp",

            // Servlet Manager
            "/AuthorAddController",
            "/AuthorController",
            "/AuthorEditController",
            "/CategoryAddController",
            "/CategoryController",
            "/CategoryEditController",
            "/DiscountAddController",
            "/DiscountController",
            "/DiscountEditController",
            "/mbooklist",       // Mapping cho mbooklist.jsp?
            "/list-order",      // Mapping cho morder-list.jsp?
            "/list-payment"     // Mapping cho mpayment-list.jsp?
    ));


    // URLs dành cho User (RoleId = 3), Manager(2) và Admin(1) cũng có thể truy cập
    // Bao gồm cả các trang "everyone" yêu cầu đăng nhập
    private static final Set<String> USER_URLS = new HashSet<>(Arrays.asList(
            // JSP User / Everyone (logged-in)
            "/UserProfile.jsp",
            "/editprofile.jsp",
            "/changepassword.jsp",
            "/books.jsp", // Danh sách sách cho user đã login (khác /book-list public?)
            "/cart1.jsp",
            "/checkout.jsp",
            "/cod-email-verify.jsp",
            "/homepage.jsp", // Trang chủ cho user đã login (khác /homepage public?)
            "/my-orders.jsp",
            "/order-detail.jsp",
            "/payment-banking.jsp",
            "/payment-method.jsp",
            "/thanks.jsp",

            // Servlet User / Everyone (logged-in)
            "/addressservlet",      // everyone -> user
            "/add-book",            // user
            "/add-to-cart",         // user
            "/cart",                // user
            "/ChangePasswordServlet",// everyone -> user
            "/checkout",            // user (trùng jsp?)
            "/comment",             // user
            "/deleteReview",        // user
            "/EditProfileServlet",  // everyone -> user
            "/minus-cart-item",     // user
            "/my-orders",           // user (trùng jsp?)
            "/order-detail",        // user (trùng jsp?)
            "/payment-banking",     // user (trùng jsp?)
            "/payment-cod",         // user
            "/payment-method",      // user (trùng jsp?)
            "/plus-cart-item",      // user
            "/remove-cartitem",     // user
            "/resendOTP",           // user
            "/userprofileservlet"   // everyone -> user
    ));

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        String requestedUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = requestedUri.substring(contextPath.length());

        // --- 1. Kiểm tra tài nguyên tĩnh trước tiên ---
        for (String prefix : STATIC_RESOURCE_PREFIXES) {
            if (path.startsWith(prefix)) {
                chain.doFilter(request, response); // Cho phép truy cập tài nguyên tĩnh
                return;
            }
        }

        // --- 2. Xác định xem URL có cần bảo vệ không ---
        boolean isProtectedRoute = ADMIN_URLS.contains(path)
                                    || MANAGER_URLS.contains(path)
                                    || USER_URLS.contains(path);

        if (isProtectedRoute) {
            // --- 3. Nếu là URL cần bảo vệ -> Kiểm tra đăng nhập ---
            Account loggedInUser = (session != null) ? (Account) session.getAttribute("account") : null;

            if (loggedInUser == null) {
                // Chưa đăng nhập, chuyển hướng về trang login
                System.out.println("Filter: User not logged in. Redirecting to login page for protected path: " + path);
                // Lưu lại URL để redirect sau (tùy chọn)
                // request.getSession().setAttribute("redirectAfterLogin", path);
                response.sendRedirect(contextPath + "/login"); // Chuyển đến Servlet /login
                return;
            }

            // --- 4. Đã đăng nhập -> Kiểm tra quyền truy cập ---
            int userRole = loggedInUser.getRoleId(); // Lấy roleId từ Account
            boolean authorized = false;

            System.out.println("Filter: Checking access for role '" + userRole + "' to protected path: " + path);

            switch (userRole) {
                case 1: // Admin
                    // Admin có quyền truy cập tất cả các trang được bảo vệ
                    if (ADMIN_URLS.contains(path) || MANAGER_URLS.contains(path) || USER_URLS.contains(path)) {
                        authorized = true;
                    }
                    break;
                case 2: // Manager
                    // Manager có quyền truy cập trang Manager và User
                    if (MANAGER_URLS.contains(path) || USER_URLS.contains(path)) {
                        authorized = true;
                    }
                    break;
                case 3: // User
                    // User chỉ có quyền truy cập trang User
                    if (USER_URLS.contains(path)) {
                        authorized = true;
                    }
                    break;
                // Có thể thêm default case nếu có các role khác hoặc xử lý lỗi
            }

            // --- 5. Xử lý kết quả kiểm tra quyền ---
            if (authorized) {
                System.out.println("Filter: Access granted.");
                chain.doFilter(request, response); // Cho phép truy cập
            } else {
                // Đã đăng nhập nhưng không có quyền
                System.out.println("Filter: Access denied for role '" + userRole + "' to path: " + path);
                response.sendRedirect(contextPath + "/accessDenied.jsp"); // Chuyển đến trang báo lỗi
            }

        } else {
            // --- 6. Nếu KHÔNG phải URL cần bảo vệ (và không phải static) -> Coi là PUBLIC ---
            // Bao gồm các URL trong PUBLIC_URLS và bất kỳ URL nào khác không được liệt kê trong các Set bảo vệ
            System.out.println("Filter: Public access or unlisted path: " + path);
            chain.doFilter(request, response); // Cho phép truy cập
        }
    }

    // init() và destroy() giữ nguyên
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AuthorizationFilter initialized.");
    }

    @Override
    public void destroy() {
        System.out.println("AuthorizationFilter destroyed.");
    }
}