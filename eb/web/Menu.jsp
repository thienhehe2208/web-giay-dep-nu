<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List, model.Category, dao.CategoryDAO"%>
<%
    List<Category> categories = CategoryDAO.getAll();
%>
<nav class="menu">
    <div class="menu-main">
        <a href="${pageContext.request.contextPath}/index.jsp">🏠 Trang chủ</a>
        <a href="${pageContext.request.contextPath}/products/new">🆕 Hàng mới</a>
        <a href="${pageContext.request.contextPath}/products/bestseller">🔥 Bán chạy</a>
        <a href="${pageContext.request.contextPath}/products/sale">🏷️ Giảm giá</a>
        <a href="${pageContext.request.contextPath}/contact">📞 Liên hệ</a>

        <!-- Dropdown danh mục -->
        <div class="dropdown">
            <button class="dropdown-btn">📂 Danh mục ▾</button>
            <div class="dropdown-content">
                <% for (Category cat : categories) { %>
                    <a href="${pageContext.request.contextPath}/products?category=<%= cat.getId() %>">
                        <%= cat.getName() %>
                    </a>
                <% } %>
            </div>
        </div>
    </div>
</nav>
