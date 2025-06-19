package model;

import java.sql.Date;
import java.sql.Time;

public class Prodotto {
	private String descrizione, tipo, immagine;
    private double prezzo;
    private String cfAdmin, cfCliente;
    private int idVenditore;
    private Date dataAcquisto, dataConsegna;
    private Time oraAcquisto, oraConsegna;

    // Costruttori diversi per admin e venditore
    public Prodotto(String descrizione, double prezzo, String tipo, String immagine, String cfAdmin, int idVenditore) {
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.tipo = tipo;
        this.immagine = immagine;
        this.cfAdmin = cfAdmin;
        this.idVenditore = idVenditore;
        this.dataAcquisto = new Date(System.currentTimeMillis());
        this.oraAcquisto = new Time(System.currentTimeMillis());
    }

    // Getter
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

}
