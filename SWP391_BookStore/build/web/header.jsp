<%-- 
    Document   : header
    Created on : Mar 3, 2025, 9:09:27 AM
    Author     : TungPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
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
        <div class="header-middle">
            <div class="container">
                <div class="row">
                    <div class="col-sm-4">
                        <div class="logo pull-left">
                            <a href="homepage"><img src="images/home/lg.PNG" alt="" width="80" height="80" /></a>
                        </div>

                    </div>
                    <div class="col-sm-8">
                        <div class="shop-menu pull-right">
                            <ul class="nav navbar-nav">
                                <c:if test="${not empty sessionScope.account}">
                                    <c:if test="${not empty sessionScope.account && sessionScope.account.roleId == 2}">
                                        <li><a href="checkout"><i class="fa fa-crosshairs"></i> Checkout</a></li>
                                        <li><a href="cart"><i class="fa fa-shopping-cart"></i> Cart</a></li>
                                        </c:if>
                                    <li class="dropdown">
                                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                            <i class="fa fa-user"></i> ${sessionScope.account.fullname} <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li><a href="userprofileservlet">Profile</a></li>
                                            <li role="separator" class="divider"></li>
                                            <li><a href="logout.jsp">Logout</a></li>
                                        </ul>
                                    </li>
                                </c:if>
                                <c:if test="${empty sessionScope.account}">
                                    <li><a href="login"><i class="fa fa-lock"></i> Login</a></li>
                                    </c:if>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div><!--/header-middle-->

        <div class="header-bottom"><!--header-bottom-->
            <div class="container">
                <div class="row">
                    <div class="col-sm-9">
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                                <span class="sr-only">Toggle navigation</span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </button>
                        </div>
                        <div class="mainmenu pull-left">
                            <ul class="nav navbar-nav collapse navbar-collapse">
                                <c:if test="${sessionScope.account.roleId==null || sessionScope.account.roleId == 2}">
                                    <li><a href="homepage">Home</a></li>
                                    <li class="dropdown"><a href="${pageContext.request.contextPath}/book-list" class="active">Shop<i class="fa fa-angle-down"></i></a>
                                        <ul role="menu" class="sub-menu">
                                            <li><a href="${pageContext.request.contextPath}/book-list" class="active">Books</a></li>
                                                <c:if test="${not empty sessionScope.account}">
                                                <li><a href="${pageContext.request.contextPath}/cart">Cart</a></li> 
                                                </c:if>
                                        </ul>
                                    </li>

                                    <li class="dropdown"><a href="${pageContext.request.contextPath}/BlogServlet">Blog<i class="fa fa-angle-down"></i></a>
                                        <ul role="menu" class="sub-menu">
                                            <li><a href="${pageContext.request.contextPath}/BlogServlet">Blog List</a></li>
                                            <li><a href="blog-detail.jsp">Blog Single</a></li>
                                        </ul>
                                    </li> 
                                </c:if>
                                <c:if test="${not empty sessionScope.account && sessionScope.account.roleId == 2}">
                                    <li><a href="${pageContext.request.contextPath}/my-orders">My orders</a></li>
                                    </c:if>

                                <c:if test="${sessionScope.account != null && sessionScope.account.roleId == 3}">
                                    <li><a href="manager.jsp">Home Manager</a></li>
                                    <li><a href="list-payment">List payment</a></li>
                                    <li><a href="list-order">List order</a></li>
                                    <li class="dropdown"><a href="#" class="active">Book<i class="fa fa-angle-down"></i></a>
                                        <ul role="menu" class="sub-menu">
                                            <li><a href="${pageContext.request.contextPath}/mbooklist">List Book</a></li>
                                            <li><a href="${pageContext.request.contextPath}/add-book">Add Book</a></li>
                                        </ul>
                                    </li>
                                    <li class="dropdown"><a href="${pageContext.request.contextPath}/BlogServlet" class="active">Blog<i class="fa fa-angle-down"></i></a>
                                        <ul role="menu" class="sub-menu">
                                            <li><a href="${pageContext.request.contextPath}/BlogServlet">List Blog</a></li>
                                            <li><a href="m-add-book.jsp">Add Blog</a></li>
                                        </ul>
                                    </li>
                                    <li class="dropdown"><a href="${pageContext.request.contextPath}/AuthorController" class="active">Author<i class="fa fa-angle-down"></i></a>
                                        <ul role="menu" class="sub-menu">
                                            <li><a href="${pageContext.request.contextPath}/AuthorController">List Author</a></li>
                                            <li><a href="${pageContext.request.contextPath}/AuthorAddController">Add Author</a></li>
                                        </ul>
                                    </li>
                                    <li class="dropdown"><a href="${pageContext.request.contextPath}/CategoryController" class="active">Category<i class="fa fa-angle-down"></i></a>
                                        <ul role="menu" class="sub-menu">
                                            <li><a href="${pageContext.request.contextPath}/CategoryController">List Category</a></li>
                                            <li><a href="${pageContext.request.contextPath}/CategoryAddController">Add Category</a></li>
                                        </ul>
                                    </li>
                                    <li class="dropdown"><a href="${pageContext.request.contextPath}/DiscountController" class="active">Discount<i class="fa fa-angle-down"></i></a>
                                        <ul role="menu" class="sub-menu">
                                            <li><a href="${pageContext.request.contextPath}/DiscountController">List Category</a></li>
                                            <li><a href="${pageContext.request.contextPath}/DiscountAddController">Add Category</a></li>
                                        </ul>
                                    </li>
                                </c:if>
                            </ul>
                        </div>
                    </div>
                    <div class="col-sm-3">

                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
