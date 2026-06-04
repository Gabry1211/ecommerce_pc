<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.OrdineBean, model.VenditoreBean" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<%
    VenditoreBean seller = (VenditoreBean) session.getAttribute("seller");
    if (seller == null) {
        response.sendRedirect(request.getContextPath() + "/LoginControl");
        return;
    }
%>

<div style="padding: 20px; max-width: 1200px; margin: 0 auto;">
    <div style="margin-bottom: 20px;">
        <a href="<%=request.getContextPath()%>/VenditoreControl?action=dashboard" style="text-decoration: none; color: #007185; font-weight: bold;">&larr; Torna al Pannello Venditore</a>
    </div>
    <h2>Ordini dei Tuoi Prodotti</h2>
    <p style="color: #565959; margin-bottom: 20px;">Qui puoi vedere gli ordini effettuati dai clienti che includono almeno uno dei tuoi articoli.</p>

    <div style="background: white; padding: 20px; border: 1px solid #ddd; border-radius: 8px;">
        <table style="width: 100%; border-collapse: collapse;">
            <thead style="background: #f0f2f2;">
                <tr>
                    <th style="padding: 10px; border: 1px solid #ddd; text-align: left;">ID Ordine</th>
                    <th style="padding: 10px; border: 1px solid #ddd; text-align: left;">Data</th>
                    <th style="padding: 10px; border: 1px solid #ddd; text-align: left;">Dettaglio Ordine</th>
                    <th style="padding: 10px; border: 1px solid #ddd; text-align: left;">Totale Ordine</th>
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
                        <td style="padding: 10px; border: 1px solid #ddd; font-weight: bold;"><%= order.getIdOrdine() %></td>
                        <td style="padding: 10px; border: 1px solid #ddd;"><%= order.getDataOrdine() != null ? order.getDataOrdine() : "N/D" %></td>
                        <td style="padding: 10px; border: 1px solid #ddd; font-size: 14px;"><%= order.getListaProdotti() %></td>
                        <td style="padding: 10px; border: 1px solid #ddd; font-weight: bold; color: #B12704;">€<%= order.getTotOrdine() %></td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr><td colspan="4" style="padding: 20px; text-align: center;">Non ci sono ancora ordini per i tuoi prodotti.</td></tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
