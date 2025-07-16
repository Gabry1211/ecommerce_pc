<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Ordine" %>
<!DOCTYPE html>

<%@ include file="fragments/header.jsp" %>

<html>
<head>
    <title>Ordine Confermato</title>
    <link rel="stylesheet" href="css/stile.css">
</head>
<body>

<div class="container">
    <h2>Grazie per il tuo ordine!</h2>

<%
    Ordine ordine = (Ordine) request.getAttribute("ordineConfermato");
    if (ordine != null) {
%>
    <p>ID Ordine: <strong><%= ordine.getIdOrdine() %></strong></p>
    <p>Totale pagato: <strong>€ <%= String.format("%.2f", ordine.getTotaleOrdine()) %></strong></p>
    <p>Riceverai una email di conferma all'indirizzo registrato.</p>
<%
    }
%>

    <a href="index.jsp">Torna alla Home</a>
</div>

</body>
<%@ include file="fragments/footer.jsp" %>
</html>
