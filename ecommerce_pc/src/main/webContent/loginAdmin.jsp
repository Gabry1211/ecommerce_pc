<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login Amministratore</title>
    <link rel="stylesheet" href="styles/style.css">
    <script src="scripts/validate.js"></script>
</head>
<body>
<%@ include file="fragments/header.jsp" %>

<h2>Login Amministratore</h2>
<form action="LoginAdminServlet" method="post" onsubmit="return validaLoginAdmin()">
    <label for="codiceFiscale">Codice Fiscale:</label>
    <input type="text" id="codiceFiscale" name="codiceFiscale" required>
    <span id="codiceFiscaleErrore" class="errore"></span><br><br>

    <label for="password">Password:</label>
    <input type="password" id="passwordAdmin" name="password" required>
    <span id="passwordAdminErrore" class="errore"></span><br><br>

    <input type="submit" value="Login Admin">
</form>

<%@ include file="fragments/footer.jsp" %>
</body>
</html>
