<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.*" %>
<%@ page import="java.util.List" %>

<%
    HttpSession session2 = request.getSession(false);
    if (session2 == null || session2.getAttribute("cliente") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    ProdottoDAO prodottoDAO = new ProdottoDAO();
    List<Prodotto> tuttiProdotti = null;
    try {
        tuttiProdotti = prodottoDAO.doRetrieveAll(); // Metodo che devi avere o creare
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home Cliente - TechZone</title>
    <link rel="stylesheet" href="styles/style.css">
</head>
<body class="home-page">

<%
    request.setAttribute("nascondiBarraRicerca", true);
%>
<jsp:include page="fragments/header.jsp" />

<!-- 🔍 Barra di ricerca e carrello -->
<div class="top-bar">
    <form action="CercaProdottiServlet" method="get" class="search-bar">
        <input type="text" name="query" placeholder="Cerca prodotti..." required>
        <button type="submit">🔍 Cerca</button>
    </form>
    <div class="carrello-link">
        <a href="carrello.jsp">🛒 Carrello</a>
    </div>
</div>

<!-- 🖥️ Sezione Prodotti dinamica -->
<section class="sezione-prodotti">
    <h2>⭐ Prodotti Disponibili</h2>
    <div class="griglia-prodotti">
        <%
            if (tuttiProdotti != null) {
                for (Prodotto p : tuttiProdotti) {
        %>
                    <div class="prodotto">
                        <img src="ImmagineServlet?file=<%= p.getImmagine() %>" alt="Prodotto" style="max-width:200px; max-height:200px;">
                        <p><strong><%= p.getDescrizione() %></strong></p>
                        <p>Prezzo: €<%= String.format("%.2f", p.getPrezzo()) %></p>
                        <a href="dettagliProdotto.jsp?id=<%= p.getIdProdotto() %>">Vedi dettagli</a>
                    </div>
        <%
                }
            } else {
        %>
                <p>Nessun prodotto disponibile al momento.</p>
        <%
            }
        %>
    </div>
</section>

<jsp:include page="fragments/footer.jsp" />
</body>
</html>
