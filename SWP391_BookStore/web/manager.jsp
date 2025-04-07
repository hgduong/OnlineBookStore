<%-- 
    Document   : manager
    Created on : Mar 20, 2025, 1:08:10 PM
    Author     : TungPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manager Page</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <style>
            #dashboard-container {
                max-width: 1000px;
                margin: 0 auto;
                padding: 20px;
            }

            .row-custom {
                display: flex;
                justify-content: space-between;
                margin-bottom: 20px;
                gap: 20px;
            }
            .btn-custom {
                position: relative;
                display: flex;
                align-items: center;
                justify-content: center;
                padding: 20px;
                flex: 1;
                height: 150px;
                font-size: 18px;
                text-decoration: none;
                transition: all 0.1s ease;
                text-align: center;
            }
            .btn-custom i {
                position: absolute;
                margin-top: 57px;
                margin-left: 95px;
                left: 20px;
                font-size: 20px;
            }
            .btn-custom:hover {
                transform: scale(1.05);
                box-shadow: 0 8px 15px rgba(0,0,0,0.2);
            }
        </style>
        
    </head>
    <body>
        <%@include file="header.jsp"%>
        <div id="dashboard-container">
            <div class="row-custom">
                <a href="list-order" class="btn btn-success btn-custom" style="background: #FF4518">
                    <i class="fas fa-shopping-cart">  Order Manage</i>
                </a>
                <a href="list-payment" class="btn btn-success btn-custom">
                    <i class="fas fa-credit-card">  Payment Manage</i>
                </a>
            </div>

            <div class="row-custom">
                <a href="add-book.jsp" class="btn btn-info btn-custom">
                    <i class="fas fa-book">  Add New Book</i>
                </a>
                <a href="add-blog.jsp" class="btn btn-warning btn-custom">
                    <i class="fas fa-blog">  Add New Blog</i>
                </a>
            </div>
        </div>
    </body>
</html>