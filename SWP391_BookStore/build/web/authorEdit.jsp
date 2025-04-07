<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Author</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
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
        <div class="container py-5">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card shadow-sm">
                        <div class="card-header bg-warning text-dark">
                            <h2 class="mb-0">
                                <i class="bi bi-pencil-square me-2"></i>Edit Author
                            </h2>
                        </div>
                        <div class="card-body">
                            <c:if test="${not empty errorMessage}">
                                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                    <i class="bi bi-exclamation-triangle-fill me-2"></i>
                                    ${errorMessage}
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                            </c:if>

                            <form action="AuthorEditController" method="post">
                                <input type="hidden" name="authorId" value="${author.authorId}">

                                <div class="mb-3">
                                    <label for="name" class="form-label">Name:</label>
                                    <input type="text" class="form-control" id="name" 
                                           name="name" value="${author.name}" required>
                                </div>

                                <div class="mb-3">
                                    <label for="dob" class="form-label">Date of Birth:</label>
                                    <input type="date" class="form-control" id="dob" 
                                           name="dob" value="${author.dob}" required>
                                </div>

                                <div class="mb-3">
                                    <label for="biography" class="form-label">Biography:</label>
                                    <textarea class="form-control" id="biography" 
                                              name="biography" maxlength="500" rows="4">${author.biography}</textarea>
                                </div>

                                <div class="d-flex justify-content-between">
                                    <a href="AuthorController" class="btn btn-secondary">
                                        <i class="bi bi-arrow-left me-2"></i>Back to List
                                    </a>
                                    <button type="submit" class="btn btn-warning">
                                        <i class="bi bi-upload me-2"></i>Update Author
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
