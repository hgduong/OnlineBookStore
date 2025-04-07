<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Đăng Nhập</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Import Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <!-- Import Animate.css for animations -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>

        <!-- Custom CSS -->
        <style>

            body {
                background: url('images/library.gif') no-repeat center center fixed;
                background-size: cover;
                height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
                animation: moveBackground 20s linear infinite;
            }

            @keyframes moveBackground {
                0% {
                    background-position: 0 0;
                }
                100% {
                    background-position: 100% 100%;
                }
            }

            .login-container {
                background: rgba(255, 255, 255, 0.9);
                padding: 30px;
                border-radius: 12px;
                box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.2);
                animation: fadeIn 1s;
            }

            .form-control {
                border: 2px solid #FFC107;
                transition: all 0.3s ease-in-out;
            }

            .form-control:focus {
                box-shadow: 0 0 8px rgba(255, 193, 7, 0.8);
                border-color: #FFC107;
            }

            .btn-login {
                background: #FFC107;
                border: none;
                transition: 0.3s;
                font-weight: bold;
            }

            .btn-login:hover {
                background: #e0a800;
                transform: scale(1.05);
            }

            .links a {
                color: #FFC107;
                text-decoration: none;
            }

            .links a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>

        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-4">
                    <div class="login-container text-center p-4 rounded shadow-lg animate__animated animate__fadeInDown">
                        <h2 class="mb-3 fw-bold text-warning">📚 Login</h2>

                        <form action="login" method="post" autocomplete="on">
                            <div class="mb-3">
                                <input type="text" name="username" class="form-control" placeholder="Username" autocomplete="current-password" required />
                            </div>
                            <br>
                            <div class="mb-3">
                                <input type="password" name="password" class="form-control" placeholder="Password" autocomplete="current-password" required />
                            </div>
                            <div class="form-check text-start mb-3">
                                <input type="checkbox" class="form-check-input" name="remember" value="true">
                                <label class="form-check-label">Remember Me</label>
                            </div>
                            <button type="submit" class="btn btn-login w-100 py-2">Login</button>
                        </form>


                        <p class="text-danger mt-3">${errorMessage}</p>

                        <div class="links mt-3">
                            <a href="forgotpassword.jsp" class="me-3">Forgot Password?</a>
                            <a href="Register.jsp">Sign up</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
