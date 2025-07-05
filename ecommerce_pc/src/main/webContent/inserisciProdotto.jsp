<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="fragments/header.jsp" />

<h2>Inserisci nuovo prodotto</h2>

<form action="InserisciProdottoServlet" method="post">
    <label>Descrizione:</label><br>
    <input type="text" name="descrizione" required><br><br>

    <label>Prezzo:</label><br>
    <input type="number" name="prezzo" step="0.01" required><br><br>

    <label>Tipo:</label><br>
    <input type="text" name="tipo" required><br><br>

    <input type="submit" value="Inserisci prodotto">
</form>

<jsp:include page="fragments/footer.jsp" />
