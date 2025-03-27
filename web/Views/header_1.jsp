<%-- 
    Document   : header.jsp
    Created on : Feb 13, 2025, 12:06:54 PM
    Author     : NV200
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                            <a href="#">Đăng kí / Đăng nhập</a>
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
                    <a href="${pageContext.request.contextPath}/index.html"><img src="${pageContext.request.contextPath}/img/logo.png" alt=""></a>
                </div>
            </div>
            <div class="col-lg-6 col-md-6">
                <nav class="header__menu mobile-menu">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/HomePage">Home</a></li>
                        <li><a href="${pageContext.request.contextPath}/ProductList">Sản phẩm</a></li>                              
                        <li><a href="${pageContext.request.contextPath}/BlogList">Blog</a></li>
                        <li><a href="${pageContext.request.contextPath}/contact.html">Liên hệ</a></li>
                    </ul>
                </nav>
            </div>
            <div class="col-lg-3 col-md-3">
                <div class="header__nav__option">
                    <a href="${pageContext.request.contextPath}/ProductList/Cart"><img src="${pageContext.request.contextPath}/img/icon/heart.png" alt=""></a>
                        <%-- thay ảnh giỏ hàng vào đây --%>
                    
                    <a href="${pageContext.request.contextPath}/order"><img src="${pageContext.request.contextPath}/img/icon/cart.png" alt=""> <span>0</span></a> 
                    <%-- thay ảnh order list hàng vào đây --%>
                    <div class="price">Danh sách đơn hàng</div>
                </div>
            </div>
        </div>
        <div class="canvas__open"><i class="fa fa-bars"></i></div>
    </div>
</header>