<%-- 
    Document   : Register
    Created on : Feb 3, 2025, 10:13:38 PM
    Author     : DuongNHH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng Ký</title>
        <link rel="stylesheet" href="styles.css">
        <link rel="stylesheet" type="text/css" href="styles.css">
        <style>
            body {
                background-color: #fff9db; /* Light yellow */
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            .container {
                width: 60%; /* Reduce frame size */
                max-width: 400px;
                background-color: #ffffff;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                text-align: center; /* Center content in container */
                box-sizing: border-box;
                margin-top: 200px;
            }

            h1, h2, h3 {
                color: #333;
                text-align: center;
                margin-bottom: 20px;
            }

            label {
                display: block;
                text-align: left;
                margin-top: 10px;
                font-weight: bold;
                margin-left: 5%;
            }

            input, select, button {
                width: 90%;
                padding: 10px;
                margin: 5px auto 15px;
                border: 1px solid #ccc;
                border-radius: 5px;
                box-sizing: border-box;
                display: block;
            }

            button {
                background-color: #fbc02d; /* Darker yellow */
                color: white;
                border: none;
                cursor: pointer;
                font-weight: bold;
            }

            button:hover {
                background-color: #f9a825;
            }

            p {
                text-align: center;
                margin-top: 10px;
            }


        </style>
    </head>
    <body>


        <div class="container">
            <h2>Register Account</h2>

            <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
            <% if (errorMessage != null) {%>
            <p style="color: red;"><%= errorMessage%></p>
            <% }%>

            <form action="register1" method="post">
                <label for="Fullname">Full Name:</label>
                <input type="text" id="Fullname" name="Fullname" placeholder="Enter full name" required>

                <label for="Username">Username:</label>
                <input type="text" id="Username" name="Username" placeholder="Enter username" required>

                <label for="Email">Email:</label>
                <input type="email" id="Email" name="Email" placeholder="example@gmail.com" required>

                <label for="Password">Password:</label>
                <input type="password" id="Password" name="Password" placeholder="Enter password" required>

                <label for="ConfirmPassword">Confirm Password:</label>
                <input type="password" id="ConfirmPassword" name="ConfirmPassword" placeholder="Re-enter password" required>

                <label for="DoB">Date of Birth:</label>
                <input type="date" id="DoB" name="DoB" required>
                <label for="phone">Phone:</label>
                <input type="tel" name="phone" pattern="[0-9]{10,11}" placeholder="Enter the number" required> 


                <label for="Gender">Gender:</label>
                <select id="Gender" name="Gender" required>
                    <option value="Male">Male</option>
                    <option value="Female">Female</option>
                    <option value="Other">Other</option>
                </select>
                <h3>Address</h3>
                <input type="text" name="street"  placeholder="Enter your address" required>
                <select id="Ward" name="Ward" required>
                    <option value="Ba Dinh" selected>Ba Đình</option>
                    <option value="Bac Tu Liem">Bắc Từ Liêm</option>
                    <option value="Cau Giay">Cầu Giấy</option>
                    <option value="Dong Da">Đống Đa</option>
                    <option value="Ha Dong">Hà Đông</option>
                    <option value="Hai Ba Trung">Hai Bà Trưng</option>
                    <option value="Hoang Mai">Hoàng Mai</option>
                    <option value="Hoang Lien">Hoàng Liệt</option>
                    <option value="Long Bien">Long Biên</option>
                    <option value="Nam Tu Liem">Nam Từ Liêm</option>
                    <option value="Tay Ho">Tây Hồ</option>
                    <option value="Thanh Xuan">Thanh Xuân</option>
                    <option value="Son Tay">Sơn Tây</option>
                    <option value="My Duc">Mỹ Đức</option>
                    <option value="Ba Vi">Ba Vì</option>
                    <option value="Phuc Tho">Phúc Thọ</option>
                    <option value="Dan Phuong">Đan Phượng</option>
                    <option value="Hoai Duc">Hoài Đức</option>
                    <option value="Quoc Oai">Quốc Oai</option>
                    <option value="Thach That">Thạch Thất</option>
                    <option value="Chuong My">Chương Mỹ</option>
                    <option value="Thanh Oai">Thanh Oai</option>
                    <option value="Thuong Tin">Thường Tín</option>
                    <option value="Phu Xuyen">Phú Xuyên</option>
                    <option value="Ung Hoa">Ứng Hòa</option>
                    <option value="Me Linh">Mê Linh</option>
                    <option value="Soc Son">Sóc Sơn</option>
                </select>
                <button type="submit">Register</button>
            </form>

            <p>Already have an account? <a href="Login.jsp">Login now</a></p>
        </div>

    </body>
</html>
