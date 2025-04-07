<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Discount List</title>
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
        <div class="row">
            <div class="col-12">
                <div class="card shadow-sm">
                    <div class="card-header bg-success text-white d-flex justify-content-between align-items-center">
                        <h2 class="mb-0">
                            <i class="bi bi-tags me-2"></i>Discount List
                        </h2>
                        <c:if test="${sessionScope.account.role.roleId == 1}">
                            <a href="DiscountAddController" class="btn btn-light">
                                <i class="bi bi-plus-circle me-2"></i>Add New Discount
                            </a>
                        </c:if>
                    </div>

                    <div class="card-body">
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
                                        <th>Start Date</th>
                                        <th>End Date</th>
                                        <th>Discount Percent</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="discount" items="${discounts}">
                                        <tr>
                                            <td>${discount.discountId}</td>
                                            <td>${discount.startDate}</td>
                                            <td>${discount.endDate}</td>
                                            <td>${discount.discountPercent}%</td>
                                            <td>
                                                <span class="badge ${discount.status == 'Active' ? 'bg-success' : 'bg-secondary'}">
                                                    ${discount.status}
                                                </span>
                                            </td>
                                            <td>
                                                    <a href="DiscountEditController?discountId=${discount.discountId}"
                                                       class="btn btn-sm btn-warning">
                                                        <i class="bi bi-pencil me-1"></i>Edit
                                                    </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>

                            <c:if test="${empty discounts}">
                                <div class="alert alert-info text-center" role="alert">
                                    <i class="bi bi-info-circle me-2"></i>
                                    No discounts available. Click "Add New Discount" to create one.
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
