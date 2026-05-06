<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Liên Hệ – TTQ SHOP</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
</head>
<body>
<jsp:include page="Header.jsp"/>
<jsp:include page="Menu.jsp"/>

<div class="page-container">
    <h2 class="page-title">📞 Liên Hệ</h2>

    <div class="contact-layout">

        <div class="contact-info">
            <h3>Thông tin liên hệ</h3>
            <p>📧 <strong>Email:</strong> ttqshop@gmail.com</p>
            <p>📞 <strong>Hotline:</strong> 0123 456 789</p>
            <p>📍 <strong>Địa chỉ:</strong> Hải Phòng, Việt Nam</p>
            <p>🕐 <strong>Giờ làm việc:</strong> 8:00 – 21:00, Thứ 2 – Chủ nhật</p>
        </div>

        <div class="contact-form-wrap">
            <h3>Gửi tin nhắn cho chúng tôi</h3>

            <% if (request.getAttribute("success") != null) { %>
                <div class="alert alert-success"><%= request.getAttribute("success") %></div>
            <% } %>
            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-error"><%= request.getAttribute("error") %></div>
            <% } %>

            <form action="${pageContext.request.contextPath}/contact" method="post">
                <div class="form-group">
                    <label>Họ và tên *</label>
                    <input type="text" name="name" placeholder="Nguyễn Văn A"
                           value="${param.name}" required>
                </div>
                <div class="form-group">
                    <label>Email *</label>
                    <input type="email" name="email" placeholder="email@example.com"
                           value="${param.email}" required>
                </div>
                <div class="form-group">
                    <label>Số điện thoại</label>
                    <input type="text" name="phone" placeholder="0912 345 678"
                           value="${param.phone}">
                </div>
                <div class="form-group">
                    <label>Nội dung *</label>
                    <textarea name="message" rows="5"
                              placeholder="Nhập nội dung tin nhắn..." required>${param.message}</textarea>
                </div>
                <button type="submit" class="btn-primary">📨 Gửi tin nhắn</button>
            </form>
        </div>

    </div>
</div>

<jsp:include page="Footer.jsp"/>
</body>
</html>
