<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Hàng mới - TTQ SHOP</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/Style.css">
</head>
<body>

<!-- ===== HEADER ===== -->
<jsp:include page="Header.jsp"/>

<!-- ===== MENU ===== -->
<jsp:include page="menu.jsp"/>

<!-- ===== TITLE ===== -->
<h2 style="text-align:center; margin-top:20px;">🆕 HÀNG MỚI</h2>

<!-- ===== PRODUCTS ===== -->
<div class="products">

    <!-- Sản phẩm 1 -->
    <div class="product">
        <img src="img/p5.jpg" alt="Giày mới 1">
        <h3>Giày thể thao nữ mới 1</h3>
        <p>750.000đ</p>
        <a href="Product.jsp?id=5">🔍 Xem</a>
        <a href="cart?id=5">🛒 Thêm</a>
    </div>

    <!-- Sản phẩm 2 -->
    <div class="product">
        <img src="img/p6.jpg" alt="Giày mới 2">
        <h3>Giày sneaker nữ mới 2</h3>
        <p>820.000đ</p>
        <a href="Product.jsp?id=6">🔍 Xem</a>
        <a href="cart?id=6">🛒 Thêm</a>
    </div>

    <!-- Sản phẩm 3 -->
    <div class="product">
        <img src="img/p7.jpg" alt="Giày mới 3">
        <h3>Giày cao gót mới 3</h3>
        <p>690.000đ</p>
        <a href="Product.jsp?id=7">🔍 Xem</a>
        <a href="cart?id=7">🛒 Thêm</a>
    </div>

    <!-- Sản phẩm 4 -->
    <div class="product">
        <img src="img/p1.jpg" alt="Giày mới 4">
        <h3>Dép sandal mới 4</h3>
        <p>550.000đ</p>
        <a href="Product.jsp?id=8">🔍 Xem</a>
        <a href="cart?id=8">🛒 Thêm</a>
    </div>

</div>

<!-- ===== FOOTER ===== -->
<jsp:include page="Footer.jsp"/>

</body>
</html>