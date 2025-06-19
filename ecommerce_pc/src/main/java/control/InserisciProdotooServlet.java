package control;

import model.Prodotto;
import model.ProdottoDAO;

import jakarta.servlet.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;


@WebServlet("/InserisciProdotooServlet")
public class InserisciProdotooServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String descrizione = request.getParameter("descrizione");
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));
        String tipo = request.getParameter("tipo");
        String immagine = request.getParameter("immagine");

        HttpSession session = request.getSession();
        String cfAdmin = (String) session.getAttribute("amministratore");
        String idVenditoreStr = (String) session.getAttribute("venditore");

        try {
            Prodotto p;
            if (cfAdmin != null) {
                p = new Prodotto(descrizione, prezzo, tipo, immagine, cfAdmin, 0);
            } else if (idVenditoreStr != null) {
                int idVenditore = Integer.parseInt(idVenditoreStr);
                p = new Prodotto(descrizione, prezzo, tipo, immagine, null, idVenditore);
            } else {
                response.sendRedirect("login.jsp");
                return;
            }

            new ProdottoDAO().aggiungiProdotto(p);
            response.sendRedirect("inserisci_prodotto.jsp?success=true");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Errore inserimento prodotto.");
        }
	}

}
