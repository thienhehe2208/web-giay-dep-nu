<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.User"%>
<%
    User currentUser = (User) session.getAttribute("user");
    Integer cartCount = (Integer) session.getAttribute("cartCount");
    if (cartCount == null) cartCount = 0;
%>
<div class="header">
    <div class="logo">
        <a href="${pageContext.request.contextPath}/index.jsp">
            <span class="logo-text">TTQ SHOP</span>
        </a>
    </div>

    <div class="search-bar">
        <form action="${pageContext.request.contextPath}/products" method="get">
            <input type="text" name="search" placeholder="Tìm kiếm giày..." value="${param.search}">
            <button type="submit">🔍</button>
        </form>
    </div>

    <div class="header-right">
        <% if (currentUser != null) { %>
            <span class="welcome">Xin chào, <b><%= currentUser.getAccountName() %></b></span>
            <a class="btn" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
        <% } else { %>
            <a class="btn" href="${pageContext.request.contextPath}/login">Đăng nhập</a>
            <a class="btn btn-outline" href="${pageContext.request.contextPath}/register">Đăng ký</a>
        <% } %>

        <a class="cart-btn" href="${pageContext.request.contextPath}/cart">
            🛒 Giỏ hàng
            <span class="cart-count"><%= cartCount %></span>
        </a>
    </div>
</div>
