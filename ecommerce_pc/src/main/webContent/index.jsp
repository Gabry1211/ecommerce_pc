<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="fragments/header.jsp" %>

<div class="homepage">
    <h2>Benvenuto su <span class="techzone">TechZone</span></h2>
    <p class="slogan">Acquista PC assemblati e componenti hardware di qualità!</p>

    <div class="homepage-cards">
        <div class="homepage-card">
            <h3>🖥️ Catalogo Prodotti</h3>
            <p>Esplora il nostro vasto assortimento di PC e componenti.</p>
            <a href="VisualizzaProdottiServlet" class="button-link">Vai ai prodotti</a>
        </div>

        <div class="homepage-card">
            <h3>🔐 Login</h3>
            <p>Accedi al tuo account cliente, venditore o amministratore.</p>
            <a href="login.jsp" class="button-link">Effettua il login</a>
        </div>

        <div class="homepage-card">
            <h3>📝 Registrati</h3>
            <p>Non hai ancora un account? Registrati ora!</p>
            <a href="registrazione.jsp" class="button-link">Registrati</a>
        </div>
    </div>
</div>

<%@ include file="fragments/footer.jsp" %>
