package control;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Cliente;
import model.ClienteDao;

import java.io.IOException;
import java.sql.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		ClienteDao clienteDAO = new ClienteDao();
		Cliente cliente = clienteDAO.getClienteByEmailAndPassword(email, password);

		if (cliente != null) {
		    session.setAttribute("cliente", cliente);
		    response.sendRedirect("clienteHome.jsp");
		} else {
		    request.setAttribute("errore", "Credenziali non valide.");
		    request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

}
