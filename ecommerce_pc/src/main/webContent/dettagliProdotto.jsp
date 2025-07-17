<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.*, java.util.*" %>
<%@ page session="true" %>

<%
    session = request.getSession(false);
    if (session == null || session.getAttribute("cliente") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String idParam = request.getParameter("id");
    if (idParam == null) {
        out.println("Prodotto non trovato.");
        return;
    }

    int idProdotto = 0;
    try {
        idProdotto = Integer.parseInt(idParam);
    } catch (NumberFormatException e) {
        out.println("ID prodotto non valido.");
        return;
    }

    ProdottoDAO prodottoDAO = new ProdottoDAO();
    Prodotto prodotto = prodottoDAO.doRetrieveById(idProdotto);

    if (prodotto == null) {
        out.println("Prodotto non trovato.");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= prodotto.getDescrizione() %> | Dettagli Prodotto</title>
    <link rel="stylesheet" href="styles/style.css">
    <script>
    function aggiungiAlCarrello(event) {
        event.preventDefault(); // ⚠️ Blocca l'invio del form

        const form = document.getElementById("formCarrello");
        const idProdotto = form.querySelector("input[name='idProdotto']").value;
        const quantita = form.querySelector("input[name='quantita']").value;

        fetch("AggiungiAlCarrelloAjaxServlet", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: "idProdotto=" + encodeURIComponent(idProdotto) + "&quantita=" + encodeURIComponent(quantita)
        })
        .then(response => {
            if (!response.ok) throw new Error("Errore nell'aggiunta al carrello");
            return response.text();
        })
        .then(data => {
            // Aggiorna il contatore del carrello (supponiamo sia nel tag con id="cart-count")
            document.getElementById("cart-count").innerText = data;
        })
        .catch(error => {
            console.error(error);
        });

        return false;
    }
</script>
</head>
<body>
<jsp:include page="fragments/header.jsp" />

<div class="container-prodotto">
    <div class="img-box">
        <img src="ImmagineServlet?file=<%= prodotto.getImmagine() %>" alt="Immagine prodotto">
    </div>

    <div class="info-box">
        <h1><%= prodotto.getDescrizione() %></h1>
        <p class="tipo">Tipo: <%= prodotto.getTipo() %></p>
        <p class="prezzo">Prezzo: €<%= String.format("%.2f", prodotto.getPrezzo()) %></p>
        <p class="disponibilita">Disponibilità: <%= prodotto.getQuantita() %> pezzi</p>

        <form id="formCarrello" class="form-carrello" onsubmit="return aggiungiAlCarrello(event)">
    		<input type="hidden" name="idProdotto" value="<%= prodotto.getIdProdotto() %>">
    		<input type="hidden" name="descrizione" value="<%= prodotto.getDescrizione() %>">
    		<input type="hidden" name="prezzo" value="<%= prodotto.getPrezzo() %>">
    		<input type="hidden" name="tipo" value="<%= prodotto.getTipo() %>">
    		<input type="hidden" name="immagine" value="<%= prodotto.getImmagine() %>">
    		<input type="hidden" name="cfAdmin" value="<%= prodotto.getCfAdmin() %>">
    		<input type="hidden" name="idVenditore" value="<%= prodotto.getIdVenditore() %>">

    		<label for="quantita">Quantità:</label>
    		<input type="number" id="quantita" name="quantita" value="1" min="1" max="<%= prodotto.getQuantita() %>" required>

    		<button type="submit">Aggiungi al carrello</button>
		</form>
    </div>
</div>

<jsp:include page="fragments/footer.jsp" />
</body>
</html>
