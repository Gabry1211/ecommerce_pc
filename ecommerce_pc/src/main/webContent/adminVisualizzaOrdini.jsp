<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.Ordine, model.DettaglioOrdine, model.OrdineDAO, model.ClienteDao, model.Prodotto" %>
<%@ page session="true" %>
<%@ page import="model.Amministratore" %>
<%
    Amministratore admin = (Amministratore) session.getAttribute("admin");
    if (admin == null) {
        response.sendRedirect("loginAdmin.jsp");
        return;
    }

    List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Visualizza Ordini</title>
    <link rel="stylesheet" href="styles/style.css">
</head>
<body>
<jsp:include page="fragments/header.jsp" />

<div class="container">
    <h2>Visualizza Ordini</h2>
    <form method="get" action="VisualizzaOrdiniAdminServlet">
        <label for="dataInizio">Data Inizio:</label>
        <input type="date" name="dataInizio" required>
        <label for="dataFine">Data Fine:</label>
        <input type="date" name="dataFine" required>
        <label for="cfCliente">Codice Fiscale Cliente (facoltativo):</label>
        <input type="text" name="cfCliente" placeholder="ABCDEF12G34H567I">
        <button type="submit">Filtra</button>
    </form>

    <% if (ordini != null && !ordini.isEmpty()) { %>
        <h3>Risultati:</h3>
        <% for (Ordine o : ordini) { %>
            <div class="card-ordine">
                <h3>Ordine ID: <%= o.getIdOrdine() %></h3>
                <p>Data: <%= o.getDataOrdine() %></p>
                <p>Ora: <%= o.getOraOrdine() %></p>
                <p>Cliente: <%= o.getCodiceFiscaleCliente() %></p>
                <ul>
                    <% for (DettaglioOrdine d : o.getDettagli()) { %>
                        <li>
                            <strong>Prodotto ID:</strong> <%= d.getIdProdotto() %>,
                            <strong>Quantità:</strong> <%= d.getQuantita() %>,
                            <strong>Prezzo:</strong> €<%= String.format("%.2f", d.getPrezzo()) %>
                        </li>
                    <% } %>
                </ul>
            </div>
        <% } %>
    <% } else if (request.getParameter("dataInizio") != null) { %>
        <p>Nessun ordine trovato per i criteri selezionati.</p>
    <% } %>
</div>

<jsp:include page="fragments/footer.jsp" />
</body>
</html>
    