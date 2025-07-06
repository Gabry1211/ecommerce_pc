<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
<%@ include file="fragments/header.jsp" %>

<h2>Login Venditore</h2>2
<form action="LoginVenditoreServlet" method="post">
    	Partita IVA: <input type="text" name="partitaIVA" required><br>
        Codice Fiscale: <input type="text" name="codiceFiscale" required><br>
        <input type="submit" value="Login">
</form>
</body>
</html>

<%@ include file="fragments/footer.jsp" %>
