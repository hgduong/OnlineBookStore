<%-- 
    Document   : payment
    Created on : Mar 10, 2025, 2:05:17 PM
    Author     : TungPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thanh Toán Chuyển Khoản</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
                margin: 0;
                padding: 0;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                height: 100vh;
            }
            .payment-container {
                background: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                text-align: center;
                width: 50%;
            }
            h2 {
                color: #007bff;
            }
            p {
                font-size: 18px;
                margin: 10px 0;
            }
            .highlight {
                font-weight: bold;
                color: #28a745;
            }
            img {
                width: 150px;
                margin: 10px 0;
            }
            input {
                width: 100%;
                padding: 10px;
                margin-top: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
            }
            button {
                background-color: #007bff;
                color: white;
                padding: 10px;
                border: none;
                cursor: pointer;
                width: 100%;
                margin-top: 10px;
                border-radius: 5px;
            }
            button:hover {
                background-color: #0056b3;
            }
        </style>
    </head>

    <body>
        <%@include file="header.jsp"%> 
        <c:set var="contextPath" value="${pageContext.request.contextPath}" />
        <div class="payment-container">
            <h2>💳 Thông Tin Thanh Toán</h2>
            <p><strong>Ngân hàng:</strong> <span class="highlight">BIDV</span></p>
            <p><strong>Tên tài khoản:</strong> <span class="highlight">Phạm Thanh Tùng</span></p>
            <p><strong>Số tài khoản:</strong> <span class="highlight">4681050242</span></p>

            <h3>📌 Quét mã QR để thanh toán</h3>
            <img src="images/home/qrcode.png" alt="QR Code Thanh Toán">

            <form action="payment-banking" method="post">
                <label><strong>Nhập mã giao dịch:</strong></label>
                <input type="text" name="transactionCode" placeholder="Nhập mã giao dịch" required>
                <button type="submit">💾 Lưu giao dịch</button>
            </form>
        </div>

    </body>
</html>
