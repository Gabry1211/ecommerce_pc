<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Registrazione Venditore</title>
</head>
<body>
  <h2>Registrati come Venditore</h2>
  <form action="RegistrazioneVenditoreServlet" method="post">
    <label for="nome">Nome:</label><br>
    <input type="text" name="nome" required><br><br>

    <label for="cognome">Cognome:</label><br>
    <input type="text" name="cognome" required><br><br>

    <label for="email">Email:</label><br>
    <input type="email" name="email" required><br><br>

    <label for="password">Password:</label><br>
    <input type="password" name="password" required><br><br>

    <input type="submit" value="Registrati">
  </form>

  <p><a href="login.jsp">Torna al login</a></p>
</body>
</html>
