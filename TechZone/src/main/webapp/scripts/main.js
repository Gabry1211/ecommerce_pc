function getContextPath() {
    var path = window.location.pathname || "";
    var idx = path.indexOf("/", 1);
    if (idx === -1) return "";
    return path.substring(0, idx);
}

function closestTag(el, tagName) {
    var t = String(tagName || "").toUpperCase();
    var cur = el;
    while (cur && cur.nodeType === 1) {
        if (cur.tagName === t) return cur;
        cur = cur.parentNode;
    }
    return null;
}

function closestClass(el, className) {
    var cur = el;
    while (cur && cur.nodeType === 1) {
        if (cur.classList && cur.classList.contains(className)) return cur;
        cur = cur.parentNode;
    }
    return null;
}

document.addEventListener("DOMContentLoaded", function() {
    var loginType = document.getElementById("type");
    var loginUsername = document.getElementById("username");
    var loginUsernameLabel = document.getElementById("label-username");

    function updateLoginLabel() {
        if (!loginType || !loginUsername || !loginUsernameLabel) return;
        var type = loginType.value;
        if (type === "seller") {
            loginUsernameLabel.textContent = "Codice Fiscale";
            loginUsername.type = "text";
            loginUsername.placeholder = "Inserisci CF";
        } else {
            loginUsernameLabel.textContent = "Email";
            loginUsername.type = "email";
            loginUsername.placeholder = "";
        }
    }

    if (loginType) {
        loginType.addEventListener("change", updateLoginLabel);
        updateLoginLabel();
    }

    var userTypeSelect = document.getElementById("userType");
    function toggleRegistrationFields() {
        if (!userTypeSelect) return;

        var type = userTypeSelect.value;
        var fieldEmail = document.getElementById("field-email");
        var fieldPIva = document.getElementById("field-pIva");
        var fieldData = document.getElementById("field-dataNascita");
        var fieldIndirizzo = document.getElementById("field-indirizzo");
        var labelNome = document.getElementById("label-nome");

        var pIva = document.getElementById("pIva");
        var email = document.getElementById("email");
        var dataNascita = document.getElementById("dataNascita");
        var indirizzo = document.getElementById("indirizzo");

        if (type === "seller") {
            if (fieldEmail) fieldEmail.style.display = "none";
            if (fieldPIva) fieldPIva.style.display = "block";
            if (fieldData) fieldData.style.display = "none";
            if (fieldIndirizzo) fieldIndirizzo.style.display = "none";
            if (labelNome) labelNome.textContent = "Nome completo / Ragione Sociale";

            if (pIva) pIva.required = true;
            if (email) email.required = false;
            if (dataNascita) dataNascita.required = false;
            if (indirizzo) indirizzo.required = false;
        } else {
            if (fieldEmail) fieldEmail.style.display = "block";
            if (fieldPIva) fieldPIva.style.display = "none";
            if (fieldData) fieldData.style.display = "block";
            if (fieldIndirizzo) fieldIndirizzo.style.display = "block";
            if (labelNome) labelNome.textContent = "Nome completo";

            if (pIva) pIva.required = false;
            if (email) email.required = true;
            if (dataNascita) dataNascita.required = true;
            if (indirizzo) indirizzo.required = true;
        }
    }

    if (userTypeSelect) {
        userTypeSelect.addEventListener("change", toggleRegistrationFields);
        toggleRegistrationFields();
    }

    var regForm = document.getElementById("regForm");
    if (regForm) {
        var inputs = regForm.querySelectorAll("input");
        for (var i = 0; i < inputs.length; i++) {
            (function(input) {
                input.addEventListener("change", function() {
                    if (input.type === "hidden") return;
                    if (!isElementVisible(input)) return;
                    validateField(input);
                });
            })(inputs[i]);
        }

        regForm.addEventListener("submit", function(e) {
            var isValid = true;
            for (var j = 0; j < inputs.length; j++) {
                var inp = inputs[j];
                if (inp.type === "hidden") continue;
                if (!isElementVisible(inp)) continue;
                if (!validateField(inp)) isValid = false;
            }
            if (!isValid) e.preventDefault();
        });
    }

    var loginForm = document.getElementById("loginForm");
    if (loginForm) {
        loginForm.addEventListener("submit", function(e) {
            var username = document.getElementById("username");
            var password = document.getElementById("password");
            var typeEl = document.getElementById("type");
            var type = typeEl ? typeEl.value : "client";
            var isValid = true;

            if (type === "client" || type === "admin") {
                if (!validateEmail(username.value)) {
                    showError(username, "Inserisci un'email valida");
                    isValid = false;
                } else {
                    clearError(username);
                }
            } else {
                if (String(username.value || "").trim().length < 16) {
                    showError(username, "Inserisci un Codice Fiscale valido");
                    isValid = false;
                } else {
                    clearError(username);
                }
            }

            if (String(password.value || "").length < 6) {
                showError(password, "La password deve essere di almeno 6 caratteri");
                isValid = false;
            } else {
                clearError(password);
            }

            if (!isValid) e.preventDefault();
        });
    }
});

function isElementVisible(el) {
    return !!(el.offsetWidth || el.offsetHeight || el.getClientRects().length);
}

function validateField(input) {
    var id = input.id;
    var value = input.value;
    var isValid = true;
    var message = "";
    var userTypeEl = document.getElementById("userType");
    var userType = userTypeEl ? userTypeEl.value : null;

    switch (id) {
        case "nome":
            if (String(value || "").trim().length < 2) {
                isValid = false;
                message = "Il nome deve avere almeno 2 caratteri";
            }
            break;
        case "cf":
            var cfRegex = /^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$/i;
            if (!cfRegex.test(value)) {
                isValid = false;
                message = "Codice Fiscale non valido";
            }
            break;
        case "email":
            if (String(value || "").trim() === "" && !input.required) {
                isValid = true;
                break;
            }
            if (!validateEmail(value)) {
                isValid = false;
                message = "Email non valida";
            } else if (userType === "client") {
                var url = getContextPath() + "/RegistrationControl?action=checkEmail&email=" + encodeURIComponent(value);
                fetch(url)
                    .then(function(response) { return response.json(); })
                    .then(function(data) {
                        if (data && data.exists) showError(input, "Email già utilizzata");
                    })
                    .catch(function() {});
            }
            break;
        case "pIva":
            if (String(value || "").trim() === "" && !input.required) {
                isValid = true;
                break;
            }
            if (!/^\d{11}$/.test(String(value || "").trim())) {
                isValid = false;
                message = "Partita IVA non valida (11 cifre)";
            }
            break;
        case "password":
            if (String(value || "").length < 6) {
                isValid = false;
                message = "Almeno 6 caratteri richiesti";
            }
            break;
        case "dataNascita":
            if (!value && input.required) {
                isValid = false;
                message = "Data obbligatoria";
            }
            break;
        case "indirizzo":
            if (String(value || "").trim() === "" && input.required) {
                isValid = false;
                message = "Indirizzo obbligatorio";
            }
            break;
    }

    if (!isValid) showError(input, message);
    else clearError(input);

    return isValid;
}

document.addEventListener("click", function(e) {
    if (!e.target || !e.target.classList || !e.target.classList.contains("btn-add-cart")) return;

    var form = closestTag(e.target, "form");
    if (!form || !form.action || form.action.indexOf("CartControl") === -1) return;
    e.preventDefault();

    var url = new URL(form.action);
    var params = new URLSearchParams(new FormData(form));
    params.append("ajax", "true");

    fetch(url.pathname + "?" + params.toString())
        .then(function(response) { return response.json(); })
        .then(function(data) {
            var cartCount = document.querySelector(".cart-count");
            if (!cartCount) return;

            cartCount.textContent = data.cartCount;

            var originalText = e.target.textContent;
            e.target.textContent = "Aggiunto! ✓";
            e.target.style.background = "#28a745";
            e.target.style.color = "white";

            setTimeout(function() {
                e.target.textContent = originalText;
                e.target.style.background = "#ffd814";
                e.target.style.color = "black";
            }, 2000);
        })
        .catch(function(err) { console.error("Errore AJAX:", err); });
});

if (!window.__techzoneCartAjaxBound) {
    window.__techzoneCartAjaxBound = true;

    var cartUpdateTimers = {};

    function normalizeMoneyString(v) {
        if (v == null) return "";
        return String(v).replace("€", "").trim().replace(",", ".");
    }

    function parseMoneyToNumber(v) {
        var raw = normalizeMoneyString(v);
        var n = parseFloat(raw);
        return isFinite(n) ? n : null;
    }

    function formatMoney(v) {
        var n = Number(v);
        if (!isFinite(n)) return "";
        return n.toFixed(2);
    }

    function recalcCartSummary() {
        var inputs = document.querySelectorAll(".cart-qty-input");
        if (!inputs || inputs.length === 0) return;

        var totalQty = 0;
        var total = 0;

        for (var i = 0; i < inputs.length; i++) {
            var input = inputs[i];
            var id = input.getAttribute("data-id");
            var qty = parseInt(input.value, 10);
            if (isNaN(qty) || qty < 0) qty = 0;
            totalQty += qty;

            var rowSubtotal = null;
            var unit = parseMoneyToNumber(input.getAttribute("data-unit-price"));
            if (unit != null) {
                rowSubtotal = unit * qty;
            } else if (id) {
                var priceEl = document.getElementById("item-price-" + id);
                if (priceEl) rowSubtotal = parseMoneyToNumber(priceEl.textContent);
            }

            if (rowSubtotal != null) total += rowSubtotal;
        }

        var grandTotalEl = document.getElementById("cart-grand-total");
        if (grandTotalEl) {
            grandTotalEl.innerHTML = "Subtotale (" + totalQty + " articoli): <strong>€" + formatMoney(total) + "</strong>";
        }

        var cartRight = document.querySelector(".cart-right");
        if (cartRight) cartRight.style.display = totalQty > 0 ? "block" : "none";

        var cartCountEl = document.querySelector(".cart-count");
        if (cartCountEl) cartCountEl.textContent = String(totalQty);
    }

    function applyCartUpdateUI(id, data, input) {
        if (input && typeof data.updatedQty === "number" && !isNaN(data.updatedQty)) {
            input.value = data.updatedQty;
        }

        var itemPrice = document.getElementById("item-price-" + id);
        if (itemPrice && data.itemSubtotal != null) {
            itemPrice.textContent = "€" + data.itemSubtotal;
        }

        if (input) {
            var qty = parseInt(input.value, 10);
            if (isNaN(qty) || qty < 0) qty = 0;
            var unit = parseMoneyToNumber(input.getAttribute("data-unit-price"));
            if (unit != null && itemPrice) {
                itemPrice.textContent = "€" + formatMoney(unit * qty);
            }
        }

        recalcCartSummary();
    }

    function sendCartUpdate(form, id, qty, input, allowFallbackReload) {
        var params = new URLSearchParams(new FormData(form));
        params.set("quantity", String(qty));
        params.append("ajax", "true");

        var fallbackParams = new URLSearchParams(new FormData(form));
        fallbackParams.set("quantity", String(qty));
        var fallbackUrl = form.action + "?" + fallbackParams.toString();

        var formAction = getContextPath() + "/CartControl";
        var fetchUrl = new URL(formAction, window.location.origin);
        fetchUrl.search = params.toString();

        fetch(fetchUrl.href, {
            method: "GET",
            headers: {
                "Accept": "application/json",
                "X-Requested-With": "fetch"
            },
            cache: "no-store"
        })
            .then(function(response) {
                return response.text().then(function(text) {
                    var data = null;
                    try {
                        data = JSON.parse(text);
                    } catch (e) {
                        if (allowFallbackReload) {
                            window.location.href = fallbackUrl;
                            return null;
                        }
                        throw new Error("Risposta non valida dal server (status " + response.status + "): " + text.substring(0, 200));
                    }
                    if (!response.ok) {
                        var msg = (data && data.error) ? data.error : "Errore server";
                        if (allowFallbackReload) {
                            window.location.href = fallbackUrl;
                            return null;
                        }
                        throw new Error(msg);
                    }
                    return data;
                });
            })
            .then(function(data) {
                if (!data) return;

                if (qty <= 0) {
                    var cartItem = input ? closestClass(input, "cart-item") : null;
                    if (cartItem && cartItem.parentNode) cartItem.parentNode.removeChild(cartItem);

                    applyCartUpdateUI(id, data, input);

                    var remainingItems = document.querySelectorAll(".cart-item").length;
                    if (remainingItems === 0) {
                        window.location.href = getContextPath() + "/CartControl";
                    }
                    return;
                }

                applyCartUpdateUI(id, data, input);
            })
            .catch(function(err) {
                console.error("Errore aggiornamento carrello:", err && err.message ? err.message : err);
            });
    }

    function scheduleCartUpdate(input, immediate) {
        var form = closestTag(input, "form");
        if (!form) return;

        var id = input.getAttribute("data-id");
        var parsedQty = parseInt(input.value, 10);
        if (isNaN(parsedQty)) return;

        var existing = cartUpdateTimers[id];
        if (existing) clearTimeout(existing);

        if (immediate) {
            sendCartUpdate(form, id, parsedQty, input, true);
            return;
        }

        cartUpdateTimers[id] = setTimeout(function() {
            sendCartUpdate(form, id, parsedQty, input, false);
        }, 250);
    }

    document.addEventListener("input", function(e) {
        if (e.target && e.target.classList && e.target.classList.contains("cart-qty-input")) {
            scheduleCartUpdate(e.target, false);
        }
    });

    document.addEventListener("change", function(e) {
        if (e.target && e.target.classList && e.target.classList.contains("cart-qty-input")) {
            scheduleCartUpdate(e.target, true);
        }
    });
}

function validateEmail(email) {
    var re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

function showError(input, message) {
    var errorDiv = document.getElementById(input.id + "Error");
    if (errorDiv) {
        errorDiv.textContent = message;
        input.style.borderColor = "#c40000";
    }
}

function clearError(input) {
    var errorDiv = document.getElementById(input.id + "Error");
    if (errorDiv) {
        errorDiv.textContent = "";
        input.style.borderColor = "#a6a6a6";
    }
}

var searchInput = document.querySelector(".header-search input[name='q']");
var suggestionsBox = document.getElementById("search-suggestions");

if (searchInput && suggestionsBox) {
    searchInput.addEventListener("input", function() {
        var query = String(this.value || "").trim();
        if (query.length < 2) {
            suggestionsBox.style.display = "none";
            return;
        }

        var url = getContextPath() + "/ProductControl?action=ajaxSearch&q=" + encodeURIComponent(query);
        fetch(url)
            .then(function(response) { return response.json(); })
            .then(function(data) {
                if (data && data.length > 0) {
                    suggestionsBox.innerHTML = "";
                    for (var i = 0; i < data.length; i++) {
                        (function(item) {
                            var div = document.createElement("div");
                            div.style.padding = "10px";
                            div.style.cursor = "pointer";
                            div.style.borderBottom = "1px solid #eee";
                            div.style.color = "#333";
                            div.textContent = item.desc;
                            div.addEventListener("click", function() {
                                window.location.href = getContextPath() + "/ProductControl?action=detail&id=" + item.id;
                            });
                            div.addEventListener("mouseover", function() { div.style.background = "#f3f3f3"; });
                            div.addEventListener("mouseout", function() { div.style.background = "white"; });
                            suggestionsBox.appendChild(div);
                        })(data[i]);
                    }
                    suggestionsBox.style.display = "block";
                } else {
                    suggestionsBox.style.display = "none";
                }
            })
            .catch(function(err) { console.error("Errore suggerimenti:", err); });
    });

    document.addEventListener("click", function(e) {
        if (!searchInput.contains(e.target) && !suggestionsBox.contains(e.target)) {
            suggestionsBox.style.display = "none";
        }
    });
}
