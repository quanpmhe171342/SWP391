<%-- 
    Document   : ProductDetail.jsp
    Created on : Feb 10, 2025, 9:15:38 AM
    Author     : phuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
            .product__details__option__siz {
                display: inline-block;
                margin-right: 50px;
            }

            .product__details__option__siz span {
                color: #111111;
                display: inline-block;
                margin-right: 10px;
            }

            .product__details__option__siz label {
                color: #111111;
                font-size: 15px;
                font-weight: 700;
                text-transform: uppercase;
                display: inline-block;
                border: 1px solid #e5e5e5;
                padding: 6px 15px;
                margin-bottom: 0;
                margin-right: 5px;
                cursor: pointer;
            }

            .product__details__option__siz label.active {
                background: #111111;
                color: #ffffff;
                border-color: #111111;
            }

            .product__details__option__siz label input {
                position: absolute;
                visibility: hidden;/* Thêm khoảng cách giữa input và text */
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

        <!-- Shop Details Section Begin -->
        <section class="shop-details">
            <div class="product__details__pic">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="product__details__breadcrumb">
                                <a href="./index.html">Home</a>
                                <a href="./shop.html">Shop</a>
                                <span>Product Details</span>
                            </div>
                        </div>
                    </div>
                    <c:set value="${Product}" var="p"></c:set>

                        <div class="row">
                            <div class="col-lg-3 col-md-3">
                                <ul class="nav nav-tabs" role="tablist">
                                <c:forEach items="${image}" var="i" varStatus="status">
                                    <li class="nav-item">
                                        <a class="nav-link ${status.first ? 'active' : ''}" data-toggle="tab" href="#tabs-${status.index}" role="tab">
                                            <div class="product__thumb__pic set-bg" data-setbg="${i}"></div>
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>

                        <div class="col-lg-6 col-md-9">
                            <div class="tab-content">
                                <c:forEach items="${image}" var="i" varStatus="status">
                                    <div class="tab-pane ${status.first ? 'active' : ''}" id="tabs-${status.index}" role="tabpanel">
                                        <div class="product__details__pic__item">
                                            <img src="${i}" alt="">
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <div class="product__details__content">
                <div class="container">
                    <div class="row d-flex justify-content-center">
                        <div class="col-lg-8">
                            <div class="product__details__text">
                                <h4>${p.product.product_name}</h4>
                                <!--                            <div class="rating">
                                                                <i class="fa fa-star"></i>
                                                                <i class="fa fa-star"></i>
                                                                <i class="fa fa-star"></i>
                                                                <i class="fa fa-star"></i>
                                                                <i class="fa fa-star-o"></i>
                                                                <span> - 5 Reviews</span>
                                                            </div>-->
                                <h3>${p.product.sale_price} VND<span>${p.product.original_Price} VND</span></h3>
                                <p>${p.product.brief_information}</p>
                                <div class="product__details__option">
                                    <div class="product__details__option__size">
                                        <span>Kích Thước:</span>
                                        <c:forEach items="${size1}" var="s">
                                            <label for="${s.size.size_name}">
                                                <input type="radio" id="${s.size.size_name}" name="size" value="${s.size.size_name}">${s.size.size_name}
                                            </label>
                                        </c:forEach>
                                    </div>
                                    <div class="product__details__option__siz">
                                        <span>Màu Sắc:</span>
                                        <c:forEach items="${Color}" var="c">
                                            <label for="${c.color.color_Name}" id="a">
                                                <input type="radio" id="${c.color.color_Name}" name="color" value="${c.color.color_Name}">${c.color.color_Name}
                                            </label>
                                        </c:forEach>
                                    </div>
                                </div>

                                <div class="product__details__cart__option">
                                    <div class="quantity">
                                        <div class="pro-qty">
                                            <input type="text" value="1">
                                        </div>
                                    </div>
                                    <a href="#" class="primary-btn">add to cart</a>
                                </div>

                                <div class="product__details__last__option">
                                    <h5><span>Guaranteed Safe Checkout</span></h5>
                                    <img src="img/shop-details/details-payment.png" alt="">
                                    <ul>
                                        <li><span>Loại: </span> ${p.product.ct.category_name}</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="product__details__tab">
                                <ul class="nav nav-tabs" role="tablist">
                                    <li class="nav-item">
                                        <a class="nav-link active" data-toggle="tab" href="#tabs-5"
                                           role="tab">Chi tiết sản phẩm</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-toggle="tab" href="#tabs-6" role="tab">
                                            Bình Luận(5)</a>
                                    </li>
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane active" id="tabs-5" role="tabpanel">
                                        <div class="product__details__tab__content">
                                            <div class="product__details__tab__content__item">
                                                <h5>Thông tin chi tiết sản phẩm</h5>
                                                <p>${p.product.product_description}</p>

                                            </div>
                                        </div>
                                    </div>

                                    <div class="tab-pane" id="tabs-6" role="tabpanel">
                                        <div class="product__details__tab__content">
                                            <p class="note">Nam tempus turpis at metus scelerisque placerat nulla deumantos
                                                solicitud felis. Pellentesque diam dolor, elementum etos lobortis des mollis
                                                ut risus. Sedcus faucibus an sullamcorper mattis drostique des commodo
                                                pharetras loremos.</p>
                                            <div class="product__details__tab__content__item">
                                                <h5>Products Infomation</h5>
                                                <p>A Pocket PC is a handheld computer, which features many of the same
                                                    capabilities as a modern PC. These handy little devices allow
                                                    individuals to retrieve and store e-mail messages, create a contact
                                                    file, coordinate appointments, surf the internet, exchange text messages
                                                    and more. Every product that is labeled as a Pocket PC must be
                                                    accompanied with specific software to operate the unit and must feature
                                                    a touchscreen and touchpad.</p>
                                                <p>As is the case with any new technology product, the cost of a Pocket PC
                                                    was substantial during it’s early release. For approximately $700.00,
                                                    consumers could purchase one of top-of-the-line Pocket PCs in 2003.
                                                    These days, customers are finding that prices have become much more
                                                    reasonable now that the newness is wearing off. For approximately
                                                    $350.00, a new Pocket PC can now be purchased.</p>
                                            </div>
                                            <div class="product__details__tab__content__item">
                                                <h5>Material used</h5>
                                                <p>Polyester is deemed lower quality due to its none natural quality’s. Made
                                                    from synthetic materials, not natural like wool. Polyester suits become
                                                    creased easily and are known for not being breathable. Polyester suits
                                                    tend to have a shine to them compared to wool and cotton suits, this can
                                                    make the suit look cheap. The texture of velvet is luxurious and
                                                    breathable. Velvet is a great choice for dinner party jacket and can be
                                                    worn all year round.</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Shop Details Section End -->

        <!-- Related Section Begin -->
        <section class="related spad">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <h3 class="related-title">Sản Phẩm Liên Quan</h3>
                    </div>
                </div>
                <div class="row">
                    <c:forEach items="${productRelated}" var="pr" begin="1" end="4">
                        <div class="col-lg-3 col-md-6 col-sm-6 col-sm-6">
                            <div class="product__item">
                                <div class="product__item__pic set-bg" data-setbg="${pr.image}">
                                    <span class="label">New</span>
                                    <ul class="product__hover">
                                        <li><a href="ProductDetails?pid=${pr.product.product_ID}"><img src="" alt=""></a></li>
                                    </ul>
                                </div>
                                <div class="product__item__text">
                                    <h6>${pr.product.product_name}</h6>
                                    <a href="#" class="add-cart">+ Add To Cart</a>
                                    <div class="rating">
                                        <i class="fa fa-star-o"></i>
                                        <i class="fa fa-star-o"></i>
                                        <i class="fa fa-star-o"></i>
                                        <i class="fa fa-star-o"></i>
                                        <i class="fa fa-star-o"></i>
                                    </div>
                                    <h5>${pr.product.sale_price} VNĐ</h5>
                                </div>
                            </div>
                        </div>  
                    </c:forEach>
                </div>
            </div>
        </section>
        <!-- Related Section End -->

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
        <script>
            document.addEventListener("DOMContentLoaded", function () {
    const sizeLabels = document.querySelectorAll(".product__details__option__siz label");

    sizeLabels.forEach(label => {
        label.addEventListener("click", function () {
            // Xóa class active khỏi tất cả label
            sizeLabels.forEach(lbl => lbl.classList.remove("active"));
            
            // Thêm class active vào label được chọn
            this.classList.add("active");
        });
    });
});

        </script>
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