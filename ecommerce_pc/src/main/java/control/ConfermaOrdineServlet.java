package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Ordine;
import model.OrdineDAO;
import model.Prodotto;
import model.ProdottoDAO;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/ConfermaOrdineServlet")
public class ConfermaOrdineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("carrello") == null) {
            response.sendRedirect("carrello.jsp");
            return;
        }

        // Mappa idProdotto -> quantità dal carrello
        Map<Integer, Integer> carrello = (Map<Integer, Integer>) session.getAttribute("carrello");

        // Lista prodotti disponibili in sessione
        List<Prodotto> listaProdotti = (List<Prodotto>) session.getAttribute("prodotti");

        if (listaProdotti == null) {
            // Lista prodotti non trovata, errore o reindirizza
            response.sendRedirect("catalogo.jsp");
            return;
        }

        // Costruisco la lista prodotti con quantità e calcolo totale
        double totaleOrdine = 0.0;
        StringBuilder listaProdottiStr = new StringBuilder();

        Map<Prodotto, Integer> prodottiConQuantita = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry : carrello.entrySet()) {
            int idProdotto = entry.getKey();
            int quantita = entry.getValue();

            // Cerca prodotto in listaProdotti
            Prodotto prodotto = null;
            for (Prodotto p : listaProdotti) {
                if (p.getIdProdotto() == idProdotto) {
                    prodotto = p;
                    break;
                }
            }

            if (prodotto != null) {
                prodottiConQuantita.put(prodotto, quantita);

                // Costruisco stringa lista prodotti per memorizzare (puoi personalizzare)
                listaProdottiStr.append(prodotto.getIdProdotto())
                    .append(":").append(quantita).append(";");

                totaleOrdine += prodotto.getPrezzo() * quantita;
            }
        }

        // Creo ordine
        Ordine ordine = new Ordine();
        ordine.setProdottiQuantita(prodottiConQuantita);
        ordine.setListaProdotti(listaProdottiStr.toString());
        ordine.setTotale(totaleOrdine);

        // Recupera codice fiscale cliente da sessione o richiesta (qui ipotizzo da sessione)
        String codiceFiscaleCliente = (String) session.getAttribute("codiceFiscaleCliente");
        ordine.setCodiceFiscaleCliente(codiceFiscaleCliente);

        // Id assistenza (puoi settare a 0 o gestire come ti serve)
        ordine.setIdAssistenza(0);

        // Salvataggio ordine nel DB tramite OrdineDAO (devi avere la connessione)
        try {
            // Ottieni connessione dal contesto (dipende da come la gestisci)
            Connection conn = (Connection) getServletContext().getAttribute("dbConn");
            OrdineDAO ordineDao = new OrdineDAO(conn);
            ordineDao.salvaOrdine(ordine);

            // Svuota carrello
            session.removeAttribute("carrello");

            // Reindirizza o forward a pagina di conferma
            request.setAttribute("messaggio", "Ordine confermato con successo!");
            request.getRequestDispatcher("confermaOrdine.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante il salvataggio dell'ordine.");
        }
	}

}
