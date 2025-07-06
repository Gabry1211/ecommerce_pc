<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ include file="fragments/header.jsp" %>

<h2>Login Venditore</h2>2
<form action="LoginVenditoreServlet" method="post">
    <label for="idVenditore">ID Venditore:</label>
    <input type="number" id="idVenditore" name="idVenditore" required><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br>

    <input type="submit" value="Login Venditore">
</form>

<%@ include file="fragments/footer.jsp" %>
