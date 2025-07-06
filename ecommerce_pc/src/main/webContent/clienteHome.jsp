<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.*" %>
<!DOCTYPE html>
<%
    HttpSession session2 = request.getSession(false);
    if (session == null || session.getAttribute("cliente") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String codiceFiscale = (String) session.getAttribute("cliente");
%>
<html>
<head>
  <title>Homepage Cliente</title>
</head>
<body>
  <h2>Benvenuto Cliente (Codice Fiscale: <%= codiceFiscale %>)</h2>

  <ul>
    <li><a href="VisualizzaProdottiServlet">Sfoglia prodotti</a></li>
    <li><a href="carrello.jsp">Visualizza carrello</a></li>
    <li><a href="ordini.jsp">I miei ordini</a></li>
    <li><a href="logout.jsp">Logout</a></li>
  </ul>
</body>
</html>
