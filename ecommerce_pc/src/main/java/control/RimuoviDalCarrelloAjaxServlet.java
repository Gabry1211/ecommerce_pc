package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Carrello;

import java.io.IOException;

@WebServlet("/RimuoviDalCarrelloAjaxServlet")
public class RimuoviDalCarrelloAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));

            HttpSession session = request.getSession(false);
            if (session != null) {
                Carrello carrello = (Carrello) session.getAttribute("carrello");
                if (carrello != null) {
                    carrello.rimuoviProdotto(idProdotto);
                    // Rispondi con il nuovo totale
                    response.setContentType("text/plain");
                    response.getWriter().write(String.format("%.2f", carrello.getTotale()));
                    return;
                }
            }
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
	}

}
