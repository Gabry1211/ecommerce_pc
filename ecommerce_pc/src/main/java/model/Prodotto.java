package model;

import java.sql.Date;
import java.sql.Time;

public class Prodotto {
	private int idProdotto;
	private String nome, descrizione, tipo, immagine;
    private double prezzo;
    private String cfAdmin, cfCliente;
    private int idVenditore;
    private Date dataAcquisto, dataConsegna;
    private Time oraAcquisto, oraConsegna;
    private int quantita;

    // Costruttori diversi per admin e venditore
    public Prodotto(int idProdotto, String descrizione, double prezzo, String tipo, String immagine, String cfAdmin, int idVenditore, int quantita) {
        this.idProdotto = idProdotto;
    	this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.tipo = tipo;
        this.immagine = immagine;
        this.cfAdmin = cfAdmin;
        this.idVenditore = idVenditore;
        this.dataAcquisto = new Date(System.currentTimeMillis());
        this.oraAcquisto = new Time(System.currentTimeMillis());
        this.quantita = quantita;
    }
    
    public Prodotto() {}

    // Getter
    public int getIdProdotto() {
    	return idProdotto;
    }
    
    public String getNome() {
    	return nome;
    }
    
    public String getDescrizione() {
    	return descrizione;
    }
    
    public double getPrezzo() {
    	return prezzo;
    }
    
    public String getTipo() {
    	return tipo;
    }
    
    public String getImmagine() {
    	return immagine;
    }
    
    public String getCfAdmin() {
    	return cfAdmin;
    }
    
    public int getIdVenditore() {
    	return idVenditore;
    }
    
    public Date getDataAcquisto() {
    	return dataAcquisto;
    }
    
    public Time getOraAcquisto() {
    	return oraAcquisto;
    }
    
    public int getQuantita() {
    	return quantita;
    }
    
//    Setter
    
    public void setIdProdotto(int idProdotto) {
    	this.idProdotto = idProdotto;
    }
    
    public void setNome(String nome) {
    	this.nome = nome;
    }
    
    public void setDescrizione(String descrizione) {
    	this.descrizione = descrizione;
    }
    
    public void setPrezzo(double prezzo) {
    	this.prezzo = prezzo;
    }
    
    public void setTipo(String tipo) {
    	this.tipo = tipo;
    }
    
    public void setIdVenditore(int idVenditore) {
    	this.idVenditore = idVenditore;
    }
    
    public void setImmagine(String immagine) {
    	this.immagine = immagine;
    }
    
    public void setQuantita(int quantita) {
    	this.quantita = quantita;
    }
    
    public void setCfAdmin(String cfAdmin) {
    	this.cfAdmin = cfAdmin;
    }

}
