<%-- 
    Document   : faq
    Created on : Mar 19, 2025, 5:48:58 PM
    Author     : TungPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FAQ - Câu hỏi thường gặp</title>
        <style>
            body {

                background-color: #f9f9f9;
                margin: 0;
                padding: 0;
            }
            .faq-container {
                width: 80%;
                max-width: 800px;
                margin: 30px auto;
                background: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
            }
            h1 {
                text-align: center;
                color: #333;
            }
            .faq-item {
                border-bottom: 1px solid #ddd;
                padding: 15px 0;
            }
            .faq-question {
                font-size: 18px;
                font-weight: bold;
                cursor: pointer;
                color: #007bff;
                transition: color 0.3s;
            }
            .faq-question:hover {
                color: #0056b3;
            }
            .faq-answer {
                display: none;
                font-size: 16px;
                margin-top: 10px;
                color: #444;
            }
        </style>
    </head>
    <body>
        <%@include file="header.jsp"%> 
        <div class="faq-container">
            <h1>Câu hỏi thường gặp</h1>

            <div class="faq-item">
                <div class="faq-question">1. Làm thế nào để đặt hàng?</div>
                <div class="faq-answer">Quý khách có thể đặt hàng trực tiếp trên website bằng cách thêm sản phẩm vào giỏ hàng và tiến hành thanh toán.</div>
            </div>

            <div class="faq-item">
                <div class="faq-question">2. Tôi có thể đổi/trả sản phẩm không?</div>
                <div class="faq-answer">Có, chúng tôi hỗ trợ đổi/trả sản phẩm trong vòng 7 ngày nếu sản phẩm có lỗi từ nhà sản xuất. Vui lòng vào Return and Exchange Policy để biết thêm chi tiết</div>
            </div>

            <div class="faq-item">
                <div class="faq-question">3. Phương thức thanh toán nào được chấp nhận?</div>
                <div class="faq-answer">Chúng tôi chấp nhận thanh toán qua chuyển khoản ngân hàng và thanh toán khi nhận hàng (COD).</div>
            </div>
        </div>
        <%@include file="footer.jsp"%> 
        <script>
            document.querySelectorAll('.faq-question').forEach(item => {
                item.addEventListener('click', function () {
                    let answer = this.nextElementSibling;
                    answer.style.display = (answer.style.display === 'block') ? 'none' : 'block';
                });
            });
        </script>

    </body>
</html>
