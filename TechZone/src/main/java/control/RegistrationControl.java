package control;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ClienteBean;
import model.ClienteModel;
import model.VenditoreBean;
import model.VenditoreModel;
import dao.ClienteModelDS;
import dao.VenditoreModelDS;

@WebServlet("/RegistrationControl")
public class RegistrationControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static ClienteModel clienteModel = new ClienteModelDS();
    static VenditoreModel venditoreModel = new VenditoreModelDS();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userType = request.getParameter("userType"); // "client" o "seller"
        
        if ("seller".equalsIgnoreCase(userType)) {
            String cf = request.getParameter("cf");
            String nome = request.getParameter("nome");
            String pIva = request.getParameter("pIva");
            String password = request.getParameter("password");

            VenditoreBean venditore = new VenditoreBean();
            venditore.setCodiceFiscale(cf);
            venditore.setNome(nome);
            venditore.setPartitaIva(pIva);
            venditore.setPassword(password);

            try {
                venditoreModel.doSave(venditore);
                response.sendRedirect(request.getContextPath() + "/LoginControl?registered=true");
            } catch (SQLException e) {
                request.setAttribute("error", "Errore durante la registrazione venditore: " + e.getMessage());
                request.getRequestDispatcher("/WEB-INF/view/registration.jsp").forward(request, response);
            }
        } else {
            String cf = request.getParameter("cf");
            String nome = request.getParameter("nome");
            String dataNascita = request.getParameter("dataNascita");
            String email = request.getParameter("email");
            String indirizzo = request.getParameter("indirizzo");
            String password = request.getParameter("password");

            ClienteBean cliente = new ClienteBean();
            cliente.setCodiceFiscale(cf);
            cliente.setNome(nome);
            cliente.setDataDiNascita(java.sql.Date.valueOf(dataNascita));
            cliente.setEmail(email);
            cliente.setIndirizzo(indirizzo);
            cliente.setPassword(password);

            try {
                clienteModel.doSave(cliente);
                response.sendRedirect(request.getContextPath() + "/LoginControl?registered=true");
            } catch (SQLException e) {
                request.setAttribute("error", "Errore durante la registrazione: " + e.getMessage());
                request.getRequestDispatcher("/WEB-INF/view/registration.jsp").forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("checkEmail".equalsIgnoreCase(action)) {
            String email = request.getParameter("email");
            response.setContentType("application/json");
            try {
                ClienteBean c = clienteModel.doRetrieveByEmail(email);
                response.getWriter().write("{\"exists\": " + (c != null) + "}");
            } catch (SQLException e) {
                response.getWriter().write("{\"exists\": false, \"error\": true}");
            }
            return;
        }
        request.getRequestDispatcher("/WEB-INF/view/registration.jsp").forward(request, response);
    }
}
