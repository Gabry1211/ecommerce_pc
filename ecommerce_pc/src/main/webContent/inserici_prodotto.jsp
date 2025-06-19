<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String cfAdmin = (String) session.getAttribute("amministratore");
    String idVenditore = (String) session.getAttribute("venditore");

    if (cfAdmin == null && idVenditore == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Inserisci Prodotto</title>
</head>
<body>
    <h2>Inserisci nuovo prodotto</h2>
    <form action="InserisciProdottoServlet" method="post">
        Descrizione: <input type="text" name="descrizione" required><br>
        Prezzo: <input type="number" step="0.01" name="prezzo" required><br>
        Tipo: <input type="text" name="tipo" required><br>
        Percorso immagine: <input type="text" name="immagine" required><br>
        <input type="submit" value="Aggiungi prodotto">
    </form>
</body>
</html>