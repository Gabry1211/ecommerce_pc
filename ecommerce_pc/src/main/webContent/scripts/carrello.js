function aggiornaQuantita(idProdotto) {
    var quantitaInput = document.getElementById("qta-" + idProdotto);
    if (!quantitaInput) {
        console.error("Elemento input quantità non trovato per idProdotto: " + idProdotto);
        return;
    }
    var nuovaQuantita = quantitaInput.value;

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "AggiornaQuantitaAjaxServlet", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log("Quantità aggiornata con successo");
            location.reload();  // o aggiorna dinamicamente senza ricaricare
        }
    };

    xhr.send("idProdotto=" + encodeURIComponent(idProdotto) + "&quantita=" + encodeURIComponent(nuovaQuantita));
}

function rimuoviProdotto(idProdotto) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "RimuoviDalCarrelloServlet", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var item = document.getElementById("item-" + idProdotto);
            if (item) item.remove();
            location.reload();
        }
    };

    xhr.send("idProdotto=" + encodeURIComponent(idProdotto));
}

function svuotaCarrello() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "SvuotaCarrelloAjaxServlet", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.querySelector(".carrello-items").innerHTML = "<h3>Il carrello è vuoto</h3>";
            document.querySelector(".carrello-totale").innerHTML = "";
        }
    };

    xhr.send(); // Nessun parametro necessario
}