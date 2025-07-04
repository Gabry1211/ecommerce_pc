<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Carrello,model.ElementoCarrello" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<%
    Carrello carrello = (Carrello) session.getAttribute("carrello");
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
            <td><%= elem.getProdotto().getPrezzo() %></td>
            <td><%= elem.getQuantita() %></td>
            <td><%= elem.getTotale() %></td>
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
    <p><strong>Totale: €<%= carrello.getTotale() %></strong></p>
    <form action="svuotaCarrello" method="post"><input type="submit" value="Svuota Carrello"></form>
    <form action="confermaOrdine" method="post"><input type="submit" value="Conferma Ordine"></form>
<%
    }
%>