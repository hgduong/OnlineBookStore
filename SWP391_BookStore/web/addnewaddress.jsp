<%-- 
    Document   : addnewaddress
    Created on : Mar 30, 2025, 9:14:29 AM
    Author     : TungPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add address</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .navbar-nav {
                flex-direction: row !important;
            }
            .navbar-nav .nav-item {
                margin-right: 10px;
            }
        </style>
    </head>
    <body class="container mt-5">
        <%@include file="header.jsp"%>

        <h1>Add new address</h1>
        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                <c:out value="${error}" />
            </div>
        </c:if>
        <div class="mt-4 border rounded p-3">
            <h4 class="fw-bold">➕ Add New Address</h4>
            <form action="addressservlet" method="POST">
                <div class="mb-3">
                    <label class="form-label">Street</label>
                    <input type="text" class="form-control" id="newStreet" name="street" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Ward</label>
                    <select class="form-control" id="newWard" name="ward" required>
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
                </div>
                <div class="mb-3">
                    <label class="form-label">City</label>
                    <input type="text" class="form-control" id="newCity" name="city" value="Hà Nội" readonly>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>