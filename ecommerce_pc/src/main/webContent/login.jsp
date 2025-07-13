<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login Cliente</title>
    <link rel="stylesheet" href="styles/style.css">
    <script src="scripts/validate.js"></script>
</head>
<body>
    <h2>Login</h2>
    <form action="LoginServlet" method="post" onsubmit="return validaLoginCliente()">
        <label>Email:</label>
        <input type="email" name="email" id="email" required>
        <span id="emailErrore" class="errore"></span><br>

        <label>Password:</label>
        <input type="password" name="password" id="password" required>
        <span id="passwordErrore" class="errore"></span><br>

        <input type="submit" value="Login">
    </form>
    
    <p>Sei un amministratore? <a href="loginAdmin.jsp">Accedi come Admin</a></p>
    <p>Sei un venditore? <a href="loginVenditore.jsp">Accedi come Venditore</a></p>
</body>
</html>
