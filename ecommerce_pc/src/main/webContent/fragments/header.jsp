<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

    <div class="search-bar">
      <form action="RicercaProdottiServlet" method="get">
        <input type="text" name="query" placeholder="Cerca prodotti..." required />
        <button type="submit">Cerca</button>
      </form>
    </div>

<div class="nav-buttons">
    <a href="home.jsp">Home</a>

    <%
        String currentPage = request.getRequestURI();
        boolean showLogout = currentPage.contains("clienteHome.jsp") ||
                             currentPage.contains("venditoreHome.jsp") ||
                             currentPage.contains("adminHome.jsp");
    %>

    <% if (showLogout) { %>
        <a href="LogoutServlet">Esci</a>
    <% } else { %>
        <a href="login.jsp">Accedi</a>
        <a href="registrazione.jsp">Registrati</a>
    <% } %>

    <a href="carrello.jsp">Carrello</a>
</div>

  </nav>

  <div class="categories-menu">
    <a href="categoria.jsp?cat=componenti">Componenti</a>
    <a href="categoria.jsp?cat=pc_fissi">PC Fissi</a>
    <a href="categoria.jsp?cat=accessori">Accessori</a>
    <a href="categoria.jsp?cat=offerte">Offerte</a>
    <a href="categoria.jsp?cat=novita">Novità</a>
  </div>
</header>

<!-- Il resto della pagina -->
</body>
</html>