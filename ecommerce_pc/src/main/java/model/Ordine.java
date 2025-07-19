package model;

import java.util.HashMap;

import java.util.Map;
import java.math.BigDecimal;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.List;



public class Ordine {
	private int idOrdine;
    private String codiceFiscaleCliente;
    private Date dataOrdine;
    private Time oraOrdine;
    private String indirizzo;
    private String citta;
    private String cap;
    private String metodo;
    private String numeroCarta;
    private String scadenzaCarta;
    private String cvv;
    private String emailPaypal;
    private double totaleOrdine;
    private int idAssistenza;
    private List<DettaglioOrdine> dettagli;

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
    
    public Date getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(Date dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public Time getOraOrdine() {
        return oraOrdine;
    }

    public void setOraOrdine(Time oraOrdine) {
        this.oraOrdine = oraOrdine;
    }
    
    public String getIndirizzoSpedizione() {
    	return indirizzo;
    }
    
    public void setIndirizzoSpedizione(String indirizzo) {
    	this.indirizzo= indirizzo;
    }
    
    public String getCitta() {
    	return citta;
    }
    
    public String getCap() {
    	return cap;
    }
    
    public String getMetodoPagamento() {
    	return metodo;
    }
    
    public void setCitta(String citta) {
    	this.citta = citta;
    }
    
    public void setCap(String cap) {
    	this.cap = cap;
    }
    
    public void setMetodoPagamento(String metodo) {
    	this.metodo = metodo;
    }
    
    public String getNumeroCarta() {
        return numeroCarta;
    }
    public void setNumeroCarta(String numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    public String getScadenzaCarta() {
        return scadenzaCarta;
    }
    public void setScadenzaCarta(String scadenzaCarta) {
        this.scadenzaCarta = scadenzaCarta;
    }

    public String getCvv() {
        return cvv;
    }
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getEmailPaypal() {
        return emailPaypal;
    }
    public void setEmailPaypal(String emailPaypal) {
        this.emailPaypal = emailPaypal;
    }
    
    public List<DettaglioOrdine> getDettagli() {
        return dettagli;
    }

    public void setDettagli(List<DettaglioOrdine> dettagli) {
        this.dettagli = dettagli;
    }

}
