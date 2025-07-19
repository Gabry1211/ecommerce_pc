<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Carrello,model.ElementoCarrello" %>
<%@ page import="java.util.*" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Carrello</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="scripts/carrello.js"></script>
</head>
<body>

<!-- HEADER -->
<jsp:include page="fragments/header.jsp" />

<div class="container">
    <h2>Il tuo Carrello</h2>

    <% Carrello carrello = (Carrello) session.getAttribute("carrello");
       String erroreQuantita = (String) session.getAttribute("erroreQuantita");
       if (erroreQuantita != null) { %>
        <p class="error-msg"><%= erroreQuantita %></p>
        <% session.removeAttribute("erroreQuantita");
       }

       if (carrello == null || carrello.isEmpty()) { %>
        <h3>Il carrello è vuoto</h3>
    <% } else { %>

    <div class="carrello-items">
        <% for (ElementoCarrello elem : carrello.getElementi()) {
               int id = elem.getProdotto().getIdProdotto(); %>
            <div class="carrello-item" id="item-<%= id %>">
                <div class="carrello-dettagli">
                    <h4><%= elem.getProdotto().getDescrizione() %></h4>
                    <p>Prezzo: €<%= String.format("%.2f", elem.getProdotto().getPrezzo()) %></p>
                    <p>Totale: <span id="totale-<%= id %>">€<%= String.format("%.2f", elem.getTotale()) %></span></p>
                </div>

                <div class="carrello-azioni">
    				<input type="number" id="qta-<%= id %>" value="<%= elem.getQuantita() %>" min="1" required>
   					<button type="button" class="btn aggiorna" onclick="aggiornaQuantita(<%= id %>)">Aggiorna</button>

    				<button type="button" class="btn rimuovi" onclick="rimuoviProdotto(<%= id %>)">Rimuovi</button>
    				
			</div>
			</div>
        <% } %>
    </div>

    <div class="carrello-totale">
		<p><strong>Totale: <span id="totaleCarrello">€<%= String.format("%.2f", carrello.getTotale()) %></span></strong></p>


        <button type="button" class="btn danger" onclick="svuotaCarrello()">Svuota Carrello</button>

        <form action="checkout.jsp" method="post" class="inline-form">
            <button type="submit" class="btn conferma">Conferma Ordine</button>
        </form>
    </div>

    <% } %>
</div>

<!-- FOOTER -->
<jsp:include page="fragments/footer.jsp" />

</body>
</html>
