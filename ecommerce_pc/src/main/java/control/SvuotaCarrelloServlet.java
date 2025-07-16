package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Carrello;

import java.io.IOException;

@WebServlet("/SvuotaCarrelloServlet")
public class SvuotaCarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false); // non crea nuova sessione
        if (session != null) {
            Carrello carrello = (Carrello) session.getAttribute("carrello");
            if (carrello != null) {
                carrello.svuota();
            }
        }

        response.sendRedirect("carrello.jsp");
	}

}
