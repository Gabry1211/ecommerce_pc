<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Registrazione Cliente</title>
    <link rel="stylesheet" href="styles/style.css">
    <script src="scripts/validate.js"></script>
</head>
<body>
    <h2>Registrati</h2>
    <form action="RegistrazioneServlet" method="post" onsubmit="return validaRegistrazione()">
        Codice Fiscale: <input type="text" id="codiceFiscale" name="codiceFiscale" required>
    <span id="erroreCodiceFiscale" class="errore"></span><br>

    Nome: <input type="text" id="nome" name="nome" required>
    <span id="erroreNome" class="errore"></span><br>

    Data di nascita: <input type="date" id="dataNascita" name="dataNascita" required>
    <span id="erroreDataNascita" class="errore"></span><br>

    Email: <input type="email" id="email" name="email" required>
    <span id="erroreEmail" class="errore"></span><br>

    Indirizzo: <input type="text" id="indirizzo" name="indirizzo" required>
    <span id="erroreIndirizzo" class="errore"></span><br>

    Password: <input type="password" id="password" name="password" required>
    <span id="errorePassword" class="errore"></span><br>

    <input type="submit" value="Registrati">
    </form>
    
    <p>Non sei registrato?</p>
<ul>
  <li><a href="registrazione.jsp">Registrati come cliente</a></li>
  <li><a href="registrazioneVenditore.jsp">Registrati come venditore</a></li>
</ul>
    
</body>
</html>