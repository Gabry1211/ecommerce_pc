<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Prodotto" %>
<%@ page import="model.ProdottoDAO" %>
<%@ page import="model.Venditore" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<%
    Venditore venditore = (Venditore) session.getAttribute("venditore");

    if (venditore == null) {
        response.sendRedirect("../login.jsp");
        return;
    }

    int idVenditore = venditore.getIdVenditore();
    ProdottoDAO prodottoDAO = new ProdottoDAO();
    List<Prodotto> prodottiVenditore = prodottoDAO.doRetrieveByVenditore(idVenditore);
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>I tuoi Prodotti</title>
</head>
<body>
    <h1>Prodotti Inseriti da Te</h1>

    <table border="1">
        <tr>
            <th>Id</th>
            <th>Descrizione</th>
            <th>Prezzo</th>
            <th>Tipo</th>
        </tr>
        <%
            for (Prodotto p : prodottiVenditore) {
        %>
        <tr>
            <td><%= p.getIdProdotto() %></td>
            <td><%= p.getDescrizione() %></td>
            <td><%= p.getPrezzo() %> €</td>
            <td><%= p.getTipo() %></td>
        </tr>
        <% } %>
    </table>
</body>
</html>
