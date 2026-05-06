<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Product, java.util.List"%>
<%
    Product p = (Product) request.getAttribute("product");
    List<String> sizes = (List<String>) request.getAttribute("sizes");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title><%= (p != null ? p.getName() : "Sản phẩm") %> – TTQ SHOP</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
</head>
<body>
<jsp:include page="Header.jsp"/>
<jsp:include page="Menu.jsp"/>

<div class="page-container">
<% if (p == null) { %>
    <p class="empty-msg">Sản phẩm không tồn tại.</p>
<% } else { %>
    <div class="product-detail">
        <div class="product-detail-img">
            <img src="<%= p.getImage() %>" alt="<%= p.getName() %>"
                 onerror="this.src='https://via.placeholder.com/600x600?text=No+Image'">
        </div>
        <div class="product-detail-info">
            <h1><%= p.getName() %></h1>
            <p class="product-category">Danh mục: <b><%= p.getCategoryName() %></b></p>

            <div class="product-price">
                <% if (p.getDiscountPrice() != null) { %>
                    <span class="price-old"><%= String.format("%,d", p.getPrice()) %>đ</span>
                    <span class="price-sale"><%= String.format("%,d", p.getDiscountPrice()) %>đ</span>
                <% } else { %>
                    <span class="price"><%= String.format("%,d", p.getPrice()) %>đ</span>
                <% } %>
            </div>

            <% if (p.isNew()) { %><span class="badge badge-new">Hàng mới</span><% } %>
            <% if (p.isBestseller()) { %><span class="badge badge-hot">Bán chạy</span><% } %>

            <form action="${pageContext.request.contextPath}/cart" method="get" class="add-cart-form">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="id" value="<%= p.getId() %>">
                <div class="form-group">
                    <label>Size:</label>
                    <select name="size">
                        <option value="">-- Chọn size --</option>
                        <% if (sizes != null && !sizes.isEmpty()) {
                               for (String sz : sizes) { %>
                            <option value="<%= sz %>"><%= sz %></option>
                        <%     }
                           } else {
                               for (int s = 35; s <= 40; s++) { %>
                            <option value="<%= s %>"><%= s %></option>
                        <%     }
                           } %>
                    </select>
                </div>
                <div class="form-group">
                    <label>Số lượng:</label>
                    <input type="number" name="qty" value="1" min="1" max="10">
                </div>
                <button type="submit" class="btn-primary">🛒 Thêm vào giỏ hàng</button>
            </form>

            <div class="product-desc">
                <h3>Mô tả sản phẩm</h3>
                <p><%= p.getDescription() != null ? p.getDescription().replace("\n","<br>") : "" %></p>
            </div>
        </div>
    </div>
<% } %>
</div>

<jsp:include page="Footer.jsp"/>
</body>
</html>
