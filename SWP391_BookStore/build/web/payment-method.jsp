<%-- 
    Document   : payment-method
    Created on : Mar 6, 2025, 4:59:32 PM
    Author     : TungPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Trang Thanh Toán</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/font-awesome.min.css" rel="stylesheet">
        <link href="css/prettyPhoto.css" rel="stylesheet">
        <link href="css/price-range.css" rel="stylesheet">
        <link href="css/animate.css" rel="stylesheet">
        <link href="css/responsive.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">

        <link rel="shortcut icon" href="images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
                padding: 20px;
            }
            form {
                max-width: 400px;
                margin: auto;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 8px;
                background-color: #f9f9f9;
            }
            input, select {
                width: 100%;
                padding: 8px;
                margin: 5px 0;
                border: 1px solid #ddd;
                border-radius: 4px;
            }
            
            .button-style {
                background-color: #4CAF50; 
                border: none; 
                color: white; 
                padding: 10px 20px; 
                text-align: center; 
                text-decoration: none;
                display: inline-block;
                font-size: 16px; 
                cursor: pointer;
            }
        </style>

    </head>
    <body>
        <c:set var="contextPath" value="${pageContext.request.contextPath}" />
        <%@include file="header.jsp"%> 
        <h2 style="text-align:center;">Thanh Toán</h2>

        <form action="process-payment" method="post">
            <label>Người mua:</label>
            <input type="text" name="buyerName" value="${buyerName}" disabled>

            <label>Số lượng sản phẩm:</label>
            <input type="text" name="quantity" value="${quantity}" disabled>

            <label>Tổng giá tiền:</label>
            <input type="text" name="totalPrice" value="${totalPrice}" disabled>

            <label>Địa chỉ đặt mua:</label>
            <input type="text" name="address" value="${address.street},${address.ward} , ${address.city}" disabled>

            <label>Phương thức thanh toán:</label>
            <select name="paymentMethod">
                <option value="COD">Thanh toán khi nhận hàng (COD)</option>
                <option value="Banking">Chuyển khoản ngân hàng</option>
            </select>

            <!-- Đổi thành button với id để xử lý chuyển hướng -->
            <a href="#" id="confirmPayment" class="button-style">Xác nhận thanh toán</a>
        </form>

        <%@include file="footer.jsp"%> 

        <script>
            document.getElementById("confirmPayment").addEventListener("click", function(e) {
                e.preventDefault();
                var paymentMethod = document.querySelector('select[name="paymentMethod"]').value;
                if(paymentMethod === "COD") {
                    window.location.href = "${contextPath}/payment-cod";
                } else if(paymentMethod === "Banking") {
                    window.location.href = "${contextPath}/payment-banking";
                }
            });
        </script>
    </body>
</html>