<%-- 
    Document   : order-detail
    Created on : Mar 20, 2025, 11:35:57 AM
    Author     : TungPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Detail</title>

        <style>
            body {
                font-family: Arial, sans-serif;
                line-height: 1.6;
                margin: 0;
                padding: 20px;
            }
            #container {
                max-width: 1000px;
                margin: 0 auto;
                padding: 20px;
                border: 1px solid #ddd;
                border-radius: 5px;
                font-size: 19px;
            }
            h1 {
                color: #333;
                text-align: center;
            }
            .order-info {
                margin-bottom: 20px;
            }
            .order-info p {
                margin: 5px 0;
            }
            .highlight {
                color: #FE980F;
                font-weight: bold;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            th, td {
                padding: 10px;
                border: 1px solid #ddd;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
            .total-row {
                font-weight: bold;
                background-color: #f9f9f9;
            }
            .status {
                display: inline-block;
                padding: 5px 10px;
                border-radius: 3px;
                font-weight: bold;
            }
            .status-pending {
                background-color: #FFF3CD;
                color: #856404;
            }
            .status-delivered {
                background-color: #D4EDDA;
                color: #155724;
            }
            .status-cancelled {
                background-color: #F8D7DA;
                color: #721C24;
            }
            .status-processing {
                background-color: #D1ECF1;
                color: #0C5460;
            }
        </style>
    </head>
    <body>
        <%@include file="header.jsp"%> 
        <div id="container">
            <h1>Order Details</h1>

            <div class="order-info">
                <p><strong>Order ID:</strong> <span class="highlight">#${order.orderId}</span></p>
                <p><strong>Customer Name:</strong> ${order.fullName}</p>
                <p><strong>Phone:</strong> ${order.phone}</p>
                <p><strong>Address:</strong> ${order.address.street}, ${order.address.ward}, ${order.address.city}</p>
                <p><strong>Order Date:</strong> ${order.orderDate}</p>
                <p><strong>Delivery Date:</strong> ${order.deliveryTime}</p>
                <p>
                    <strong>Status:</strong> 
                    <span class="status 
                          <c:choose>
                              <c:when test="${order.status eq 'Pending'}">status-pending</c:when>
                              <c:when test="${order.status eq 'Delivered'}">status-delivered</c:when>
                              <c:when test="${order.status eq 'Cancelled'}">status-cancelled</c:when>
                              <c:when test="${order.status eq 'Processing'}">status-processing</c:when>
                          </c:choose>
                          ">
                        ${order.status}
                    </span>
                </p>
            </div>

            <h2>Order Items</h2>
            <table>
                <thead>
                    <tr>
                        <th>Book Name</th>
                        <th>Quantity</th>
                        <th>Total Price</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${orderDetails}" var="item">
                        <tr>
                            <td>${item.bookName}</td>
                            <td>${item.quantity}</td>
                            <td class="highlight">${item.totalPrice}</td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr class="total-row">
                        <td colspan="2" style="text-align: right;">Total:</td>
                        <td class="highlight">${order.totalPrice}</td>
                    </tr>
                </tfoot>
            </table>
        </div>
        <%@include file="footer.jsp"%> 
    </body>
</html>

