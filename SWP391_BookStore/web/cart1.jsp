<%-- 
    Document   : cart1
    Created on : Mar 13, 2025, 6:37:46 AM
    Author     : TungPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Cart | E-Shopper</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/font-awesome.min.css" rel="stylesheet">
        <link href="css/prettyPhoto.css" rel="stylesheet">
        <link href="css/price-range.css" rel="stylesheet">
        <link href="css/animate.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
        <link href="css/responsive.css" rel="stylesheet">
        <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
        <![endif]-->       
        <link rel="shortcut icon" href="images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">
        <style>
            .no-orders {
                text-align: center;
                font-style: italic;
                padding: 20px;
                color: #555;
            }
        </style>
        <script src="js/jquery.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
    </head><!--/head-->

    <body>
        <%@include file="header.jsp"%>

        <section id="cart_items">
            <div class="container">
                <div class="breadcrumbs">
                    <ol class="breadcrumb">
                        <li><a href="#">Home</a></li>
                        <li class="active">Shopping Cart</li>
                    </ol>
                </div>
                <div class="table-responsive cart_info">
                    <table class="table table-condensed">
                        <thead>
                            <tr class="cart_menu">
                                <td class="image">Item</td>
                                <td class="description"></td>
                                <td class="price">Price</td>
                                <td class="quantity">Quantity</td>
                                <td class="total">Total</td>
                                <td></td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="cartItem" items="${cartItems}">
                                <tr>
                                    <td class="cart_product">
                                        <a href=""><img src="images/shop/product12.jpg" alt="" style="width:100px;height:120px;margin-right: 20px"></a>
                                    </td>
                                    <td class="cart_description">
                                        <h4><a href="">${cartItem.bookName}</a></h4>
                                        <p>Web ID: 1089772</p>
                                        <input type="hidden" name="bookId" value="${cartItem.bookId}">
                                        <input type="hidden" name="cartId" value="${cartItem.cartId}">
                                    </td>
                                    <td class="cart_price">
                                        <p>${cartItem.bookPrice} đ</p>
                                    </td>
                                    <td class="cart_quantity">
                                        <div class="cart_quantity_button">
                                            <a class="cart_quantity_up" href="${pageContext.request.contextPath}/plus-cart-item?cartId=${cartItem.cartId}&bookId=${cartItem.bookId}"> + </a>
                                            <input 
                                                class="cart_quantity_input" type="text" name="quantity" value="${cartItem.quantity}" data-original-quantity="${cartItem.quantity}"
                                                size="2" pattern="[1-9][0-9]*" title="Vui lòng nhập số dương" oninput="this.value = this.value.replace(/[^0-9]/g, '')"/>
                                            <a class="cart_quantity_down" href="${pageContext.request.contextPath}/minus-cart-item?cartId=${cartItem.cartId}&bookId=${cartItem.bookId}"> - </a>
                                        </div>
                                    </td>
                                    <td class="cart_total">
                                        <p class="cart_total_price">${cartItem.price} đ</p>
                                    </td>
                                    <td class="cart_delete">
                                        <a class="cart_quantity_delete" href="remove-cartitem?cartId=${cartItem.cartId}&bookId=${cartItem.bookId}"><i class="fa fa-times"></i></a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty cartItems}">
                                <tr>
                                    <td colspan="4" class="no-orders">There is no item!</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>

                </div>
                <div class="total-price">
                    <h2>Tổng tiền: ${totalPrice} đ</h2>
                </div>
                <form action="checkout" method="GET">
                    <button type="submit" class="btn btn-primary" style="padding: 20px; margin:20px ">Mua hàng</button>
                </form>
            </div>
        </section> 


        <%@include file="footer.jsp"%>
        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
        <script>
            $(document).ready(function () {
                $(".cart_quantity_up").on("click", function (e) {
                    e.preventDefault();
                    var url = $(this).attr("href");
                    var $row = $(this).closest("tr");

                    $.ajax({
                        url: url,
                        type: "GET",
                        dataType: "json",
                        success: function (data) {

                            $row.find("input.cart_quantity_input").val(data.newQuantity);
                            $row.find("p.cart_total_price").text(data.newPrice + " đ");
                            $(".total-price h2").text("Tổng tiền: " + data.newTotalPrice + " đ");
                        },
                        error: function (xhr, status, error) {
                            console.error("Lỗi khi tăng số lượng: " + error);
                        }
                    });
                });

                $(".cart_quantity_down").on("click", function (e) {
                    e.preventDefault();
                    var url = $(this).attr("href");
                    var $row = $(this).closest("tr");

                    $.ajax({
                        url: url,
                        type: "GET",
                        dataType: "json",
                        success: function (data) {
                            if (data.newQuantity === 0) {
                                $row.remove();
                            } else {
                                $row.find("input.cart_quantity_input").val(data.newQuantity);
                                $row.find("p.cart_total_price").text(data.newPrice + " đ");
                            }
                            $(".total-price h2").text("Tổng tiền: " + data.newTotalPrice + " đ");
                        },
                        error: function (xhr, status, error) {
                            console.error("Lỗi khi giảm số lượng: " + error);
                        }
                    });
                });
            });

            $(".cart_quantity_input").on("change", function () {
                var $input = $(this);
                var $row = $input.closest("tr");
                var bookId = $row.find("h4 a").closest("tr").find("input[name='bookId']").val();
                var cartId = $row.find("h4 a").closest("tr").find("input[name='cartId']").val();
                var quantity = parseInt($input.val(), 10);

                if (isNaN(quantity) || quantity <= 0) {
                    alert("Vui lòng nhập số lượng lớn hơn 0");
                    $input.val($input.attr("data-original-quantity") || 1);
                    return;
                }

                $.ajax({
                    url: `${pageContext.request.contextPath}/cart`,
                    type: "POST",
                    data: {
                        bookId: bookId,
                        cartId: cartId,
                        quantity: quantity
                    },
                    dataType: "json",
                    success: function (data) {
                        $row.find("input.cart_quantity_input").val(data.newQuantity);
                        $row.find("p.cart_total_price").text(data.newPrice + " đ");
                        $(".total-price h2").text("Tổng tiền: " + data.newTotalPrice + " đ");
                    },
                    error: function (xhr, status, error) {
                        console.error("Lỗi khi cập nhật số lượng: " + error);
                        alert("Không thể cập nhật số lượng. Vui lòng thử lại.");
                        $input.val($input.attr("data-original-quantity") || 1);
                    }
                });
            });

            $(document).ready(function () {
                $(".cart_quantity_delete").on("click", function (e) {
                    e.preventDefault();
                    var $deleteLink = $(this);
                    var url = $deleteLink.attr("href");
                    var $row = $deleteLink.closest("tr");

                    $.ajax({
                        url: url,
                        type: "POST",
                        dataType: "json",
                        success: function (data) {
                            if (data.success) {
                                $row.remove();

                                $(".total-price h2").text("Tổng tiền: " + data.newTotalPrice + " đ");

                                if ($(".table-responsive tbody tr").length === 0) {
                                    $(".table-responsive tbody").append(
                                            '<tr><td colspan="4" class="no-orders">There is no item!</td></tr>'
                                            );
                                }
                            } else {
                                alert("Không thể xóa sản phẩm. Vui lòng thử lại.");
                            }
                        },
                        error: function (xhr, status, error) {
                            console.error("Lỗi khi xóa sản phẩm: " + error);
                            alert("Có lỗi xảy ra khi xóa sản phẩm.");
                        }
                    });
                });
            });

        </script>

    </body>
</html>