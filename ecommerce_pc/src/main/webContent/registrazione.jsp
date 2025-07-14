<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Registrazione Cliente - TechZone</title>
    <link rel="stylesheet" href="styles/style.css">
    <script src="scripts/validate.js"></script>
</head>
<body class="login-page">

    <%@ include file="fragments/header.jsp" %>

    <div class="login-card">
        <h2 class="login-title">Registrazione Cliente</h2>

        <form action="RegistrazioneServlet" method="post" onsubmit="return validaRegistrazione()" class="login-form">

            <label for="codiceFiscale">Codice Fiscale:</label>
            <input type="text" id="codiceFiscale" name="codiceFiscale" required>
            <span id="erroreCodiceFiscale" class="errore"></span>

            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" required>
            <span id="erroreNome" class="errore"></span>

            <label for="dataNascita">Data di nascita:</label>
            <input type="date" id="dataNascita" name="dataNascita" required>
            <span id="erroreDataNascita" class="errore"></span>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
            <span id="erroreEmail" class="errore"></span>

            <label for="indirizzo">Indirizzo:</label>
            <input type="text" id="indirizzo" name="indirizzo" required>
            <span id="erroreIndirizzo" class="errore"></span>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            <span id="errorePassword" class="errore"></span>

            <input type="submit" value="Registrati" class="btn-login">
        </form>

        <p class="login-links">
            Sei un venditore? <a href="registrazioneVenditore.jsp">Registrati come venditore</a><br>
            Hai già un account? <a href="login.jsp">Effettua il login</a>
        </p>
    </div>

    <%@ include file="fragments/footer.jsp" %>

</body>
</html>
