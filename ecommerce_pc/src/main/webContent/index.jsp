<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.*, java.util.*" %>
<% request.setAttribute("mostraLogin", true); %>
<%@ include file="fragments/header.jsp" %>

<div class="homepage-container">
    <h2>Benvenuto su <span class="techzone">TechZone</span></h2>
    <p class="slogan">Scopri le ultime novità e acquista i migliori componenti hardware!</p>

    <!-- Sezione Prodotti Nuovi -->
    <section class="section-nuovi-prodotti">
        <h3>🆕 Prodotti Nuovi</h3>
        <div class="card-container">
            <% 
                ProdottoDAO prodottoDAO = new ProdottoDAO();
                List<Prodotto> nuoviProdotti = prodottoDAO.doRetrieveUltimi(4); // metodo da creare se non esiste

                for (Prodotto p : nuoviProdotti) {
            %>
                <div class="card-prodotto">
                    <img src="ImmagineServlet?file=<%= p.getImmagine() %>" alt="img">
                    <h4><%= p.getDescrizione() %></h4>
                    <p>€<%= String.format("%.2f", p.getPrezzo()) %></p>
                    <a href="dettagliProdotto.jsp?id=<%= p.getIdProdotto() %>" class="btn-dettagli">Dettagli</a>
                </div>
            <% } %>
        </div>
    </section>

    <!-- Sezione per Categoria -->
    <section class="section-categorie">
        <h3>🗂️ Categorie</h3>
        <div class="categorie-container">
            <a href="VisualizzaProdottiServlet?tipo=PC" class="categoria-card">🖥️ PC</a>
            <a href="VisualizzaProdottiServlet?tipo=SSD" class="categoria-card">💾 SSD</a>
            <a href="VisualizzaProdottiServlet?tipo=GPU" class="categoria-card">🎮 GPU</a>
            <a href="VisualizzaProdottiServlet?tipo=RAM" class="categoria-card">🧠 RAM</a>
            <a href="VisualizzaProdottiServlet?tipo=HDD" class="categoria-card">📀 HDD</a>
            <a href="VisualizzaProdottiServlet?tipo=Alimentatore" class="categoria-card">⚡ PSU</a>
            <a href="VisualizzaProdottiServlet?tipo=Scheda Madre" class="categoria-card">🧩 Schede Madri</a>
        </div>
    </section>
</div>

<%@ include file="fragments/footer.jsp" %>
