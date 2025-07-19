<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Amministratore" %>
<%@ page session="true" %>
<%@ page import="model.*" %>
<%@ page import="java.util.*" %>
<%
    Amministratore admin = (Amministratore) session.getAttribute("admin");
    if (admin == null) {
        response.sendRedirect("loginAdmin.jsp");
        return;
    }

    ProdottoDAO prodottoDAO = new ProdottoDAO();
    List<Prodotto> tuttiProdotti = prodottoDAO.doRetrieveAll();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home Amministratore</title>
    <link rel="stylesheet" href="styles/style.css"> <!-- CSS separato -->
</head>
<body>
<jsp:include page="fragments/header.jsp" />

<div class="admin-dashboard">
    <div class="admin-header">
        <h2>Benvenuto, <%= admin.getNome() %>!</h2>
        <a href="logout.jsp">Logout</a>
    </div>

    <div class="admin-actions">
        <a href="adminInserisciProdotto.jsp" class="btn">➕ Inserisci nuovo prodotto</a>
        <a href="adminVisualizzaOrdini.jsp" class="btn">📦 Visualizza Ordini</a>
    </div>

    <h3>Catalogo completo dei prodotti</h3>
    <div class="admin-product-grid">
        <% for (Prodotto p : tuttiProdotti) { %>
            <div class="admin-product-card">
                <img src="ImmagineServlet?file=<%= p.getImmagine() %>" alt="Prodotto" style="max-width: 200px; max-height: 200px;">
                <h3><%= p.getDescrizione() %></h3>
                <p>Prezzo: €<%= String.format("%.2f", p.getPrezzo()) %></p>
                <p>Tipo: <%= p.getTipo() %></p>
                <p>Quantità: <%= p.getQuantita() %></p>
                <a href="adminModificaProdotto.jsp?id=<%= p.getIdProdotto() %>" class="btn-modifica">✏️ Modifica</a>
                <a href="AdminEliminaProdottoServlet?id=<%= p.getIdProdotto() %>" class="btn-elimina">🗑️ Elimina</a>
            </div>
        <% } %>
    </div>
</div>

<jsp:include page="fragments/footer.jsp" />
</body>
</html>

