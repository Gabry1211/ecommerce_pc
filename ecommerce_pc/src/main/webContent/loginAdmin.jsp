<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="fragments/header.jsp" %>

<h2>Login Amministratore</h2>
<form action="LoginAdminServlet" method="post">
    <label for="codiceFiscale">Codice Fiscale:</label>
    <input type="text" id="codiceFiscale" name="codiceFiscale" required><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br>

    <input type="submit" value="Login Admin">
</form>

<%@ include file="fragments/footer.jsp" %>
