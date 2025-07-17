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
		HttpSession session = request.getSession();

        String idProdottoStr = request.getParameter("idProdotto");
        String quantitaStr = request.getParameter("quantita");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (idProdottoStr == null || quantitaStr == null) {
            String json = "{\"success\":false,\"message\":\"Parametri mancanti\"}";
            response.getWriter().write(json);
            return;
        }

        try {
            int idProdotto = Integer.parseInt(idProdottoStr);
            int quantita = Integer.parseInt(quantitaStr);

            // Recupera o crea carrello
            Carrello carrello = (Carrello) session.getAttribute("carrello");
            if (carrello == null) {
                carrello = new Carrello();
                session.setAttribute("carrello", carrello);
            }

            // Recupera prodotto da DB
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            Prodotto prodotto = prodottoDAO.doRetrieveById(idProdotto);

            if (prodotto == null) {
                String json = "{\"success\":false,\"message\":\"Prodotto non trovato\"}";
                response.getWriter().write(json);
                return;
            }

            prodotto.setQuantita(quantita); // imposta quantità da aggiungere

            carrello.aggiungiProdotto(prodotto);

            // Risposta JSON manuale
            String json = "{\"success\":true,\"numeroProdotti\":" + carrello.getElementi().size() + "}";
            response.getWriter().write(json);

        } catch (NumberFormatException e) {
            String json = "{\"success\":false,\"message\":\"Parametri non validi\"}";
            response.getWriter().write(json);
        }
	}

}
