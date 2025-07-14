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
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
					maxFileSize = 1024 * 1024 * 10,       // 10MB
					maxRequestSize = 1024 * 1024 * 50)    // 50MB
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

        // Gestione file immagine
        Part filePart = request.getPart("immagine");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // es. "pc1.jpg"
        String uploadPath = getServletContext().getRealPath("") + File.separator + "images";

        // Crea la cartella se non esiste
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        // Scrive il file nella cartella /images
        filePart.write(uploadPath + File.separator + fileName);
        String imagePath = "images/" + fileName;

        // Crea il prodotto e salva nel DB
        Prodotto prodotto = new Prodotto();
        prodotto.setDescrizione(descrizione);
        prodotto.setTipo(tipo);
        prodotto.setPrezzo(prezzo);
        prodotto.setIdVenditore(idVenditore);
        prodotto.setImmagine(imagePath); // nuovo campo

        try {
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            prodottoDAO.doSave(prodotto);
            response.sendRedirect("areaVenditore.jsp"); // o dove vuoi tu
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("errore.jsp");
        }
	}

}
