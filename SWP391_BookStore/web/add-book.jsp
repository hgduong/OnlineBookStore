<%-- 
    Document   : add-book
    Created on : Mar 19, 2025, 6:30:15 PM
    Author     : TungPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add New Book</title>
    <style>
        body {
            font-family: sans-serif;
        }

        .container {
            width: 80%;
            margin: 20px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        input[type="text"],
        input[type="number"],
        input[type="date"],
        textarea,
        select {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        
        textarea{
            resize: vertical;
            min-height: 100px;
        }

        .error-message {
            color: red;
            font-size: 0.9em;
            margin-top: -10px;
            margin-bottom: 10px;
        }
        input[type="submit"]{
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }

        .form-group {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<div class="container">
    <%@include file="header.jsp"%>
    <h1>Add New Book</h1>

    <form action="add-book" method="post">
        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" >
        </div>

        <div class="form-group">
            <label for="authorId">Author:</label>
            <select id="authorId" name="authorId">
                <option value="">-- Select Author --</option>
                <c:forEach var="author" items="${authors}">
                    <option value="${author.authorId}">${author.name}</option>
                </c:forEach>
            </select>
            <c:if test="${not empty errors.authorId}">
              <p class="error-message">${errors.authorId}</p>
            </c:if>
        </div>

        <div class="form-group">
            <label for="stock">Stock:</label>
            <input type="number" id="quantity" name="quantity" min="1">
            <c:if test="${not empty errors.stock}">
                <p class="error-message">${errors.stock}</p>
            </c:if>
        </div>

        <div class="form-group">
            <label for="price">Price:</label>
            <input type="text" id="price" name="price" >
            <c:if test="${not empty errors.price}">
              <p class="error-message">${errors.price}</p>
            </c:if>
        </div>

        <div class="form-group">
            <label for="description">Description:</label>
            <textarea id="description" name="description"></textarea>
             <c:if test="${not empty errors.description}">
              <p class="error-message">${errors.description}</p>
            </c:if>
        </div>

        <div class="form-group">
            <label for="releaseDate">Release Date:</label>
            <input type="date" id="releaseDate" name="releaseDate">
            <c:if test="${not empty errors.releaseDate}">
                <p class="error-message">${errors.releaseDate}</p>
            </c:if>
        </div>

        <div class="form-group">
            <label for="nxb">NXB:</label>
            <input type="text" id="nxb" name="nxb" value="${param.nxb}">
            <c:if test="${not empty errors.nxb}">
                <p class="error-message">${errors.nxb}</p>
            </c:if>
        </div>

        <div class="form-group">
            <label for="categoryId">Category:</label>
            <select id="categoryId" name="categoryId">
                <option value="">-- Select Category --</option>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.categoryId}">${category.categoryName}</option>
                </c:forEach>
            </select>
             <c:if test="${not empty errors.categoryId}">
                <p class="error-message">${errors.categoryId}</p>
            </c:if>
        </div>

        <input type="submit" value="Add Book">
    </form>
</div>

</body>
</html>
