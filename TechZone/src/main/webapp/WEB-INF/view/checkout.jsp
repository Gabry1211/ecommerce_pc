<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.ProdottoBean, java.math.BigDecimal" %>

<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div class="checkout-container" style="max-width: 1000px; margin: 20px auto; padding: 20px;">
    <h1 style="border-bottom: 1px solid #ddd; padding-bottom: 10px;">Riepilogo e Pagamento</h1>

    <div style="display: flex; gap: 30px; margin-top: 20px;">
        <div style="flex: 2;">
            <form action="<%=request.getContextPath()%>/OrderControl" method="post" id="checkoutForm">
                <input type="hidden" name="action" value="confirmOrder">
                <input type="hidden" name="token" value="<%= (String) session.getAttribute("token") %>">

                <div style="background: white; padding: 20px; border: 1px solid #ddd; border-radius: 8px; margin-bottom: 20px;">
                    <h3 style="margin-top: 0;">1. Indirizzo di spedizione</h3>
                    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 15px; margin-top: 15px;">
                        <div style="grid-column: span 2;">
                            <label style="display: block; margin-bottom: 5px; font-weight: bold;">Indirizzo completo</label>
                            <input type="text" name="indirizzo" required placeholder="Via, numero civico, interno" style="width: 100%; padding: 8px; border: 1px solid #a6a6a6; border-radius: 3px;">
                        </div>
                        <div>
                            <label style="display: block; margin-bottom: 5px; font-weight: bold;">Città</label>
                            <input type="text" name="citta" required style="width: 100%; padding: 8px; border: 1px solid #a6a6a6; border-radius: 3px;">
                        </div>
                        <div>
                            <label style="display: block; margin-bottom: 5px; font-weight: bold;">CAP</label>
                            <input type="text" name="cap" required pattern="[0-9]{5}" title="Inserire un CAP valido di 5 cifre" style="width: 100%; padding: 8px; border: 1px solid #a6a6a6; border-radius: 3px;">
                        </div>
                    </div>
                </div>

                <div style="background: white; padding: 20px; border: 1px solid #ddd; border-radius: 8px; margin-bottom: 20px;">
                    <h3 style="margin-top: 0;">2. Metodo di pagamento</h3>
                    <div style="margin-top: 15px;">
                        <div style="border: 1px solid #ddd; padding: 15px; border-radius: 5px; background: #fcfcfc;">
                            <label style="display: block; margin-bottom: 5px; font-weight: bold;">Nome sulla carta</label>
                            <input type="text" name="cardName" required style="width: 100%; padding: 8px; border: 1px solid #a6a6a6; border-radius: 3px; margin-bottom: 10px;">

                            <label style="display: block; margin-bottom: 5px; font-weight: bold;">Numero carta</label>
                            <input type="text" name="cardNumber" required pattern="[0-9]{16}" title="Inserire 16 cifre" placeholder="0000 0000 0000 0000" style="width: 100%; padding: 8px; border: 1px solid #a6a6a6; border-radius: 3px; margin-bottom: 10px;">

                            <div style="display: flex; gap: 15px;">
                                <div style="flex: 1;">
                                    <label style="display: block; margin-bottom: 5px; font-weight: bold;">Scadenza</label>
                                    <input type="text" name="expiry" required placeholder="MM/AA" pattern="(0[1-9]|1[0-2])/[0-9]{2}" style="width: 100%; padding: 8px; border: 1px solid #a6a6a6; border-radius: 3px;">
                                </div>
                                <div style="flex: 1;">
                                    <label style="display: block; margin-bottom: 5px; font-weight: bold;">CVV</label>
                                    <input type="text" name="cvv" required pattern="[0-9]{3}" title="Inserire 3 cifre" style="width: 100%; padding: 8px; border: 1px solid #a6a6a6; border-radius: 3px;">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div style="background: white; padding: 20px; border: 1px solid #ddd; border-radius: 8px;">
                    <h3 style="margin-top: 0;">3. Rivedi articoli</h3>
                    <%
                        BigDecimal total = BigDecimal.ZERO;
                        if (cart != null) {
                            for (ProdottoBean item : cart) {
                                total = total.add(item.getPrezzo().multiply(new BigDecimal(item.getQuantita())));
                    %>
                        <div style="display: flex; gap: 15px; padding: 10px 0; border-bottom: 1px solid #eee;">
                            <img src="<%= item.getFullImagePath(request.getContextPath()) %>" style="width: 50px; height: 50px; object-fit: contain;">
                            <div>
                                <div style="font-weight: bold;"><%= item.getDescrizione() %></div>
                                <div style="font-size: 14px; color: #565959;">Quantità: <%= item.getQuantita() %></div>
                                <div style="color: #b12704; font-weight: bold;">€<%= item.getPrezzo() %></div>
                            </div>
                        </div>
                    <%
                            }
                        }
                    %>
                </div>
            </form>
        </div>

        <div style="flex: 1;">
            <div style="background: white; padding: 20px; border: 1px solid #ddd; border-radius: 8px; position: sticky; top: 20px;">
                <button type="submit" form="checkoutForm" style="width: 100%; background: #ffd814; border: 1px solid #fcd200; border-radius: 8px; padding: 12px; cursor: pointer; font-weight: bold; font-size: 14px; margin-bottom: 15px;">
                    Acquista ora
                </button>
                <p style="font-size: 12px; text-align: center; color: #565959; margin-bottom: 15px;">
                    Effettuando l'ordine, accetti le nostre condizioni generali di uso e vendita.
                </p>
                <hr>
                <h3 style="margin: 15px 0 10px 0;">Riepilogo ordine</h3>
                <div style="display: flex; justify-content: space-between; margin-bottom: 5px;">
                    <span>Articoli:</span>
                    <span>€<%= total %></span>
                </div>
                <div style="display: flex; justify-content: space-between; margin-bottom: 5px;">
                    <span>Spedizione:</span>
                    <span style="color: green;">GRATUITA</span>
                </div>
                <hr>
                <div style="display: flex; justify-content: space-between; font-size: 18px; font-weight: bold; color: #b12704; margin-top: 10px;">
                    <span>Totale ordine:</span>
                    <span>€<%= total %></span>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
