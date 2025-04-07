<%-- 
    Document   : returnpolicy
    Created on : Mar 19, 2025, 5:30:33 PM
    Author     : TungPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chính Sách Bảo Hành</title>
        <style>
            #container {
                width: 100%;
                display: flex;
                justify-content: center;
            }
            .container p{
                width: 80%;
                max-width: 800px;
                margin: 20px auto 10px auto;
                font-weight: bold;
                font-size: 16px;
                color: #444;

            }
            .warranty-table {
                border-collapse: collapse;
                width: 80%;
                max-width: 800px;
                margin: 20px auto;
            }

            .warranty-table td, .warranty-table th {
                border: 2px solid black;
                padding: 8px;
                vertical-align: top;
            }

            .warranty-table th {
                font-weight: bold;
                text-align: center;
                background-color: #f2f2f2;
            }

            .header-cell {
                font-weight: bold;
                text-align: center;
            }

            .small-cell {
                width: 20%;
            }

            .medium-cell {
                width: 25%;
            }

            .large-cell {
                width: 35%;
            }
            #warranty-text {
                width: 100%;
                max-width: 800px;
                margin: 0 auto;
                padding: 20px;
                line-height: 1.6;
                font-family: Arial, sans-serif;
                border-radius: 8px;
                box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
            }

            #warranty-text h1 {
                text-align: center;
                font-size: 24px;
                color: #333;
                margin-bottom: 15px;
            }

            #warranty-text p {
                text-align: justify;
                font-size: 16px;
                color: #444;
            }

            #warranty-text p,
            #warranty-text ul {
                margin-bottom: 10px;
            }
        </style>
    </head>
    <body>
        <%@include file="header.jsp"%> 
        <div id="warranty-text">
            <h1>Chính sách đổi/hoàn trả</h1>
            <p>BookStore Online cam kết cung cấp 100% sản phẩm chất lượng, có nguồn gốc rõ ràng, hợp pháp và an toàn cho người tiêu dùng. <br><!-- comment -->
                Chúng tôi mong muốn mang đến cho quý khách trải nghiệm mua sắm tiện lợi và đáng tin cậy. Để đảm bảo đơn hàng được xử lý tốt nhất, quý khách vui lòng kiểm tra kỹ các thông tin sau trước khi nhận hàng:
                <br>
                - Thông tin sản phẩm: Tên sách.
                <br>
                - Số lượng sản phẩm: Đảm bảo đúng với đơn đặt hàng.
                <br>
                Trong trường hợp hiếm hoi sản phẩm quý khách nhận được bị lỗi, hư hỏng hoặc không đúng mô tả, BookStore Online cam kết bảo vệ quyền lợi khách hàng thông qua chính sách đổi trả và hoàn tiền, nhằm đảm bảo chất lượng sản phẩm cũng như dịch vụ tốt nhất.

                Nếu quý khách cần hỗ trợ đổi/trả, bảo hành hoặc hoàn tiền, vui lòng liên hệ chúng tôi qua hotline 0988454009 để biết thêm chi tiết.</p>

        </div>
        <div class="container">
            <p>1.Thời gian trả hàng:</p>
            <table class="warranty-table">
                <tr>
                    <th class="small-cell">KỂ TỪ KHI GIAO HÀNG THÀNH CÔNG</th>
                    <th class="small-cell">SẢN PHẨM LỖI (do nhà cung cấp)</th>
                    <th class="small-cell">SẢN PHẨM KHÔNG LỖI</th>
                    <th class="medium-cell">SẢN PHẨM LỖI DO NGƯỜI SỬ DỤNG</th>
                </tr>
                <tr>
                    <td rowspan="2">7 ngày đầu tiên</td>
                    <td>Đổi mới</td>
                    <td rowspan="3">Trả hàng không thu phí</td>
                    <td rowspan="4">Bảo hành hoặc sửa chữa có thu phí theo quy định của nhà cung cấp.</td>
                </tr>
                <tr>
                    <td>Trả không thu phí</td>
                </tr>
                <tr>
                    <td>8 - 30 ngày</td>
                    <td>Bảo hành</td>

                </tr>
                <tr>
                    <td>30 ngày trở đi</td>
                    <td>Bảo hành</td>
                    <td>Không hỗ trợ đổi/trả</td>
                </tr>
            </table>
        </div>
        <%@include file="footer.jsp"%> 
    </body>
</html>
