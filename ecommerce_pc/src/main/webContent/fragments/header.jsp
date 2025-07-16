<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TechZone</title>
    <link rel="stylesheet" href="styles/style.css">
</head>
<body>
<header class="navbar">
    <div class="navbar-left">
        <div class="logo">
            <a href="index.jsp">TechZone</a>
        </div>
        <div class="register-link">
            <a href="registrazione.jsp">Registrati</a>
        </div>
    </div>

    <%
        Boolean nascondi = (Boolean) request.getAttribute("nascondiBarraRicerca");
        if (nascondi == null || !nascondi) {
    %>
    <div class="search-bar">
        <form action="RicercaProdottiServlet" method="get">
            <input type="text" name="query" placeholder="Cerca prodotti..." />
            <button type="submit">Cerca</button>
        </form>
    </div>
    <% } %>

    <nav class="nav-links">
        <a href="index.jsp">Home</a>
        
        <div class="dropdown">
            <a href="#">Categorie ▼</a>
            <div class="dropdown-content">
                <a href="VisualizzaCategoriaServlet?tipo=PC">PC Assemblati</a>
                <a href="VisualizzaCategoriaServlet?tipo=SSD">SSD</a>
                <a href="VisualizzaCategoriaServlet?tipo=GPU">Schede Video</a>
                <a href="VisualizzaCategoriaServlet?tipo=RAM">RAM</a>
                <a href="VisualizzaCategoriaServlet?tipo=Alimentatore">Alimentatori</a>
            </div>
        </div>

        <a href="VisualizzaUltimiServlet">Novità</a>
        <a href="carrello.jsp">Carrello</a>
        <a href="login.jsp">Accedi</a>
    </nav>
</header>

<main class="main-container">