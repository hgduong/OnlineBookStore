<%-- 
    Document   : booklist
    Created on : Feb 5, 2025, 4:29:27 PM
    Author     : TungPham
--%>

<%@page import="java.util.List"%>
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
        <title>Home Page</title>
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
    </head><!--/head-->

    <body>


        <%@include file="header.jsp"%>   
        <%@include file="slider.jsp"%>  
<!--        <section id="advertisement">
            <div class="container">
                <img src="images/shop/advertisement1.jpg" alt="" />
            </div>
        </section>-->
        <div class="row">
            <div class="col-xs-12">
                <div class="search_box pull-right" style="margin-bottom:20px; margin-right:200px;"> 
                    <form action="book-list" method="get">
                        <input type="text" placeholder="Search by Name" name="searchName" value="${searchName}"/>
                        <button type="submit">Search</button>
                    </form>
                </div>
            </div>
        </div>
        <div class="clearfix"></div>

        <div>
            <section>
                <div class="container">
                    <div class="row">
                        <div class="col-sm-3">
                            <div class="left-sidebar">
                                <h2>Category</h2>
                                <div class="panel-group category-products" id="accordian"><!--category-productsr-->
                                    <c:forEach items="${cates}" var="category"> 
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h4 class="panel-title">
                                                    <a href="${pageContext.request.contextPath}/book-list?categoryId=${category.categoryId}">${category.categoryName}</a>
                                                </h4>
                                            </div>
                                        </div>
                                    </c:forEach>

                                </div><!--/category-productsr-->

                                <h2>Author</h2>
                                <div class="panel-group category-products" id="accordian"><!--category-productsr-->
                                    <c:forEach items="${authors}" var="author"> 
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h4 class="panel-title">
                                                    <a href="${pageContext.request.contextPath}/book-list?authorId=${author.authorId}">${author.name}</a>
                                                </h4>
                                            </div>
                                        </div>
                                    </c:forEach>

                                </div>

                                <div class="price-range">
                                    <h2>Price Range</h2>
                                    <div class="well">
                                        <input type="text" class="span2" value="" data-slider-min="0" data-slider-max="600000" data-slider-step="5000" data-slider-value="[${minPrice},${maxPrice}]" id="sl2" ><br />
                                        <b>0K VND</b> <b class="pull-right">600K VND</b>
                                    </div>
                                </div>

                                <div class="shipping text-center"><!--shipping-->
                                    <img src="images/home/shipping1.jpg" alt="" />
                                </div><!--/shipping-->

                            </div>
                        </div>

                        <div class="col-sm-9 padding-right">
                            <div class="features_items"><!--features_items-->
                                <h2 class="title text-center">New Books</h2>
                                <div class ="items-wrapper">
                                    <c:forEach items="${books}" var="book">
                                        <div class="col-sm-4">
                                            <div class="product-image-wrapper">
                                                <div class="single-products">
                                                    <a href="${pageContext.request.contextPath}/book-detail?bookId=${book.bookId}"> <%-- Thêm thẻ <a> bao ngoài và tạo URL liên kết --%>

                                                        <div class="productinfo text-center">
                                                            <img src="images/shop/product12.jpg" alt="${book.title}" />
                                                            <h2>${book.title}</h2>
                                                            <p>${book.author.name}</p>
                                                            <p>${book.category.categoryName}</p>
                                                            <p>${book.price}</p>

                                                        </div>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/add-to-cart?bookId=${book.bookId}&quantity=1" 
                                                       class="btn btn-default add-to-cart" 
                                                       data-book-id="${book.bookId}">
                                                        <i class="fa fa-shopping-cart"></i>Add to cart
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                                
                                <h2 class="title text-center">All Blogs</h2>
                                <div class ="items-wrapper">
                                    <c:forEach items="${books}" var="book">
                                        <div class="col-sm-4">
                                            <div class="product-image-wrapper">
                                                <div class="single-products">
                                                    <a href="${pageContext.request.contextPath}/book-detail?bookId=${book.bookId}"> <%-- Thêm thẻ <a> bao ngoài và tạo URL liên kết --%>

                                                        <div class="productinfo text-center">
                                                            <img src="images/shop/product12.jpg" alt="${book.title}" />
                                                            <h2>${book.title}</h2>
                                                            <p>${book.author.name}</p>
                                                            <p>${book.category.categoryName}</p>
                                                            <p>${book.price}</p>

                                                        </div>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/add-to-cart?bookId=${book.bookId}&quantity=1" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div><!--features_items-->
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <%@include file="footer.jsp"%> 



        <script src="js/jquery.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>

        <script>
            $(document).ready(function () {
                var contextPath = "<%= request.getContextPath()%>";
                $("#sl2").slider();

                $("#sl2").on("slideStop", function (slideEvt) {
                    var minPrice = slideEvt.value[0];
                    var maxPrice = slideEvt.value[1];

                    filterBooks(minPrice, maxPrice);

                });

                function filterBooks(min, max) {
                    var url = contextPath + "/book-list?minPrice=" + min + "&maxPrice=" + max;
                    window.location.href = url;
                }
            });
            
            
        </script>
        
        <script>
            $('.add-to-cart').click(function (e) {
                e.preventDefault();
                var url = $(this).attr('href');
                $.ajax({
                    url: url,
                    type: 'GET',
                    success: function (response) {
                        alert(response);
                    },
                    error: function (xhr) {
                        alert(xhr.responseText);
                        if (xhr.status === 401) {
                            window.location.href = 'login';
                        }
                    }
                });
            });
        </script>
    </body>
</html>