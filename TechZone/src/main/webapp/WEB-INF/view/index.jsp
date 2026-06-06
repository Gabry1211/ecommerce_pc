<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.ProdottoBean" %>

<%
    @SuppressWarnings("unchecked")
    Collection<ProdottoBean> products = (Collection<ProdottoBean>) request.getAttribute("products");
%>

<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div class="banner">
    <div style="background: linear-gradient(to bottom, rgba(0,0,0,0.3), #eaeded), url('https://m.media-amazon.com/images/I/61Bvx6VuPML._SX3000_.jpg'); height: 300px; background-size: cover;"></div>
</div>

<div class="product-grid">
    <%
        if (products != null && !products.isEmpty()) {
            for (ProdottoBean product : products) {
    %>
        <div class="product-card">
            <a href="<%=request.getContextPath()%>/ProductControl?action=detail&id=<%= product.getIdProdotto() %>" style="text-decoration: none; color: inherit;">
                <div class="product-image-wrapper" style="height: 200px; display: flex; align-items: center; justify-content: center; overflow: hidden;">
                    <img src="<%= product.getFullImagePath(request.getContextPath()) %>"
                         alt="<%= product.getDescrizione() %>"
                         style="max-width: 100%; max-height: 100%; object-fit: contain; transition: transform 0.3s;">
                </div>
                <h3 class="product-title"><%= product.getDescrizione() %></h3>
            </a>
            <p class="product-price" style="font-size: 18px; font-weight: bold; color: #B12704;">€<%= product.getPrezzo() %></p>
            <p class="product-type" style="color: #565959; font-size: 12px; margin-bottom: 10px;"><%= product.getTipo() %></p>
            <form action="<%=request.getContextPath()%>/CartControl" method="get">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="id" value="<%= product.getIdProdotto() %>">
                <input type="hidden" name="token" value="<%= (String) session.getAttribute("token") %>">
                <% if (product.getQuantita() > 0) { %>
                    <button type="submit" class="btn-add-cart" style="width: 100%; background: #ffd814; border: 1px solid #fcd200; border-radius: 20px; padding: 7px; cursor: pointer; font-weight: 500;">Aggiungi al carrello</button>
                <% } else { %>
                    <button disabled style="width: 100%; background: #e7e9ec; border: 1px solid #adb1b8; border-radius: 20px; padding: 7px; color: #8d9096; cursor: not-allowed;">Esaurito</button>
                <% } %>
            </form>
        </div>
    <%
            }
        } else {
    %>
        <p>Nessun prodotto disponibile.</p>
    <%
        }
    %>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
