package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Carrello;
import model.Cliente;
import model.ElementoCarrello;
import model.Ordine;
import model.OrdineDAO;
import model.Prodotto;
import model.ProdottoDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/ConfermaOrdineServlet")
public class ConfermaOrdineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Carrello carrello = (Carrello) request.getSession().getAttribute("carrello");
        Cliente cliente = (Cliente) request.getSession().getAttribute("utente");

        if (carrello == null || cliente == null) {
            response.sendRedirect("carrello.jsp");
            return;
        }

        Ordine ordine = new Ordine();
        ordine.setCodiceFiscaleCliente(cliente.get_codiceFiscale());
        ordine.setDataOrdine(new Date());
        ordine.setTotale(carrello.getTotale());
        ordine.setProdottiQuantita(carrello.getProdottiQuantita());

        OrdineDAO ordineDAO = new OrdineDAO();
        ProdottoDAO prodottoDAO = new ProdottoDAO();

        try {
            // Salvataggio ordine
            ordineDAO.salvaOrdine(ordine);

            // Aggiorna quantità disponibili in magazzino
            for (ElementoCarrello elem : carrello.getElementi()) {
                Prodotto prodotto = elem.getProdotto();
                int nuovaQuantita = prodotto.getQuantita() - elem.getQuantita();
                prodottoDAO.aggiornaQuantita(prodotto.getIdProdotto(), nuovaQuantita);
            }

            // Svuota carrello
            carrello.svuota();
            request.getSession().setAttribute("carrello", carrello);

            // Reindirizza a pagina di conferma
            request.setAttribute("ordineConfermato", ordine);
            request.getRequestDispatcher("ordineConfermato.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("errore.jsp");
        }
	}

}
