<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.OrdineBean" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div style="padding: 20px; max-width: 1200px; margin: 0 auto;">
    <div style="margin-bottom: 20px;">
        <a href="<%=request.getContextPath()%>/AdminControl?action=dashboard" style="text-decoration: none; color: #007185; font-weight: bold;">&larr; Torna al Pannello Admin</a>
    </div>
    <h2>Visualizzazione Ordini Complessivi</h2>
    
    <div style="background: white; padding: 20px; border: 1px solid #ddd; border-radius: 8px; margin-bottom: 30px;">
        <form action="<%=request.getContextPath()%>/AdminControl" method="get" style="display: flex; gap: 15px; align-items: flex-end;">
            <input type="hidden" name="action" value="viewOrders">
            <div class="form-group" style="margin: 0;">
                <label>Da data</label>
                <input type="date" name="from" style="width: auto;">
            </div>
            <div class="form-group" style="margin: 0;">
                <label>A data</label>
                <input type="date" name="to" style="width: auto;">
            </div>
            <div class="form-group" style="margin: 0;">
                <label>Codice Fiscale Cliente</label>
                <input type="text" name="cf" placeholder="Opzionale">
            </div>
            <button type="submit" style="background: #ffd814; border: 1px solid #fcd200; padding: 10px 20px; border-radius: 4px; cursor: pointer;">Filtra</button>
        </form>
    </div>

    <div style="background: white; padding: 20px; border: 1px solid #ddd; border-radius: 8px;">
        <table style="width: 100%; border-collapse: collapse;">
            <thead style="background: #f0f2f2;">
                <tr>
                    <th style="padding: 10px; border: 1px solid #ddd; text-align: left;">ID Ordine</th>
                    <th style="padding: 10px; border: 1px solid #ddd; text-align: left;">Data</th>
                    <th style="padding: 10px; border: 1px solid #ddd; text-align: left;">Cliente (CF)</th>
                    <th style="padding: 10px; border: 1px solid #ddd; text-align: left;">Totale</th>
                    <th style="padding: 10px; border: 1px solid #ddd; text-align: left;">Prodotti</th>
                </tr>
            </thead>
            <tbody>
                <%
                    @SuppressWarnings("unchecked")
                    Collection<OrdineBean> orders = (Collection<OrdineBean>) request.getAttribute("orders");
                    if (orders != null && !orders.isEmpty()) {
                        for (OrdineBean order : orders) {
                %>
                    <tr>
                        <td style="padding: 10px; border: 1px solid #ddd;"><%= order.getIdOrdine() %></td>
                        <td style="padding: 10px; border: 1px solid #ddd;"><%= order.getDataOrdine() != null ? order.getDataOrdine() : "N/D" %></td>
                        <td style="padding: 10px; border: 1px solid #ddd;"><%= order.getCodiceFiscale() %></td>
                        <td style="padding: 10px; border: 1px solid #ddd;">€<%= order.getTotOrdine() %></td>
                        <td style="padding: 10px; border: 1px solid #ddd;"><%= order.getListaProdotti() %></td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr><td colspan="4" style="padding: 20px; text-align: center;">Nessun ordine trovato.</td></tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
