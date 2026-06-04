<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.VenditoreBean" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<%
    VenditoreBean seller = (VenditoreBean) session.getAttribute("seller");
    if (seller == null) {
        response.sendRedirect(request.getContextPath() + "/LoginControl");
        return;
    }
%>

<div style="padding: 20px; max-width: 1200px; margin: 0 auto;">
    <h1 style="border-bottom: 2px solid #131921; padding-bottom: 10px;">Pannello Venditore: <%= seller.getNome() %></h1>
    <p style="color: #565959;">P.IVA: <%= seller.getPartitaIva() %></p>
    
    <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 30px; margin-top: 30px;">
        <div style="background: white; padding: 25px; border-radius: 8px; border: 1px solid #ddd; text-align: center; box-shadow: 0 2px 5px rgba(0,0,0,0.1);">
            <div style="font-size: 50px; margin-bottom: 15px;">📦</div>
            <h2 style="margin-bottom: 15px;">I Tuoi Prodotti</h2>
            <p style="color: #565959; margin-bottom: 20px;">Aggiungi nuovi componenti, modifica i prezzi o elimina prodotti dal tuo catalogo.</p>
            <a href="<%=request.getContextPath()%>/VenditoreControl" style="display: inline-block; background: #ffd814; border: 1px solid #fcd200; padding: 12px 25px; border-radius: 8px; text-decoration: none; color: #111; font-weight: bold;">Gestisci Prodotti</a>
        </div>

        <div style="background: white; padding: 25px; border-radius: 8px; border: 1px solid #ddd; text-align: center; box-shadow: 0 2px 5px rgba(0,0,0,0.1);">
            <div style="font-size: 50px; margin-bottom: 15px;">📜</div>
            <h2 style="margin-bottom: 15px;">Ordini Ricevuti</h2>
            <p style="color: #565959; margin-bottom: 20px;">Visualizza gli ordini che contengono i tuoi prodotti e monitora le vendite.</p>
            <a href="<%=request.getContextPath()%>/VenditoreControl?action=viewOrders" style="display: inline-block; background: #ffd814; border: 1px solid #fcd200; padding: 12px 25px; border-radius: 8px; text-decoration: none; color: #111; font-weight: bold;">Visualizza Ordini</a>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
