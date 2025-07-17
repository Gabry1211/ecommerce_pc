package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Carrello;
import model.ElementoCarrello;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/AggiornaQuantitaAjaxServlet")
public class AggiornaQuantitaAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("idProdotto");
        String quantitaStr = request.getParameter("quantita");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            if (idStr == null || quantitaStr == null) {
                out.print("{\"success\":false,\"message\":\"Parametri mancanti\"}");
                return;
            }

            int idProdotto = Integer.parseInt(idStr);
            int nuovaQuantita = Integer.parseInt(quantitaStr);

            HttpSession session = request.getSession();
            Carrello carrello = (Carrello) session.getAttribute("carrello");

            if (carrello == null || carrello.getElemento(idProdotto) == null) {
                out.print("{\"success\":false,\"message\":\"Prodotto non trovato nel carrello\"}");
                return;
            }

            if (nuovaQuantita < 1) {
                out.print("{\"success\":false,\"message\":\"Quantità non valida\"}");
                return;
            }

            ElementoCarrello elemento = carrello.getElemento(idProdotto);
            elemento.setQuantita(nuovaQuantita);

            double nuovoTotaleProdotto = elemento.getTotale();
            double totaleCarrello = carrello.getTotale();

            // Costruiamo manualmente la risposta JSON
            out.print("{");
            out.print("\"success\":true,");
            out.print("\"nuovoTotaleProdotto\":\"€" + String.format("%.2f", nuovoTotaleProdotto) + "\",");
            out.print("\"totaleCarrello\":\"€" + String.format("%.2f", totaleCarrello) + "\"");
            out.print("}");
        } catch (NumberFormatException e) {
            response.getWriter().print("{\"success\":false,\"message\":\"Formato numerico errato\"}");
        }
	}

}
