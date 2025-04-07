<%@ page import="model.Blog" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Blog blog = (Blog) request.getAttribute("blog");
    boolean isEditing = request.getParameter("edit") != null; 
%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title><%= isEditing ? "Chỉnh sửa Blog" : blog.getTitle() %></title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            body {
                background-color: #fff9e6;
            }
            .container {
                max-width: 800px;
            }
            .card {
                border-radius: 10px;
                padding: 20px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            }
            .form-control {
                border-radius: 8px;
            }
            .btn-warning {
                font-weight: bold;
                transition: 0.3s;
            }
            .btn-warning:hover {
                background-color: #e69500;
            }
            .img-preview {
                width: 100%;
                height: auto;
                border-radius: 10px;
                margin-bottom: 15px;
            }
            .blog-content {
                text-align: justify;
                font-size: 1.1rem;
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
        <div class="container mt-5">
            <div class="card">
                <% if (isEditing) { %>
                
                <h3 class="text-warning text-center mb-4">View Detail Blog</h3>
                <form action="BlogServlet?action=update" method="post">
                    <input type="hidden" name="blogId" value="<%= blog.getBlogId() %>">
                    <div class="mb-3">
                        <label class="form-label">Title</label>
                        <input type="text" class="form-control" name="title" value="<%= blog.getTitle() %>" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Description</label>
                        <input type="text" class="form-control" name="description" value="<%= blog.getDescription() %>" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Image</label>
                        <input type="text" class="form-control" name="image" value="<%= blog.getImage() %>" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Content</label>
                        <textarea class="form-control" name="content" rows="5" required><%= blog.getContent() %></textarea>
                    </div>
                    <div class="d-flex justify-content-between">
                        <a href="BlogServlet?action=detail&blogId=<%= blog.getBlogId() %>" class="btn btn-secondary">Cancel</a>
                        <button type="submit" class="btn btn-success">Save</button>
                    </div>
                </form>
                <% } else { %>
                
                <h2 class="text-warning text-center"><%= blog.getTitle() %></h2>

                <img src="images\shop\product12.jpg" class="img-preview d-block mx-auto" style="max-width: 150px; height: auto;" alt="Ảnh bài viết">
                <p class="blog-content"><%= blog.getContent() %></p>
                <div class="d-flex justify-content-between">
                    <a href="BlogServlet?action=list" class="btn btn-secondary">Back</a>
                    <a href="BlogServlet?action=edit&blogId=<%= blog.getBlogId() %>" class="btn btn-warning">Edit</a>
                </div>
                <% } %>
            </div>
        </div>
    </body>
</html>
