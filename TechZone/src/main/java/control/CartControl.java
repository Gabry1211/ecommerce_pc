package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ProdottoBean;
import model.ProductModel;
import dao.ProductModelDS;
import java.math.RoundingMode;

@WebServlet("/CartControl")
public class CartControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static ProductModel model = new ProductModelDS();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }

        boolean isAjax = "true".equalsIgnoreCase(request.getParameter("ajax"));
        if (!isAjax) {
            String accept = request.getHeader("Accept");
            if (accept != null && accept.toLowerCase().contains("application/json")) {
                isAjax = true;
            }
        }
        if (!isAjax) {
            String requestedWith = request.getHeader("X-Requested-With");
            if (requestedWith != null && !requestedWith.trim().isEmpty()) {
                isAjax = true;
            }
        }
        String errorMessage = null;
        String action = request.getParameter("action");
        String sessionToken = null;
        String requestToken = null;

        HttpSession session = request.getSession();
        List<ProdottoBean> cart = null;
        try {
            cart = (List<ProdottoBean>) session.getAttribute("cart");
        } catch (ClassCastException ex) {
            session.removeAttribute("cart");
        }
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        sessionToken = (String) session.getAttribute("token");
        requestToken = request.getParameter("token");

        try {
            if (action != null) {
                if (!"view".equalsIgnoreCase(action)) {
                    if (sessionToken == null || requestToken == null || !sessionToken.equals(requestToken)) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }
                }
                if (action.equalsIgnoreCase("add")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    ProdottoBean product = model.doRetrieveByKey(id);
                    if (product != null && product.getQuantita() > 0) {
                        boolean found = false;
                        for (ProdottoBean item : cart) {
                            if (item.getIdProdotto() == id) {
                                if (item.getQuantita() < product.getQuantita()) {
                                    item.setQuantita(item.getQuantita() + 1);
                                }
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            product.setQuantita(1);
                            cart.add(product);
                        }
                    }
                } else if (action.equalsIgnoreCase("remove")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    cart.removeIf(item -> item.getIdProdotto() == id);
                } else if (action.equalsIgnoreCase("clear")) {
                    cart.clear();
                } else if (action.equalsIgnoreCase("update")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    int qty;
                    try {
                        qty = Integer.parseInt(request.getParameter("quantity"));
                    } catch (Exception ex) {
                        qty = 0;
                    }

                    ProdottoBean dbProduct;
                    try {
                        dbProduct = model.doRetrieveByKey(id);
                    } catch (Exception ex) {
                        dbProduct = null;
                    }

                    int maxStock = (dbProduct != null) ? dbProduct.getQuantita() : Integer.MAX_VALUE;
                    if (maxStock < 0) maxStock = 0;

                    int finalQty = qty;
                    if (finalQty > maxStock) finalQty = maxStock;

                    for (int i = 0; i < cart.size(); i++) {
                        ProdottoBean item = cart.get(i);
                        if (item.getIdProdotto() == id) {
                            if (finalQty > 0) {
                                item.setQuantita(finalQty);
                            } else {
                                cart.remove(i);
                            }
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        session.setAttribute("cart", cart);

        if (isAjax) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);

            java.math.BigDecimal total = java.math.BigDecimal.ZERO;
            java.math.BigDecimal itemSubtotal = java.math.BigDecimal.ZERO;
            int cartCount = 0;
            int updatedId = -1;
            int updatedQty = 0;

            try {
                String idParam = request.getParameter("id");
                if (idParam != null) updatedId = Integer.parseInt(idParam);
            } catch (NumberFormatException e) {}

            try {
                for (ProdottoBean item : cart) {
                    java.math.BigDecimal price = (item.getPrezzo() != null) ? item.getPrezzo() : java.math.BigDecimal.ZERO;
                    int itemQty = Math.max(0, item.getQuantita());
                    java.math.BigDecimal sub = price.multiply(new java.math.BigDecimal(itemQty));
                    total = total.add(sub);
                    cartCount += itemQty;
                    if (item.getIdProdotto() == updatedId) {
                        itemSubtotal = sub;
                        updatedQty = itemQty;
                    }
                }
            } catch (Exception ex) {
                if (errorMessage == null) errorMessage = ex.getMessage();
            }

            if (errorMessage != null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

            String totalStr = total.setScale(2, RoundingMode.HALF_UP).toPlainString();
            String itemSubtotalStr = itemSubtotal.setScale(2, RoundingMode.HALF_UP).toPlainString();
            String errorJson = (errorMessage == null) ? "null" : "\"" + errorMessage.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
            response.getWriter().write("{\"cartCount\": " + cartCount + ", \"total\": \"" + totalStr + "\", \"itemSubtotal\": \"" + itemSubtotalStr + "\", \"updatedQty\": " + updatedQty + ", \"error\": " + errorJson + "}");
            return;
        }

        if (action == null || "view".equalsIgnoreCase(action)) {
            request.getRequestDispatcher("/WEB-INF/view/cart.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/CartControl");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
