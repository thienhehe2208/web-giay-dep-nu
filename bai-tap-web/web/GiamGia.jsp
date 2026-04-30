<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, model.Product, model.ProductDAO"%>
<%
    List<Product> list = ProductDAO.getDiscountProducts();
    java.text.NumberFormat fmt = java.text.NumberFormat.getInstance(new java.util.Locale("vi","VN"));
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Giảm giá - TTQ SHOP</title>
    <link rel="stylesheet" href="css/Style.css">
</head>
<body>

<jsp:include page="Header.jsp"/>
<jsp:include page="menu.jsp"/>

<h2 style="text-align:center; margin-top:20px;">💸 SẢN PHẨM GIẢM GIÁ</h2>

<div class="products">
<%
    if (list.isEmpty()) {
%>
    <p style="text-align:center;">Hiện chưa có sản phẩm giảm giá.</p>
<%
    } else {
        for (Product p : list) {
            long giaGoc = (long) p.getPrice();
            long giaGiam = (long) p.getDiscountPrice();
            long phanTramGiam = Math.round((giaGoc - giaGiam) * 100.0 / giaGoc);
%>
    <div class="product">
        <img src="<%= p.getImage() %>" width="100%">
        <span class="badge-sale">-<%= phanTramGiam %>%</span>
        <h3><%= p.getName() %></h3>
        <p class="price-original"><del><%= fmt.format(giaGoc) %>đ</del></p>
        <p class="price-sale"><%= fmt.format(giaGiam) %>đ</p>

        <a href="products?action=detail&id=<%= p.getId() %>">
            <img src="img/thongtinsp.png"> Thông Tin
        </a>
        <button class="add-cart-btn" onclick="openModal(<%= p.getId() %>, '<%= p.getName().replace("'", "\\'") %>')">
            <img src="img/them.png"> Thêm vào giỏ
        </button>
    </div>
<%
        }
    }
%>
</div>

<a href="index.jsp" style="display:block;text-align:center;margin:20px;">⬅ Quay lại trang chủ</a>

<!-- MODAL GIỎ HÀNG -->
<div id="cartModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <h2 id="productName"></h2>
        <form action="cart" method="post">
            <input type="hidden" name="productId" id="productId">
            <label>Size:</label><br><br>
            <select name="size" required>
                <option value="">-- Chọn size --</option>
                <option value="34">34</option><option value="35">35</option>
                <option value="36">36</option><option value="37">37</option>
                <option value="38">38</option><option value="39">39</option>
                <option value="40">40</option>
            </select>
            <br><br>
            <label>Số lượng:</label>
            <input type="number" name="quantity" value="1" min="1" required>
            <br><br>
            <button type="submit">Thêm vào giỏ</button>
            <button type="button" onclick="closeModal()">Hủy</button>
        </form>
    </div>
</div>

<script>
function openModal(id, name) {
    document.getElementById("cartModal").style.display = "block";
    document.getElementById("productId").value = id;
    document.getElementById("productName").innerText = name;
}
function closeModal() { document.getElementById("cartModal").style.display = "none"; }
</script>

<jsp:include page="Footer.jsp"/>
</body>
</html>
