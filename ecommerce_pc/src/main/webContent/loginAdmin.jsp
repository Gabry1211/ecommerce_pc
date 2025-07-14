<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login Amministratore - TechZone</title>
    <link rel="stylesheet" href="styles/style.css">
    <script src="scripts/validate.js"></script>
</head>
<body class="login-page">

    <%@ include file="fragments/header.jsp" %>

    <div class="login-card">
        <h2 class="login-title">Accesso Amministratore</h2>

        <form action="LoginAdminServlet" method="post" onsubmit="return validaLoginAdmin()" class="login-form">
            <label for="codiceFiscale">Codice Fiscale:</label>
            <input type="text" id="codiceFiscale" name="codiceFiscale" required>
            <span id="codiceFiscaleErrore" class="errore"></span>

            <label for="passwordAdmin">Password:</label>
            <input type="password" id="passwordAdmin" name="password" required>
            <span id="passwordAdminErrore" class="errore"></span>

            <input type="submit" value="Login Admin" class="btn-login">
        </form>

        <p class="login-links">
            Sei un cliente? <a href="login.jsp">Accedi come Cliente</a><br>
            Sei un venditore? <a href="loginVenditore.jsp">Accedi come Venditore</a>
        </p>
    </div>

    <%@ include file="fragments/footer.jsp" %>

</body>
</html>
