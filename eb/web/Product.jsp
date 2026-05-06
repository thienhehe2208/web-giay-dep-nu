<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List, model.Product, model.Category, dao.CategoryDAO"%>
<%
    List<Category> categories = (List<Category>) request.getAttribute("categories");
    if (categories == null) categories = CategoryDAO.getAll();
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${pageTitle} – TTQ SHOP</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
</head>
<body>
<jsp:include page="Header.jsp"/>
<jsp:include page="Menu.jsp"/>

<div class="page-container">
    <div class="page-layout">

        <!-- LEFT SIDEBAR -->
        <aside class="sidebar">
            <div class="sidebar-section">
                <h4 class="sidebar-title">Danh mục</h4>
                <ul class="sidebar-list">
                    <% for (Category cat : categories) { %>
                    <li>
                        <a href="${pageContext.request.contextPath}/products?category=<%= cat.getId() %>">
                            <%= cat.getName() %>
                        </a>
                    </li>
                    <% } %>
                </ul>
            </div>

            <div class="sidebar-section">
                <h4 class="sidebar-title">Lọc sản phẩm</h4>
                <ul class="sidebar-list">
                    <li><a href="${pageContext.request.contextPath}/products/new">🆕 Hàng mới</a></li>
                    <li><a href="${pageContext.request.contextPath}/products/bestseller">🔥 Bán chạy</a></li>
                    <li><a href="${pageContext.request.contextPath}/products/sale">🏷️ Giảm giá</a></li>
                    <li><a href="${pageContext.request.contextPath}/products">Tất cả sản phẩm</a></li>
                </ul>
            </div>
        </aside>

        <!-- MAIN CONTENT -->
        <div class="main-content">
            <h2 class="page-title">${pageTitle}</h2>

            <%
                List<Product> products = (List<Product>) request.getAttribute("products");
            %>

            <div class="products">
            <% if (products != null && !products.isEmpty()) {
                   for (Product p : products) { %>
                <div class="product-card">
                    <a href="${pageContext.request.contextPath}/product?id=<%= p.getId() %>">
                        <div class="product-img-wrap">
                            <img class="product-thumb" src="<%= p.getImage() %>" alt="<%= p.getName() %>"
                                 onerror="this.src='https://via.placeholder.com/400x400?text=No+Image'">
                            <% if (p.isNew()) { %><span class="badge badge-new">Mới</span><% } %>
                            <% if (p.isBestseller()) { %><span class="badge badge-hot">Bán chạy</span><% } %>
                        </div>
                    </a>
                    <div class="product-info">
                        <h3><a href="${pageContext.request.contextPath}/product?id=<%= p.getId() %>">
                            <%= p.getName() %>
                        </a></h3>
                        <div class="product-price">
                            <% if (p.getDiscountPrice() != null) { %>
                                <span class="price-old"><%= String.format("%,d", p.getPrice()) %>đ</span>
                                <span class="price-sale"><%= String.format("%,d", p.getDiscountPrice()) %>đ</span>
                            <% } else { %>
                                <span class="price"><%= String.format("%,d", p.getPrice()) %>đ</span>
                            <% } %>
                        </div>
                        <div class="product-actions">
                            <a class="btn-cart" href="${pageContext.request.contextPath}/cart?action=add&id=<%= p.getId() %>">
                                🛒 Thêm vào giỏ
                            </a>
                        </div>
                    </div>
                </div>
            <% } } else { %>
                <p class="empty-msg">Không tìm thấy sản phẩm nào.</p>
            <% } %>
            </div>
        </div>

    </div>
</div>

<jsp:include page="Footer.jsp"/>
</body>
</html>
