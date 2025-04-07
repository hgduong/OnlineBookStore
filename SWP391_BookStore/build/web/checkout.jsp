<%-- 
    Document   : checkout
    Created on : Feb 27, 2025, 2:00:04 PM
    Author     : TungPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Checkout</title>
        <!-- Bootstrap CSS -->

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

        <script src="js/jquery.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
    </head>
    <body>
        <%@include file="header.jsp"%>

        <div class="container">
            <h1>Thông tin đơn hàng</h1>

            <div class="row" style="margin-bottom: 30px">
                <div class="col-md-8">
                    <!-- Danh sách sản phẩm -->
                    <div class="product-list mb-4">
                        <h2>Danh sách sản phẩm</h2>
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>Tên sách</th>
                                    <th>Số lượng</th>
                                    <th>Tổng tiền</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${items}" var="item">
                                    <tr>
                                        <td>${item.bookName}</td>
                                        <td>${item.quantity}</td>
                                        <td>${item.price} đ</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <div class="address-info">
                        <h2>Địa chỉ giao hàng</h2>
                        <div class="form-group">
                            <label for="addressSelect">Chọn địa chỉ:</label>
                            <select class="form-control" id="addressSelect" name="addressId">
                                <c:choose>
                                    <c:when test="${not empty addresses}">
                                        <c:forEach items="${addresses}" var="address">
                                            <option value="${address.addressId}"
                                                    <c:if test="${address.addressId == currentAddress.addressId}">selected</c:if>>
                                                ${address.street}, 
                                                ${fn:replace(
                                                  fn:replace(
                                                  fn:replace(
                                                  fn:replace(address.ward, 'Hoang Mai', 'Hoàng Mai'),
                                                  'Cau Giay', 'Cầu Giấy'),
                                                  'Ba Dinh', 'Ba Đình'),
                                                  'Dong Da', 'Đống Đa')}, 
                                                  ${fn:replace(address.city, 'Ha Noi', 'Hà Nội')}
                                                  <c:if test="${address.isIsMain()}"> (Mặc định)</c:if>
                                                  </option>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="" disabled>Chưa có địa chỉ</option>
                                        </c:otherwise>
                                    </c:choose>
                                </select>
                            </div>
                        </div>

                        <div class="discount mb-4">
                            <h2>Mã giảm giá</h2>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <!-- Tổng tiền -->
                        <div class="total-price">
                            <h2>Tổng tiền</h2>
                            <p>Tổng tiền sản phẩm: ${totalPrice} đ</p>
                            <p>Giá ship: <span id="shippingPrice">${shippingPrice}</span> đ</p>
                            <p>Mã giảm giá: -0 đ</p>  
                            <p>Tổng cộng: <span id="finalPrice">${finalPrice}</span> đ</p>
                        </div>
                        <!-- Nút thanh toán -->
                        <form action="payment-method" method="GET">
                            <button type="submit" class="btn btn-success btn-block">Thanh toán</button>
                        </form>
                    </div>
                </div>


            </div>
            <%@include file="footer.jsp"%> 
            <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>


            <script>
                $(document).ready(function () {
                    $("#addressSelect").change(function () {
                        var selectedAddressId = $(this).val();

                        var contextPath = "<%= request.getContextPath()%>";
                        $.ajax({
                            url: contextPath + "/checkout",
                            type: "POST",
                            data: {addressId: selectedAddressId},
                            dataType: "json",
                            success: function (data) {
                                $("#shippingPrice").text(data.shippingPrice + " ");
                                $("#finalPrice").text(data.finalPrice + " ");
                            },
                            error: function (xhr, status, error) {
                                alert("Lỗi xảy ra khi chọn địa chỉ: " + xhr.responseText);
                            }
                        });
                    });
                });
            </script>
        </body>
    </html>