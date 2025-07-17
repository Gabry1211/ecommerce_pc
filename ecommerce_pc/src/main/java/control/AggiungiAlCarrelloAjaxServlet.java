package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Carrello;
import model.Prodotto;
import model.ProdottoDAO;

import java.io.IOException;

@WebServlet("/AggiungiAlCarrelloAjaxServlet")
public class AggiungiAlCarrelloAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
	        int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
	        int quantita = Integer.parseInt(request.getParameter("quantita"));

	        HttpSession session = request.getSession();
	        Carrello carrello = (Carrello) session.getAttribute("carrello");
	        if (carrello == null) {
	            carrello = new Carrello();
	            session.setAttribute("carrello", carrello);
	        }

	        ProdottoDAO prodottoDAO = new ProdottoDAO();
	        Prodotto p = prodottoDAO.doRetrieveById(idProdotto);
	        if (p == null) {
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	            return;
	        }

	        carrello.aggiungi(p, quantita);

	        response.setContentType("text/plain");
	        response.getWriter().write(String.valueOf(carrello.getTotaleQuantita()));
	    } catch (Exception e) {
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    }
	}

}
