<%@ page import="java.util.List" %>
<%@ page import="model.Blog" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    List<Blog> blogs = (List<Blog>) request.getAttribute("blogs");
%>


<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Blog List</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>

            body {
                background-color: #fff9e6;
            }

            .container {
                max-width: 1200px;
            }

            .blog-header {
                text-align: center;
                margin-bottom: 20px;
            }

            .blog-card {
                width: 300px;
                height: 450px;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: space-between;
                padding: 15px;
                text-align: center;
            }

            .blog-card:hover {
                transform: translateY(-5px);
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
            }

            .blog-card img {
                width: 100%;
                height: 200px;
                object-fit: cover;
            }

            .btn-add-blog {
                display: flex;
                justify-content: center;
                margin-bottom: 20px;
            }

            .blog-grid {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                gap: 20px;
            }
            .search-form {
                max-width: 350px;
                margin: 0 auto;
                display: flex;
                align-items: center;
            }

            .search-form input {
                height: 38px;
                font-size: 14px;
                flex: 1;
                padding: 5px 10px;
            }

            .search-form button {
                height: 38px;
                padding: 0 12px;
                font-size: 14px;
                display: flex;
                align-items: center;
                justify-content: center;
                border-radius: 5px;
            }
            .blog-card .card-body {
                flex-grow: 1;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
            }
            .card-text {
                font-size: 14px;
                color: #6c757d;
                height: 60px;
                overflow: hidden;
                display: -webkit-box;
                -webkit-line-clamp: 3;
                -webkit-box-orient: vertical;
            }
        </style>
    <style>
            .navbar-nav {
                flex-direction: row !important;
            }
            .navbar-nav .nav-item {
                margin-right: 10px;
            }
        </style>
    </head>
    <body>
        <%@include file="header.jsp"%>
        <div class="container py-5"></div>
        <div>
            <h2 class="text-warning blog-header">Blog List</h2>


            <div class="btn-add-blog">
                <a href="editblog.jsp?action=add" class="btn btn-warning" >Add Blog</a>
            </div>
            <form action="BlogServlet" method="get" class="d-flex mb-3 search-form">
                <input type="hidden" name="action" value="search">
                <input type="text" name="keyword" class="form-control me-2" placeholder="Nhập từ khóa..." value="<%= request.getAttribute("keyword") != null ? request.getAttribute("keyword") : "" %>">
                <button type="submit" class="btn btn-primary"></button>
            </form>


            <div class="blog-grid">
                <% if (blogs != null && !blogs.isEmpty()) { %>
                <% for (Blog blog : blogs) { %>
                <div class="card blog-card d-flex align-items-center">
                    <img src="images\shop\product12.jpg" style="max-width: 200px; height: auto;" class="card-img-top" alt="<%= blog.getTitle() %>">
                    <div class="card-body">
                        <h5 class="card-title text-dark"><%= blog.getTitle() %></h5>
                        <p class="card-text text-secondary"><%= blog.getDescription() %></p>
                        <a href="BlogServlet?action=view&id=<%= blog.getBlogId() %>" class="btn btn-primary">View Detail</a>
                        <a href="BlogServlet?action=delete&id=<%= blog.getBlogId() %>" 
                           class="btn btn-danger"
                           onclick="return confirm('Bạn có chắc chắn muốn xóa blog này?');">
                            Delete
                        </a>
                    </div>
                </div>
                <% } %>
                <% } else { %>
                <p class="text-muted text-center">Don't have blog.</p>
                <% } %>
            </div>
        </div>
    </body>
</html>
