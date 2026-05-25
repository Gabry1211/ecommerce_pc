package control;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrdineBean;
import model.OrdineModel;
import model.ProdottoBean;
import model.ProductModel;
import dao.ProductModelDS;
import model.VenditoreBean;
import dao.OrdineModelDS;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.File;

@WebServlet("/VenditoreControl")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
                 maxFileSize = 1024 * 1024 * 10,
                 maxRequestSize = 1024 * 1024 * 50)
public class VenditoreControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static ProductModel productModel = new ProductModelDS();
    static OrdineModel orderModel = new OrdineModelDS();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Boolean isSeller = (Boolean) request.getSession().getAttribute("isSeller");
        VenditoreBean seller = (VenditoreBean) request.getSession().getAttribute("seller");

        if (isSeller == null || !isSeller || seller == null) {
            response.sendRedirect(request.getContextPath() + "/LoginControl");
            return;
        }

        String action = request.getParameter("action");

        try {
            if ("dashboard".equalsIgnoreCase(action)) {
                request.getRequestDispatcher("/WEB-INF/view/venditore/dashboard.jsp").forward(request, response);
                return;
            }
            if ("viewOrders".equalsIgnoreCase(action)) {
                Collection<OrdineBean> orders = orderModel.doRetrieveByVenditore(seller.getIdVenditore());
                request.setAttribute("orders", orders);
                request.getRequestDispatcher("/WEB-INF/view/venditore/orders_list.jsp").forward(request, response);
                return;
            }
            if ("deleteProduct".equalsIgnoreCase(action)) {
                String sessionToken = (String) request.getSession().getAttribute("token");
                String requestToken = request.getParameter("token");
                if (sessionToken == null || requestToken == null || !sessionToken.equals(requestToken)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }

                int id = Integer.parseInt(request.getParameter("id"));
                // Verifica che il prodotto appartenga al venditore prima di cancellare
                ProdottoBean p = productModel.doRetrieveByKey(id);
                if (p.getIdVenditore() == seller.getIdVenditore()) {
                    productModel.doDelete(id);
                }
            } else if ("editProductLoad".equalsIgnoreCase(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                ProdottoBean p = productModel.doRetrieveByKey(id);
                if (p.getIdVenditore() == seller.getIdVenditore()) {
                    request.setAttribute("productToEdit", p);
                }
            }
            
            // Recupera solo i prodotti del venditore tramite ID_Venditore
            Collection<ProdottoBean> products = productModel.doRetrieveByVenditore(seller.getIdVenditore());
            request.setAttribute("products", products);
            request.getRequestDispatcher("/WEB-INF/view/venditore/products_list.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Boolean isSeller = (Boolean) request.getSession().getAttribute("isSeller");
        VenditoreBean seller = (VenditoreBean) request.getSession().getAttribute("seller");

        if (isSeller == null || !isSeller || seller == null) {
            response.sendRedirect(request.getContextPath() + "/LoginControl");
            return;
        }

        String sessionToken = (String) request.getSession().getAttribute("token");
        String requestToken = request.getParameter("token");
        if (sessionToken == null || requestToken == null || !sessionToken.equals(requestToken)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String action = request.getParameter("action");
        try {
            if ("addProduct".equalsIgnoreCase(action) || "editProduct".equalsIgnoreCase(action)) {
                ProdottoBean p = new ProdottoBean();
                String idStr = request.getParameter("id");
                
                if (idStr != null && !idStr.isEmpty()) {
                    p.setIdProdotto(Integer.parseInt(idStr));
                    // Verifica proprietà se è un edit
                    ProdottoBean existing = productModel.doRetrieveByKey(p.getIdProdotto());
                    if (existing.getIdVenditore() != seller.getIdVenditore()) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }
                }
                
                p.setDescrizione(request.getParameter("descrizione"));
                p.setPrezzo(new BigDecimal(request.getParameter("prezzo")));
                p.setTipo(request.getParameter("tipo"));
                p.setQuantita(Integer.parseInt(request.getParameter("quantita")));
                p.setIdVenditore(seller.getIdVenditore());

                // Gestione upload immagine
                Part filePart = request.getPart("immagine");
                String fileName = extractFileName(filePart);
                if (fileName != null && !fileName.isEmpty()) {
                    // 1. Salvataggio nella cartella di deploy (per visione immediata)
                    String deployPath = getServletContext().getRealPath("/") + "images";
                    File deployDir = new File(deployPath);
                    if (!deployDir.exists()) deployDir.mkdir();
                    filePart.write(deployPath + File.separator + fileName);
                    
                    // 2. Backup nella cartella sorgente del progetto (per persistenza al riavvio)
                    String projectPath = getServletContext().getRealPath("/").split(".metadata")[0] + "TechZone/src/main/webapp/images";
                    File projectDir = new File(projectPath);
                    if (projectDir.exists()) {
                        try {
                            filePart.write(projectPath + File.separator + fileName);
                        } catch (Exception e) {
                            System.out.println("Backup in project folder failed (safe to ignore): " + e.getMessage());
                        }
                    }
                    
                    p.setPercorsoImmagine(fileName);
                } else if (idStr != null && !idStr.isEmpty()) {
                    ProdottoBean old = productModel.doRetrieveByKey(p.getIdProdotto());
                    p.setPercorsoImmagine(old.getPercorsoImmagine());
                }

                if ("editProduct".equalsIgnoreCase(action)) {
                    productModel.doUpdate(p);
                } else {
                    productModel.doSave(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/VenditoreControl");
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return null;
    }
}
