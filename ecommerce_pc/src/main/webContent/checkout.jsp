<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ page import="model.*" %>
<%
    Cliente cliente = (Cliente) session.getAttribute("cliente");
    if (cliente == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout</title>
    <link rel="stylesheet" href="styles/style.css">
    <script src="scripts/paymentForm.js"></script>
</head>
<body>
<jsp:include page="fragments/header.jsp" />

<div class="checkout-container">
    <h2>Informazioni di Spedizione e Pagamento</h2>
    <form action="ConfermaOrdineServlet" method="post">
        <label for="indirizzo">Indirizzo di spedizione:</label>
        <input type="text" name="indirizzo" required>

        <label for="citta">Città:</label>
        <input type="text" name="citta" required>

        <label for="cap">CAP:</label>
        <input type="text" name="cap" required>

        <label>Metodo di pagamento:</label><br>
  		<input type="radio" id="carta" name="metodoPagamento" value="carta" onclick="aggiornaForm()" checked>
  		<label for="carta">Carta di credito</label><br>
  		<input type="radio" id="paypal" name="metodoPagamento" value="paypal" onclick="aggiornaForm()">
  		<label for="paypal">PayPal</label><br>

  		<!-- Campi carta di credito -->
  		<div id="cartaCreditoFields">
    		<label for="numeroCarta">Numero Carta:</label>
    		<input type="text" id="numeroCarta" name="numeroCarta" maxlength="16" pattern="\d{16}" required><br>

    		<label for="scadenzaCarta">Data scadenza (MM/AA):</label>
    		<input type="text" id="scadenzaCarta" name="scadenzaCarta" placeholder="MM/AA" pattern="(0[1-9]|1[0-2])\/\d{2}" required><br>

    		<label for="cvv">CVV:</label>
    		<input type="text" id="cvv" name="cvv" maxlength="3" pattern="\d{3}" required><br>
  		</div>

  		<!-- Campi PayPal -->
  		<div id="paypalFields">
    		<label for="emailPaypal">Email PayPal:</label>
    		<input type="email" id="emailPaypal" name="emailPaypal"><br>
  		</div>

  		<input type="submit" value="Conferma ordine">
	</form>
</div>

<jsp:include page="fragments/footer.jsp" />
</body>
</html>
    