package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class OrdineBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idOrdine;
    private String listaProdotti;
    private BigDecimal totOrdine;
    private int idAssistenza;
    private String codiceFiscale;
    private Date dataOrdine;

    public OrdineBean() {}

    public int getIdOrdine() { return idOrdine; }
    public void setIdOrdine(int idOrdine) { this.idOrdine = idOrdine; }

    public String getListaProdotti() { return listaProdotti; }
    public void setListaProdotti(String listaProdotti) { this.listaProdotti = listaProdotti; }

    public BigDecimal getTotOrdine() { return totOrdine; }
    public void setTotOrdine(BigDecimal totOrdine) { this.totOrdine = totOrdine; }

    public int getIdAssistenza() { return idAssistenza; }
    public void setIdAssistenza(int idAssistenza) { this.idAssistenza = idAssistenza; }

    public String getCodiceFiscale() { return codiceFiscale; }
    public void setCodiceFiscale(String codiceFiscale) { this.codiceFiscale = codiceFiscale; }

    public Date getDataOrdine() { return dataOrdine; }
    public void setDataOrdine(Date dataOrdine) { this.dataOrdine = dataOrdine; }
}
