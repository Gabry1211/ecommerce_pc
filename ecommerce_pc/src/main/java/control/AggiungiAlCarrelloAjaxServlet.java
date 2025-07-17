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
		String idProdottoParam = request.getParameter("idProdotto");
        String quantitaParam = request.getParameter("quantita");

        if (idProdottoParam == null || quantitaParam == null) {
            response.setStatus(400);
            response.getWriter().write("Parametri mancanti");
            return;
        }

        int idProdotto;
        int quantita;
        try {
            idProdotto = Integer.parseInt(idProdottoParam);
            quantita = Integer.parseInt(quantitaParam);
        } catch (NumberFormatException e) {
            response.setStatus(400);
            response.getWriter().write("Parametri non validi");
            return;
        }

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        Prodotto prodotto = prodottoDAO.doRetrieveById(idProdotto);

        if (prodotto == null) {
            response.setStatus(404);
            response.getWriter().write("Prodotto non trovato");
            return;
        }

        HttpSession session = request.getSession();
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        if (carrello == null) {
            carrello = new Carrello();
            session.setAttribute("carrello", carrello);
        }

        carrello.aggiungi(prodotto, quantita);

        // Rispondi con il totale aggiornato
        response.setContentType("text/plain");
        response.getWriter().write(String.valueOf(carrello.getTotaleQuantita()));
	}

}
