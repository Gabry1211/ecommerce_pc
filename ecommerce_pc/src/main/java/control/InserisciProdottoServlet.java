package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.Prodotto;
import model.ProdottoDAO;

import java.io.IOException;

import java.io.*;
import java.nio.file.Paths;
import java.sql.SQLException;

@WebServlet("/InserisciProdottoServlet")
@MultipartConfig
public class InserisciProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recupera i dati dal form
        String descrizione = request.getParameter("descrizione");
        String tipo = request.getParameter("tipo");
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));
        int quantita = Integer.parseInt(request.getParameter("quantita")); // 🆕 aggiunta quantità

        // Recupera il venditore dalla sessione
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("venditore") == null) {
            response.sendRedirect("loginVenditore.jsp");
            return;
        }

        model.Venditore venditore = (model.Venditore) session.getAttribute("venditore");
        int idVenditore = venditore.getIdVenditore();

        // Upload immagine
        Part filePart = request.getPart("immagine");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

     // Percorso assoluto della cartella persistente (fuori da WebContent)
        String uploadPath = "C:/upload_ecommerce/"; // <-- Cambia questo percorso se sei su Linux o altro

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        File file = new File(uploadDir, fileName);
        filePart.write(file.getAbsolutePath());

        // Salva nel DB solo il nome del file
        String percorsoImmagine = fileName;

        // Costruisci il Prodotto
        Prodotto prodotto = new Prodotto();
        prodotto.setDescrizione(descrizione);
        prodotto.setTipo(tipo);
        prodotto.setPrezzo(prezzo);
        prodotto.setQuantita(quantita); // 🆕 nuova quantità
        prodotto.setIdVenditore(idVenditore);
        prodotto.setImmagine(percorsoImmagine); // percorso relativo

        try {
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            prodottoDAO.doSave(prodotto);
            response.sendRedirect("venditoreHome.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("errore.jsp");
        }
	}

}
