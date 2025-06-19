<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login Cliente</title>
</head>
<body>
    <h2>Login</h2>
    <form action="LoginServlet" method="post">
        Codice Fiscale: <input type="text" name="codiceFiscale" required><br>
        Password: <input type="password" name="password" required><br>
        <input type="submit" value="Login">
    </form>
</body>
</html>