<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="fragments/header.jsp" %>

<h2>Registrazione Venditore</h2>

<form action="RegistrazioneVenditoreServlet" method="post">
    <label for="nome">Nome:</label><br>
    <input type="text" id="nome" name="nome" required><br><br>

    <label for="partitaIva">Partita IVA:</label><br>
    <input type="text" id="partitaIva" name="partitaIva"><br><br>

    <label for="codiceFiscale">Codice Fiscale:</label><br>
    <input type="text" id="codiceFiscale" name="codiceFiscale"><br><br>

    <input type="submit" value="Registrati">
</form>

<%@ include file="fragments/footer.jsp" %>

