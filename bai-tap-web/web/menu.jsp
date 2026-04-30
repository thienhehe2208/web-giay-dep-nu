<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, java.sql.*, model.DBConnection"%>
<%
    // Lấy danh mục từ database
    List<String[]> categories = new ArrayList<>();
    try {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT id, name FROM categories");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            categories.add(new String[]{ rs.getString("id"), rs.getString("name") });
        }
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<div class="menu">

    <button onclick="toggleMenu()" class="menu-btn">
        <img src="img/menu.png"> Danh mục
    </button>

    <div class="menu-main">
        <a href="index.jsp" class="menu-link category">
            <img src="img/trangchu.png"> Trang chủ
        </a>
        <a href="HangMoi.jsp" class="menu-link category">
            <img src="img/hangmoi.png"> Hàng mới
        </a>
        <a href="BanChay.jsp" class="menu-link category">
            <img src="img/banchay.png"> Bán chạy
        </a>
        <a href="GiamGia.jsp" class="menu-link category">
            <img src="img/giamgia.png"> Giảm giá
        </a>
        <button class="menu-link contact" onclick="openContact()">
            <img src="img/lienhe.png"> Liên hệ
        </button>
    </div>

</div>

<!-- MENU CON - DANH MỤC TỪ DATABASE -->
<div id="subMenu" class="submenu">
    <p><b>DANH MỤC SẢN PHẨM</b></p>
<%
    for (String[] cat : categories) {
%>
    <a href="products?categoryId=<%= cat[0] %>"><%= cat[1] %></a>
<%
    }
%>
</div>

<script>
function toggleMenu() {
    var x = document.getElementById("subMenu");
    x.style.display = (x.style.display === "block") ? "none" : "block";
}
</script>
