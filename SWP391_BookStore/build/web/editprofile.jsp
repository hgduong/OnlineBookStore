<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Account" %>
<%@page import="model.Address" %>
<%
    Account account = (Account) request.getAttribute("account");
    if (account == null) {
        response.sendRedirect("userprofileservlet?action=edit");
        return;
    }

    Address mainAddress = (account.getAddresses() != null && !account.getAddresses().isEmpty())
            ? account.getAddresses().get(0)
            : new Address();
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Profile</title>
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

        <div class="container mt-5">
            <div class="card shadow-lg">
                <div class="card-header bg-warning text-dark text-center">
                    <h2 class="fw-bold">Edit Profile</h2>
                </div>
                <div class="card-body">
                    <form action="userprofileservlet" method="post">
                        <input type="hidden" name="accountId" value="<%= account.getAccountId()%>">

                        <div class="mb-3">
                            <label class="form-label">Full Name</label>
                            <input type="text" class="form-control" name="fullname" value="<%= (account.getFullname() != null) ? account.getFullname() : ""%>" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" class="form-control" name="email" value="<%= (account.getEmail() != null) ? account.getEmail() : ""%>" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Phone</label>
                            <input type="text" class="form-control" name="phone" value="<%= (account.getPhone() != null) ? account.getPhone() : ""%>" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Date of Birth</label>
                            <input type="date" class="form-control" name="dob" value="<%= (account.getDob() != null) ? account.getDob() : ""%>" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Gender</label>
                            <select class="form-control" name="gender">
                                <option value="Male" <%= "Male".equals(account.getGender()) ? "selected" : ""%>>Male</option>
                                <option value="Female" <%= "Female".equals(account.getGender()) ? "selected" : ""%>>Female</option>
                            </select>
                        </div>
                        <% if (account.getAddresses() != null && !account.getAddresses().isEmpty()) { %>
                        <h4 class="fw-bold mt-4">🏠 Addresses</h4>
                        <% for (Address addr : account.getAddresses()) {%>
                        <div class="border rounded p-3 mb-3">
                            <p class="fs-5"><strong>Address: </strong> <%= addr.getStreet()%>, <%= addr.getWard()%>, <%= addr.getCity()%></p>

                        </div>
                        <% } %>
                        <% } else { %>
                        <p class="fs-5 text-danger">⚠️ No addresses found!</p>
                        <% }%>
                        
                         
                        
                        <div class="d-flex justify-content-center gap-3 mt-4">
                            <button type="submit" class="btn btn-warning">Save Changes</button>
                            <a href="userprofileservlet" class="btn btn-secondary">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        
    </body>
</html>
