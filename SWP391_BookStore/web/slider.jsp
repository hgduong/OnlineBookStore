<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Slider</title>
        <link href="css/slider.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    </head>
    <body>
        <div class="containers">
            <div class="main">
                <img src="images/slider/img1.jpg" class="img-feature" data-link="book-list">
                <div class="control prev">
                    <i class="fa fa-chevron-left"></i>
                </div>
                <div class="control next">
                    <i class="fa fa-chevron-right"></i>
                </div>
            </div>
            <div class="list-image">
                <div><img class="imgs" src="images/slider/img1.jpg" data-link="book-list"></div>
                <div><img class="imgs" src="images/slider/img2.jpg" data-link="blog-list"></div>
                <div><img class="imgs" src="images/slider/img3.jpg" data-link="profile"></div>
                <div><img class="imgs" src="images/slider/img4.jpg" data-link=""></div>
            </div>
        </div>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/slider.js"></script>
        
    </body>
</html>