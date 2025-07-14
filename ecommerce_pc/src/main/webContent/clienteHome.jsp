<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.*,jakarta.servlet.*" %>
<!DOCTYPE html>
<%
    HttpSession session2 = request.getSession(false);
    if (session2 == null || session2.getAttribute("cliente") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
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

<!-- 🖥️ Sezioni principali -->
<div class="sezioni">
    <!-- ⭐ Prodotti in evidenza -->
    <section class="sezione-prodotti">
        <h2>⭐ Prodotti in evidenza</h2>
        <div class="griglia-prodotti">
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
        </div>
    </section>

    <!-- 🎯 Consigliati per te -->
    <section class="sezione-prodotti">
        <h2>🎯 Consigliati per te</h2>
        <div class="griglia-prodotti">
            <div class="prodotto">
                <img src="images/ssd.jpg" alt="SSD NVMe">
                <p><strong>SSD NVMe 1TB</strong></p>
                <p>Prezzo: €89.99</p>
                <a href="dettagliProdotto.jsp?id=3">Vedi dettagli</a>
            </div>
            <div class="prodotto">
                <img src="images/tastiera.jpg" alt="Tastiera meccanica">
                <p><strong>Tastiera Meccanica RGB</strong></p>
                <p>Prezzo: €69.99</p>
                <a href="dettagliProdotto.jsp?id=4">Vedi dettagli</a>
            </div>
        </div>
    </section>

    <!-- 🆕 Novità -->
    <section class="sezione-prodotti">
        <h2>🆕 Novità</h2>
        <div class="griglia-prodotti">
            <div class="prodotto">
                <img src="images/webcam.jpg" alt="Webcam Full HD">
                <p><strong>Webcam Full HD</strong></p>
                <p>Prezzo: €49.99</p>
                <a href="dettagliProdotto.jsp?id=5">Vedi dettagli</a>
            </div>
            <div class="prodotto">
                <img src="images/cuffie.jpg" alt="Cuffie da gaming">
                <p><strong>Cuffie Gaming Surround</strong></p>
                <p>Prezzo: €59.99</p>
                <a href="dettagliProdotto.jsp?id=6">Vedi dettagli</a>
            </div>
        </div>
    </section>
</div>

<jsp:include page="fragments/footer.jsp" />
</body>
</html>
