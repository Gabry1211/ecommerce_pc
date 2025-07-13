<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login Venditore</title>
    <link rel="stylesheet" href="styles/style.css">
    <script src="scripts/validate.js"></script>
</head>
<body>
<%@ include file="fragments/header.jsp" %>

<h2>Login Venditore</h2>

<form action="LoginVenditoreServlet" method="post" onsubmit="return validaLoginVenditore()">
    <label>Partita IVA:</label>
    <input type="text" id="partitaIVA" name="partitaIVA" required>
    <span id="partitaIVAErrore" class="errore"></span><br><br>

    <label>Password:</label>
    <input type="password" id="passwordVenditore" name="password" required>
    <span id="passwordVenditoreErrore" class="errore"></span><br><br>

    <input type="submit" value="Login">
</form>

<%@ include file="fragments/footer.jsp" %>
</body>
</html>
