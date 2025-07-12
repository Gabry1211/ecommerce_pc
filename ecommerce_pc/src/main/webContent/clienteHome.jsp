<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.*,jakarta.servlet.*" %>
<!DOCTYPE html>

<%
    HttpSession sessione = request.getSession(false);
    if (sessione == null || sessione.getAttribute("cliente") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<head>
    <meta charset="UTF-8">
    <title>Homepage Cliente - EcommercePC</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="fragments/header.jsp" />

<!-- Barra di ricerca e carrello -->
<div class="top-bar">
    <form action="CercaProdottiServlet" method="get" class="search-bar">
        <input type="text" name="query" placeholder="Cerca prodotti..." required>
        <button type="submit">🔍 Cerca</button>
    </form>
    <div class="carrello-link">
        <a href="carrello.jsp">🛒 Carrello</a>
    </div>
</div>

<!-- Sezioni principali (es. PC assemblati, schede video, monitor, etc.) -->
<div class="sezioni">
    <h2>Prodotti in evidenza</h2>
    <div class="griglia-prodotti">
        <%-- In futuro qui puoi stampare i prodotti usando un ciclo su una lista passata dalla servlet --%>
        <div class="prodotto">
            <img src="images/pc1.jpg" alt="PC da gaming">
            <p><strong>PC Gaming RTX 4070</strong></p>
            <p>Prezzo: €1499.99</p>
            <a href="dettagliProdotto.jsp?id=1">Vedi dettagli</a>
        </div>

        <div class="prodotto">
            <img src="images/monitor.jpg" alt="Monitor 27 pollici">
            <p><strong>Monitor 27" Full HD</strong></p>
            <p>Prezzo: €199.99</p>
            <a href="dettagliProdotto.jsp?id=2">Vedi dettagli</a>
        </div>

        <%-- altri prodotti statici o dinamici da database --%>
    </div>
</div>

<jsp:include page="fragments/footer.jsp" />
</body>
</html>

