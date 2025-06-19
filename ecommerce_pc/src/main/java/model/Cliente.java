package model;

import java.sql.Date;

public class Cliente {
	private String codiceFiscale, nome, email, indirizzo;
    private Date dataDiNascita;

    public Cliente(String codiceFiscale, String nome, Date dataDiNascita, String email, String indirizzo) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.dataDiNascita = dataDiNascita;
        this.email = email;
        this.indirizzo = indirizzo;
    }
    
    public void set_codiceFiscale(String codiceFiscale) {
    	this.codiceFiscale = codiceFiscale;
    }
    
    public String get_codiceFiscale() {
    	return codiceFiscale;
    }
    
    public void set_nome(String nome) {
    	this.nome = nome;
    }
    
    public String get_nome() {
    	return nome;
    }
    
    public void set_dataDiNascita(Date dataDiNascita) {
    	this.dataDiNascita = dataDiNascita;
    }
    
    public Date get_dataDiNascita() {
    	return dataDiNascita;
    }
    
    public void set_email(String email) {
    	this.email = email;
    }
    
    public String get_email() {
    	return email;
    }
    
    public void set_indirizzo(String indirizzo) {
    	this.indirizzo = indirizzo;
    }
    
    public String get_indirizzo() {
    	return indirizzo;
    }

}
