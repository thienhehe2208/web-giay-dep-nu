<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List, model.Product, dao.ProductDAO"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>TTQ SHOP – Giày dép nữ cao cấp</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
    <link rel="preload" as="image" href="${pageContext.request.contextPath}/img/banner1.jpg">
    <link rel="preload" as="image" href="${pageContext.request.contextPath}/img/banner2.jpg">
    <link rel="preload" as="image" href="${pageContext.request.contextPath}/img/banner3.jpg">
</head>
<body>
<jsp:include page="Header.jsp"/>
<jsp:include page="Menu.jsp"/>

<!-- BANNER -->
<div class="banner">
    <button class="slide-btn prev" onclick="prevSlide()">❮</button>
    <img id="bannerImg" src="${pageContext.request.contextPath}/img/banner1.jpg" alt="Banner" loading="eager" fetchpriority="high">
    <button class="slide-btn next" onclick="nextSlide()">❯</button>
</div>

<!-- Hàng mới về -->
<div class="page-container">
    <h2 class="section-title">🆕 Hàng Mới Về</h2>
    <div class="products">
    <%
        List<Product> newProducts = ProductDAO.getNewProducts();
        for (Product p : newProducts) {
    %>
        <div class="product-card">
            <a href="${pageContext.request.contextPath}/product?id=<%= p.getId() %>">
                <div class="product-img-wrap">
                    <img class="product-thumb" src="<%= p.getImage() %>" alt="<%= p.getName() %>"
                         onerror="this.src='https://via.placeholder.com/400x400?text=No+Image'">
                    <% if (p.isNew()) { %><span class="badge badge-new">Mới</span><% } %>
                </div>
            </a>
            <div class="product-info">
                <h3><%= p.getName() %></h3>
                <div class="product-price">
                    <% if (p.getDiscountPrice() != null) { %>
                        <span class="price-old"><%= String.format("%,d", p.getPrice()) %>đ</span>
                        <span class="price-sale"><%= String.format("%,d", p.getDiscountPrice()) %>đ</span>
                    <% } else { %>
                        <span class="price"><%= String.format("%,d", p.getPrice()) %>đ</span>
                    <% } %>
                </div>
                <a class="btn-cart" href="${pageContext.request.contextPath}/cart?action=add&id=<%= p.getId() %>">
                    🛒 Thêm vào giỏ
                </a>
            </div>
        </div>
    <% } %>
    </div>
    <div class="view-all">
        <a href="${pageContext.request.contextPath}/products/new" class="btn-outline">Xem tất cả hàng mới →</a>
    </div>

    <!-- Bán chạy -->
    <h2 class="section-title">🔥 Bán Chạy Nhất</h2>
    <div class="products">
    <%
        List<Product> bestsellers = ProductDAO.getBestsellers();
        for (Product p : bestsellers) {
    %>
        <div class="product-card">
            <a href="${pageContext.request.contextPath}/product?id=<%= p.getId() %>">
                <div class="product-img-wrap">
                    <img class="product-thumb" src="<%= p.getImage() %>" alt="<%= p.getName() %>"
                         onerror="this.src='https://via.placeholder.com/400x400?text=No+Image'">
                    <span class="badge badge-hot">Bán chạy</span>
                </div>
            </a>
            <div class="product-info">
                <h3><%= p.getName() %></h3>
                <div class="product-price">
                    <% if (p.getDiscountPrice() != null) { %>
                        <span class="price-old"><%= String.format("%,d", p.getPrice()) %>đ</span>
                        <span class="price-sale"><%= String.format("%,d", p.getDiscountPrice()) %>đ</span>
                    <% } else { %>
                        <span class="price"><%= String.format("%,d", p.getPrice()) %>đ</span>
                    <% } %>
                </div>
                <a class="btn-cart" href="${pageContext.request.contextPath}/cart?action=add&id=<%= p.getId() %>">
                    🛒 Thêm vào giỏ
                </a>
            </div>
        </div>
    <% } %>
    </div>
    <div class="view-all">
        <a href="${pageContext.request.contextPath}/products/bestseller" class="btn-outline">Xem tất cả bán chạy →</a>
    </div>
</div>

<jsp:include page="Footer.jsp"/>

<script>
const images = ["img/banners1.png","img/banners2.png","img/banners3.png"];
let idx = 0;
function showSlide() { document.getElementById("bannerImg").src = images[idx]; }
function nextSlide() { idx = (idx + 1) % images.length; showSlide(); }
function prevSlide() { idx = (idx - 1 + images.length) % images.length; showSlide(); }
setInterval(nextSlide, 4000);
</script>
</body>
</html>
