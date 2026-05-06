<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập – TTQ SHOP</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
</head>
<body>
<jsp:include page="Header.jsp"/>
<jsp:include page="Menu.jsp"/>

<div class="auth-container">
    <div class="auth-box">
        <h2>Đăng Nhập</h2>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error"><%= request.getAttribute("error") %></div>
        <% } %>
        <% if (request.getAttribute("success") != null) { %>
            <div class="alert alert-success"><%= request.getAttribute("success") %></div>
        <% } %>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label>Tên đăng nhập *</label>
                <input type="text" name="username" placeholder="Nhập tên đăng nhập"
                       value="${param.username}" required>
            </div>
            <div class="form-group">
                <label>Mật khẩu *</label>
                <input type="password" name="password" placeholder="Nhập mật khẩu" required>
            </div>
            <button type="submit" class="btn-primary">Đăng Nhập</button>
        </form>

        <p class="auth-link">Chưa có tài khoản?
            <a href="${pageContext.request.contextPath}/register">Đăng ký ngay</a>
        </p>
    </div>
</div>

<jsp:include page="Footer.jsp"/>
</body>
</html>
