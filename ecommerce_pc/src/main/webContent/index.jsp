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
</div>

<%@ include file="fragments/footer.jsp" %>
