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


}
