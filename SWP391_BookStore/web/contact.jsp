<%-- 
    Document   : contact
    Created on : Mar 10, 2025, 1:56:09 PM
    Author     : TungPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Liên Hệ - Book Shop</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
                margin: 0;
                padding: 0;
                display: flex;
                flex-direction: column;
                height: 100vh;
            }
            .contact-container {
                background: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                justify-content: center;
                text-align: center;
                align-items: center;
                width: 100%;
            }
            h1 {
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
        </style>
    </head>
    <body>
        <%@include file="header.jsp"%> 
        <div class="contact-container">
            <h1>📚 Book Shop 📚</h1>
            <p><strong>Số điện thoại:</strong> <span class="highlight">0988 454 009</span></p>
            <p><strong>Email:</strong> <span class="highlight">bookstore@gmail.com</span></p>
            <h2>Về chúng tôi</h2>
            <p>📖 Book Shop là điểm đến lý tưởng dành cho những người yêu sách. Tại đây, chúng tôi mang đến hàng ngàn đầu sách phong phú từ văn học cổ điển, tiểu thuyết, sách kinh tế, khoa học đến truyện tranh và sách thiếu nhi.</p>
            <p>🛒 Chúng tôi cam kết cung cấp sách chính hãng với giá cả hợp lý, cùng nhiều ưu đãi hấp dẫn. Dịch vụ giao hàng nhanh chóng giúp bạn nhận sách trong thời gian ngắn nhất.</p>
            <p>📍 Ghé thăm Book Shop ngay hôm nay để khám phá kho tàng tri thức vô tận!</p>
        </div>
        <%@include file="footer.jsp"%> 
    </body>
</html>