<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.Prodotto" %>
<%@ page session="true" %>
<!DOCTYPE html>
<jsp:include page="fragments/header.jsp" />

<%
    List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti");
    if (prodotti == null) prodotti = new ArrayList<>();
%>

<h2>Gestione Prodotti</h2>

<a href="inserisciProdotto.jsp">➕ Aggiungi nuovo prodotto</a>
<br><br>

<table border="1">
    <tr>
        <th>ID</th><th>Descrizione</th><th>Prezzo</th><th>Tipo</th><th>Azioni</th>
    </tr>
    <% for (Prodotto p : prodotti) { %>
        <tr>
            <td><%= p.getIdProdotto() %></td>
            <td><%= p.getDescrizione() %></td>
            <td><%= p.getPrezzo() %></td>
            <td><%= p.getTipo() %></td>
            <td>
                <a href="modificaProdotto.jsp?id=<%= p.getIdProdotto() %>">✏️ Modifica</a>
                |
                <a href="EliminaProdottoServlet?id=<%= p.getIdProdotto() %>">🗑️ Elimina</a>
            </td>
        </tr>
    <% } %>
</table>

<jsp:include page="fragments/footer.jsp" />
