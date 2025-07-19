function aggiungiAlCarrello(button) {
    const idProdotto = button.getAttribute("data-id");
    const descrizione = button.getAttribute("data-descrizione");
    const prezzo = button.getAttribute("data-prezzo");
    const tipo = button.getAttribute("data-tipo");
    const immagine = button.getAttribute("data-immagine");
    const cfAdmin = button.getAttribute("data-cfadmin");
    const idVenditore = button.getAttribute("data-idvenditore");
    const quantita = document.getElementById("quantita").value;

    const params = new URLSearchParams();
    params.append("idProdotto", idProdotto);
    params.append("descrizione", descrizione);
    params.append("prezzo", prezzo);
    params.append("tipo", tipo);
    params.append("immagine", immagine);
    params.append("cfAdmin", cfAdmin);
    params.append("idVenditore", idVenditore);
    params.append("quantita", quantita);

    fetch("AggiungiAlCarrelloAjaxServlet", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: params
    })
    .then(response => {
        if (!response.ok) throw new Error("Errore AJAX");
        return response.text();
    })
    .then(nuovaQuantita => {
        document.getElementById("carrelloCounter").innerText = nuovaQuantita;
    })
    .catch(error => {
        console.error("Errore:", error);
    });
}