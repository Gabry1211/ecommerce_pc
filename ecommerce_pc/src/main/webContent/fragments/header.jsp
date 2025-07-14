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
        <div class="logo">
            <a href="index.jsp">TechZone</a>
        </div>
        
        <div class="search-bar">
            <form action="RicercaProdottiServlet" method="get">
                <input type="text" name="query" placeholder="Cerca prodotti..." />
                <button type="submit">Cerca</button>
            </form>
        </div>

        <nav class="nav-links">
            <a href="index.jsp">Home</a>
            <a href="VisualizzaProdottiServlet">Prodotti</a>
            <a href="carrello.jsp">Carrello</a>
            <a href="login.jsp">Accedi</a>
        </nav>
    </header>

    <main class="main-container">
