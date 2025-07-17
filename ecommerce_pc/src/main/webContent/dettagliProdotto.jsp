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
    function aggiungiAlCarrello(button) {
        const idProdotto = button.getAttribute("data-id");
        const descrizione = button.getAttribute("data-descrizione");
        const prezzo = button.getAttribute("data-prezzo");
        const tipo = button.getAttribute("data-tipo");
        const immagine = button.getAttribute("data-immagine");
        const cfAdmin = button.getAttribute("data-cfadmin");
        const idVenditore = button.getAttribute("data-idvenditore");
        const quantita = document.getElementById("quantita").value;

        const params = new URLSearchParams();
        params.append("idProdotto", idProdotto);
        params.append("descrizione", descrizione);
        params.append("prezzo", prezzo);
        params.append("tipo", tipo);
        params.append("immagine", immagine);
        params.append("cfAdmin", cfAdmin);
        params.append("idVenditore", idVenditore);
        params.append("quantita", quantita);

        fetch("AggiungiAlCarrelloAjaxServlet", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: params
        })
        .then(response => {
            if (!response.ok) throw new Error("Errore AJAX");
            return response.text();
        })
        .then(nuovaQuantita => {
            document.getElementById("carrelloCounter").innerText = nuovaQuantita;
        })
        .catch(error => {
            console.error("Errore:", error);
        });
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

        <div class="aggiunta-carrello-container">
    	<label for="quantita">Quantità:</label>
    	<input type="number" id="quantita" value="1" min="1" max="<%= prodotto.getQuantita() %>" required>

    	<button type="button"
        	onclick="aggiungiAlCarrello(this)"
        	data-id="<%= prodotto.getIdProdotto() %>"
        	data-descrizione="<%= prodotto.getDescrizione() %>"
        	data-prezzo="<%= prodotto.getPrezzo() %>"
        	data-tipo="<%= prodotto.getTipo() %>"
        	data-immagine="<%= prodotto.getImmagine() %>"
        	data-cfadmin="<%= prodotto.getCfAdmin() %>"
        	data-idvenditore="<%= prodotto.getIdVenditore() %>">
        	Aggiungi al carrello
    	</button>
	</div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp" />
</body>
</html>
