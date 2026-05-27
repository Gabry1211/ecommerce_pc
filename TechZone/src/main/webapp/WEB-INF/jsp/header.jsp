<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.ClienteBean, model.ProdottoBean, java.util.List, java.util.UUID" %>
<%
    ClienteBean user = (ClienteBean) session.getAttribute("user");
    Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
    Boolean isSeller = (Boolean) session.getAttribute("isSeller");

    String token = (String) session.getAttribute("token");
    if (token == null || token.isEmpty()) {
        token = UUID.randomUUID().toString();
        session.setAttribute("token", token);
    }
    
    List<ProdottoBean> cart = null;
    try {
        @SuppressWarnings("unchecked")
        List<ProdottoBean> tempCart = (List<ProdottoBean>) session.getAttribute("cart");
        cart = tempCart;
    } catch (Exception e) {
        // Se c'è un errore di cast (es. sessione vecchia), puliamo il carrello
        session.removeAttribute("cart");
    }
    
    int cartCount = 0;
    if (cart != null) {
        for (ProdottoBean item : cart) {
            cartCount += item.getQuantita();
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/main.css">
    <title>TechZone - PC & Componenti</title>
</head>
<body>
    <header class="amazon-header">
        <div class="header-left">
            <a href="<%=request.getContextPath()%>/Home" class="logo">TechZone</a>
        </div>
        
        <div class="header-search" style="position: relative;">
            <form action="<%=request.getContextPath()%>/ProductControl" method="get" style="display: flex; width: 100%;">
                <input type="hidden" name="action" value="search">
                <input type="text" name="q" placeholder="Cerca PC o componenti..." style="flex-grow: 1;" autocomplete="off">
                <button type="submit">🔍</button>
            </form>
            <div id="search-suggestions" style="position: absolute; top: 100%; left: 0; right: 0; background: white; border: 1px solid #ddd; border-top: none; z-index: 1001; display: none; box-shadow: 0 4px 8px rgba(0,0,0,0.1); border-radius: 0 0 4px 4px;"></div>
        </div>
        
        <div class="header-right">
            <div class="nav-item">
                <% if (user != null) { %>
                    <span class="nav-line-1">Ciao, <%= user.getNome() %></span>
                    <a href="<%=request.getContextPath()%>/LoginControl?action=logout" class="nav-line-2">Logout</a>
                <% } else if (isAdmin != null && isAdmin) { %>
                    <span class="nav-line-1">Admin Panel</span>
                    <a href="<%=request.getContextPath()%>/LoginControl?action=logout" class="nav-line-2">Logout</a>
                <% } else if (isSeller != null && isSeller) { %>
                    <span class="nav-line-1">Seller Panel</span>
                    <a href="<%=request.getContextPath()%>/LoginControl?action=logout" class="nav-line-2">Logout</a>
                <% } else { %>
                    <span class="nav-line-1">Ciao, Accedi</span>
                    <a href="<%=request.getContextPath()%>/LoginControl" class="nav-line-2">Account e liste</a>
                <% } %>
            </div>
            
            <a href="<%=request.getContextPath()%>/OrderControl" class="nav-item">
                <span class="nav-line-1">Resi</span>
                <span class="nav-line-2">e Ordini</span>
            </a>
            
            <a href="<%=request.getContextPath()%>/CartControl" class="nav-item cart-icon">
                <span class="cart-count"><%= cartCount %></span>
                <span class="nav-line-2">Carrello</span>
            </a>
            
            <% if (isAdmin != null && isAdmin) { %>
                <a href="<%=request.getContextPath()%>/AdminControl?action=dashboard" class="nav-item">
                    <span class="nav-line-1">Gestione</span>
                    <span class="nav-line-2">Admin</span>
                </a>
            <% } %>

            <% if (isSeller != null && isSeller) { %>
                <a href="<%=request.getContextPath()%>/VenditoreControl?action=dashboard" class="nav-item">
                    <span class="nav-line-1">Negozio</span>
                    <span class="nav-line-2">Venditore</span>
                </a>
            <% } %>
        </div>
    </header>
    <main>
