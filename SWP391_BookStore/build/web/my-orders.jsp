<%-- 
    Document   : my-orders
    Created on : Mar 20, 2025, 11:00:32 AM
    Author     : TungPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Danh sách đơn hàng</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f7f7f7;
                margin: 0;
                padding: 20px;
            }
            h1 {
                color: #FE980F;
                text-align: center;
                margin-bottom: 20px;
            }
            table {
                border-collapse: collapse;
                width: 90%;
                margin: 0 auto;
                background-color: #fff;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            th, td {
                border: 1px solid #ddd;
                padding: 12px 15px;
                text-align: center;
            }
            th {
                background-color: #FE980F;
                color: #fff;
                text-transform: uppercase;
            }
            tr:nth-child(even) {
                background-color: #f2f2f2;
            }
            tr:hover {
                background-color: #ffe6cc;
            }
            .no-orders {
                text-align: center;
                font-style: italic;
                padding: 20px;
                color: #555;
            }
        </style>
        <script src="js/jquery.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
    </head>
    <body>
        <%@include file="header.jsp"%> 
        <h1>Your orders</h1>
        <table>
            <thead>
                <tr>
                    <th>Order Code</th>
                    <th>Ngày đặt hàng</th>
                    <th>Tổng tiền</th>
                    <th>Trạng thái</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orders}">

                    <tr>

                        <td>
                            <a href="${pageContext.request.contextPath}/order-detail?orderId=${order.orderId}">
                                ${order.orderId}</a>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/order-detail?orderId=${order.orderId}">
                                ${order.orderDate}</a>
                        </td>
                        <td>${order.totalPrice}</td>
                        <td>${order.status}</td>

                    </tr>

                </c:forEach>
                <c:if test="${empty orders}">
                    <tr>
                        <td colspan="4" class="no-orders">There is no order.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
        <%@include file="footer.jsp"%> 
    </body>
</html>