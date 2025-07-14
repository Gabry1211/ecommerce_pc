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
		// Recupera i dati del form
        String descrizione = request.getParameter("descrizione");
        String tipo = request.getParameter("tipo");
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));

        // Recupera il venditore dalla sessione
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("venditore") == null) {
            response.sendRedirect("loginVenditore.jsp");
            return;
        }

        model.Venditore venditore = (model.Venditore) session.getAttribute("venditore");
        int idVenditore = venditore.getIdVenditore();

     // Prendi l'immagine
        Part filePart = request.getPart("immagine"); // input name="immagine"
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // Percorso assoluto alla cartella "images" (dentro WebContent/)
        String imagesPath = getServletContext().getRealPath("/images");
        File imagesDir = new File(imagesPath);
        if (!imagesDir.exists()) {
            imagesDir.mkdirs();
        }
        
        System.out.println("Percorso immagini: " + imagesPath);

        // Salva fisicamente il file
        File file = new File(imagesDir, fileName);
        filePart.write(file.getAbsolutePath());

        // Salva nel DB solo il nome del file o il relativo path
        String percorsoImmagine = "images/" + fileName;

        // Crea il prodotto e salva nel DB
        Prodotto prodotto = new Prodotto();
        prodotto.setDescrizione(descrizione);
        prodotto.setTipo(tipo);
        prodotto.setPrezzo(prezzo);
        prodotto.setIdVenditore(idVenditore);
        prodotto.setImmagine(percorsoImmagine); // nuovo campo

        try {
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            prodottoDAO.doSave(prodotto);
            response.sendRedirect("venditoreHome.jsp"); // o dove vuoi tu
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("errore.jsp");
        }
	}

}
