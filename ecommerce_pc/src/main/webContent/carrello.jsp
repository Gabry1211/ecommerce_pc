<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Carrello, model.ElementoCarrello, model.Prodotto" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Carrello</title>
    <link rel="stylesheet" href="styles/style.css">
</head>
<body>
<jsp:include page="fragments/header.jsp" />

<%
    Carrello carrello = (Carrello) session.getAttribute("carrello");
    String erroreQuantita = (String) session.getAttribute("erroreQuantita");
    session.removeAttribute("erroreQuantita");

    if (erroreQuantita != null) {
%>
    <p style="color:red;"><%= erroreQuantita %></p>
<%
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
            Prodotto prod = elem.getProdotto();
            int id = prod.getIdProdotto();
%>
        <tr>
            <td><%= prod.getDescrizione() %></td>
            <td>€<%= String.format("%.2f", prod.getPrezzo()) %></td>
            <td>
                <form action="AggiornaQuantitaCarrelloServlet" method="post" style="display:inline;">
                    <input type="hidden" name="idProdotto" value="<%= id %>">
                    <input type="number" name="quantita" value="<%= elem.getQuantita() %>" min="1" max="<%= prod.getQuantita() %>" required>
                    <input type="submit" value="Aggiorna">
                </form>
                <small>(Disponibili: <%= prod.getQuantita() %>)</small>
            </td>
            <td>€<%= String.format("%.2f", elem.getTotale()) %></td>
            <td>
                <form action="rimuoviCarrello" method="post" style="display:inline;">
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
    <form action="SvuotaCarrelloServlet" method="post"><input type="submit" value="Svuota Carrello"></form>
    <form action="confermaOrdine" method="post"><input type="submit" value="Conferma Ordine"></form>
<%
    }
%>

<jsp:include page="fragments/footer.jsp" />
</body>
</html>
