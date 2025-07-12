package control;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Prodotto;
import model.ProdottoDAO;

@WebServlet("/CercaProdottiServlet")
public class CercaProdottiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
        String categoria = request.getParameter("categoria");
        String minPrezzoStr = request.getParameter("minPrezzo");
        String maxPrezzoStr = request.getParameter("maxPrezzo");

        double minPrezzo = 0;
        double maxPrezzo = Double.MAX_VALUE;

        try {
            if (minPrezzoStr != null && !minPrezzoStr.isEmpty())
                minPrezzo = Double.parseDouble(minPrezzoStr);
            if (maxPrezzoStr != null && !maxPrezzoStr.isEmpty())
                maxPrezzo = Double.parseDouble(maxPrezzoStr);
        } catch (NumberFormatException e) {
            // ignora o imposta valori di default
        }

        ProdottoDAO dao = new ProdottoDAO();
        List<Prodotto> risultati = dao.cercaProdotti(keyword, categoria, minPrezzo, maxPrezzo);

        request.setAttribute("risultati", risultati);
        RequestDispatcher dispatcher = request.getRequestDispatcher("risultatiRicerca.jsp");
        dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
