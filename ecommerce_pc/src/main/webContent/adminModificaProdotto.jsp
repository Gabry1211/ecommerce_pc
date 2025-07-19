<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Prodotto" %>
<%@ page import="model.ProdottoDAO" %>
<%@ page import="model.Amministratore" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifica Prodotti - Admin</title>
    <link rel="stylesheet" href="styles/style.css">
</head>
<body>
<jsp:include page="fragments/header.jsp" />

<%
    Amministratore admin = (Amministratore) session.getAttribute("admin");
    if (admin == null) {
        response.sendRedirect("loginAdmin.jsp");
        return;
    }

    ProdottoDAO prodottoDAO = new ProdottoDAO();
    List<Prodotto> prodotti = prodottoDAO.doRetrieveAll(); // Tutti i prodotti

    String idToEditStr = request.getParameter("id");
    int idToEdit = -1;
    if (idToEditStr != null) {
        idToEdit = Integer.parseInt(idToEditStr);
    }
%>

<h2>Gestione Prodotti</h2>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Descrizione</th>
        <th>Prezzo</th>
        <th>Tipo</th>
        <th>Quantità</th>
        <th>Modifica</th>
    </tr>

<%
    for (Prodotto p : prodotti) {
%>
    <tr>
        <td><%= p.getIdProdotto() %></td>
        <td><%= p.getDescrizione() %></td>
        <td><%= p.getPrezzo() %> €</td>
        <td><%= p.getTipo() %></td>
        <td><%= p.getQuantita() %></td>
        <td>
            <form action="adminModificaProdotto.jsp" method="get">
                <input type="hidden" name="id" value="<%= p.getIdProdotto() %>">
                <input type="submit" value="Modifica">
            </form>
        </td>
    </tr>

<%
        if (p.getIdProdotto() == idToEdit) {
%>
    <tr>
        <td colspan="6">
            <h3>Modifica Prodotto ID <%= p.getIdProdotto() %></h3>
            <form action="AdminModificaProdottoServlet" method="post">
                <input type="hidden" name="idProdotto" value="<%= p.getIdProdotto() %>">

                <label>Descrizione:</label><br>
                <input type="text" name="descrizione" value="<%= p.getDescrizione() %>" required><br><br>

                <label>Prezzo:</label><br>
                <input type="number" step="0.01" name="prezzo" value="<%= p.getPrezzo() %>" required><br><br>

                <label>Tipo:</label><br>
                <input type="text" name="tipo" value="<%= p.getTipo() %>" required><br><br>

                <label>Quantità:</label><br>
                <input type="number" name="quantita" value="<%= p.getQuantita() %>" min="0" required><br><br>

                <input type="submit" value="Salva modifiche">
            </form>
        </td>
    </tr>
<%
        }
    }
%>
</table>

<jsp:include page="fragments/footer.jsp" />
</body>
</html>
    