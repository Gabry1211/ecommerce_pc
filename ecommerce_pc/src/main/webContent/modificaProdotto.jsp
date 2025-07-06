<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Prodotto" %>
<!DOCTYPE html>
<jsp:include page="fragments/header.jsp" />

<%
    Prodotto prodotto = (Prodotto) request.getAttribute("prodotto");
    if (prodotto == null) {
%>
    <p>Prodotto non trovato.</p>
<%
    } else {
%>
    <h2>Modifica Prodotto</h2>
    <form action="ModificaProdottoServlet" method="post">
        <input type="hidden" name="idProdotto" value="<%= prodotto.getIdProdotto() %>">

        <label>Descrizione:</label><br>
        <input type="text" name="descrizione" value="<%= prodotto.getDescrizione() %>" required><br><br>

        <label>Prezzo:</label><br>
        <input type="number" name="prezzo" step="0.01" value="<%= prodotto.getPrezzo() %>" required><br><br>

        <label>Tipo:</label><br>
        <input type="text" name="tipo" value="<%= prodotto.getTipo() %>" required><br><br>

        <input type="submit" value="Salva modifiche">
    </form>
<%
    }
%>

<jsp:include page="fragments/footer.jsp" />
