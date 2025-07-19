package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;
import model.ProdottoDAO;

import java.io.IOException;

@WebServlet("/AdminModificaProdottoServlet")
public class AdminModificaProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
			String descrizione = request.getParameter("descrizione");
			double prezzo = Double.parseDouble(request.getParameter("prezzo"));
			String tipo = request.getParameter("tipo");
			int quantita = Integer.parseInt(request.getParameter("quantita"));

			Prodotto prodotto = new Prodotto();
			prodotto.setIdProdotto(idProdotto);
			prodotto.setDescrizione(descrizione);
			prodotto.setPrezzo(prezzo);
			prodotto.setTipo(tipo);
			prodotto.setQuantita(quantita);

			ProdottoDAO prodottoDAO = new ProdottoDAO();
			prodottoDAO.doUpdate(prodotto);

			response.sendRedirect("adminHome.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("errore.jsp");
		}
	}

}
