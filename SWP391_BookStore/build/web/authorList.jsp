<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Author List</title>
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
            <div class="row">
                <div class="col-12">
                    <div class="card shadow-sm">
                        <div class="card-header bg-success text-white d-flex justify-content-between align-items-center">
                            <h2 class="mb-0">
                                <i class="bi bi-people me-2"></i>Author List
                            </h2>
                            <a href="AuthorAddController" class="btn btn-light">
                                <i class="bi bi-plus-circle me-2"></i>Add New Author
                            </a>
                        </div>

                        <div class="card-body">
                            <div class="mb-3 d-flex justify-content-between align-items-center">
                                <form action="AuthorController" method="get" class="d-flex">
                                    <input type="hidden" name="action" value="search">
                                    <input type="text" name="searchTerm" class="form-control me-2" placeholder="Search by name...">
                                    <button type="submit" class="btn btn-outline-primary">
                                        <i class="bi bi-search me-1"></i>Search
                                    </button>
                                </form>
                            </div>

                            <c:if test="${not empty errorMessage}">
                                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                    <i class="bi bi-exclamation-triangle-fill me-2"></i>
                                    ${errorMessage}
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                            </c:if>

                            <div class="table-responsive">
                                <table class="table table-striped table-hover">
                                    <thead class="table-light">
                                        <tr>
                                            <th>ID</th>
                                            <th>Name</th>
                                            <th>Date of Birth</th>
                                            <th>Biography</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="author" items="${authors}">
                                            <tr>
                                                <td>${author.authorId}</td>
                                                <td>${author.name}</td>
                                                <td>${author.dob}</td>
                                                <td class="text-truncate" style="max-width: 300px;" title="${author.biography}">
                                                    ${author.biography}
                                                </td>
                                                <td>
                                                    <a href="AuthorEditController?action=edit&authorId=${author.authorId}" 
                                                       class="btn btn-sm btn-warning">
                                                        <i class="bi bi-pencil me-1"></i>Edit
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                                <c:if test="${empty authors}">
                                    <div class="alert alert-info text-center" role="alert">
                                        <i class="bi bi-info-circle me-2"></i>
                                        No authors available. Click "Add New Author" to create one.
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
