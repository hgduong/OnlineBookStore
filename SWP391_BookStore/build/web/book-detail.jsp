<%-- 
    Document   : book-detail
    Created on : Feb 6, 2025, 10:27:26 AM
    Author     : TungPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Product Details | E-Shopper</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/font-awesome.min.css" rel="stylesheet">
        <link href="css/prettyPhoto.css" rel="stylesheet">
        <link href="css/price-range.css" rel="stylesheet">
        <link href="css/animate.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
        <link href="css/responsive.css" rel="stylesheet">
        <link href="css/comment.css" rel="stylesheet">
        <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
        <![endif]-->       
        <script>
            var bookStock = ${book.stock};
        </script>
        <link rel="shortcut icon" href="images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">
    </head><!--/head-->

    <body> 

        <%@include file="header.jsp"%>

        <div class="container">
            <div class="row">
                <div class="padding-right">
                    <div class="product-details"><!--product-details-->
                        <div class="col-sm-5">
                            <div class="view-product">
                                <img src="images/shop/product12.jpg" alt="" />

                            </div>
                            <div id="similar-product" class="carousel slide" data-ride="carousel">


                            </div>

                        </div>
                        <div class="col-sm-7">
                            <div class="product-information"><!--/product-information-->
                                <img src="images/product-details/new.jpg" class="newarrival" alt="New Arrival" /> 
                                <h2>${book.title}</h2> 
                                <br><!-- comment -->
                                <span>${rating}</span>
                                <img src="images/product-details/rating1.png" alt="Rating" />
                                <span>(   ${numOfVote} votes)</span>
                                <br>
                                <span>
                                    <span>${book.price} VND</span> 
                                    <br>
                                    <label>Quantity:</label>
                                    <input type="number" min="1" value="1" id="quantityInput"/>
                                    <a href="${pageContext.request.contextPath}/add-to-cart?bookId=${book.bookId}&quantity=1" 
                                       class="btn btn-default add-to-cart" 
                                       data-book-id="${book.bookId}">
                                        <i class="fa fa-shopping-cart"></i>Add to cart
                                    </a>
                                </span>
                                <div id="quantityError" style="color: red; display: none;"></div>
                                <p><b>Availability:</b>
                                    <c:choose>
                                        <c:when test="${book.stock > 0}">
                                            In Stock (${book.stock})
                                        </c:when>
                                        <c:otherwise>
                                            Out of Stock
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                                <p><b>Publisher:</b> ${book.publisher}</p>
                                <p><b>Author:</b> ${book.author.name}</p>
                                <p><b>Category:</b> ${book.category.categoryName}</p>
                                <p><b>Published Date:</b>
                                    ${book.publishedDate}
                                </p>
                                <p><b>Description:</b> ${book.description}</p> <%-- Hiển thị Description từ đối tượng book --%>
                                <a href=""><img src="images/product-details/share.png" class="share img-responsive"  alt="" /></a> <%-- Giữ nguyên icon share --%>
                            </div><!--/product-information-->
                        </div>
                    </div><!--/product-details-->
                </div>
            </div>
        </div>
        <c:if test="${not empty sessionScope.account}">
            <div class="add-review-section" style="background-color: #fff; padding: 20px; margin-bottom: 30px; border: 1px solid #ddd; border-radius: 5px;">
                <h3>Add Your Review</h3>
                <form action="${pageContext.request.contextPath}/comment?bookId=${book.bookId}" method="post">
                    <input type="hidden" name="bookId" value="${book.bookId}" />
                    <div class="form-group" style="margin-bottom: 15px;">
                        <label for="rating" style="display: block; font-weight: bold;">Rating:</label>
                        <select name="rating" id="rating" style="padding: 8px; width: 100%; max-width: 200px;">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>     
                        </select>
                    </div>
                    <div class="form-group" style="margin-bottom: 15px;">
                        <label for="comment" style="display: block; font-weight: bold;">Comment:</label>
                        <textarea name="comment" id="comment" rows="4" style="width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px;"></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary" style="padding: 10px 20px; background-color: #007BFF; border: none; color: #fff; border-radius: 4px; cursor: pointer;">
                        Submit Review
                    </button>
                </form>
            </div>
        </c:if>
        <div class="reviews-section">
            <h3>Customer Reviews</h3>
            <c:choose>
                <c:when test="${not empty reviews}">
                    <c:forEach var="review" items="${reviews}">
                        <div class="review-item" style="padding: 10px; border-bottom: 1px solid #ccc;">
                            <p class="review-header" style="margin: 0; font-size: 16px;">
                                <strong>${review.fullName}</strong> - <span style="color: #888;">${review.reviewDate}</span>
                            </p>
                            <p class="review-rating" style="margin: 5px 0; color: #f39c12;">Rating: ${review.rating}/5</p>
                            <p class="review-comment" style="margin: 5px 0;">${review.comment}</p>
                            <c:if test="${not empty sessionScope.account}">
                                <c:choose>
                                    <c:when test="${sessionScope.account.role == 1}">
                                        <!-- role=1 , hiển thị nút delete cho tất cả -->
                                        <form action="${pageContext.request.contextPath}/deleteReview" method="post" style="display: inline;">
                                            <input type="hidden" name="reviewId" value="${review.reviewId}" />
                                            <input type="hidden" name="bookId" value="${book.bookId}" />
                                            <button type="submit" class="btn-delete" 
                                                    style="color: red; text-decoration: none; font-size: 14px; background: none; border: none;">
                                                Delete
                                            </button>
                                        </form>
                                    </c:when>
                                    <c:when test="${sessionScope.account.role == 2 && sessionScope.account.accountId == review.accountId}">
                                        <!-- role=2, chỉ cho phép xóa nếu trùng id-->
                                        <form action="${pageContext.request.contextPath}/deleteReview" method="post" style="display: inline;">
                                            <input type="hidden" name="reviewId" value="${review.reviewId}" />
                                            <input type="hidden" name="bookId" value="${book.bookId}" />
                                            <button type="submit" class="btn-delete" style="color: red; text-decoration: none; font-size: 14px; background: none; border: none;">Delete</button>
                                        </form>
                                    </c:when>
                                </c:choose>
                            </c:if>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p>No reviews yet. Be the first to review this book!</p>
                </c:otherwise>
            </c:choose>
        </div>

        <%@include file="footer.jsp"%> 
        <script src="js/jquery.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
        <script>
            $('.add-to-cart').click(function (e) {
                e.preventDefault();
                var bookId = $(this).data('book-id');
                var quantity = parseInt($('#quantityInput').val(), 10);

                if (isNaN(quantity) || quantity < 1) {
                    $('#quantityError').text('Số lượng nhập vào phải là số dương lớn hơn 0').show();
                    return;
                }

                if (quantity > bookStock) {
                    $('#quantityError').text('Số lượng vượt quá số sách trong kho').show();
                    return;
                }

                var baseUrl = '${pageContext.request.contextPath}/add-to-cart';
                var url = baseUrl + '?bookId=' + bookId + '&quantity=' + quantity;

                $.ajax({
                    url: url,
                    type: 'GET',
                    success: function (response) {
                        $('#quantityError').hide();
                        alert(response);
                    },
                    error: function (xhr) {
                        $('#quantityError').text(xhr.responseText).show();
                        if (xhr.status === 401) {
                            window.location.href = 'login.jsp';
                        }
                    }
                });
            });
        </script>
    </body>
</html>
