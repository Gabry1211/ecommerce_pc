<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Amministratore" %>
<%@ page session="true" %>
<!DOCTYPE html>
<%
    Amministratore admin = (Amministratore) session.getAttribute("amministratore");
    if (admin == null) {
        response.sendRedirect("loginAdmin.jsp");
        return;
    }
%>
<html>
<head><title>Home Amministratore</title></head>
<body>
    <h1>Benvenuto, <%= admin.getNome() %></h1>

    <ul>
        <li><a href="inserisciProdotto.jsp">Inserisci Prodotto</a></li>
        <li><a href="adminVisualizzaOrdini.jsp">Visualizza Ordini</a></li>
        <li><a href="logout.jsp">Logout</a></li>
    </ul>
</body>
</html>
