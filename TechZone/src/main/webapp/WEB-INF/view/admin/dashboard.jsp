<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div style="padding: 20px; max-width: 1000px; margin: 0 auto;">
    <h1>Pannello di Controllo Amministratore</h1>
    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-top: 30px;">
        <a href="<%=request.getContextPath()%>/AdminControl?action=viewProducts" style="display: block; padding: 40px; background: white; border: 1px solid #ddd; text-align: center; text-decoration: none; color: black; border-radius: 8px;">
            <h2 style="margin: 0;">Gestione Catalogo</h2>
            <p>Inserisci, modifica o elimina prodotti</p>
        </a>
        <a href="<%=request.getContextPath()%>/AdminControl?action=viewOrders" style="display: block; padding: 40px; background: white; border: 1px solid #ddd; text-align: center; text-decoration: none; color: black; border-radius: 8px;">
            <h2 style="margin: 0;">Visualizza Ordini</h2>
            <p>Monitora tutti gli ordini dei clienti</p>
        </a>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
