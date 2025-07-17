package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Carrello;

import java.io.IOException;

@WebServlet("/SvuotaCarrelloAjaxServlet")
public class SvuotaCarrelloAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
        if (session != null) {
            Carrello carrello = (Carrello) session.getAttribute("carrello");
            if (carrello != null) {
                carrello.svuota();
            }
        }

        response.setContentType("text/plain");
        response.getWriter().write("Carrello svuotato");
	}

}
