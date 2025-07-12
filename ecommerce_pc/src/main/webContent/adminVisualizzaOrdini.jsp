<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.Ordine, model.OrdineDAO" %>
<!DOCTYPE html>
<%@ include file="fragments/header.jsp" %>

<%
    if (session.getAttribute("amministratore") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String codiceFiscale = request.getParameter("cf");
    String dataInizio = request.getParameter("dataInizio");
    String dataFine = request.getParameter("dataFine");

    OrdineDAO ordineDAO = new OrdineDAO();
    List<Ordine> ordini;

    if (codiceFiscale != null && !codiceFiscale.isEmpty()) {
        ordini = ordineDAO.doRetrieveByCliente(codiceFiscale);
    } else if (dataInizio != null && dataFine != null && !dataInizio.isEmpty() && !dataFine.isEmpty()) {
        ordini = ordineDAO.doRetrieveByData(dataInizio, dataFine);
    } else {
        ordini = ordineDAO.doRetrieveAll();
    }
%>

<html>
<head>
    <title>Visualizza Ordini</title>
</head>
<body>
    <h2>Gestione Ordini</h2>

    <form method="get">
        <label>Filtra per Cliente (Codice Fiscale):</label>
        <input type="text" name="cf" placeholder="Codice Fiscale">
        <input type="submit" value="Filtra">
    </form>
    <form method="get">
        <label>Filtra per Data:</label>
        <input type="date" name="dataInizio"> - 
        <input type="date" name="dataFine">
        <input type="submit" value="Filtra">
    </form>

    <br>
    <table border="1">
        <tr>
            <th>ID Ordine</th><th>Prodotti</th><th>Totale</th><th>Cliente</th><th>Assistenza</th>
        </tr>
        <% for (Ordine o : ordini) { %>
        <tr>
            <td><%= o.getIdOrdine() %></td>
            <td><%= o.getListaProdotti() %></td>
            <td><%= o.getTotaleOrdine() %> €</td>
            <td><%= o.getCodiceFiscaleCliente() %></td>
            <td><%= o.getIdAssistenza() %></td>
        </tr>
        <% } %>
    </table>
</body>
</html>

<%@ include file="fragments/footer.jsp" %>
