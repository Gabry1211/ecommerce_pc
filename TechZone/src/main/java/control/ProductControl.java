package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ProductModel;
import dao.ProductModelDS;
import model.ProdottoBean;

@WebServlet("/ProductControl")
public class ProductControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static ProductModel model = new ProductModelDS();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String query = request.getParameter("q");

        try {
            if (action != null) {
                if (action.equalsIgnoreCase("detail")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    ProdottoBean product = model.doRetrieveByKey(id);
                    request.setAttribute("product", product);
                    request.getRequestDispatcher("/WEB-INF/view/product_detail.jsp").forward(request, response);
                    return;
                } else if (action.equalsIgnoreCase("search")) {
                    Collection<ProdottoBean> products = model.doRetrieveByQuery(query);
                    request.setAttribute("products", products);
                    request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(request, response);
                    return;
                } else if (action.equalsIgnoreCase("ajaxSearch")) {
                    Collection<ProdottoBean> products = model.doRetrieveByQuery(query);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    StringBuilder json = new StringBuilder("[");
                    int i = 0;
                    for (ProdottoBean p : products) {
                        json.append("{\"id\":").append(p.getIdProdotto())
                            .append(",\"desc\":\"").append(p.getDescrizione().replace("\"", "\\\"")).append("\"}");
                        if (++i < products.size()) json.append(",");
                        if (i >= 5) break; // Limita a 5 suggerimenti
                    }
                    json.append("]");
                    response.getWriter().write(json.toString());
                    return;
                }
            }
            
            String sort = request.getParameter("sort");
            Collection<ProdottoBean> products = model.doRetrieveAll(sort);
            
            // Se products è null (errore DB), inizializzalo come lista vuota per evitare loop
            if (products == null) {
                products = new java.util.ArrayList<>();
            }
            
            request.setAttribute("products", products);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("products", new java.util.ArrayList<ProdottoBean>());
        }

        request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
