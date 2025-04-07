<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Account" %>
<%@page import="model.Address" %>
<%@page import="jakarta.servlet.http.HttpSession" %>

<%
    HttpSession sessionObj = request.getSession();
    Account account = (Account) request.getAttribute("account");
%>


<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Profile</title>
        
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
            .navbar-nav {
                flex-direction: row !important;
            }
            .navbar-nav .nav-item {
                margin-right: 10px;
            }
        </style>
    </head>
    <body class="bg-light">
        <%@include file="header.jsp"%>
        
        <!-- Profile Container -->
        <div class="container mt-5">
            <% if (account != null) { %>
            <div class="card shadow-lg">
                <div class="card-header bg-warning text-dark text-center">
                    <h2 class="fw-bold">👤 User Profile</h2>
                </div>
                <div class="card-body">
                    <p class="fs-5"><strong>Account ID:</strong> <%= account.getAccountId() %></p>
                    <p class="fs-5"><strong>Username:</strong> <%= account.getUsername() %></p>
                    <p class="fs-5"><strong>Full Name:</strong> <%= account.getFullname() %></p>
                    <p class="fs-5"><strong>Email:</strong> <%= account.getEmail() %></p>
                    <p class="fs-5"><strong>Phone:</strong> <%= account.getPhone() %></p>
                    <p class="fs-5"><strong>Role:</strong> <%= account.getRoleId() == 1 ? "Admin" : "User" %></p>
                    <p class="fs-5"><strong>Date of Birth:</strong> <%= account.getDob() %></p>
                    <p class="fs-5"><strong>Gender:</strong> <%= account.getGender() %></p>
                    <p class="fs-5"><strong>Created At:</strong> <%= account.getCreatedAt() %></p>
                    <p class="fs-5"><strong>Updated At:</strong> <%= account.getUpdatedAt() %></p>
                    <p class="fs-5"><strong>Status:</strong> <%= account.getStatus() %></p>

                    <% if (account.getAddresses() != null && !account.getAddresses().isEmpty()) { %>
                    <h4 class="fw-bold mt-4">🏠 Addresses</h4>
                    <% for (Address addr : account.getAddresses()) { %>
                    <div class="border rounded p-3 mb-3">
                        <p class="fs-5"><strong>Address: </strong> <%= addr.getStreet() %>, <%= addr.getWard() %>, <%= addr.getCity() %></p>
                        
                    </div>
                    <% } %>
                    <% } else { %>
                    <p class="fs-5 text-danger">⚠️ No addresses found!</p>
                    <% } %>

                    <!-- Buttons -->
                    <div class="d-flex justify-content-center gap-3 mt-4">
                        <a href="editprofile.jsp" class="btn btn-warning">Edit Profile</a>
                        <a href="addnewaddress.jsp" class="btn btn-warning">Add new Address</a>
                        <a href="changepassword.jsp" class="btn btn-warning">Change Password</a>
                        <a href="javascript:history.back()" class="btn btn-secondary">Back</a>
                    </div>
                </div>
            </div>
            <% } else { %>
            <div class="alert alert-danger text-center fs-5">⚠️ Account not found!</div>
            <% } %>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
