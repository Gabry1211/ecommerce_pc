<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login Venditore - TechZone</title>
    <link rel="stylesheet" href="styles/style.css">
    <script src="scripts/validate.js"></script>
</head>
<body class="login-page">

    <div class="login-card">
        <h2 class="login-title">Accesso Venditore</h2>

        <form action="LoginVenditoreServlet" method="post" onsubmit="return validaLoginVenditore()" class="login-form">
            <label for="partitaIVA">Partita IVA:</label>
            <input type="text" id="partitaIVA" name="partitaIVA" required>
            <span id="partitaIVAErrore" class="errore"></span>

            <label for="passwordVenditore">Password:</label>
            <input type="password" id="passwordVenditore" name="password" required>
            <span id="passwordVenditoreErrore" class="errore"></span>

            <input type="submit" value="Login" class="btn-login">
        </form>

        <p class="login-links">
            Sei un cliente? <a href="login.jsp">Accedi come Cliente</a><br>
            Sei un amministratore? <a href="loginAdmin.jsp">Accedi come Admin</a>
        </p>
    </div>

</body>
</html>
