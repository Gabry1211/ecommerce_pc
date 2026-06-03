package model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProdottoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idProdotto;
    private String descrizione;
    private BigDecimal prezzo;
    private String tipo;
    private String percorsoImmagine;
    private int quantita;
    private Integer idVenditore;

    public ProdottoBean() {}

    public int getIdProdotto() { return idProdotto; }
    public void setIdProdotto(int idProdotto) { this.idProdotto = idProdotto; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public BigDecimal getPrezzo() { return prezzo; }
    public void setPrezzo(BigDecimal prezzo) { this.prezzo = prezzo; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getPercorsoImmagine() { return percorsoImmagine; }
    public void setPercorsoImmagine(String percorsoImmagine) { this.percorsoImmagine = percorsoImmagine; }

    public String getFullImagePath(String contextPath) {
        if (percorsoImmagine == null || percorsoImmagine.isEmpty()) {
            return "https://via.placeholder.com/200x200?text=No+Image";
        }
        if (percorsoImmagine.toLowerCase().startsWith("http")) {
            return percorsoImmagine;
        }
        
        String cleanPath = percorsoImmagine.replace("\\", "/");
        if (cleanPath.startsWith("/")) {
            cleanPath = cleanPath.substring(1);
        }
        
        if (!cleanPath.toLowerCase().startsWith("images/")) {
            cleanPath = "images/" + cleanPath;
        }
        
        // Usiamo un percorso relativo al context path
        return contextPath + "/" + cleanPath;
    }

    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }

    public Integer getIdVenditore() { return idVenditore; }
    public void setIdVenditore(Integer idVenditore) { this.idVenditore = idVenditore; }
}
