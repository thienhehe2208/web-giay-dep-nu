<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng nhập</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/Style.css">
</head>

<body class="login-body">

<div class="login-container">

    <h2>Đăng nhập</h2>

    <form action="login" method="post">

        <div class="input-group">
            <input type="text" name="username" required>
            <label>Tên đăng nhập</label>
        </div>

        <div class="input-group">
            <input type="password" name="password" required>
            <label>Mật khẩu</label>
        </div>

        <button type="submit" class="login-btn">Đăng nhập</button>

        <p class="register-link">
            Chưa có tài khoản? <a href="DangKy.jsp">Đăng ký</a>
        </p>

    </form>

</div>

</body>
</html>