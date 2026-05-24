package control;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrdineBean;
import model.OrdineModel;
import model.ProductModel;
import model.ProdottoBean;
import model.VenditoreModel;
import dao.OrdineModelDS;
import dao.ProductModelDS;
import dao.VenditoreModelDS;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.File;

@WebServlet("/AdminControl")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 1MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class AdminControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static ProductModel productModel = new ProductModelDS();
    static OrdineModel orderModel = new OrdineModelDS();
    static VenditoreModel venditoreModel = new VenditoreModelDS();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Boolean isAdmin = (Boolean) request.getSession().getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            response.sendRedirect(request.getContextPath() + "/LoginControl");
            return;
        }

        String action = request.getParameter("action");

        try {
            if ("dashboard".equalsIgnoreCase(action)) {
                request.getRequestDispatcher("/WEB-INF/view/admin/dashboard.jsp").forward(request, response);
                return;
            }
            if ("viewProducts".equalsIgnoreCase(action)) {
                request.setAttribute("products", productModel.doRetrieveAll(null));
                request.getRequestDispatcher("/WEB-INF/view/admin/products_list.jsp").forward(request, response);
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
                productModel.doDelete(id);
            } else if ("editProductLoad".equalsIgnoreCase(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                ProdottoBean p = productModel.doRetrieveByKey(id);
                request.setAttribute("productToEdit", p);
            } else if ("viewOrders".equalsIgnoreCase(action)) {
                String fromStr = request.getParameter("from");
                String toStr = request.getParameter("to");
                String cf = request.getParameter("cf");
                
                Date from = (fromStr != null && !fromStr.isEmpty()) ? Date.valueOf(fromStr) : null;
                Date to = (toStr != null && !toStr.isEmpty()) ? Date.valueOf(toStr) : null;
                
                Collection<OrdineBean> orders = orderModel.doRetrieveAll(from, to, cf);
                request.setAttribute("orders", orders);
                request.getRequestDispatcher("/WEB-INF/view/admin/orders_list.jsp").forward(request, response);
                return;
            }
            
            request.setAttribute("products", productModel.doRetrieveAll(null));
            request.getRequestDispatcher("/WEB-INF/view/admin/products_list.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                }
                
                p.setDescrizione(request.getParameter("descrizione"));
                p.setPrezzo(new BigDecimal(request.getParameter("prezzo")));
                p.setTipo(request.getParameter("tipo"));
                p.setQuantita(Integer.parseInt(request.getParameter("quantita")));

                if ("editProduct".equalsIgnoreCase(action)) {
                    // Recuperiamo l'ID_Venditore originale per non perderlo durante la modifica dell'Admin
                    ProdottoBean existing = productModel.doRetrieveByKey(p.getIdProdotto());
                    p.setIdVenditore(existing.getIdVenditore());
                } else {
                    // È un nuovo prodotto aggiunto dall'admin, quindi ID_Venditore rimane null
                    p.setIdVenditore(null);
                }

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
        response.sendRedirect(request.getContextPath() + "/AdminControl");
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
