<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="model.*" %>
<%@ page import="control.*" %>
<%
    Venditore venditore = (Venditore) session.getAttribute("venditore");
    if (venditore == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    int idVenditore = venditore.getIdVenditore();
    ProdottoDAO prodottoDAO = new ProdottoDAO();
    List<Prodotto> prodottiVenditore = prodottoDAO.doRetrieveByVenditore(idVenditore);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Homepage Venditore</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="fragments/header.jsp" />

<div class="dashboard">
    <div class="intestazione">
        <h2>Benvenuto, <%= venditore.getNome() %>!</h2>
        <a href="logout.jsp">Logout</a>
    </div>

    <div class="azioni">
        <a href="inserisciProdotto.jsp" class="btn">➕ Inserisci nuovo prodotto</a>
    </div>

    <h3>I tuoi prodotti</h3>
    <div class="griglia-prodotti">
        <% for (Prodotto p : prodottiVenditore) { %>
            <div class="card-prodotto">
                <img src="<%= p.getImmagine() != null ? "images/" + p.getImmagine() : "images/default.jpg" %>" alt="Prodotto">
                <h3><%= p.getDescrizione() %></h3>
                <p>Prezzo: €<%= String.format("%.2f", p.getPrezzo()) %></p>
                <p>Tipo: <%= p.getTipo() %></p>
                <a href="modificaProdotto.jsp?id=<%= p.getIdProdotto() %>" class="btn-modifica">✏️ Modifica</a>
                <a href="EliminaProdottoServlet?id=<%= p.getIdProdotto() %>" class="btn-elimina" onclick="return confirm('Sei sicuro di voler eliminare questo prodotto?');">🗑️ Elimina</a>
            </div>
        <% } %>
    </div>
</div>

<jsp:include page="fragments/footer.jsp" />
</body>
</html>
