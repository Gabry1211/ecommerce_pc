package model;

public class ElementoCarrello {
	private Prodotto prodotto;
    private int quantita;

    public ElementoCarrello(Prodotto prodotto, int quantita) {
        this.prodotto = prodotto;
        this.quantita = quantita;
    }
    
    public ElementoCarrello() {}

    public Prodotto getProdotto() { return prodotto; }
    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }

    public double getTotale() {
        return prodotto.getPrezzo() * quantita;
    }
    
    public void setProdotto(Prodotto prodotto) {
    	this.prodotto = prodotto;
    }

}
