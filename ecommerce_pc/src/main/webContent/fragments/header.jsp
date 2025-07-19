<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="model.*" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8" />
    <title>TechZone - Header</title>
    <link rel="stylesheet" href="styles/style.css" />
    <!-- Puoi aggiungere altri link CSS o script qui -->
</head>
<body>

<header>
  <nav class="navbar">
    <div class="logo">
      <a href="index.jsp">TechZone</a>
    </div>

<div class="nav-buttons">
    <a href="index.jsp">Home</a>

    <%
        String currentPage = request.getRequestURI();
        boolean showLogout = currentPage.contains("clienteHome.jsp") ||
                             currentPage.contains("venditoreHome.jsp") ||
                             currentPage.contains("adminHome.jsp");
    %>

    <% if (showLogout) { %>
        <a href="LogoutServlet">Esci</a>
    <% }
    	Boolean mostraLogin = (Boolean) request.getAttribute("mostraLogin");
    	if (mostraLogin != null && mostraLogin) {
	%>
    	<a href="login.jsp">Accedi</a>
    	<a href="registrazione.jsp">Registrati</a>
	<%
    	}
	%>

    <a href="carrello.jsp">🛒 Carrello (<span id="carrelloCounter">
    	<% 
        	model.Carrello carrello = (model.Carrello) session.getAttribute("carrello");
        	out.print(carrello != null ? carrello.getTotaleQuantita() : 0);
    	%>
	</span>)</a>
	
		<%
    		Cliente cliente = (Cliente) session.getAttribute("cliente");
    		String currentPage1 = request.getRequestURI();
    		if (cliente != null && currentPage1.contains("clienteHome.jsp")) {
		%>
    <a href="storicoOrdini.jsp">Storico Ordini</a>
		<%
    		}
		%>
</div>

  </nav>
</header>

<!-- Il resto della pagina -->
</body>
</html>