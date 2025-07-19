package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ProdottoDAO;

import java.io.IOException;

@WebServlet("/AdminEliminaProdottoServlet")
public class AdminEliminaProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idProdottoStr = request.getParameter("id");

		if (idProdottoStr != null) {
			try {
				int idProdotto = Integer.parseInt(idProdottoStr);
				ProdottoDAO prodottoDAO = new ProdottoDAO();
				prodottoDAO.doDelete(idProdotto);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		// Redireziona alla home admin dopo l'eliminazione
		response.sendRedirect("adminHome.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
