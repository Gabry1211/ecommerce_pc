package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet("/ImmagineServlet")
public class ImmagineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	private static final String IMAGE_DIR = "C:/upload_ecommerce/";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getParameter("file");
        if (fileName == null || fileName.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametro file mancante");
            return;
        }

        File imageFile = new File(IMAGE_DIR, fileName);

        if (!imageFile.exists() || imageFile.isDirectory()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404
            return;
        }

        // Imposta il tipo MIME in base all’estensione del file
        String mimeType = getServletContext().getMimeType(imageFile.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setContentLengthLong(imageFile.length());

        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(imageFile));
             BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream())) {
            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
