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
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.sql.Date;
import java.sql.Time;

@WebServlet("/ConfermaOrdineServlet")
public class ConfermaOrdineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Cliente cliente = (Cliente) session.getAttribute("cliente");
		Carrello carrello = (Carrello) session.getAttribute("carrello");

		if (cliente == null || carrello == null || carrello.isEmpty()) {
			response.sendRedirect("carrello.jsp");
			return;
		}

		// Dati dal form
		String indirizzo = request.getParameter("indirizzo");
		String citta = request.getParameter("citta");
		String cap = request.getParameter("cap");
		String metodoPagamento = request.getParameter("metodoPagamento");

		// Crea ordine
		Ordine ordine = new Ordine();
		ordine.setCodiceFiscaleCliente(cliente.get_codiceFiscale());
		ordine.setDataOrdine(java.sql.Date.valueOf(LocalDate.now()));
		ordine.setOraOrdine(java.sql.Time.valueOf(LocalTime.now()));
		ordine.setTotale(carrello.getTotale());
		ordine.setIndirizzoSpedizione(indirizzo);
		ordine.setCitta(citta);
		ordine.setCap(cap);
		ordine.setMetodoPagamento(metodoPagamento);

		OrdineDAO ordineDAO = new OrdineDAO();
		int idOrdine = ordineDAO.doSave(ordine); // salva e restituisce ID ordine

		// Salva dettagli
		for (ElementoCarrello elemento : carrello.getElementi()) {
			ordineDAO.salvaDettaglioOrdine(idOrdine, elemento.getProdotto(), elemento.getQuantita(),
					elemento.getTotale());
		}

		// Svuota carrello
		carrello.svuota();
		session.setAttribute("carrello", carrello);

		// Vai a pagina conferma
		response.sendRedirect("ordineConfermato.jsp");
	}

}
