<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.ProdottoBean" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<%
    ProdottoBean product = (ProdottoBean) request.getAttribute("product");
    if (product == null) {
        response.sendRedirect(request.getContextPath() + "/Home");
        return;
    }
%>

<div class="product-detail-container" style="padding: 20px; max-width: 1200px; margin: 0 auto; display: flex; gap: 40px; background: white; margin-top: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);">
    <div class="product-image-section" style="flex: 1; text-align: center;">
        <img src="<%= product.getFullImagePath(request.getContextPath()) %>"
             alt="<%= product.getDescrizione() %>"
             style="max-width: 100%; max-height: 500px; object-fit: contain;">
    </div>

    <div class="product-info-section" style="flex: 1.5;">
        <h1 style="font-size: 28px; margin-bottom: 10px; color: #0f1111;"><%= product.getDescrizione() %></h1>
        <p style="color: #007185; font-size: 14px; margin-bottom: 20px;">Marca: TechZone Official</p>

        <hr style="border: 0; border-top: 1px solid #e7e7e7; margin: 20px 0;">

        <div class="price-tag" style="margin-bottom: 20px;">
            <span style="font-size: 14px; vertical-align: top; margin-top: 4px; display: inline-block;">€</span>
            <span style="font-size: 28px; font-weight: 500;"><%= product.getPrezzo() %></span>
        </div>

        <div class="availability" style="margin-bottom: 20px;">
            <% if (product.getQuantita() > 0) { %>
                <p style="color: #007600; font-size: 18px; font-weight: bold;">Disponibilità immediata</p>
                <p style="font-size: 14px; color: #565959;">Quantità disponibile: <%= product.getQuantita() %></p>
            <% } else { %>
                <p style="color: #B12704; font-size: 18px; font-weight: bold;">Prodotto non disponibile</p>
            <% } %>
            <p style="font-size: 14px; color: #565959;">Transazione sicura</p>
        </div>

        <div class="product-specs" style="margin-bottom: 30px;">
            <h3 style="font-size: 16px; margin-bottom: 10px;">Informazioni su questo articolo:</h3>
            <ul style="padding-left: 20px; font-size: 14px; line-height: 1.6; color: #0f1111;">
                <li>Categoria: <%= product.getTipo() %></li>
                <li>Garanzia ufficiale italiana di 24 mesi</li>
                <li>Spedizione veloce e imballaggio eco-friendly</li>
                <li>Prodotto selezionato per alte prestazioni</li>
            </ul>
        </div>

        <div class="action-buttons" style="background: #f3f3f3; padding: 20px; border-radius: 8px; border: 1px solid #ddd; max-width: 300px;">
            <% if (product.getQuantita() > 0) { %>
                <form action="<%=request.getContextPath()%>/CartControl" method="get">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="id" value="<%= product.getIdProdotto() %>">
                    <input type="hidden" name="token" value="<%= (String) session.getAttribute("token") %>">

                    <div style="margin-bottom: 15px;">
                        <label for="quantity">Quantità:</label>
                        <select name="quantity" id="quantity" style="padding: 5px; border-radius: 4px;">
                            <% for(int i=1; i<=Math.min(10, product.getQuantita()); i++) { %>
                                <option value="<%= i %>"><%= i %></option>
                            <% } %>
                        </select>
                    </div>

                    <button type="submit" class="btn-add-cart" style="width: 100%; background: #ffd814; border: 1px solid #fcd200; border-radius: 20px; padding: 10px; cursor: pointer; font-weight: bold; margin-bottom: 10px; transition: background 0.2s;">Aggiungi al carrello</button>
                </form>
                <button style="width: 100%; background: #ffa41c; border: 1px solid #ff8f00; border-radius: 20px; padding: 10px; cursor: pointer; font-weight: bold; transition: background 0.2s;">Acquista ora</button>
            <% } else { %>
                <p style="text-align: center; color: #565959; font-weight: bold;">Attualmente non disponibile</p>
                <button disabled style="width: 100%; background: #e7e9ec; border: 1px solid #adb1b8; border-radius: 20px; padding: 10px; color: #8d9096; cursor: not-allowed; margin-bottom: 10px;">Aggiungi al carrello</button>
            <% } %>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
