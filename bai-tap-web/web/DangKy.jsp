<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng ký TTQ SHOP</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/Style.css">
</head>

<body class="login-body">

<div class="login-container">

    <h2>Đăng ký tài khoản</h2>

    <form action="register" method="post">

        <div class="input-group">
            <input type="text" name="username" required>
            <label>Tên đăng nhập</label>
        </div>

        <div class="input-group">
            <input type="password" name="password" required>
            <label>Mật khẩu</label>
        </div>

        <div class="input-group">
            <input type="password" name="confirm" required>
            <label>Xác nhận mật khẩu</label>
        </div>

        <button type="submit" class="login-btn">Đăng ký</button>

        <p class="register-link">
            Đã có tài khoản? <a href="DangNhap.jsp">Đăng nhập</a>
        </p>

    </form>

</div>

</body>
</html>