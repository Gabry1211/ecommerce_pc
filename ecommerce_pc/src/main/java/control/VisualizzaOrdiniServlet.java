package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DBConnection;
import model.Ordine;
import model.OrdineDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/VisualizzaOrdiniServlet")
public class VisualizzaOrdiniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
        String codiceFiscale = (String) session.getAttribute("codiceFiscaleCliente");

        if (codiceFiscale == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        Connection conn = null;
		try {
			conn = DBConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        OrdineDAO ordineDAO = null;
		try {
			ordineDAO = new OrdineDAO(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        List<Ordine> ordini = ordineDAO.getOrdiniByCodiceFiscale(codiceFiscale);

        request.setAttribute("ordini", ordini);
        request.getRequestDispatcher("ordiniCliente.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
