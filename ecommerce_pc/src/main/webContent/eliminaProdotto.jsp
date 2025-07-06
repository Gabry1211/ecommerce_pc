<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="fragments/header.jsp" />

<h2>Elimina prodotto</h2>

<p>Sei sicuro di voler eliminare questo prodotto?</p>

<form action="EliminaProdottoServlet" method="post">
    <input type="hidden" name="idProdotto" value="${param.id}">
    <input type="submit" value="Conferma eliminazione">
</form>

<a href="VisualizzaProdottiServlet">Annulla</a>

<jsp:include page="fragments/footer.jsp" />
