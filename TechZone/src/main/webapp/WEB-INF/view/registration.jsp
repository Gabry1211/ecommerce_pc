<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div class="form-container">
    <h2 style="text-align: center;">Crea account</h2>

    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null) { %>
        <p class="error-msg" style="text-align: center;"><%= error %></p>
    <% } %>

    <form action="<%=request.getContextPath()%>/RegistrationControl" method="post" id="regForm">
        <div class="form-group">
            <label for="userType">Tipo account</label>
            <select name="userType" id="userType" style="width: 100%; padding: 8px;" onchange="toggleRegistrationFields()">
                <option value="client">Cliente</option>
                <option value="seller">Venditore</option>
            </select>
        </div>

        <div class="form-group">
            <label for="nome" id="label-nome">Nome completo</label>
            <input type="text" id="nome" name="nome" required>
            <div id="nomeError" class="error-msg"></div>
        </div>

        <div class="form-group">
            <label for="cf">Codice Fiscale</label>
            <input type="text" id="cf" name="cf" required maxlength="16">
            <div id="cfError" class="error-msg"></div>
        </div>

        <div class="form-group" id="field-email">
            <label for="email">Email</label>
            <input type="email" id="email" name="email">
            <div id="emailError" class="error-msg"></div>
        </div>

        <div class="form-group" id="field-pIva" style="display: none;">
            <label for="pIva">Partita IVA</label>
            <input type="text" id="pIva" name="pIva" maxlength="11">
            <div id="pIvaError" class="error-msg"></div>
        </div>

        <div class="form-group" id="field-dataNascita">
            <label for="dataNascita">Data di Nascita</label>
            <input type="date" id="dataNascita" name="dataNascita">
            <div id="dataNascitaError" class="error-msg"></div>
        </div>

        <div class="form-group" id="field-indirizzo">
            <label for="indirizzo">Indirizzo di spedizione</label>
            <input type="text" id="indirizzo" name="indirizzo">
            <div id="indirizzoError" class="error-msg"></div>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required minlength="6">
            <div id="passwordError" class="error-msg"></div>
        </div>

        <button type="submit" style="width: 100%; background: #ffd814; border: 1px solid #fcd200; border-radius: 4px; padding: 8px; cursor: pointer; font-weight: bold;">Registrati</button>
    </form>

    <p style="font-size: 12px; margin-top: 15px;">Hai già un account? <a href="<%=request.getContextPath()%>/LoginControl">Accedi</a></p>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
