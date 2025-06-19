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
        Codice Fiscale: <input type="text" name="codiceFiscale" required><br>
        Nome: <input type="text" name="nome" required><br>
        Data di nascita: <input type="date" name="dataNascita" required><br>
        Email: <input type="email" name="email" required><br>
        Indirizzo: <input type="text" name="indirizzo" required><br>
        Password: <input type="password" name="password" required><br>
        <input type="submit" value="Registrati">
    </form>
</body>
</html>