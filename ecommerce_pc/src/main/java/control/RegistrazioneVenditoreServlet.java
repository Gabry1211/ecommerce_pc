package control;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DBConnection;
import model.Venditore;
import model.VenditoreDAO;

import java.io.IOException;

import java.sql.*;
import javax.naming.*;

import javax.sql.DataSource;


@WebServlet("/RegistrazioneVenditoreServlet")
public class RegistrazioneVenditoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  String nome = request.getParameter("nome");
	        String partitaIVA = request.getParameter("partitaIva");
	        String codiceFiscale = request.getParameter("codiceFiscale");

	        Venditore v = new Venditore();
	        v.setNome(nome);
	        v.setPartitaIVA(partitaIVA);
	        v.setCodiceFiscale(codiceFiscale);

	        VenditoreDAO dao = new VenditoreDAO();
	        try {
	            dao.registraVenditore(v);
	            response.sendRedirect("login.jsp");
	        } catch (SQLException e) {
	            e.printStackTrace();
	            response.sendRedirect("errore.jsp");
	        }
	}

}
