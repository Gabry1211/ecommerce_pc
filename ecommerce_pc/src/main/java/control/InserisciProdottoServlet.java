package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;
import model.ProdottoDAO;

import java.io.IOException;

@WebServlet("/InserisciProdottoServlet")
public class InserisciProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String descrizione = request.getParameter("descrizione");
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));
        String tipo = request.getParameter("tipo");

        Prodotto prodotto = new Prodotto(0, "", prezzo, "", "", tipo, 0);
        prodotto.setDescrizione(descrizione);
        prodotto.setPrezzo(prezzo);
        prodotto.setTipo(tipo);

        ProdottoDAO dao = new ProdottoDAO();
        dao.doSave(prodotto);

        response.sendRedirect("VisualizzaProdottiServlet");
	}

}
