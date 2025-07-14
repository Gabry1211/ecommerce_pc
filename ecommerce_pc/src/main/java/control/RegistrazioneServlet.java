package control;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Cliente;
import model.ClienteDao;
import java.io.IOException;
import java.sql.Date;
@WebServlet("/RegistrazioneServlet")
public class RegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            String cf = request.getParameter("codiceFiscale");
            String nome = request.getParameter("nome");
            Date data = Date.valueOf(request.getParameter("dataNascita"));
            String email = request.getParameter("email");
            String indirizzo = request.getParameter("indirizzo");
            String password = request.getParameter("password");

            Cliente c = new Cliente(cf, nome, data, email, indirizzo, password);
            ClienteDao dao = new ClienteDao();
            dao.registraCliente(c, password);

            response.sendRedirect("login.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Errore nella registrazione.");
        }
	}

}
