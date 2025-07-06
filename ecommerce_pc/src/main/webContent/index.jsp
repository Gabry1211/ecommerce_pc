<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="fragments/header.jsp" %>>

<h2>Benvenuto su EcommercePC</h2>
<p>Acquista PC assemblati e componenti hardware di qualità!</p>

<div style="display: flex; flex-wrap: wrap; gap: 20px; margin-top: 20px;">
    <div style="flex: 1; min-width: 250px; padding: 20px; background-color: #f5f5f5; border-radius: 10px;">
        <h3>🖥️ Catalogo Prodotti</h3>
        <p>Esplora il nostro vasto assortimento di PC e componenti.</p>
        <a href="VisualizzaProdottiServlet">Vai ai prodotti</a>
    </div>

    <div style="flex: 1; min-width: 250px; padding: 20px; background-color: #f5f5f5; border-radius: 10px;">
        <h3>🔐 Login</h3>
        <p>Accedi al tuo account cliente, venditore o amministratore.</p>
        <a href="login.jsp">Effettua il login</a>
    </div>

    <div style="flex: 1; min-width: 250px; padding: 20px; background-color: #f5f5f5; border-radius: 10px;">
        <h3>📝 Registrati</h3>
        <p>Non hai ancora un account? Registrati ora!</p>
        <a href="registrazione.jsp">Registrati</a>
    </div>
</div>

<%@ include file="fragments/footer.jsp" %>
