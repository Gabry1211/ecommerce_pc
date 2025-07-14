<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Prodotto" %>
<%@ page import="model.ProdottoDAO" %>
<%@ page import="model.Venditore" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifica Prodotti</title>
</head>
<body>
<jsp:include page="fragments/header.jsp" />

<%
    Venditore venditore = (Venditore) session.getAttribute("venditore");
    if (venditore == null) {
        response.sendRedirect("loginVenditore.jsp");
        return;
    }

    ProdottoDAO prodottoDAO = new ProdottoDAO();
    List<Prodotto> prodotti = prodottoDAO.doRetrieveByVenditore(venditore.getIdVenditore());

    String idToEditStr = request.getParameter("id");
    int idToEdit = -1;
    if (idToEditStr != null) {
        idToEdit = Integer.parseInt(idToEditStr);
    }
%>

<h2>I tuoi prodotti</h2>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Descrizione</th>
        <th>Prezzo</th>
        <th>Tipo</th>
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
        <td>
            <form action="modificaProdotto.jsp" method="get">
                <input type="hidden" name="id" value="<%= p.getIdProdotto() %>">
                <input type="submit" value="Modifica">
            </form>
        </td>
    </tr>

<%
        if (p.getIdProdotto() == idToEdit) {
%>
    <tr>
        <td colspan="5">
            <h3>Modifica Prodotto ID <%= p.getIdProdotto() %></h3>
            <form action="ModificaProdottoServlet" method="post">
                <input type="hidden" name="idProdotto" value="<%= p.getIdProdotto() %>">
                <label>Descrizione:</label><br>
                <input type="text" name="descrizione" value="<%= p.getDescrizione() %>" required><br><br>

                <label>Prezzo:</label><br>
                <input type="number" step="0.01" name="prezzo" value="<%= p.getPrezzo() %>" required><br><br>

                <label>Tipo:</label><br>
                <input type="text" name="tipo" value="<%= p.getTipo() %>" required><br><br>

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
