<%-- 
    Document   : cod-email-verify
    Created on : Mar 26, 2025, 9:54:53 AM
    Author     : TungPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
    <head>
        <title>Verify OTP</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <style>
            #container {
                max-width: 400px;
                margin: 50px auto;
                padding: 30px;
                background-color: #f9f9f9;
                border-radius: 10px;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                text-align: center;
            }

            #container h1 {
                color: #333;
                margin-bottom: 25px;
                font-size: 24px;
            }

            #otpInput {
                width: 100%;
                padding: 10px;
                margin-bottom: 15px;
                border: 2px solid #ddd;
                border-radius: 5px;
                font-size: 16px;
                text-align: center;
                transition: border-color 0.3s ease;
            }

            #otpInput:focus {
                outline: none;
                border-color: #4CAF50;
            }

            #saveBtn, #resendBtn {
                width: 100%;
                padding: 12px;
                margin-bottom: 15px;
                border: none;
                border-radius: 5px;
                font-size: 16px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            #saveBtn {
                background-color: #4CAF50;
                color: white;
            }

            #saveBtn:hover {
                background-color: #45a049;
            }

            #saveBtn:disabled {
                background-color: #cccccc;
                cursor: not-allowed;
            }

            #resendBtn {
                background-color: #2196F3;
                color: white;
            }

            #resendBtn:hover {
                background-color: #1E88E5;
            }

            #resendBtn:disabled {
                background-color: #cccccc;
                cursor: not-allowed;
            }

            #timer {
                color: #666;
                font-weight: bold;
                margin-bottom: 15px;
            }

            #message {
                margin-top: 15px;
                font-weight: bold;
            }

            #message[style*="color: green"] {
                color: #4CAF50 !important;
            }

            #message[style*="color: red"] {
                color: #F44336 !important;
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
        <c:set var="contextPath" value="${pageContext.request.contextPath}" />
        <%@include file="header.jsp"%>
        <div id="container">
            <h1>Nhập mã OTP</h1>
            <input type="text" id="otpInput" />
            <button id="saveBtn">Lưu</button>
            <p id="timer">Còn 60 giây</p>
            <button id="resendBtn" disabled>Gửi lại OTP</button>
            <p id="message"></p>
        </div>

        <script>
            var timeLeft = 60;
            var timerId = setInterval(countdown, 1000);

            function countdown() {
                if (timeLeft == 0) {
                    clearInterval(timerId);
                    $("#message").text("Mã OTP đã hết hạn");
                    $("#otpInput").prop("disabled", true);
                    $("#saveBtn").prop("disabled", true);
                    $("#resendBtn").prop("disabled", false);
                    $("#timer").text("Còn " + timeLeft + " giây");
                } else {
                    $("#timer").text("Còn " + timeLeft + " giây");
                    timeLeft--;
                }
            }

            $("#saveBtn").click(function () {
                $.ajax({
                    url: "payment-cod",
                    type: "POST",
                    data: {otp: $("#otpInput").val()},
                    success: function (response) {
                        if (response == "success") {
                            $("#message").text("Xác nhận thành công");
                            window.location.href = "${contextPath}/thanks.jsp";
                        } else if (response == "expired") {
                            $("#message").text("Mã OTP đã hết hạn");
                        } else {
                            $("#message").text("Mã OTP không đúng");
                        }
                    }
                });
            });

            $("#resendBtn").click(function () {
                $.ajax({
                    url: "resendOTP",
                    type: "POST",
                    success: function (response) {
                        if (response == "success") {
                            timeLeft = 60;
                            timerId = setInterval(countdown, 1000);
                            $("#otpInput").prop("disabled", false);
                            $("#saveBtn").prop("disabled", false);
                            $("#message").text("Đã gửi lại mã OTP");
                            $("#resendBtn").prop("disabled", true);
                        }
                    }
                });
            });
        </script>
    </body>
</html>