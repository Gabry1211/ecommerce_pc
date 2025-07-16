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
            <div class="carrello-item">
                <div class="carrello-dettagli">
                    <h4><%= elem.getProdotto().getDescrizione() %></h4>
                    <p>Prezzo: €<%= String.format("%.2f", elem.getProdotto().getPrezzo()) %></p>
                    <p>Totale: €<%= String.format("%.2f", elem.getTotale()) %></p>
                </div>

                <div class="carrello-azioni">
                    <form action="AggiornaQuantitaCarrelloServlet" method="post" class="quantita-form">
                        <input type="hidden" name="idProdotto" value="<%= id %>">
                        <input type="number" name="quantita" value="<%= elem.getQuantita() %>" min="1" required>
                        <button type="submit" class="btn aggiorna">Aggiorna</button>
                    </form>

                    <form action="RimuoviDalCarrelloServlet" method="post" class="rimuovi-form">
                        <input type="hidden" name="idProdotto" value="<%= id %>">
                        <button type="submit" class="btn rimuovi">Rimuovi</button>
                    </form>
                </div>
            </div>
        <% } %>
    </div>

    <div class="carrello-totale">
        <p><strong>Totale: €<%= String.format("%.2f", carrello.getTotale()) %></strong></p>

        <form action="SvuotaCarrelloServlet" method="post" class="inline-form">
            <button type="submit" class="btn danger">Svuota Carrello</button>
        </form>

        <form action="confermaOrdine" method="post" class="inline-form">
            <button type="submit" class="btn conferma">Conferma Ordine</button>
        </form>
    </div>

    <% } %>
</div>

<!-- FOOTER -->
<jsp:include page="fragments/footer.jsp" />

</body>
</html>
