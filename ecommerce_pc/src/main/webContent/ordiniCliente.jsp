<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Ordine" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>I tuoi ordini</title>
    <link rel="stylesheet" href="styles/style.css">
</head>
<body>
    <%@ include file="includes/header.jsp" %>

    <h2>I tuoi ordini</h2>
    <%
        List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
        if (ordini == null || ordini.isEmpty()) {
    %>
        <p>Non hai ancora effettuato ordini.</p>
    <%
        } else {
    %>
        <table>
            <tr>
                <th>ID Ordine</th>
                <th>Prodotti</th>
                <th>Totale (€)</th>
                <th>ID Assistenza</th>
            </tr>
            <%
                for (Ordine o : ordini) {
            %>
                <tr>
                    <td><%= o.getIdOrdine() %></td>
                    <td><%= o.getListaProdotti() %></td>
                    <td><%= o.getTotaleOrdine() %></td>
                    <td><%= o.getIdAssistenza() %></td>
                </tr>
            <%
                }
            %>
        </table>
    <%
        }
    %>

    <%@ include file="includes/footer.jsp" %>
</body>
</html>
