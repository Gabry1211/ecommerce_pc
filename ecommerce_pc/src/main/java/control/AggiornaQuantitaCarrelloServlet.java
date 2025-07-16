package control;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import model.Carrello;
import model.ElementoCarrello;
import model.Prodotto;
import model.ProdottoDAO;

@WebServlet("/AggiornaQuantitaCarrelloServlet")
public class AggiornaQuantitaCarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
        int nuovaQuantita = Integer.parseInt(request.getParameter("quantita"));

        HttpSession session = request.getSession();
        Carrello carrello = (Carrello) session.getAttribute("carrello");

        if (carrello == null) {
            response.sendRedirect("carrello.jsp");
            return;
        }

        ElementoCarrello elem = carrello.getElemento(idProdotto);
        if (elem == null) {
            response.sendRedirect("carrello.jsp");
            return;
        }

        ProdottoDAO dao = new ProdottoDAO();
        Prodotto prodotto = dao.doRetrieveById(idProdotto);

        if (nuovaQuantita <= prodotto.getQuantita()) {
            elem.setQuantita(nuovaQuantita);
            session.setAttribute("carrello", carrello);
        } else {
            session.setAttribute("erroreQuantita", "Non puoi aggiungere più di " + prodotto.getQuantita() + " pezzi di \"" + prodotto.getDescrizione() + "\".");
        }

        response.sendRedirect("carrello.jsp");
	}

}
