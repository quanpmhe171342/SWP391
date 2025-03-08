<%-- 
    Document   : BlogDetail
    Created on : Feb 10, 2025, 10:31:49 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zxx">

    <head>
        <meta charset="UTF-8">
        <meta name="description" content="Male_Fashion Template">
        <meta name="keywords" content="Male_Fashion, unica, creative, html">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Male-Fashion | Template</title>

        <!-- Google Font -->
        <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@300;400;600;700;800;900&display=swap"
              rel="stylesheet">

        <!-- Css Styles -->
        <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
        <link rel="stylesheet" href="css/elegant-icons.css" type="text/css">
        <link rel="stylesheet" href="css/magnific-popup.css" type="text/css">
        <link rel="stylesheet" href="css/nice-select.css" type="text/css">
        <link rel="stylesheet" href="css/owl.carousel.min.css" type="text/css">
        <link rel="stylesheet" href="css/slicknav.min.css" type="text/css">
        <link rel="stylesheet" href="css/style.css" type="text/css">
        <style>
            .feedback-section {
                background-color: #f8f9fa;
                padding: 15px;
                border-radius: 10px;
            }

            .feedback-item {
                border-left: 4px solid #007bff;
                transition: background-color 0.3s ease;
            }

            .feedback-item:hover {
                background-color: #f1f1f1;
            }

            .avatar img {
                object-fit: cover;
                border: 2px solid #007bff;
            }

            .feedback-content p {
                margin: 0;
                line-height: 1.5;
            }

            .feedback-content small {
                display: block;
                margin-top: 5px;
                color: #6c757d;
            }

        </style>
    </head>

    <body>
        <!-- Page Preloder -->
        <div id="preloder">
            <div class="loader"></div>
        </div>

        <!-- Offcanvas Menu Begin -->
        <div class="offcanvas-menu-overlay"></div>
        <div class="offcanvas-menu-wrapper">
            <div class="offcanvas__option">
                <div class="offcanvas__links">
                    <a href="#">Sign in</a>
                    <a href="#">FAQs</a>
                </div>
                <div class="offcanvas__top__hover">
                    <span>Usd <i class="arrow_carrot-down"></i></span>
                    <ul>
                        <li>USD</li>
                        <li>EUR</li>
                        <li>USD</li>
                    </ul>
                </div>
            </div>
            <div class="offcanvas__nav__option">
                <a href="#" class="search-switch"><img src="img/icon/search.png" alt=""></a>
                <a href="#"><img src="img/icon/heart.png" alt=""></a>
                <a href="#"><img src="img/icon/cart.png" alt=""> <span>0</span></a>
                <div class="price">$0.00</div>
            </div>
            <div id="mobile-menu-wrap"></div>
            <div class="offcanvas__text">
                <p>Free shipping, 30-day return or refund guarantee.</p>
            </div>
        </div>
        <!-- Offcanvas Menu End -->

        <!-- Header Section Begin -->
                       <jsp:include page="header_1.jsp"></jsp:include>

        <!-- Header Section End -->

        <!-- Blog Details Hero Begin -->
        <section class="blog-hero spad">
            <div class="container">
                <div class="row d-flex justify-content-center">
                    <div class="col-lg-9 text-center">
                        <div class="blog__hero__text">
                            <h2>${blog.title}</h2>
                            <ul>
                                <li>By Deercreative</li>
                                <li>${blog.createdAt}</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Blog Details Hero End -->

        <!-- Blog Details Section Begin -->
        <section class="blog-details spad">
            <div class="container">
                <div class="row d-flex justify-content-center">
                    <div class="col-lg-12">
                        <div class="blog__details__pic">
                            <img src="${blog.image}" alt="">
                        </div>
                    </div>
                    <div class="col-lg-8">
                        <div class="blog__details__content">

                            <div class="blog__details__text">
                                <p>${blog.content}</p>
                            </div>

                            <div class="blog__details__option">
                                <div class="row">
                                    <div class="col-lg-6 col-md-6 col-sm-6">
                                        <div class="blog__details__author">
                                            <div class="blog__details__author__pic">
                                                <img src="img/blog/details/blog-author.jpg" alt="">
                                            </div>
                                            <div class="blog__details__author__text">
                                                <h5>Aiden Blair</h5>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-6 col-md-6 col-sm-6">
                                        <div class="blog__details__tags">
                                            <a href="#">#Fashion</a>
                                            <a href="#">#Trending</a>
                                            <a href="#">#2025</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--                            <div class="blog__details__btns">
                                                            <div class="row">
                                                                <div class="col-lg-6 col-md-6 col-sm-6">
                                                                    <a href="" class="blog__details__btns__item">
                                                                        <p><span class="arrow_left"></span> Previous Pod</p>
                                                                        <h5>It S Classified How To Utilize Free Classified Ad Sites</h5>
                                                                    </a>
                                                                </div>
                            
                                                            </div>
                                                        </div>-->

                        </div>
                    </div>
                    <div class="col-lg-4 col-md-4 col-sm-4">
                        <a href="BlogDetail?blogID=${blognew.blogID}" class="blog__details__btns__item blog__details__btns__item--next">
                            <p>New Post <span class="arrow_right"></span></p>
                            <img src="${blognew.image}" alt="">

                            <h5>${blognew.title}</h5>
                        </a>
                    </div>
                    <div class="blog__details__comment">
                        <h4>Leave A Comment</h4>
                        <form action="#">
                            <div class="row">
                                <div class="col-lg-4 col-md-4">
                                    <input type="text" placeholder="Name">
                                </div>
                                <div class="col-lg-4 col-md-4">
                                    <input type="text" placeholder="Email">
                                </div>
                                <div class="col-lg-4 col-md-4">
                                    <input type="text" placeholder="Phone">
                                </div>
                                <div class="col-lg-12 text-center">
                                    <textarea placeholder="Comment"></textarea>
                                    <button type="submit" class="site-btn">Post Comment</button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="feedback-section mt-4">
                        <h4 class="mb-3">Feedback</h4>
                        <c:choose>
                            <c:when test="${not empty fb}">
                                <c:forEach var="fb" items="${fb}">
                                    <div class="feedback-item d-flex align-items-start mb-4 shadow-sm rounded bg-white p-3" style="width: 500px;">
                                        <!-- Avatar -->
                                        <div class="avatar me-3">
                                            <img src="img/blog/blog-2.jpg" 
                                                 alt="Avatar" class="rounded-circle" width="50" height="50">
                                        </div>
                                        <!-- Feedback Content -->
                                        <div class="feedback-content">
                                            <h5 class="username text-primary fw-bold mb-1">@${fb.u.firstName}</h5>
                                            <p class="feedback-text mb-1">${fb.content}</p>
                                            <small class="text-muted">🗓️ Posted on: ${fb.createdAt}</small>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="text-center text-muted py-4">
                                    <p>No feedback available</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>


                </div>
            </div>
        </section>
        <!-- Blog Details Section End -->

        <!-- Footer Section Begin -->
        <footer class="footer">
            <div class="container">
                <div class="row">
                    <div class="col-lg-3 col-md-6 col-sm-6">
                        <div class="footer__about">
                            <div class="footer__logo">
                                <a href="#"><img src="img/footer-logo.png" alt=""></a>
                            </div>
                            <p>The customer is at the heart of our unique business model, which includes design.</p>
                            <a href="#"><img src="img/payment.png" alt=""></a>
                        </div>
                    </div>
                    <div class="col-lg-2 offset-lg-1 col-md-3 col-sm-6">
                        <div class="footer__widget">
                            <h6>Shopping</h6>
                            <ul>
                                <li><a href="#">Clothing Store</a></li>
                                <li><a href="#">Trending Shoes</a></li>
                                <li><a href="#">Accessories</a></li>
                                <li><a href="#">Sale</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-3 col-sm-6">
                        <div class="footer__widget">
                            <h6>Shopping</h6>
                            <ul>
                                <li><a href="#">Contact Us</a></li>
                                <li><a href="#">Payment Methods</a></li>
                                <li><a href="#">Delivary</a></li>
                                <li><a href="#">Return & Exchanges</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-lg-3 offset-lg-1 col-md-6 col-sm-6">
                        <div class="footer__widget">
                            <h6>NewLetter</h6>
                            <div class="footer__newslatter">
                                <p>Be the first to know about new arrivals, look books, sales & promos!</p>
                                <form action="#">
                                    <input type="text" placeholder="Your email">
                                    <button type="submit"><span class="icon_mail_alt"></span></button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-lg-12 text-center">
                        <div class="footer__copyright__text">
                            <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                            <p>Copyright ©
                                <script>
                                    document.write(new Date().getFullYear());
                                </script>2020
                                All rights reserved | This template is made with <i class="fa fa-heart-o"
                                                                                    aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
                            </p>
                            <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                        </div>
                    </div>
                </div>
            </div>
        </footer>
        <!-- Footer Section End -->

        <!-- Search Begin -->
        <div class="search-model">
            <div class="h-100 d-flex align-items-center justify-content-center">
                <div class="search-close-switch">+</div>
                <form class="search-model-form">
                    <input type="text" id="search-input" placeholder="Search here.....">
                </form>
            </div>
        </div>
        <!-- Search End -->

        <!-- Js Plugins -->
        <script src="js/jquery-3.3.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.nice-select.min.js"></script>
        <script src="js/jquery.nicescroll.min.js"></script>
        <script src="js/jquery.magnific-popup.min.js"></script>
        <script src="js/jquery.countdown.min.js"></script>
        <script src="js/jquery.slicknav.js"></script>
        <script src="js/mixitup.min.js"></script>
        <script src="js/owl.carousel.min.js"></script>
        <script src="js/main.js"></script>
    </body>

</html>
