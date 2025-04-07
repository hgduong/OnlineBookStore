<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Discount</title>
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
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card shadow-sm">
                    <div class="card-header bg-warning text-dark">
                        <h2 class="mb-0">
                            <i class="bi bi-pencil-square me-2"></i>Edit Discount
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

                        <form action="DiscountEditController" method="post">
                            <input type="hidden" name="discountId" value="${discount.discountId}">

                            <div class="mb-3">
                                <label for="startDate" class="form-label">Start Date</label>
                                <input type="date" class="form-control" id="startDate" 
                                       name="startDate" value="${discount.startDate}" required>
                            </div>

                            <div class="mb-3">
                                <label for="endDate" class="form-label">End Date</label>
                                <input type="date" class="form-control" id="endDate" 
                                       name="endDate" value="${discount.endDate}" required>
                            </div>

                            <div class="mb-3">
                                <label for="discountPercent" class="form-label">Discount Percent</label>
                                <div class="input-group">
                                    <input type="number" class="form-control" id="discountPercent" 
                                           name="discountPercent" step="0.01" min="0" max="100" 
                                           value="${discount.discountPercent}" required>
                                    <span class="input-group-text">%</span>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="status" class="form-label">Status</label>
                                <select class="form-select" id="status" name="status">
                                    <option value="Active" ${discount.status == 'Active' ? 'selected' : ''}>Active</option>
                                    <option value="Inactive" ${discount.status == 'Inactive' ? 'selected' : ''}>Inactive</option>
                                </select>
                            </div>

                            <div class="d-flex justify-content-between">
                                <a href="DiscountListController" class="btn btn-secondary">
                                    <i class="bi bi-arrow-left me-2"></i>Back to List
                                </a>
                                <button type="submit" class="btn btn-warning">
                                    <i class="bi bi-upload me-2"></i>Update Discount
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Optional: Add client-side validation
        document.addEventListener('DOMContentLoaded', function () {
            const startDateInput = document.getElementById('startDate');
            const endDateInput = document.getElementById('endDate');

            endDateInput.addEventListener('change', function () {
                if (new Date(startDateInput.value) > new Date(endDateInput.value)) {
                    alert('End date must be after start date');
                    endDateInput.value = '';
                }
            });
        });
    </script>
</body>
</html>
