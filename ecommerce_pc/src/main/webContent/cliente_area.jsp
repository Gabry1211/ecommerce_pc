<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String utente = (String) session.getAttribute("utente");
    if (utente == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<html>
<head><title>Area Cliente</title></head>
<body>
    <h2>Benvenuto, <%= utente %></h2>
    <a href="LogoutServlet">Logout</a>
</body>
</html>