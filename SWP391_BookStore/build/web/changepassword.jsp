<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Change Password</title>
        <!-- Import Bootstrap -->
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

        <script>
            // Tự động chuyển về trang profile nếu đổi mật khẩu thành công
            function redirectToProfile() {
                window.location.href = 'http://localhost:9999/SWP391_BookStore/userprofileservlet';
            }

            window.onload = function () {
                var successMessage = document.getElementById('successMessage');
                if (successMessage) {
                    setTimeout(redirectToProfile, 3000);
                }
            };
        </script>

        <div class="container py-5"></div>


        <!-- Change Password Form -->
        <div class="container mt-5">
            <div class="card shadow-lg w-50 mx-auto">
                <div class="card-header bg-warning text-dark text-center">
                    <h2 class="fw-bold">🔒 Change Password</h2>
                </div>
                <div class="card-body">

                    <%-- Hiển thị thông báo --%>
                    <%
                        String successMessage = (String) session.getAttribute("successMessage");
                        String errorMessage = (String) session.getAttribute("errorMessage");
                        if (successMessage != null) {
                    %>
                    <div id="successMessage" class="alert alert-success text-center"><%= successMessage%></div>
                    <%
                            session.removeAttribute("successMessage");
                        }
                        if (errorMessage != null) {
                    %>
                    <div class="alert alert-danger text-center"><%= errorMessage%></div>
                    <%
                            session.removeAttribute("errorMessage");
                        }
                    %>

                    <form action="ChangePasswordServlet" method="post" onsubmit="return validatePasswords();">
                        <div class="mb-3">
                            <label for="currentpassword" class="form-label">Current Password</label>
                            <input type="password" id="currentpassword" name="currentpassword" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label for="newpassword" class="form-label">New Password</label>
                            <input type="password" id="newpassword" name="newpassword" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label for="confirmpassword" class="form-label">Confirm Password</label>
                            <input type="password" id="confirmpassword" name="confirmpassword" class="form-control" required>
                        </div>

                        <div class="d-flex justify-content-between">
                            <a href="javascript:history.back()" class="btn btn-secondary">⬅️ Back</a>
                            <button type="submit" class="btn btn-warning">💾 Save</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
