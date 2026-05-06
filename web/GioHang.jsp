<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List, model.CartItem"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Giỏ Hàng – TTQ SHOP</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
</head>
<body>
<jsp:include page="Header.jsp"/>
<jsp:include page="Menu.jsp"/>

<div class="page-container">
    <h2 class="page-title">🛒 Giỏ Hàng</h2>

    <%
        List<CartItem> items = (List<CartItem>) request.getAttribute("cartItems");
        Long total = (Long) request.getAttribute("cartTotal");
        if (total == null) total = 0L;
    %>

    <% if (items == null || items.isEmpty()) { %>
        <div class="empty-cart">
            <p>Giỏ hàng của bạn đang trống.</p>
            <a href="${pageContext.request.contextPath}/products" class="btn-primary">Tiếp tục mua sắm</a>
        </div>
    <% } else { %>
        <form action="${pageContext.request.contextPath}/cart" method="post">
            <input type="hidden" name="action" value="update">
            <table class="cart-table">
                <thead>
                    <tr>
                        <th>Ảnh</th>
                        <th>Sản phẩm</th>
                        <th>Size</th>
                        <th>Đơn giá</th>
                        <th>Số lượng</th>
                        <th>Thành tiền</th>
                        <th>Xoá</th>
                    </tr>
                </thead>
                <tbody>
                <% for (CartItem item : items) { %>
                    <tr>
                        <td>
                            <img src="<%= item.getProduct().getImage() %>"
                                 alt="<%= item.getProduct().getName() %>"
                                 class="cart-item-img"
                                 style="width:70px;height:70px;object-fit:cover;display:block;"
                                 onerror="this.src='https://via.placeholder.com/70x70?text=?'">
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/product?id=<%= item.getProductId() %>">
                                <%= item.getProduct().getName() %>
                            </a>
                        </td>
                        <td><%= item.getSize() != null ? item.getSize() : "–" %></td>
                        <td><%= String.format("%,d", item.getProduct().getDisplayPrice()) %>đ</td>
                        <td>
                            <input type="hidden" name="itemId" value="<%= item.getId() %>">
                            <input type="number" name="quantity" value="<%= item.getQuantity() %>"
                                   min="0" max="99" class="qty-input">
                        </td>
                        <td><%= String.format("%,d", item.getLineTotal()) %>đ</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/cart?action=remove&itemId=<%= item.getId() %>"
                               class="btn-remove" onclick="return confirm('Xoá sản phẩm này?')">✕</a>
                        </td>
                    </tr>
                <% } %>
                </tbody>
            </table>

            <div style="display:flex; justify-content:space-between; align-items:center; padding:20px 0; border-top:1px solid #ede8df;">
                <button type="submit" class="btn-outline">🔄 Cập nhật giỏ hàng</button>
                <div class="cart-total">
                    <span>Tổng cộng:</span>
                    <strong><%= String.format("%,d", total) %>đ</strong>
                </div>
            </div>
        </form>

        <div style="display:flex; justify-content:space-between; align-items:center; margin-top:24px; margin-bottom:60px; gap:16px;">
            <a href="${pageContext.request.contextPath}/products" class="btn-outline">← Tiếp tục mua sắm</a>
            <a href="${pageContext.request.contextPath}/checkout" class="btn-primary" style="width:auto; padding:13px 48px;">Thanh toán →</a>
        </div>
    <% } %>
</div>

<jsp:include page="Footer.jsp"/>
</body>
</html>
