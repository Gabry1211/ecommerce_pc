<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
<%@ include file="fragments/header.jsp" %>

<h2>Login Venditore</h2>2
<form action="LoginVenditoreServlet" method="post">
  	<label>Partita IVA:</label>
    <input type="text" name="partitaIVA" required><br>

    <label>Password:</label>
    <input type="password" name="password" required><br>

    <input type="submit" value="Login">
</form>
</body>
</html>

<%@ include file="fragments/footer.jsp" %>
