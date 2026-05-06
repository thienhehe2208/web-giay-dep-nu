<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng ký – TTQ SHOP</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
</head>
<body>
<jsp:include page="Header.jsp"/>
<jsp:include page="Menu.jsp"/>

<div class="auth-container">
    <div class="auth-box">
        <h2>Đăng Ký Tài Khoản</h2>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error"><%= request.getAttribute("error") %></div>
        <% } %>

        <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="form-group">
                <label>Họ và tên *</label>
                <input type="text" name="account_name" placeholder="Nguyễn Văn A" required>
            </div>
            <div class="form-group">
                <label>Tên đăng nhập *</label>
                <input type="text" name="username" placeholder="username123" required>
            </div>
            <div class="form-group">
                <label>Email *</label>
                <input type="email" name="email" placeholder="email@example.com" required>
            </div>
            <div class="form-group">
                <label>Số điện thoại</label>
                <input type="text" name="phone" placeholder="0912 345 678">
            </div>
            <div class="form-group">
                <label>Địa chỉ</label>
                <input type="text" name="address" placeholder="Số nhà, Đường, Quận/Huyện, Tỉnh/TP">
            </div>
            <div class="form-group">
                <label>Mật khẩu *</label>
                <input type="password" name="password" placeholder="Tối thiểu 6 ký tự" required>
            </div>
            <div class="form-group">
                <label>Xác nhận mật khẩu *</label>
                <input type="password" name="confirm_password" placeholder="Nhập lại mật khẩu" required>
            </div>
            <button type="submit" class="btn-primary">Tạo Tài Khoản</button>
        </form>

        <p class="auth-link">Đã có tài khoản?
            <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
        </p>
    </div>
</div>

<jsp:include page="Footer.jsp"/>
</body>
</html>
