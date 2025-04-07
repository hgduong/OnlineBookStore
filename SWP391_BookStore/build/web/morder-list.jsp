<%-- 
    Document   : morder-list
    Created on : Mar 20, 2025, 1:57:04 PM
    Author     : TungPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Management</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                line-height: 1.6;
                margin: 0;
                padding: 20px;
                background-color: #f4f4f4;
            }
            #container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 20px;
                background-color: #fff;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-radius: 5px;
            }
            h1 {
                color: #333;
                text-align: center;
                margin-bottom: 30px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
            }
            th, td {
                padding: 12px 15px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }
            th {
                background-color: #f2f2f2;
                font-weight: bold;
            }
            tr:hover {
                background-color: #f9f9f9;
            }
            .status-select {
                padding: 8px;
                border-radius: 4px;
                border: 1px solid #ddd;
                width: 100%;
            }
            .status-pending {
                color: #856404;
                background-color: #fff3cd;
                padding: 3px 8px;
                border-radius: 3px;
            }
            .status-completed {
                color: #155724;
                background-color: #d4edda;
                padding: 3px 8px;
                border-radius: 3px;
            }
            .status-failed {
                color: #721c24;
                background-color: #f8d7da;
                padding: 3px 8px;
                border-radius: 3px;
            }
            .status-processing {
                color: #0c5460;
                background-color: #d1ecf1;
                padding: 3px 8px;
                border-radius: 3px;
            }
            .search-container {
                margin-bottom: 20px;
                display: flex;
                justify-content: space-between;
            }
            .search-box {
                padding: 10px;
                width: 300px;
                border: 1px solid #ddd;
                border-radius: 4px;
            }
            .pagination {
                display: flex;
                justify-content: center;
                margin-top: 20px;
            }
            .pagination a {
                color: black;
                float: left;
                padding: 8px 16px;
                text-decoration: none;
                border: 1px solid #ddd;
                margin: 0 4px;
            }
            .pagination a.active {
                background-color: #4CAF50;
                color: white;
                border: 1px solid #4CAF50;
            }
            .pagination a:hover:not(.active) {
                background-color: #ddd;
            }
        </style>
        <script>
            function updateStatus(orderId, selectElement) {
                var status = selectElement.value;
                var contextPath = '<%=request.getContextPath()%>';
                fetch(contextPath + '/list-order?orderId=' + orderId + '&status=' + status, {
                    method: 'POST'
                }).then(response => {
                    if (response.ok) {
                        alert('Status updated successfully');
                    }
                });

                console.log('Updated order ' + orderId + ' status to: ' + status);
                selectElement.className = 'status-select ' + 'status-' + status.toLowerCase();
            }
        </script>
    </head>
    <body>
        <%@include file="header.jsp"%> 
        <div id="container">
            <h1>Order Management</h1>

            <div class="search-container">
                <input type="text" class="search-box" placeholder="Search by account name, order ID or transaction code...">
                <div>
                    <label for="statusFilter">Filter by status:</label>
                    <select id="statusFilter" class="status-select" style="width: auto;">
                        <option value="all">All</option>
                        <option value="pending">Pending</option>
                        <option value="completed">Completed</option>
                        <option value="processing">Processing</option>
                        <option value="failed">Failed</option>
                    </select>
                </div>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Account Name</th>
                        <th>Order Date</th>
                        <th>Payment Type</th>
                        <th>Total Amount</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${orderList}" var="order">
                        <tr>
                            <td>${order.orderId}</td>
                            <td>${order.fullName}</td>
                            <td>${order.orderDate}</td>
                            <td>${order.paymentType}</td>
                            <td>${order.totalPrice} VND</td>
                            <td>
                                <c:if test="${order.paymentType == 'Banking'}">
                                    <select class="status-select status-${fn:toUpperCase(order.status)}" 
                                            onchange="updateStatus(${order.orderId}, this)">
                                        <option value="PAYMENT PROCESSING" ${order.status eq 'PAYMENT PROCESSING' ? 'selected' : ''}>PAYMENT PROCESSING</option>
                                        <option value="PAYMENT COMPLETED" ${order.status eq 'PAYMENT COMPLETED' ? 'selected' : ''}>PAYMENT COMPLETED</option>
                                        <option value="PAYMENT FAILED" ${order.status eq 'PAYMENT FAILED' ? 'selected' : ''}>PAYMENT FAILED</option>
                                        <option value="ORDER PREPARING" ${order.status eq 'ORDER PREPARING' ? 'selected' : ''}>ORDER PREPARING</option>
                                        <option value="DELIVERING" ${order.status eq 'DELIVERING' ? 'selected' : ''}>DELIVERING</option>
                                        <option value="COMPLETED" ${order.status eq 'COMPLETED' ? 'selected' : ''}>COMPLETED</option>
                                        <option value="RETURNED" ${order.status eq 'RETURNED' ? 'selected' : ''}>RETURNED</option>
                                    </select>
                                </c:if>
                                <c:if test="${order.paymentType == 'COD'}">
                                    <select class="status-select status-${fn:toUpperCase(order.status)}" 
                                            onchange="updateStatus(${order.orderId}, this)">
                                        <option value="MAIL CONFIRMED" ${order.status eq 'MAIL CONFIRMED' ? 'selected' : ''}>MAIL CONFIRMED</option>
                                        <option value="ORDER PREPARING" ${order.status eq 'PREPARING ORDER' ? 'selected' : ''}>ORDER PREPARING</option>
                                        <option value="DELIVERING" ${order.status eq 'DELIVERING' ? 'selected' : ''}>DELIVERING</option>
                                        <option value="COMPLETED" ${order.status eq 'COMPLETED' ? 'selected' : ''}>COMPLETED</option>
                                        <option value="RETURNED" ${order.status eq 'RETURNED' ? 'selected' : ''}>RETURNED</option>
                                    </select>
                                </c:if>

                            </td>
                        </tr>
                    </c:forEach>

                    <!-- Nếu không có dữ liệu, hiển thị thông báo -->
                    <c:if test="${empty orderList}">
                        <tr>
                            <td colspan="7" style="text-align: center;">No payment records found</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>

            <!-- Phân trang -->
            <div class="pagination">
                <a href="#">&laquo;</a>
                <a href="#" class="active">1</a>
                <a href="#">2</a>
                <a href="#">3</a>
                <a href="#">4</a>
                <a href="#">5</a>
                <a href="#">&raquo;</a>
            </div>
        </div>
    </body>
</html>