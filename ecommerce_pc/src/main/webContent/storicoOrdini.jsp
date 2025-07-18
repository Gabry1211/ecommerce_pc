<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Ordine" %>
<%@ page import="model.OrdineDAO" %>
<%@ page import="model.Cliente" %>
<%@ page import="model.ElementoOrdine" %>
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
    <link rel="stylesheet" href="styles/style.css">
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
                <%
    				String metodo = o.getMetodoPagamento();
    				String numeroCarta = o.getNumeroCarta();

    				if (metodo != null && metodo.equalsIgnoreCase("Carta di Credito")) {
        			if (numeroCarta != null && numeroCarta.length() >= 4) {
				%>
            	<p><strong>Carta:</strong> **** **** **** <%= numeroCarta.substring(numeroCarta.length() - 4) %></p>
				<%
        			} else {
				%>
            	<p><strong>Carta:</strong> Nessuna</p>
				<%
        			}
    				} else if (metodo != null && metodo.equalsIgnoreCase("PayPal")) {
        			String email = o.getEmailPaypal();
				%>
        		<p><strong>Email PayPal:</strong> <%= (email != null ? email : "Nessuna") %></p>
				<%
    				}
				%>
				<%
    				List<ElementoOrdine> prodottiOrdine = ordineDAO.doRetrieveProdottiByOrdine(o.getIdOrdine());
				%>
				<div class="prodotti-ordine">
    			<p><strong>Prodotti ordinati:</strong></p>
    			<ul>
        		<% for (ElementoOrdine eo : prodottiOrdine) { %>
            	<li>
                <%= eo.getProdotto().getDescrizione() %> -
                <%= eo.getQuantita() %> pezzi -
                €<%= eo.getPrezzo() %> (cadauno)
            </li>
        	<% } %>
    </ul>
</div>
            </div>
        </div>
    <%
            }
        }
    %>
</div>
</body>
</html>
