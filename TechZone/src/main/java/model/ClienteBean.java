package model;

import java.io.Serializable;
import java.sql.Date;

public class ClienteBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String codiceFiscale;
    private String nome;
    private Date dataDiNascita;
    private String email;
    private String indirizzo;
    private String password;

    public ClienteBean() {}

    public String getCodiceFiscale() { return codiceFiscale; }
    public void setCodiceFiscale(String codiceFiscale) { this.codiceFiscale = codiceFiscale; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Date getDataDiNascita() { return dataDiNascita; }
    public void setDataDiNascita(Date dataDiNascita) { this.dataDiNascita = dataDiNascita; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getIndirizzo() { return indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
