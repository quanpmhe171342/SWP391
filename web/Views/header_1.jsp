<%-- 
    Document   : header.jsp
    Created on : Feb 13, 2025, 12:06:54 PM
    Author     : NV200
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="header">
    <div class="header__top">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 col-md-7">
                    <div class="header__top__left">
                        <p>Miễn phí vận chuyển, bảo đảm hoàn trả hoặc đổi hàng trong 30 ngày.</p>
                    </div>
                </div>
                <div class="col-lg-6 col-md-5">
                    <div class="header__top__right">
                        <div class="header__top__links">
                            <a href="auth/register.jsp">Đăng kí</a> / <a href="auth/login.jsp">Đăng nhập</a>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-lg-3 col-md-3">
                <div class="header__logo">
                    <a href="HomePage"><img src="img/logo.png" alt=""></a>
                </div>
            </div>
            <div class="col-lg-6 col-md-6">
                <nav class="header__menu mobile-menu">
                    <ul>
                        <li><a href="./HomePage">Home</a></li>
                         <li class="dropdown">
                            <a href="./ProductList">Sản phẩm</a>
                            <ul class="submenu">

                                <c:forEach items="${cateproduct}" var="cp">
                                    <li><a href="ProductList?cate=${cp.category_productID}">${cp.category_name}</a></li>
                                    </c:forEach>
                            </ul>
                        </li>
                        <li><a href="./BlogList">Blog</a></li>
                        <li><a href="./contact.html">Liên hệ</a></li>
                    </ul>
                </nav>
            </div>

            <div class="col-lg-3 col-md-3">
                <div class="header__nav__option">
                    <a href="#"><img src="img/icon/heart.png" alt=""></a>
                    <a href="#"><img src="img/icon/cart.png" alt=""> <span>0</span></a>
                    <div class="price">vnd</div>
                </div>
            </div>
        </div>
        <div class="canvas__open"><i class="fa fa-bars"></i></div>
    </div>
</header>