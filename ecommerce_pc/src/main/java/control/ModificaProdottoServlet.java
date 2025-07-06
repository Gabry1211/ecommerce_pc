package control;

import jakarta.servlet.ServletException;

import model.Prodotto;
import model.ProdottoDAO;

import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ModificaProdottoServlet")
public class ModificaProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("idProdotto"));
        String descrizione = request.getParameter("descrizione");
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));
        String tipo = request.getParameter("tipo");

        Prodotto prodotto = new Prodotto(id, descrizione, prezzo, tipo, "", "", 0);
        

        ProdottoDAO dao = new ProdottoDAO();
        dao.doUpdate(prodotto);

        response.sendRedirect("VisualizzaProdottiServlet");
	}

}
