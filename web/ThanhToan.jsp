<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List, model.CartItem, model.User"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thanh Toán – TTQ SHOP</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
</head>
<body>
<jsp:include page="Header.jsp"/>
<jsp:include page="Menu.jsp"/>

<%
    List<CartItem> items = (List<CartItem>) request.getAttribute("cartItems");
    Long total = (Long) request.getAttribute("cartTotal");
    if (total == null) total = 0L;
    User user = (User) request.getAttribute("user");
%>

<div class="page-container">
    <h2 class="page-title">💳 Thanh Toán</h2>

    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-error"><%= request.getAttribute("error") %></div>
    <% } %>

    <div class="checkout-layout">

        <!-- Đơn hàng -->
        <div class="order-summary">
            <h3>Đơn hàng của bạn</h3>
            <table class="cart-table">
                <thead>
                    <tr><th>Sản phẩm</th><th>Size</th><th>SL</th><th>Thành tiền</th></tr>
                </thead>
                <tbody>
                <% if (items != null) { for (CartItem item : items) { %>
                    <tr>
                        <td><%= item.getProduct().getName() %></td>
                        <td><%= item.getSize() != null ? item.getSize() : "–" %></td>
                        <td><%= item.getQuantity() %></td>
                        <td><%= String.format("%,d", item.getLineTotal()) %>đ</td>
                    </tr>
                <% } } %>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="3"><strong>Tổng cộng</strong></td>
                        <td><strong><%= String.format("%,d", total) %>đ</strong></td>
                    </tr>
                </tfoot>
            </table>
        </div>

        <!-- Form giao hàng -->
        <div class="checkout-form">
            <h3>Thông tin giao hàng</h3>
            <form action="${pageContext.request.contextPath}/checkout" method="post">
                <div class="form-group">
                    <label>Họ và tên *</label>
                    <input type="text" name="full_name"
                           value="<%= user != null ? user.getAccountName() : "" %>" required>
                </div>
                <div class="form-group">
                    <label>Số điện thoại *</label>
                    <input type="text" name="phone"
                           value="<%= user != null ? user.getPhone() : "" %>" required>
                </div>
                <div class="form-group">
                    <label>Địa chỉ giao hàng *</label>
                    <textarea name="address" rows="3" required><%= user != null ? user.getAddress() : "" %></textarea>
                </div>
                <div class="form-group">
                    <label>Phương thức thanh toán</label>
                    <div class="radio-group">
                        <label><input type="radio" name="pay_method" value="COD" checked> Thanh toán khi nhận hàng (COD)</label>
                        <label><input type="radio" name="pay_method" value="BANK"> Chuyển khoản ngân hàng</label>
                    </div>
                </div>
                <button type="submit" class="btn-primary btn-lg">✅ Xác nhận đặt hàng</button>
            </form>
        </div>

    </div>
</div>

<jsp:include page="Footer.jsp"/>
</body>
</html>
