package model;

import java.util.*;

public class Carrello {
	private Map<Integer, ElementoCarrello> elementi = new HashMap<>();

    public void aggiungiProdotto(Prodotto p) {
        int id = p.getIdProdotto();
        if (elementi.containsKey(id)) {
            ElementoCarrello elem = elementi.get(id);
            elem.setQuantita(elem.getQuantita() + 1);
        } else {
            elementi.put(id, new ElementoCarrello(p, 1));
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

}
