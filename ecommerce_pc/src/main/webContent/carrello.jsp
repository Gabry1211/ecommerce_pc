<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Carrello,model.ElementoCarrello,model.Prodotto" %>
<%@ page import="java.util.*" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Carrello</title>
</head>
<body>

<%
    Carrello carrello = (Carrello) session.getAttribute("carrello");
    String erroreQuantita = (String) session.getAttribute("erroreQuantita");
    if (erroreQuantita != null) {
%>
    <p style="color:red;"><%= erroreQuantita %></p>
<%
        session.removeAttribute("erroreQuantita");
    }

    if (carrello == null || carrello.isEmpty()) {
%>
    <h3>Il carrello è vuoto</h3>
<%
    } else {
%>
    <h2>Carrello</h2>
    <table border="1">
        <tr><th>Prodotto</th><th>Prezzo</th><th>Quantità</th><th>Totale</th><th>Azioni</th></tr>
<%
        for (ElementoCarrello elem : carrello.getElementi()) {
            int id = elem.getProdotto().getIdProdotto();
%>
        <tr>
            <td><%= elem.getProdotto().getDescrizione() %></td>
            <td>€<%= String.format("%.2f", elem.getProdotto().getPrezzo()) %></td>
            <td>
                <form action="AggiornaQuantitaCarrelloServlet" method="post">
                    <input type="hidden" name="idProdotto" value="<%= id %>">
                    <input type="number" name="quantita" value="<%= elem.getQuantita() %>" min="1" required>
                    <input type="submit" value="Aggiorna">
                </form>
            </td>
            <td>€<%= String.format("%.2f", elem.getTotale()) %></td>
            <td>
                <form action="rimuoviCarrello" method="post" style="display:inline">
                    <input type="hidden" name="idProdotto" value="<%= id %>">
                    <input type="submit" value="Rimuovi">
                </form>
            </td>
        </tr>
<%
        }
%>
    </table>
    <p><strong>Totale: €<%= String.format("%.2f", carrello.getTotale()) %></strong></p>

    <form action="SvuotaCarrelloServlet" method="post">
        <input type="submit" value="Svuota Carrello">
    </form>

    <form action="confermaOrdine" method="post">
        <input type="submit" value="Conferma Ordine">
    </form>
<%
    }
%>
</body>
</html>
