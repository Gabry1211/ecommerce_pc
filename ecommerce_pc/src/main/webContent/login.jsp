<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login Cliente - TechZone</title>
    <link rel="stylesheet" href="styles/style.css">
    <script src="scripts/validate.js"></script>
</head>
<body class="login-page">

    <div class="login-card">
        <h2 class="login-title">Accedi al tuo account</h2>
        <form action="LoginServlet" method="post" onsubmit="return validaLoginCliente()" class="login-form">
            <label for="email">Email:</label>
            <input type="email" name="email" id="email" required>
            <span id="emailErrore" class="errore"></span>

            <label for="password">Password:</label>
            <input type="password" name="password" id="password" required>
            <span id="passwordErrore" class="errore"></span>

            <input type="submit" value="Login" class="btn-login">
        </form>

        <p class="login-links">
            Sei un amministratore? <a href="loginAdmin.jsp">Accedi come Admin</a><br>
            Sei un venditore? <a href="loginVenditore.jsp">Accedi come Venditore</a>
        </p>
    </div>

</body>
</html>
