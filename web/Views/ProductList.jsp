<%-- 
    Document   : ProductList
    Created on : Feb 9, 2025, 7:14:06 PM
    Author     : phuan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

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

            <!-- Breadcrumb Section Begin -->
            <section class="breadcrumb-option">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="breadcrumb__text">
                                <h4>Shop</h4>
                                <div class="breadcrumb__links">
                                    <a href="./index.html">Home</a>
                                    <span>Shop</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- Breadcrumb Section End -->

            <!-- Shop Section Begin -->
            <section class="shop spad">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-3">
                            <div class="shop__sidebar">
                                <div class="shop__sidebar__search">
                                    <!--                                <form method="GET" action="ProductList">
                                                                        <input type="text" name="s" placeholder="Tìm Kiếm ...">
                                                                        <button type="submit"><span class="icon_search"></span></button>
                                                                    </form>-->
                                </div>
                                <div class="shop__sidebar__accordion">
                                    <div class="accordion" id="accordionExample">
                                        <div class="card">
                                            <div class="card-heading">
                                                <a data-toggle="collapse" data-target="#collapseOne">Categories</a>
                                            </div>
                                            <div id="collapseOne" class="collapse show" data-parent="#accordionExample">
                                                <div class="card-body">
                                                    <div class="shop__sidebar__categories">
                                                        <ul class="nice-scroll">
                                                        <c:forEach items="${cateproduct}" var="cp">
                                                            <li><a href="ProductList?cate=${cp.category_productID}">${cp.category_name}</a></li>
                                                            </c:forEach>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!--                                <div class="card">
                                                                        <div class="card-heading">
                                                                            <a data-toggle="collapse" data-target="#collapseTwo">Branding</a>
                                                                        </div>
                                                                        <div id="collapseTwo" class="collapse show" data-parent="#accordionExample">
                                                                            <div class="card-body">
                                                                                <div class="shop__sidebar__brand">
                                                                                    <ul>
                                                                                        <li><a href="#">Louis Vuitton</a></li>
                                                                                        <li><a href="#">Chanel</a></li>
                                                                                        <li><a href="#">Hermes</a></li>
                                                                                        <li><a href="#">Gucci</a></li>
                                                                                    </ul>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>-->
                                    <div class="card">
                                        <div class="card-heading">
                                            <a data-toggle="collapse" data-target="#collapseThree">Filter Price</a>
                                        </div>
                                        <div id="collapseThree" class="collapse show" data-parent="#accordionExample">
                                            <div class="card-body">
                                                <div class="shop__sidebar__price">
                                                    <ul>
                                                        <c:set var="cate" value="${param.cate}" />

                                                        <li><a href="ProductList?priceRange=0-1200000${not empty cate ? '&cate=' : ''}${cate}&priceFilter=${priceFilter}">0đ - 1.200.000đ</a></li>
                                                        <li><a href="ProductList?priceRange=1200000-2400000${not empty cate ? '&cate=' : ''}${cate}&priceFilter=${priceFilter}">1.200.000đ - 2.400.000đ</a></li>
                                                        <li><a href="ProductList?priceRange=2400000-3600000${not empty cate ? '&cate=' : ''}${cate}&priceFilter=${priceFilter}">2.400.000đ - 3.600.000đ</a></li>
                                                        <li><a href="ProductList?priceRange=3600000-4800000${not empty cate ? '&cate=' : ''}${cate}&priceFilter=${priceFilter}">3.600.000đ - 4.800.000đ</a></li>
                                                        <li><a href="ProductList?priceRange=4800000-6000000${not empty cate ? '&cate=' : ''}${cate}&priceFilter=${priceFilter}">4.800.000đ - 6.000.000đ</a></li>
                                                        <li><a href="ProductList?priceRange=Isc6000000${not empty cate ? '&cate=' : ''}${cate}&priceFilter=${priceFilter}">6.000.000đ+</a></li>

                                                    </ul>

                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!--                                    <div class="card">
                                                                            <div class="card-heading">
                                                                                <a data-toggle="collapse" data-target="#collapseFive">Colors</a>
                                                                            </div>
                                                                            <div id="collapseFive" class="collapse show" data-parent="#accordionExample">
                                                                                <div class="card-body">
                                                                                    <div class="shop__sidebar__color">
                                                                                        <label class="c-1" for="sp-1">
                                                                                            <input type="radio" id="sp-1">
                                                                                        </label>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>-->
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-9">
                        <div class="shop__product__option">
                            <div class="row">
                                <div class="col-lg-6 col-md-6 col-sm-6">
                                    <div class="shop__product__option__left">
                                        <p>Showing 1–6 of ${size} results</p>
                                    </div>
                                </div>
                                <div class="col-lg-6 col-md-6 col-sm-6">
                                    <div class="shop__product__option__right">
                                        <form method="GET" action="ProductList">
                                            <c:if test="${not empty cate}">
                                                <input type="hidden" name="cate" value="${cate}">
                                            </c:if>
                                            <select name="priceFilter"  onchange="this.form.submit()">
                                                <option value="" ${empty filteractive ? 'selected' : ''}>Thứ tự mặc định</option>
                                                <option value="ASC" ${filteractive == 'ASC' ? 'selected' : ''}>Thứ tự theo giá: thấp đến cao</option>
                                                <option value="DESC" ${filteractive == 'DESC' ? 'selected' : ''}>Thứ tự theo giá: cao đến thấp</option>
                                            </select>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="productList">
                            <c:forEach items="${product}" var="p">
                                <div class="col-lg-4 col-md-6 col-sm-6 product-item" data-price="${p.product.original_Price}">
                                    <div class="product__item">
                                        <div class="product__item__pic set-bg" data-setbg="${p.image}">
                                            <ul class="product__hover">
                                                <li><a href="ProductDetails?pid=${p.product.product_ID}"><img src="img/icon/search.png" alt=""></a></li>
                                            </ul>
                                        </div>
                                        <div class="product__item__text">
                                            <h6>${p.product.product_name}</h6>                                           
                                            <a href="product/cart?pid=${p.product.product_ID}" class="add-cart">+ Add To Cart</a>
                                            <div class="rating">
                                                <i class="fa fa-star-o"></i>
                                                <i class="fa fa-star-o"></i>
                                                <i class="fa fa-star-o"></i>
                                                <i class="fa fa-star-o"></i>
                                                <i class="fa fa-star-o"></i>
                                            </div>
                                            <h5>${p.product.original_Price} VND</h5>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="product__pagination">
                                    <c:choose>
                                        <c:when test="${totalPages < 7}">
                                            <c:forEach begin="1" end="${totalPages}" var="i">
                                                <a href="ProductList?page=${i}&cate=${cate}&priceRange=${priceRange}&priceFilter=${priceFilter}" class="${currentPage == i ? 'active' : ''}">${i}</a>
                                            </c:forEach>
                                        </c:when>

                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${currentPage <= 4}">
                                                    <c:forEach begin="1" end="5" var="i">
                                                        <a href="ProductList?page=${i}&cate=${cate}&priceRange=${priceRange}&priceFilter=${priceFilter}" class="${currentPage == i ? 'active' : ''}">${i}</a>
                                                    </c:forEach>
                                                    <span>...</span>
                                                    <a href="ProductList?page=${totalPages}&cate=${cate}&priceRange=${priceRange}&priceFilter=${priceFilter}">${totalPages}</a>
                                                </c:when>

                                                <c:when test="${currentPage > 4 && currentPage < totalPages - 3}">
                                                    <a href="ProductList?page=1&cate=${cate}&priceRange=${priceRange}&priceFilter=${priceFilter}">1</a>
                                                    <span>...</span>
                                                    <c:forEach begin="${currentPage - 2}" end="${currentPage + 2}" var="i">
                                                        <a href="ProductList?page=${i}&cate=${cate}&priceRange=${priceRange}&priceFilter=${priceFilter}" class="${currentPage == i ? 'active' : ''}">${i}</a>
                                                    </c:forEach>
                                                    <span>...</span>
                                                    <a href="ProductList?page=${totalPages}&cate=${cate}&priceRange=${priceRange}&priceFilter=${priceFilter}">${totalPages}</a>
                                                </c:when>

                                                <c:otherwise>
                                                    <a href="ProductList?page=1&cate=${cate}&priceRange=${priceRange}&priceFilter=${priceFilter}">1</a>
                                                    <span>...</span>
                                                    <c:forEach begin="${totalPages - 4}" end="${totalPages}" var="i">
                                                        <a href="ProductList?page=${i}&cate=${cate}&priceRange=${priceRange}&priceFilter=${priceFilter}" class="${currentPage == i ? 'active' : ''}">${i}</a>
                                                    </c:forEach>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Shop Section End -->

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
