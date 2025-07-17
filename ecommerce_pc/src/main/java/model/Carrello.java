package model;

import java.util.*;

public class Carrello {
	private Map<Integer, ElementoCarrello> elementi = new HashMap<>();

	public void aggiungiProdotto(Prodotto p) {
	    int id = p.getIdProdotto();
	    int quantitaDaAggiungere = p.getQuantita(); // prendi la quantità dal prodotto passato
	    
	    if (elementi.containsKey(id)) {
	        ElementoCarrello elem = elementi.get(id);
	        elem.setQuantita(elem.getQuantita() + quantitaDaAggiungere);
	    } else {
	        elementi.put(id, new ElementoCarrello(p, quantitaDaAggiungere));
	    }
	}


    public void rimuoviProdotto(int idProdotto) {
        elementi.remove(idProdotto);
    }

    public void svuota() {
        elementi.clear();
    }

    public Collection<ElementoCarrello> getElementi() {
        return elementi.values();
    }

    public double getTotale() {
        return elementi.values().stream().mapToDouble(ElementoCarrello::getTotale).sum();
    }

    public boolean isEmpty() {
        return elementi.isEmpty();
    }
    
    public ElementoCarrello getElemento(int idProdotto) {
        return elementi.get(idProdotto);
    }
    
    public Map<Prodotto, Integer> getProdottiQuantita() {
        Map<Prodotto, Integer> mappa = new HashMap<>();
        for (ElementoCarrello elemento : elementi.values()) {
            mappa.put(elemento.getProdotto(), elemento.getQuantita());
        }
        return mappa;
    }
    
    public int getTotaleQuantita() {
        int totale = 0;
        for (ElementoCarrello elem : elementi.values()) {
            totale += elem.getQuantita();
        }
        return totale;
    }
    
    public void aggiungi(Prodotto prodotto, int quantita) {
        int idProdotto = prodotto.getIdProdotto();
        
        // Se il prodotto è già nel carrello, aumenta la quantità
        if (elementi.containsKey(idProdotto)) {
            ElementoCarrello esistente = elementi.get(idProdotto);
            esistente.setQuantita(esistente.getQuantita() + quantita);
        } else {
            // Altrimenti, crea un nuovo elemento e aggiungilo
            ElementoCarrello nuovo = new ElementoCarrello();
            nuovo.setProdotto(prodotto);
            nuovo.setQuantita(quantita);
            elementi.put(idProdotto, nuovo);
        }
    }


}
