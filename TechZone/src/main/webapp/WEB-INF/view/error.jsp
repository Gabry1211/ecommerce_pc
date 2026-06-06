<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div style="text-align: center; padding: 50px;">
    <h1 style="color: #c40000;">Oops! Qualcosa è andato storto.</h1>
    <p>La pagina che stai cercando non è disponibile o si è verificato un errore nel server.</p>
    <img src="https://media.giphy.com/media/8L0Pky6C83SzkzU55a/giphy.gif" alt="Error" style="max-width: 300px; margin: 20px;">
    <br>
    <a href="<%=request.getContextPath()%>/Home" style="display: inline-block; background: #ffd814; border: 1px solid #fcd200; border-radius: 8px; padding: 10px 20px; text-decoration: none; color: black; font-weight: bold;">Torna alla Home</a>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
