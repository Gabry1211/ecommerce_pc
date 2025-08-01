function mostraErrore(idCampo, messaggio) {
    document.getElementById(idCampo + "Errore").innerText = messaggio;
}

function pulisciErrore(idCampo) {
    document.getElementById(idCampo + "Errore").innerText = "";
}


function validaRegistrazione() {
    let valido = true;

    // Pulizia messaggi precedenti
    document.querySelectorAll(".errore").forEach(e => e.textContent = "");

    const codiceFiscale = document.getElementById("codiceFiscale").value.trim();
    const nome = document.getElementById("nome").value.trim();
    const dataNascita = document.getElementById("dataNascita").value;
    const email = document.getElementById("email").value.trim();
    const indirizzo = document.getElementById("indirizzo").value.trim();
    const password = document.getElementById("password").value;

    // Codice Fiscale
    if (!/^[A-Z0-9]{16}$/i.test(codiceFiscale)) {
        document.getElementById("erroreCodiceFiscale").textContent = "Codice fiscale non valido (16 caratteri alfanumerici).";
        valido = false;
    }

    // Nome
    if (!/^[a-zA-ZÀ-ÿ\s]+$/.test(nome)) {
        document.getElementById("erroreNome").textContent = "Il nome può contenere solo lettere.";
        valido = false;
    }

    // Data di nascita
    const oggi = new Date().toISOString().split('T')[0];
    if (dataNascita >= oggi) {
        document.getElementById("erroreDataNascita").textContent = "La data di nascita deve essere nel passato.";
        valido = false;
    }

    // Email
    if (!/^\S+@\S+\.\S+$/.test(email)) {
        document.getElementById("erroreEmail").textContent = "Email non valida.";
        valido = false;
    }

    // Indirizzo
    if (indirizzo.length < 5) {
        document.getElementById("erroreIndirizzo").textContent = "L'indirizzo deve contenere almeno 5 caratteri.";
        valido = false;
    }

    // Password
    if (!/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(password)) {
        document.getElementById("errorePassword").textContent = "La password deve avere almeno 8 caratteri, una lettera e un numero.";
        valido = false;
    }

    return valido;
}

function validaLoginCliente() {
    let valido = true;

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    const regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!regexEmail.test(email)) {
        mostraErrore("email", "Inserisci un'email valida.");
        valido = false;
    } else {
        pulisciErrore("email");
    }

    if (password.length < 6) {
        mostraErrore("password", "La password deve contenere almeno 6 caratteri.");
        valido = false;
    } else {
        pulisciErrore("password");
    }

    return valido;
}

function validaRegistrazioneVenditore() {
    let valido = true;

    const nome = document.getElementById("nome").value.trim();
    const partitaIva = document.getElementById("partitaIva").value.trim();
    const codiceFiscale = document.getElementById("codiceFiscale").value.trim();
    const password = document.getElementById("password").value.trim();

    const regexCF = /^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$/i;
    const regexPIva = /^[0-9]{11}$/;

    if (nome.length < 2) {
        mostraErrore("nome", "Inserisci un nome valido.");
        valido = false;
    } else {
        pulisciErrore("nome");
    }

    if (!regexPIva.test(partitaIva)) {
        mostraErrore("partitaIva", "Inserisci una Partita IVA valida (11 cifre).");
        valido = false;
    } else {
        pulisciErrore("partitaIva");
    }

    if (!regexCF.test(codiceFiscale)) {
        mostraErrore("codiceFiscale", "Inserisci un Codice Fiscale valido.");
        valido = false;
    } else {
        pulisciErrore("codiceFiscale");
    }

    if (password.length < 6) {
        mostraErrore("password", "La password deve contenere almeno 6 caratteri.");
        valido = false;
    } else {
        pulisciErrore("password");
    }

    return valido;
}

function validaLoginVenditore() {
    let valido = true;

    const partitaIVA = document.getElementById("partitaIVA").value.trim();
    const password = document.getElementById("passwordVenditore").value.trim();

    const regexPIva = /^[0-9]{11}$/;

    if (!regexPIva.test(partitaIVA)) {
        mostraErrore("partitaIVA", "Inserisci una Partita IVA valida (11 cifre).");
        valido = false;
    } else {
        pulisciErrore("partitaIVA");
    }

    if (password.length < 6) {
        mostraErrore("passwordVenditore", "La password deve contenere almeno 6 caratteri.");
        valido = false;
    } else {
        pulisciErrore("passwordVenditore");
    }

    return valido;
}

function validaLoginAdmin() {
    let valido = true;

    const codiceFiscale = document.getElementById("codiceFiscale").value.trim();
    const password = document.getElementById("passwordAdmin").value.trim();

    // Codice fiscale: 16 caratteri alfanumerici (lettere maiuscole + numeri)
    const regexCF = /^[A-Z0-9]{16}$/;

    if (!regexCF.test(codiceFiscale)) {
        mostraErrore("codiceFiscale", "Inserisci un codice fiscale valido (16 caratteri maiuscoli e numeri).");
        valido = false;
    } else {
        pulisciErrore("codiceFiscale");
    }

    if (password.length < 6) {
        mostraErrore("passwordAdmin", "La password deve contenere almeno 6 caratteri.");
        valido = false;
    } else {
        pulisciErrore("passwordAdmin");
    }

    return valido;
}
