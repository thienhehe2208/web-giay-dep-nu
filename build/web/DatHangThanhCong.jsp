<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đặt hàng thành công – TTQ SHOP</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
</head>
<body>
<jsp:include page="Header.jsp"/>

<div class="page-container success-page">
    <div class="success-box">
        <div class="success-icon">🎉</div>
        <h2>Đặt hàng thành công!</h2>
        <p>Cảm ơn <strong>${customerName}</strong> đã mua sắm tại TTQ SHOP.</p>
        <p>Đơn hàng sẽ được giao đến:</p>
        <p><strong>${customerAddress}</strong></p>
        <p>Liên hệ: <strong>${customerPhone}</strong></p>
        <p>Phương thức thanh toán: <strong>${payMethod}</strong></p>
        <p class="note">Chúng tôi sẽ liên hệ xác nhận đơn hàng trong vòng 24 giờ.</p>
        <a href="${pageContext.request.contextPath}/index.jsp" class="btn-primary">Tiếp tục mua sắm</a>
    </div>
</div>

<jsp:include page="Footer.jsp"/>
</body>
</html>
