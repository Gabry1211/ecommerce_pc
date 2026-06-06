<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div class="form-container">
    <h2 style="text-align: center;">Accedi</h2>

    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null) { %>
        <p class="error-msg" style="text-align: center;"><%= error %></p>
    <% } %>

    <form action="<%=request.getContextPath()%>/LoginControl" method="post" id="loginForm">
        <div class="form-group">
            <label for="username" id="label-username">Email</label>
            <input type="text" id="username" name="username" required>
            <div id="usernameError" class="error-msg"></div>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
            <div id="passwordError" class="error-msg"></div>
        </div>

        <div class="form-group">
            <label for="type">Tipo account</label>
            <select name="type" id="type" style="width: 100%; padding: 8px;" onchange="updateLoginLabel()">
                <option value="client">Cliente</option>
                <option value="seller">Venditore</option>
                <option value="admin">Amministratore</option>
            </select>
        </div>

        <button type="submit" style="width: 100%; background: #ffd814; border: 1px solid #fcd200; border-radius: 4px; padding: 8px; cursor: pointer;">Accedi</button>
    </form>

    <hr style="margin: 20px 0;">
    <p style="text-align: center; font-size: 12px; color: #565959;">Nuovo su TechZone?</p>
    <a href="<%=request.getContextPath()%>/RegistrationControl" style="display: block; text-align: center; background: #e7e9ec; border: 1px solid #adb1b8; border-radius: 4px; padding: 8px; text-decoration: none; color: black;">Crea il tuo account TechZone</a>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
