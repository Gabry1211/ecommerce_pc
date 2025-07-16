<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8" />
    <title>TechZone - Header</title>
    <link rel="stylesheet" href="css/header.css" />
    <!-- Puoi aggiungere altri link CSS o script qui -->
</head>
<body>

<header>
  <nav class="navbar">
    <div class="logo">
      <a href="home.jsp">TechZone</a>
    </div>

    <div class="search-bar">
      <form action="RicercaProdottiServlet" method="get">
        <input type="text" name="query" placeholder="Cerca prodotti..." required />
        <button type="submit">Cerca</button>
      </form>
    </div>

    <div class="nav-buttons">
      <a href="home.jsp">Home</a>
      
      <c:choose>
        <c:when test="${not empty sessionScope.utente}">
          <a href="profilo.jsp">Profilo</a>
          <a href="LogoutServlet">Esci</a>
        </c:when>
        <c:otherwise>
          <a href="login.jsp">Accedi</a>
          <a href="registrazione.jsp">Registrati</a>
        </c:otherwise>
      </c:choose>

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