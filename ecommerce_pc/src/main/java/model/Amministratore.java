package model;

public class Amministratore {
	private String codiceFiscale;
    private String nome;
    private String indirizzo;
    private String email;
    private int eta;
    private java.sql.Date dataDiNascita;
    private String password;

    // Getter e Setter
    public String getCodiceFiscale() { 
    	return codiceFiscale; 
    }
    
    public void setCodiceFiscale(String codiceFiscale) { 
    	this.codiceFiscale = codiceFiscale;
    }

    public String getNome() { 
    	return nome;
    }
    
    public void setNome(String nome) { 
    	this.nome = nome;
    }

    public String getIndirizzo() {
    	return indirizzo;
    }
    
    public void setIndirizzo(String indirizzo) {
    	this.indirizzo = indirizzo;
    }

    public String getEmail() {
    	return email;
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }

    public int getEta() {
    	return eta;
    }
    
    public void setEta(int eta) {
    	this.eta = eta;
    }

    public java.sql.Date getDataDiNascita() {
    	return dataDiNascita;
    }
    
    public void setDataDiNascita(java.sql.Date dataDiNascita) {
    	this.dataDiNascita = dataDiNascita;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
