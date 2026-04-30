<%@page import="java.util.*, model.GioHangItem"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Giỏ hàng</title>
    <link rel="stylesheet" href="css/Style.css">
</head>
<body>

<jsp:include page="Header.jsp"/>

<h2 style="text-align:center;">🛒 Giỏ hàng</h2>

<table border="1" width="80%" align="center">
    <tr>
        <th>Ảnh</th>
        <th>Tên</th>
        <th>Giá</th>
        <th>Số lượng</th>
        <th>Tổng</th>
    </tr>

<%
    Map<Integer, GioHangItem> cart =
        (Map<Integer, GioHangItem>) session.getAttribute("cart");

    double total = 0;

    if (cart != null) {
        for (GioHangItem item : cart.values()) {
            total += item.getTotal();
%>

<tr>
    <td><img src="<%= item.getProduct().getImage() %>" width="80"></td>
    <td><%= item.getProduct().getName() %></td>
    <td><%= item.getProduct().getPrice() %></td>
    <td><%= item.getQuantity() %></td>
    <td><%= item.getTotal() %></td>
</tr>

<%
        }
    }
%>

</table>

<h3 style="text-align:center;">Tổng tiền: <%= total %> đ</h3>

<jsp:include page="Footer.jsp"/>

</body>
</html>