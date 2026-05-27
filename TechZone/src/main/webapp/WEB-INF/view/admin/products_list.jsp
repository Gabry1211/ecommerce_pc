<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.ProdottoBean, model.VenditoreBean" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div style="padding: 20px; max-width: 1200px; margin: 0 auto;">
    <div style="margin-bottom: 20px;">
        <a href="<%=request.getContextPath()%>/AdminControl?action=dashboard" style="text-decoration: none; color: #007185; font-weight: bold;">&larr; Torna al Pannello Admin</a>
    </div>
    <h2>Gestione Prodotti</h2>
    
    <div style="background: white; padding: 20px; border: 1px solid #ddd; border-radius: 8px; margin-bottom: 30px;">
        <%
            ProdottoBean pEdit = (ProdottoBean) request.getAttribute("productToEdit");
            String formAction = (pEdit != null) ? "editProduct" : "addProduct";
            String title = (pEdit != null) ? "Modifica Prodotto ID: " + pEdit.getIdProdotto() : "Aggiungi Nuovo Prodotto";
        %>
        <h3><%= title %></h3>
        <form action="<%=request.getContextPath()%>/AdminControl" method="post" enctype="multipart/form-data" style="display: grid; grid-template-columns: 1fr 1fr; gap: 15px;">
            <input type="hidden" name="action" value="<%= formAction %>">
            <input type="hidden" name="token" value="<%= (String) session.getAttribute("token") %>">
            <% if (pEdit != null) { %>
                <input type="hidden" name="id" value="<%= pEdit.getIdProdotto() %>">
            <% } %>
            
            <div class="form-group">
                <label>Descrizione</label>
                <input type="text" name="descrizione" value="<%= (pEdit != null) ? pEdit.getDescrizione() : "" %>" required>
            </div>
            <div class="form-group">
                <label>Prezzo</label>
                <input type="number" step="0.01" name="prezzo" value="<%= (pEdit != null) ? pEdit.getPrezzo() : "" %>" required>
            </div>
            <div class="form-group">
                <label>Tipo</label>
                <input type="text" name="tipo" value="<%= (pEdit != null) ? pEdit.getTipo() : "" %>" required>
            </div>
            <div class="form-group">
                <label>Quantità Disponibile</label>
                <input type="number" name="quantita" value="<%= (pEdit != null) ? pEdit.getQuantita() : "1" %>" min="0" required>
            </div>
            <div class="form-group">
                <label>Immagine Prodotto <%= (pEdit != null) ? "(lascia vuoto per mantenere attuale)" : "" %></label>
                <input type="file" name="immagine" accept="image/*" <%= (pEdit != null) ? "" : "required" %>>
                <% if (pEdit != null) { %>
                    <p style="font-size: 11px; color: #555;">Attuale: <%= pEdit.getPercorsoImmagine() %></p>
                <% } %>
            </div>
            <div style="grid-column: span 2; display: flex; gap: 10px;">
                <button type="submit" style="flex: 1; background: #ffd814; border: 1px solid #fcd200; padding: 10px; border-radius: 4px; cursor: pointer; font-weight: bold;">
                    <%= (pEdit != null) ? "Salva Modifiche" : "Salva Prodotto" %>
                </button>
                <% if (pEdit != null) { %>
                    <a href="<%=request.getContextPath()%>/AdminControl" style="flex: 1; text-align: center; background: #e7e9ec; border: 1px solid #adb1b8; padding: 10px; border-radius: 4px; text-decoration: none; color: black; font-size: 13px;">Annulla</a>
                <% } %>
            </div>
        </form>
    </div>

    <div style="background: white; padding: 20px; border: 1px solid #ddd; border-radius: 8px;">
        <h3>Catalogo Attuale</h3>
        <table style="width: 100%; border-collapse: collapse; margin-top: 15px;">
            <thead style="background: #f0f2f2;">
                <tr>
                    <th style="padding: 10px; border: 1px solid #ddd; text-align: left;">ID</th>
                    <th style="padding: 10px; border: 1px solid #ddd; text-align: left;">Immagine</th>
                    <th style="padding: 10px; border: 1px solid #ddd; text-align: left;">Descrizione</th>
                    <th style="padding: 10px; border: 1px solid #ddd; text-align: left;">Prezzo</th>
                    <th style="padding: 10px; border: 1px solid #ddd; text-align: left;">Tipo</th>
                    <th style="padding: 10px; border: 1px solid #ddd; text-align: left;">Qtà</th>
                    <th style="padding: 10px; border: 1px solid #ddd; text-align: left;">Azioni</th>
                </tr>
            </thead>
            <tbody>
                <%
                    @SuppressWarnings("unchecked")
                    Collection<ProdottoBean> products = (Collection<ProdottoBean>) request.getAttribute("products");
                    if (products != null && !products.isEmpty()) {
                        for (ProdottoBean p : products) {
                %>
                    <tr>
                        <td style="padding: 10px; border: 1px solid #ddd;"><%= p.getIdProdotto() %></td>
                        <td style="padding: 10px; border: 1px solid #ddd;"><img src="<%= p.getFullImagePath(request.getContextPath()) %>" style="width: 50px; height: 50px; object-fit: contain;"></td>
                        <td style="padding: 10px; border: 1px solid #ddd;"><%= p.getDescrizione() %></td>
                        <td style="padding: 10px; border: 1px solid #ddd;">€<%= p.getPrezzo() %></td>
                        <td style="padding: 10px; border: 1px solid #ddd;"><%= p.getTipo() %></td>
                        <td style="padding: 10px; border: 1px solid #ddd;"><%= p.getQuantita() %></td>
                        <td style="padding: 10px; border: 1px solid #ddd; display: flex; gap: 10px;">
                            <a href="<%=request.getContextPath()%>/AdminControl?action=editProductLoad&id=<%= p.getIdProdotto() %>" style="color: #007185; text-decoration: none; font-weight: bold;">Modifica</a>
                            <a href="<%=request.getContextPath()%>/AdminControl?action=deleteProduct&id=<%= p.getIdProdotto() %>&token=<%= (String) session.getAttribute("token") %>" style="color: #c40000; text-decoration: none;" onclick="return confirm('Sicuro di voler eliminare?')">Elimina</a>
                        </td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr><td colspan="6" style="padding: 20px; text-align: center;">Nessun prodotto trovato.</td></tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
