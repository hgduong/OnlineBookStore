<%-- 
    Document   : mbooklist
    Created on : Mar 30, 2025, 10:49:19 AM
    Author     : TungPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Book List</title>
        <style>
            body {
                font-family: sans-serif;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 8px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
            tr:nth-child(even) {
                background-color: #f9f9f9;
            }
            tr:hover {
                background-color: #e2e2e2;
            }
        </style>
    </head>
    <body>
        <%@include file="header.jsp"%> 
        <h1>Available Books</h1>

        <%-- Optional: Hiển thị thông báo lỗi nếu có từ Servlet --%>
        <c:if test="${not empty errorMessage}">
            <p style="color: red;">Error: ${errorMessage}</p>
        </c:if>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Category</th>
                    <th>Stock</th>
                    <th>Status</th>

                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty bookList}">
                        <%-- Lặp qua danh sách sách --%>
                        <c:forEach var="book" items="${bookList}">
                            <tr>
                                <td>${book.bookId}</td>
                                <td><c:out value="${book.title}"/></td> 
                                <td><c:out value="${book.author.name}"/></td> 
                                <td><c:out value="${book.category.categoryName}"/></td>
                                <td>${book.stock}</td>
                                <td><c:out value="${book.status}"/></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="6" style="text-align: center;">No books found in the library.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>

        <p><a href="add-book">Add New Book</a></p>

    </body>
</html>