<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.*" %>
<!DOCTYPE html>
<%
model.Venditore venditore = (model.Venditore) session.getAttribute("venditore");
if (venditore == null) {
    response.sendRedirect("login.jsp");
    return;
}
int idVenditore = venditore.getIdVenditore();
%>
<html>
<head>
  <title>Homepage Venditore</title>
</head>
<body>
  <h2>Benvenuto Venditore (ID: <%= idVenditore %>)</h2>

  <ul>
    <li><a href="inserisciProdotto.jsp">Inserisci nuovo prodotto</a></li>
    <li><a href="modificaProdotto.jsp">Modifica un prodotto</a></li>
    <li><a href="eliminaProdotto.jsp">Elimina un prodotto</a></li>
    <li><a href="visualizzaProdottiVenditore.jsp">Visualizza i tuoi prodotti</a></li>
    <li><a href="logout.jsp">Logout</a></li>
  </ul>
</body>
</html>
