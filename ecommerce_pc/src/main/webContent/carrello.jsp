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
    <script>
    function aggiornaQuantita(idProdotto) {
        var quantitaInput = document.getElementById("qta-" + idProdotto);
        if (!quantitaInput) {
            console.error("Elemento input quantità non trovato per idProdotto: " + idProdotto);
            return;
        }
        var nuovaQuantita = quantitaInput.value;

        // Esempio: AJAX per aggiornare quantità nel carrello (usa XMLHttpRequest o fetch)
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "AggiornaQuantitaAjaxServlet", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                // Puoi gestire risposta, aggiornare UI ecc.
                console.log("Quantità aggiornata con successo");
                location.reload();  // o aggiornare dinamicamente senza ricaricare
            }
        };

        xhr.send("idProdotto=" + encodeURIComponent(idProdotto) + "&quantita=" + encodeURIComponent(nuovaQuantita));
    }
    
    function rimuoviProdotto(idProdotto) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "RimuoviDalCarrelloServlet", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                // Rimuove direttamente l'elemento dal DOM
                var item = document.getElementById("item-" + idProdotto);
                if (item) item.remove();

                // Aggiorna il totale oppure ricarica
                location.reload();
            }
        };

        xhr.send("idProdotto=" + encodeURIComponent(idProdotto));
    }
    function svuotaCarrello() {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "SvuotaCarrelloAjaxServlet", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                // Rimuove tutti gli elementi dal DOM e aggiorna il totale
                document.querySelector(".carrello-items").innerHTML = "<h3>Il carrello è vuoto</h3>";
                document.querySelector(".carrello-totale").innerHTML = "";
            }
        };

        xhr.send(); // Nessun parametro necessario
    }
</script>
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
