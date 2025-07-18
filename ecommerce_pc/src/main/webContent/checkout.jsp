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

        <label for="metodoPagamento">Metodo di pagamento:</label>
        <select name="metodoPagamento" required>
            <option value="Carta di credito">Carta di credito</option>
            <option value="PayPal">PayPal</option>
            <option value="Contrassegno">Contrassegno</option>
        </select>

        <button type="submit">Conferma Acquisto</button>
    </form>
</div>

<jsp:include page="fragments/footer.jsp" />
</body>
</html>
    