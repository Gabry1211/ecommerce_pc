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
		        HttpSession session = request.getSession(false);
		        if (session == null) {
		            response.sendRedirect("login.jsp");
		            return;
		        }

		        Carrello carrello = (Carrello) session.getAttribute("carrello");
		        if (carrello == null) {
		            response.sendRedirect("carrello.jsp");
		            return;
		        }

		        try {
		            int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
		            int nuovaQuantita = Integer.parseInt(request.getParameter("quantita"));

		            ProdottoDAO prodottoDAO = new ProdottoDAO();
		            Prodotto prodotto = prodottoDAO.doRetrieveById(idProdotto);

		            if (prodotto == null) {
		                session.setAttribute("erroreQuantita", "Prodotto non trovato.");
		                response.sendRedirect("carrello.jsp");
		                return;
		            }

		            int disponibilita = prodotto.getQuantita();

		            if (nuovaQuantita <= disponibilita && nuovaQuantita > 0) {
		                ElementoCarrello elemento = carrello.getElemento(idProdotto);
		                if (elemento != null) {
		                    elemento.setQuantita(nuovaQuantita);
		                    session.removeAttribute("erroreQuantita");
		                }
		            } else {
		                session.setAttribute("erroreQuantita",
		                        "Quantità richiesta superiore alla disponibilità (" + disponibilita + ").");
		            }

		        } catch (NumberFormatException e) {
		            session.setAttribute("erroreQuantita", "Valore non valido.");
		        }

		        response.sendRedirect("carrello.jsp");
		    }

	}
