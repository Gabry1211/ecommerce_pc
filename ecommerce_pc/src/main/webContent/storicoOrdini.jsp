<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Ordine" %>
<%@ page import="model.OrdineDAO" %>
<%@ page import="model.Cliente" %>
<%
    Cliente cliente = (Cliente) session.getAttribute("cliente");
    if (cliente == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    OrdineDAO ordineDAO = new OrdineDAO();
    List<Ordine> ordini = ordineDAO.doRetrieveByCliente(cliente);
%>
<!DOCTYPE html>
<html>
<head>
    <title>Storico Ordini</title>
    <link rel="stylesheet" href="styles/storicoOrdini.css">
</head>
<body>
<jsp:include page="fragments/header.jsp" />

<div class="storico-container">
    <h1>I tuoi ordini</h1>

    <%
        if (ordini.isEmpty()) {
    %>
        <p>Non hai effettuato alcun ordine.</p>
    <%
        } else {
            for (Ordine o : ordini) {
    %>
        <div class="ordine-card">
            <div class="ordine-info">
                <p><strong>Data:</strong> <%= o.getDataOrdine() %></p>
                <p><strong>Ora:</strong> <%= o.getOraOrdine() %></p>
                <p><strong>Indirizzo:</strong> <%= o.getIndirizzoSpedizione() %>, <%= o.getCitta() %>, <%= o.getCap() %></p>
                <p><strong>Metodo di Pagamento:</strong> <%= o.getMetodoPagamento() %></p>
                <% if (o.getMetodoPagamento().equalsIgnoreCase("Carta di Credito")) { %>
                    <p><strong>Carta:</strong> **** **** **** <%= o.getNumeroCarta().substring(o.getNumeroCarta().length() - 4) %></p>
                <% } else if (o.getMetodoPagamento().equalsIgnoreCase("PayPal")) { %>
                    <p><strong>Email PayPal:</strong> <%= o.getEmailPaypal() %></p>
                <% } %>
            </div>
        </div>
    <%
            }
        }
    %>
</div>
</body>
</html>
