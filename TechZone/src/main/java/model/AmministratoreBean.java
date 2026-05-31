package model;

import java.io.Serializable;
import java.sql.Date;

public class AmministratoreBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String codiceFiscale;
    private String nome;
    private String indirizzo;
    private Date dataDiNascita;
    private int eta;
    private String email;
    private String password;

    public AmministratoreBean() {}

    public String getCodiceFiscale() { return codiceFiscale; }
    public void setCodiceFiscale(String codiceFiscale) { this.codiceFiscale = codiceFiscale; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getIndirizzo() { return indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }

    public Date getDataDiNascita() { return dataDiNascita; }
    public void setDataDiNascita(Date dataDiNascita) { this.dataDiNascita = dataDiNascita; }

    public int getEta() { return eta; }
    public void setEta(int eta) { this.eta = eta; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
