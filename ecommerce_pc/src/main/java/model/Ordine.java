package model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;

public class Ordine {
	private int idOrdine;
    private String codiceFiscaleCliente;
    private Date dataOrdine;
    private double totaleOrdine;
    private int idAssistenza;

    // Lista prodotti in formato stringa (es: "1:2,3:1")
    private String listaProdotti;

    private HashMap<Prodotto, Integer> prodottiQuantita; // prodotti con quantità

    public Ordine() {
        prodottiQuantita = new HashMap<>();
    }

    // Costruttore con parametri utili
    public Ordine(int idOrdine, String codiceFiscaleCliente, Date dataOrdine, double totaleOrdine, int idAssistenza) {
        this.idOrdine = idOrdine;
        this.codiceFiscaleCliente = codiceFiscaleCliente;
        this.dataOrdine = dataOrdine;
        this.totaleOrdine = totaleOrdine;
        this.idAssistenza = idAssistenza;
        this.prodottiQuantita = new HashMap<>();
    }

    // Getters e Setters
    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public String getCodiceFiscaleCliente() {
        return codiceFiscaleCliente;
    }

    public void setCodiceFiscaleCliente(String codiceFiscaleCliente) {
        this.codiceFiscaleCliente = codiceFiscaleCliente;
    }

    public Date getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(Date dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public double getTotaleOrdine() {
        return totaleOrdine;
    }

    public void setTotale(double totaleOrdine) {
        this.totaleOrdine = totaleOrdine;
    }
    
    public int getIdAssistenza() {
    	return idAssistenza;
    }
    
    public void setIdAssistenza(int idAssistenza) {
    	this.idAssistenza = idAssistenza;
    }

    public HashMap<Prodotto, Integer> getProdottiQuantita() {
        return prodottiQuantita;
    }

    public void setProdottiQuantita(Map<Prodotto, Integer> prodottiConQuantita) {
        this.prodottiQuantita = (HashMap<Prodotto, Integer>) prodottiConQuantita;
    }

    public void aggiungiProdotto(Prodotto prodotto, int quantita) {
        prodottiQuantita.put(prodotto, quantita);
    }

    // Getter e setter per la listaProdotti in formato stringa
    public String getListaProdotti() {
        return listaProdotti;
    }

    public void setListaProdotti(String listaProdotti) {
        this.listaProdotti = listaProdotti;
    }

}
