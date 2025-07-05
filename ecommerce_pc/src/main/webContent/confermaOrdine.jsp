<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Prodotto" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Conferma Ordine</title>
    <link rel="stylesheet" href="styles/style.css">
</head>
<body>


<h2>Conferma Ordine</h2>

<%
    HashMap<Prodotto, Integer> carrello = (HashMap<Prodotto, Integer>) session.getAttribute("carrello");
    if (carrello == null || carrello.isEmpty()) {
%>
    <p>Il carrello è vuoto.</p>
<%
    } else {
        double totale = 0;
%>
    <table border="1">
        <tr>
            <th>Prodotto</th>
            <th>Prezzo</th>
            <th>Quantità</th>
            <th>Subtotale</th>
        </tr>
<%
        for (Map.Entry<Prodotto, Integer> entry : carrello.entrySet()) {
            Prodotto prodotto = entry.getKey();
            int quantita = entry.getValue();
            double prezzo = prodotto.getPrezzo();
            double subtotale = prezzo * quantita;
            totale += subtotale;
%>
        <tr>
            <td><%= prodotto.getDescrizione() %></td>
            <td>€ <%= String.format("%.2f", prezzo) %></td>
            <td><%= quantita %></td>
            <td>€ <%= String.format("%.2f", subtotale) %></td>
        </tr>
<%
        }
%>
        <tr>
            <td colspan="3"><strong>Totale</strong></td>
            <td><strong>€ <%= String.format("%.2f", totale) %></strong></td>
        </tr>
    </table>

    <form action="ConfermaOrdineServlet" method="post">
        <input type="submit" value="Conferma Ordine">
    </form>
<%
    }
%>


</body>
</html>