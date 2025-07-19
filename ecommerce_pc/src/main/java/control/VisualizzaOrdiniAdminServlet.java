package control;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DettaglioOrdine;
import model.Ordine;
import model.OrdineDAO;

import java.io.IOException;
import java.util.List;
import java.sql.Date;

@WebServlet("/VisualizzaOrdiniAdminServlet")
public class VisualizzaOrdiniAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dataInizioStr = request.getParameter("dataInizio");
        String dataFineStr = request.getParameter("dataFine");
        String cfCliente = request.getParameter("cfCliente");
        java.sql.Date dataInizio = java.sql.Date.valueOf(dataInizioStr);
        java.sql.Date dataFine = java.sql.Date.valueOf(dataFineStr);

        OrdineDAO ordineDAO = new OrdineDAO();
        List<Ordine> ordini = null;

        try {
            if (cfCliente != null && !cfCliente.trim().isEmpty()) {
                ordini = ordineDAO.doRetrieveByDateRangeAndCliente(dataInizio, dataFine, cfCliente.trim());
            } else {
                ordini = ordineDAO.doRetrieveByDateRange(dataInizio, dataFine);
            }

            for (Ordine o : ordini) {
                List<DettaglioOrdine> dettagli = ordineDAO.doRetrieveDettagliById(o.getIdOrdine());
                o.setDettagli(dettagli);
            }

            request.setAttribute("ordini", ordini);
            request.getRequestDispatcher("adminVisualizzaOrdini.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("errore.jsp");
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
