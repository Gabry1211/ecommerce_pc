<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.*, java.util.*" %>
<%@ page session="true" %>

<%
    // Controllo login cliente
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
    <title>Dettagli prodotto - <%= prodotto.getDescrizione() %></title>
    <link rel="stylesheet" href="styles/style.css">
</head>
<body>
<jsp:include page="fragments/header.jsp" />

<div class="dettagli-prodotto">
    <h2><%= prodotto.getDescrizione() %></h2>
    <img src="ImmagineServlet?file=<%= prodotto.getImmagine() %>" alt="Immagine prodotto" style="max-width:300px; max-height:300px;">
    <p><strong>Prezzo:</strong> €<%= String.format("%.2f", prodotto.getPrezzo()) %></p>
    <p><strong>Tipo:</strong> <%= prodotto.getTipo() %></p>

    <form action="AggiungiAlCarrelloServlet" method="post">
        <input type="hidden" name="idProdotto" value="<%= prodotto.getIdProdotto() %>">
        <input type="hidden" name="descrizione" value="<%= prodotto.getDescrizione() %>">
        <input type="hidden" name="prezzo" value="<%= prodotto.getPrezzo() %>">
        <input type="hidden" name="tipo" value="<%= prodotto.getTipo() %>">
        <input type="hidden" name="immagine" value="<%= prodotto.getImmagine() %>">
        <input type="hidden" name="cfAdmin" value="<%= prodotto.getCfAdmin() %>">
        <input type="hidden" name="idVenditore" value="<%= prodotto.getIdVenditore() %>">

        <label for="quantita">Quantità:</label>
        <input type="number" name="quantita" id="quantita" value="1" min="1" required>

        <button type="submit">Aggiungi al carrello</button>
    </form>
</div>

<jsp:include page="fragments/footer.jsp" />
</body>
</html>
