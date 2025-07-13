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
    <label>Email:</label>
    <input type="email" name="email" required><br>

    <label>Password:</label>
    <input type="password" name="password" required><br>

    <input type="submit" value="Login">
    </form>
    
    <p>Sei un amministratore? <a href="loginAdmin.jsp">Accedi come Admin</a></p>
	<p>Sei un venditore? <a href="loginVenditore.jsp">Accedi come Venditore</a></p>
</body>
</html>