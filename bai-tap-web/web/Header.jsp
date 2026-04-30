<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, model.GioHangItem"%>

<div class="header">

    <!-- LOGO -->
    <div class="logo">
        <img src="img/logo.png">
        <span>NỮ NHI SHOP</span>
    </div>

    <!-- RIGHT -->
    <div class="header-right">

        <a class="btn" href="DangNhap.jsp">Đăng nhập</a>
        <a class="btn" href="DangKy.jsp">Đăng ký</a>
      
        <!-- CART -->
        <div class="cart-container">

            <a class="cart-btn" href="GioHang.jsp">
                <img src="img/giohang.png" class="cart-icon">
                <span class="cart-count">
                    <%
    List<GioHangItem> cart =
        (List<GioHangItem>) session.getAttribute("cart");

    int count = 0;

    if (cart != null) {
        for (GioHangItem item : cart) {
            count += item.getQuantity();
        }
    }
%>

<span class="cart-count"><%= count %></span>
                </span>
            </a>

            <!-- DROPDOWN -->
            <div class="cart-dropdown">

                <%
    if (cart == null || cart.isEmpty()) {
%>
    <p style="text-align:center;">Chưa có sản phẩm</p>
<%
    } else {
        double total = 0;

        for (GioHangItem item : cart) {  // ✅ THÊM VÒNG LẶP
            total += item.getTotal();
%>

<div class="cart-item">
    <img src="<%= item.getProduct().getImage() %>">

    <div>
        <div><%= item.getProduct().getName() %></div>

        <div class="cart-info">
            x<%= item.getQuantity() %> - <%= item.getTotal() %> đ
        </div>
    </div>
</div>

<%
        } // ✅ ĐÓNG VÒNG LẶP
%>

<div class="cart-total">
    Tổng: <%= total %> đ
</div>

<%
    }
%>
            </div>

        </div>

    </div>

</div>