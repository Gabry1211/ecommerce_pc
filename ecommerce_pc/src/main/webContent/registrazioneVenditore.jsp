<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="fragments/header.jsp" %>
<head>
    <link rel="stylesheet" href="styles/style.css">
    <script src="scripts/validate.js"></script>
</head>

<h2>Registrazione Venditore</h2>

<form action="RegistrazioneVenditoreServlet" method="post" onsubmit="return validaRegistrazioneVenditore()">
    <label for="nome">Nome:</label><br>
    <input type="text" id="nome" name="nome" required>
    <span id="nomeErrore" class="errore"></span><br><br>

    <label for="partitaIva">Partita IVA:</label><br>
    <input type="text" id="partitaIva" name="partitaIva" required>
    <span id="partitaIvaErrore" class="errore"></span><br><br>

    <label for="codiceFiscale">Codice Fiscale:</label><br>
    <input type="text" id="codiceFiscale" name="codiceFiscale" required>
    <span id="codiceFiscaleErrore" class="errore"></span><br><br>

    <label for="password">Password:</label><br>
    <input type="password" id="password" name="password" required>
    <span id="passwordErrore" class="errore"></span><br><br>

    <input type="submit" value="Registrati">
</form>

<%@ include file="fragments/footer.jsp" %>
