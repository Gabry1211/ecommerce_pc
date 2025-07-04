package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Carrello;

import java.io.IOException;

@WebServlet("/RimuoviDalCarrelloServlet")
public class RimuoviDalCarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("idProdotto"));
        Carrello carrello = (Carrello) request.getSession().getAttribute("carrello");
        if (carrello != null) carrello.rimuoviProdotto(id);
        response.sendRedirect("carrello.jsp");
	}

}
