package control;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ClienteBean;
import model.OrdineBean;
import model.OrdineModel;
import model.ProdottoBean;
import model.ProductModel;
import dao.OrdineModelDS;
import dao.ProductModelDS;

@WebServlet("/OrderControl")
public class OrderControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static OrdineModel model = new OrdineModelDS();
    static ProductModel productModel = new ProductModelDS();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ClienteBean user = (ClienteBean) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/LoginControl");
            return;
        }

        String action = request.getParameter("action");

        try {
            if ("checkout".equalsIgnoreCase(action)) {
                String sessionToken = (String) session.getAttribute("token");
                String requestToken = request.getParameter("token");
                if (sessionToken == null || requestToken == null || !sessionToken.equals(requestToken)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }

                List<ProdottoBean> cart = (List<ProdottoBean>) session.getAttribute("cart");
                if (cart == null || cart.isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/CartControl?error=empty");
                    return;
                }
                request.getRequestDispatcher("/WEB-INF/view/checkout.jsp").forward(request, response);
                return;
            } else if ("confirmOrder".equalsIgnoreCase(action)) {
                String sessionToken = (String) session.getAttribute("token");
                String requestToken = request.getParameter("token");
                if (sessionToken == null || requestToken == null || !sessionToken.equals(requestToken)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }

                List<ProdottoBean> cart = (List<ProdottoBean>) session.getAttribute("cart");
                if (cart == null || cart.isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/CartControl");
                    return;
                }

                // Verifica disponibilità stock prima di procedere
                for (ProdottoBean p : cart) {
                    ProdottoBean dbProduct = productModel.doRetrieveByKey(p.getIdProdotto());
                    if (dbProduct.getQuantita() < p.getQuantita()) {
                        request.setAttribute("error", "Spiacenti, il prodotto '" + p.getDescrizione() + "' non ha abbastanza scorte (Disponibili: " + dbProduct.getQuantita() + ")");
                        request.getRequestDispatcher("/WEB-INF/view/cart.jsp").forward(request, response);
                        return;
                    }
                }

                OrdineBean ordine = new OrdineBean();
                ordine.setCodiceFiscale(user.getCodiceFiscale());
                
                String indirizzo = request.getParameter("indirizzo");
                String citta = request.getParameter("citta");
                String cap = request.getParameter("cap");
                
                StringBuilder listaProdotti = new StringBuilder();
                BigDecimal total = BigDecimal.ZERO;
                
                // Decrementa stock e prepara lista
                for (ProdottoBean p : cart) {
                    listaProdotti.append(p.getDescrizione()).append(" (x").append(p.getQuantita()).append("), ");
                    total = total.add(p.getPrezzo().multiply(new BigDecimal(p.getQuantita())));
                    
                    // Aggiorna stock nel DB
                    ProdottoBean dbProduct = productModel.doRetrieveByKey(p.getIdProdotto());
                    dbProduct.setQuantita(dbProduct.getQuantita() - p.getQuantita());
                    productModel.doUpdate(dbProduct);
                }
                
                ordine.setListaProdotti(listaProdotti.toString() + " - Spedito a: " + indirizzo + ", " + citta + " (" + cap + ")");
                ordine.setTotOrdine(total);

                model.doSave(ordine);
                cart.clear();
                session.setAttribute("cart", cart);
                response.sendRedirect(request.getContextPath() + "/OrderControl?success=true");
                return;
            } else {
                Collection<OrdineBean> orders = model.doRetrieveByCliente(user.getCodiceFiscale());
                request.setAttribute("orders", orders);
                request.getRequestDispatcher("/WEB-INF/view/orders.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Errore del database: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
