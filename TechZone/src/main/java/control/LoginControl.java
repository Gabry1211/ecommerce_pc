package control;

import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.AdminModel;
import model.AmministratoreBean;
import model.ClienteBean;
import model.ClienteModel;
import model.VenditoreBean;
import model.VenditoreModel;
import dao.AdminModelDS;
import dao.ClienteModelDS;
import dao.VenditoreModelDS;

@WebServlet("/LoginControl")
public class LoginControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static ClienteModel clienteModel = new ClienteModelDS();
    static AdminModel adminModel = new AdminModelDS();
    static VenditoreModel venditoreModel = new VenditoreModelDS();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username"); // Può essere Email o CF
        String password = request.getParameter("password");
        String type = request.getParameter("type"); // "client", "admin", or "seller"

        HttpSession session = request.getSession();

        try {
            if ("admin".equalsIgnoreCase(type)) {
                if (adminModel.checkLogin(username, password)) {
                    AmministratoreBean admin = adminModel.doRetrieveByEmail(username);
                    session.setAttribute("admin", admin);
                    session.setAttribute("isLoggedIn", true);
                    session.setAttribute("isAdmin", true);
                    session.setAttribute("isSeller", false);
                    response.sendRedirect(request.getContextPath() + "/AdminControl?action=dashboard");
                    return;
                }
            } else if ("seller".equalsIgnoreCase(type)) {
                // Per il venditore il checkLogin usa il CF
                if (venditoreModel.checkLogin(username, password)) {
                    VenditoreBean seller = venditoreModel.doRetrieveByKey(username);
                    session.setAttribute("seller", seller);
                    session.setAttribute("isLoggedIn", true);
                    session.setAttribute("isAdmin", false);
                    session.setAttribute("isSeller", true);
                    response.sendRedirect(request.getContextPath() + "/VenditoreControl?action=dashboard");
                    return;
                }
            } else {
                if (clienteModel.checkLogin(username, password)) {
                    ClienteBean cliente = clienteModel.doRetrieveByEmail(username);
                    session.setAttribute("user", cliente);
                    session.setAttribute("isLoggedIn", true);
                    session.setAttribute("isAdmin", false);
                    session.setAttribute("isSeller", false);
                    response.sendRedirect(request.getContextPath() + "/Home");
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Errore durante il login: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
            return;
        }

        String errorMsg = "client".equalsIgnoreCase(type) ? "Email o password errati" : 
                         ("seller".equalsIgnoreCase(type) ? "Codice Fiscale o password errati" : "Credenziali admin errate");
        
        request.setAttribute("error", errorMsg);
        request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("logout".equalsIgnoreCase(action)) {
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath() + "/Home");
        } else {
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
        }
    }
}
