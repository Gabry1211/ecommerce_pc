package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.Amministratore;
import model.Prodotto;
import model.ProdottoDAO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;

@WebServlet("/AdminInserisciProdottoServlet")
@MultipartConfig
public class AdminInserisciProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recupera i dati dal form
        String descrizione = request.getParameter("descrizione");
        String tipo = request.getParameter("tipo");
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));
        int quantita = Integer.parseInt(request.getParameter("quantita"));

        // Recupera l'amministratore dalla sessione
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect("loginAdmin.jsp");
            return;
        }

        Amministratore admin = (Amministratore) session.getAttribute("admin");
        String cfAdmin = admin.getCodiceFiscale(); // o getCfAdmin()

        // Upload immagine
        Part filePart = request.getPart("immagine");
        if (filePart == null || filePart.getSubmittedFileName() == null || filePart.getSubmittedFileName().trim().isEmpty()) {
            request.setAttribute("errore", "Errore nel caricamento dell'immagine.");
            request.getRequestDispatcher("adminInserisciProdotto.jsp").forward(request, response);
            return;
        }
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        String uploadPath = "C:/upload_ecommerce/";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        File file = new File(uploadDir, fileName);
        filePart.write(file.getAbsolutePath());

        // Costruisci il Prodotto
        Prodotto prodotto = new Prodotto();
        prodotto.setDescrizione(descrizione);
        prodotto.setTipo(tipo);
        prodotto.setPrezzo(prezzo);
        prodotto.setQuantita(quantita);
        prodotto.setCfAdmin(cfAdmin); // Assegna il CF dell'amministratore
        prodotto.setImmagine(fileName);

        try {
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            prodottoDAO.doSave(prodotto);
            response.sendRedirect("adminHome.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("errore.jsp");
        }
	}

}
