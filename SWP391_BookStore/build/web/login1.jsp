<%-- 
    Document   : login
    Created on : Feb 18, 2025, 5:08:20 PM
    Author     : TungPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/login.css">
        <title>Login Form</title>
        <style>

        </style>
    </head>
    <body>

        <div class="login-container">
            <c:if test="${not empty errorMessage}">
                <div class="error-message" style="color: red;">
                    ${errorMessage}
                </div>
            </c:if>
            <h2>Login</h2>
            <form action="login" method="POST">
                <div class="input-group">
                    <input type="text" id="username" name="username" value="${username}" placeholder="Username" required>
                </div>
                <div class="input-group">
                    <input type="password" id="password" name="password" placeholder="Password" required>
                </div>
                <div class="forgot">
                    <section>
                        <input type ="checkbox" id="check">
                        <label for="check">Remember me</label>
                    </section>
                    <section>
                        <a href="#">Forgot password?</a>
                    </section>
                </div>
                <div class="input-submit">
                    <button class="submit-btn" id ="submit">Sign In</button>
                    
                </div>

                <div class="signup">
                    Don't have an account? <a href="#">Sign up</a>
                </div>
            </form>
        </div>

    </body>
</html>

