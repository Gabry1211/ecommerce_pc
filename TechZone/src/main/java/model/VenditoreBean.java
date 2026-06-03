package model;

import java.io.Serializable;

public class VenditoreBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idVenditore;
    private String nome;
    private String partitaIva;
    private String codiceFiscale;
    private String password;

    public VenditoreBean() {}

    public int getIdVenditore() { return idVenditore; }
    public void setIdVenditore(int idVenditore) { this.idVenditore = idVenditore; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getPartitaIva() { return partitaIva; }
    public void setPartitaIva(String partitaIva) { this.partitaIva = partitaIva; }

    public String getCodiceFiscale() { return codiceFiscale; }
    public void setCodiceFiscale(String codiceFiscale) { this.codiceFiscale = codiceFiscale; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
