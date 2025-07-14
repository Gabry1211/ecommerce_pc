<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Registrazione Venditore - TechZone</title>
    <link rel="stylesheet" href="styles/style.css">
    <script src="scripts/validate.js"></script>
</head>
<body class="login-page">

<div class="login-card">
    <h2 class="login-title">Registrazione Venditore</h2>

    <form action="RegistrazioneVenditoreServlet" method="post" onsubmit="return validaRegistrazioneVenditore()" class="login-form">
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" required>
        <span id="nomeErrore" class="errore"></span>

        <label for="partitaIva">Partita IVA:</label>
        <input type="text" id="partitaIva" name="partitaIva" required>
        <span id="partitaIvaErrore" class="errore"></span>

        <label for="codiceFiscale">Codice Fiscale:</label>
        <input type="text" id="codiceFiscale" name="codiceFiscale" required>
        <span id="codiceFiscaleErrore" class="errore"></span>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
        <span id="passwordErrore" class="errore"></span>

        <input type="submit" value="Registrati" class="btn-login">
    </form>

    <p class="login-links">
        Sei un cliente? <a href="registrazione.jsp">Registrati come cliente</a><br>
        Hai già un account? <a href="loginVenditore.jsp">Effettua il login</a>
    </p>
</div>

</body>
</html>
