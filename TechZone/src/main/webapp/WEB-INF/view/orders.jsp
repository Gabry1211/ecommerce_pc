<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.OrdineBean" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div class="orders-container" style="padding: 20px; max-width: 1000px; margin: 0 auto;">
    <% if ("true".equals(request.getParameter("success"))) { %>
        <div style="background: #e7f4e4; border: 1px solid #007600; color: #007600; padding: 15px; border-radius: 8px; margin-bottom: 20px;">
            <h3 style="margin: 0;">Ordine effettuato con successo!</h3>
            <p style="margin: 5px 0 0 0;">Il tuo pacco arriverà presto.</p>
        </div>
    <% } %>
    <h2>I miei ordini</h2>

    <%
        @SuppressWarnings("unchecked")
        Collection<OrdineBean> orders = (Collection<OrdineBean>) request.getAttribute("orders");
        if (orders == null) {
            response.sendRedirect(request.getContextPath() + "/OrderControl");
            return;
        }

        if (!orders.isEmpty()) {
            for (OrdineBean order : orders) {
    %>
        <div class="order-card" style="background: white; border: 1px solid #ddd; border-radius: 8px; margin-bottom: 20px; overflow: hidden;">
            <div class="order-header" style="background: #f0f2f2; padding: 15px; display: flex; justify-content: space-between; font-size: 12px; color: #565959;">
                <div>
                    <p>ORDINE EFFETTUATO IL</p>
                    <p style="color: black;"><%= (order.getDataOrdine() != null) ? order.getDataOrdine() : "N/D" %></p>
                </div>
                <div>
                    <p>TOTALE</p>
                    <p style="color: black;">€<%= order.getTotOrdine() %></p>
                </div>
                <div>
                    <p>ORDINE # <%= order.getIdOrdine() %></p>
                </div>
            </div>
            <div class="order-body" style="padding: 15px;">
                <p><strong>Prodotti:</strong> <%= order.getListaProdotti() %></p>
            </div>
        </div>
    <%
            }
        } else {
    %>
        <p>Non hai ancora effettuato ordini.</p>
        <a href="<%=request.getContextPath()%>/Home" style="color: #007185;">Inizia a fare acquisti ora</a>
    <%
        }
    %>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
