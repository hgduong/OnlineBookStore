<%-- 
    Document   : userlist
    Created on : Mar 30, 2025, 8:09:35 AM
    Author     : TungPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Account" %>
<%@ page import="java.util.Optional" %>
<!DOCTYPE html>
<html>
    <head>
        <title>User List</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <style>
            body {
                background-color: #FFF9E6;
            }
            .container {
                margin-top: 30px;
                background: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
            }
            h2 {
                color: #D4A017;
            }
            .btn-gold {
                background-color: #D4A017;
                color: white;
            }
            .btn-gold:hover {
                background-color: #B8860B;
            }
            .table th {
                background-color: #D4A017;
                color: white;
            }
            .btn-gray {
                background-color: #6c757d;
                color: white;
            }
            .btn-gray:hover {
                background-color: #5a6268;
            }
        </style>
    
    </head>
    <body>
        <%@include file="header.jsp"%>
        <div class="container">
            <h2 class="text-center">📚 User List</h2>
            <a href="home.jsp" class="btn btn-secondary">🏠 Home</a>
            <a href="booklist.jsp" class="btn btn-primary">📚 Book List</a>
            <a href="LogoutServlet" class="btn btn-danger float-end">🚪 Logout</a>

            <!-- Search and Sort Form -->
            <form action="ListServlet" method="get" class="row g-3 mt-3">
                <%
                    String searchQuery = request.getParameter("search") != null ? request.getParameter("search") : "";
                    String sortOption = request.getParameter("sort") != null ? request.getParameter("sort") : "";
                %>
                <div class="col-md-4">
                    <input type="text" class="form-control" name="search" placeholder="Search" value="<%= searchQuery %>">
                </div>
                <div class="col-md-3">
                    <select class="form-select" name="sort">
                        <option value="">Sort by</option>
                        <option value="name" <%= "name".equals(sortOption) ? "selected" : "" %>>Name</option>
                        <option value="dob" <%= "dob".equals(sortOption) ? "selected" : "" %>>Date of Birth</option>
                    </select>
                </div>
                <div class="col-md-3">
                    <button type="submit" class="btn btn-gold">Apply</button>
                </div>
            </form>

            <!-- User Table -->
            <table class="table table-bordered text-center mt-3">
                <thead>
                    <tr>
                        <th>STT</th>
                        <th>Full Name</th>
                        <th>Date of Birth</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Gender</th>
                        <th>Role</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Account> userList = (List<Account>) request.getAttribute("userList");
                        if (userList != null && !userList.isEmpty()) {
                            int index = 1;
                            for (Account user : userList) {
                    %>
                    <tr>
                        <td><%= index++ %></td>
                        <td><%= user.getFullname() %></td>
                        <td><%= user.getDob() %></td>
                        <td><%= user.getEmail() %></td>
                        <td><%= user.getPhone() %></td>
                        <td><%= user.getGender() %></td>
                        <td>
                            <select class="update-role" data-id="<%= user.getAccountId() %>">
                                <option value="1" <%= user.getRole().getRoleId() == 1 ? "selected" : "" %>>Admin</option>
                                <option value="2" <%= user.getRole().getRoleId() == 2 ? "selected" : "" %>>User</option>
                                <option value="3" <%= user.getRole().getRoleId() == 3 ? "selected" : "" %>>Manager</option>
                            </select>
                        </td>

                        <td>
                            <select class="update-status" data-id="<%= user.getAccountId() %>">
                                <option value="Active" <%= "Active".equals(user.getStatus()) ? "selected" : "" %>>ACTIVE</option>
                                <option value="Inactive" <%= "Inactive".equals(user.getStatus()) ? "selected" : "" %>>INACTIVE</option>
                            </select>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="9">No users found.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>

            <!-- Pagination -->
            <%
    int currentPage = request.getAttribute("currentPage") != null ? (Integer) request.getAttribute("currentPage") : 1;
    int totalPages = request.getAttribute("totalPages") != null ? (Integer) request.getAttribute("totalPages") : 1;
%>
<nav>
    <ul class="pagination justify-content-center">
        <li class="page-item <%= (currentPage == 1) ? "disabled" : "" %>">
            <a class="page-link" href="?page=<%= currentPage - 1 %>&sort=<%= sortOption %>&search=<%= searchQuery %>">Previous</a>
        </li>
        <% for (int i = 1; i <= totalPages; i++) { %>
        <li class="page-item <%= (currentPage == i) ? "active" : "" %>">
            <a class="page-link" href="?page=<%= i %>&sort=<%= sortOption %>&search=<%= searchQuery %>"><%= i %></a>
        </li>
        <% } %>
        <li class="page-item <%= (currentPage == totalPages) ? "disabled" : "" %>">
            <a class="page-link" href="?page=<%= currentPage + 1 %>&sort=<%= sortOption %>&search=<%= searchQuery %>">Next</a>
        </li>
    </ul>
</nav>
        </div>

        <script>
            $(".update-role, .update-status").change(function () {
                let accountId = $(this).data("id");
                let roleId = $(".update-role[data-id='" + accountId + "']").val(); 
                let status = $(".update-status[data-id='" + accountId + "']").val();

                $.post("UpdateUserServlet", {accountId: accountId, roleId: roleId, status: status}, function (response) {
                    alert("User updated successfully!");
                }).fail(function () {
                    alert("Failed to update user.");
                });
            });

        </script>

    </body>
</html>
