<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.ProdottoBean, java.math.BigDecimal" %>

<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div class="cart-container" style="padding: 20px; max-width: 1200px; margin: 0 auto; display: flex; gap: 20px;">
    <div class="cart-left" style="flex: 3; background: white; padding: 20px;">
        <h2>Carrello</h2>
        <% String cartError = (String) request.getAttribute("error"); %>
        <% if (cartError != null) { %>
            <div style="background: #fdf0f0; border: 1px solid #d00; color: #b12704; padding: 10px; border-radius: 4px; margin-bottom: 15px;">
                <%= cartError %>
            </div>
        <% } %>
        <hr>
        <%
            @SuppressWarnings("unchecked")
            List<ProdottoBean> cartList = (List<ProdottoBean>) session.getAttribute("cart");
            BigDecimal grandTotal = BigDecimal.ZERO;
            int totalItems = 0;
            if (cartList != null && !cartList.isEmpty()) {
                for (ProdottoBean item : cartList) {
                    BigDecimal itemTotal = item.getPrezzo().multiply(new BigDecimal(item.getQuantita()));
                    grandTotal = grandTotal.add(itemTotal);
                    totalItems += item.getQuantita();
        %>
            <div class="cart-item" style="display: flex; gap: 20px; padding: 15px 0; border-bottom: 1px solid #ddd;">
                <img src="<%= item.getFullImagePath(request.getContextPath()) %>" style="width: 100px; height: 100px; object-fit: contain;">
                <div style="flex-grow: 1;">
                    <h3><%= item.getDescrizione() %></h3>
                    <p style="color: green; font-size: 12px;">Disponibilità immediata</p>
                    <div style="display: flex; align-items: center; gap: 10px;">
                        <form action="<%=request.getContextPath()%>/CartControl" method="get" style="display: inline;" class="cart-update-form">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="id" value="<%= item.getIdProdotto() %>">
                            <input type="hidden" name="token" value="<%= (String) session.getAttribute("token") %>">
                            Qtà:
                            <input type="number"
                                   name="quantity"
                                   value="<%= item.getQuantita() %>"
                                   min="0"
                                   style="width: 50px;"
                                   class="cart-qty-input"
                                   data-id="<%= item.getIdProdotto() %>"
                                   data-unit-price="<%= item.getPrezzo() %>">
                        </form>
                        | <a href="<%=request.getContextPath()%>/CartControl?action=remove&id=<%= item.getIdProdotto() %>&token=<%= (String) session.getAttribute("token") %>" style="color: #007185; text-decoration: none; font-size: 12px;">Rimuovi</a>
                    </div>
                </div>
                <div style="text-align: right;">
                    <p style="font-weight: bold; font-size: 18px;" id="item-price-<%= item.getIdProdotto() %>">€<%= itemTotal %></p>
                </div>
            </div>
        <%
                }
            } else {
        %>
            <p>Il tuo carrello è vuoto.</p>
        <%
            }
        %>
    </div>

    <div class="cart-right" style="flex: 1; background: white; padding: 20px; height: fit-content; display: <%= (cartList != null && !cartList.isEmpty()) ? "block" : "none" %>;">
        <p style="font-size: 18px;" id="cart-grand-total">Subtotale (<%= totalItems %> articoli): <strong>€<%= grandTotal %></strong></p>
        <form action="<%=request.getContextPath()%>/OrderControl" method="get">
            <input type="hidden" name="action" value="checkout">
            <input type="hidden" name="token" value="<%= (String) session.getAttribute("token") %>">
            <button type="submit" style="width: 100%; background: #ffd814; border: 1px solid #fcd200; border-radius: 8px; padding: 10px; cursor: pointer; font-weight: bold;">Procedi all'ordine</button>
        </form>
        <hr style="margin: 15px 0;">
        <a href="<%=request.getContextPath()%>/CartControl?action=clear&token=<%= (String) session.getAttribute("token") %>" style="display: block; text-align: center; color: #007185; text-decoration: none; font-size: 13px;">Svuota carrello</a>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
