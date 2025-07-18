package model;

public class ElementoOrdine {

	private Prodotto prodotto;
    private int quantita;
    private double prezzo; // Prezzo al momento dell'acquisto

    public ElementoOrdine(Prodotto prodotto, int quantita, double prezzo) {
        this.prodotto = prodotto;
        this.quantita = quantita;
        this.prezzo = prezzo;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public double getPrezzo() {
        return prezzo;
    }

}
