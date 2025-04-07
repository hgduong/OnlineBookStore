<%@ page import="model.Blog" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Blog blog = (Blog) request.getAttribute("blog");
%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Edit Blog</title>
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
                <h3 class="text-warning text-center mb-4">✏ ️Update Blog</h3>
                <form action="BlogServlet?action=update" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="blogId" value="<%= blog.getBlogId()%>">
                    <div class="mb-3">
                        <label class="form-label">📌 Title</label>
                        <input type="text" class="form-control" name="title" value="<%= blog.getTitle()%>" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">📝 Description</label>
                        <input type="text" class="form-control" name="description" value="<%= blog.getDescription()%>" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">📖 Content</label>
                        <textarea class="form-control" name="content" rows="5" required><%= blog.getContent()%></textarea>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">🖼 Current Image</label><br>
                        <img src="images\shop\product12.jpg" class="img-preview">
                        <input type="hidden" name="existingImage" value="<%= blog.getImage()%>">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">📌 Status</label>
                        <select class="form-control" name="status">
                            <option value="Published" <%= blog.getStatus().equals("Published") ? "selected" : ""%>>Published</option>

                        </select>
                    </div>
                    <div class="d-flex justify-content-between">
                        <a href="BlogServlet?action=list" class="btn btn-secondary">Cancel</a>
                        <button type="submit" class="btn btn-success">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
