<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Inserisci Prodotto</title>
    <link rel="stylesheet" href="styles/style.css">
</head>
<body>
<jsp:include page="fragments/header.jsp" />

<h2>Inserisci nuovo prodotto</h2>

<form action="InserisciProdottoServlet" method="post" enctype="multipart/form-data">
    <label>Descrizione:</label><br>
    <input type="text" name="descrizione" required><br><br>

    <label>Prezzo (€):</label><br>
    <input type="number" name="prezzo" step="0.01" required><br><br>

    <label>Tipo:</label><br>
    <input type="text" name="tipo" required><br><br>

    <label>Quantità:</label><br>
    <input type="number" name="quantita" min="1" required><br><br>

    <label>Immagine del prodotto:</label><br>
    <input type="file" name="immagine" accept="image/*" required><br><br>

    <input type="submit" value="Inserisci prodotto">
</form>

<jsp:include page="fragments/footer.jsp" />
</body>
</html>
