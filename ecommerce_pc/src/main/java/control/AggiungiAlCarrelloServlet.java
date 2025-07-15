package control;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import model.Carrello;
import model.Prodotto;
import model.ProdottoDAO;
@WebServlet("/AggiungiAlCarrelloServlet")
public class AggiungiAlCarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
		String nome = request.getParameter("nome");
		String descrizione = request.getParameter("descrizione");
		double prezzo = Double.parseDouble("prezzo");
		String tipo = request.getParameter("tipo");
		String immagine = request.getParameter("immagine");
		String cfAdmin = request.getParameter("cfAdmin");
		int idVenditore = Integer.parseInt("idVenditore");
		int quantita = Integer.parseInt("quantita");

        HttpSession session = request.getSession();
        Carrello carrello = (Carrello) session.getAttribute("carrello");

        if (carrello == null) {
            carrello = new Carrello();
            session.setAttribute("carrello", carrello);
        }

        try {
            Prodotto p = new Prodotto(idProdotto, descrizione, prezzo, tipo, immagine, cfAdmin, idVenditore, quantita);
            carrello.aggiungiProdotto(p);
            response.sendRedirect("carrello.jsp");
        } catch (Exception e) {
            e.printStackTrace();
          
	}


	}
}