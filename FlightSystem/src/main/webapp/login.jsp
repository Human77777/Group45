<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
</head>
<body>
    <h2>User Login Page</h2>
    <form action="LoginS" method="post">
    <table>
        <tr><td>UserName:</td><td><input type="text" name="txtName"></td></tr>
        <tr><td>Password:</td><td><input type="password" name="txtPwd"></td></tr>
        <tr><td colspan="2"><input type="submit" value="Login"></td></tr>
        <!-- Register button -->
        <tr><td colspan="2" style="text-align:center;"><button onclick="window.location.href='register.jsp'; return false;">Register</button></td></tr>
    </table>
    </form>
</body>
</html>
