document.addEventListener('DOMContentLoaded', function() {
  const cartaRadio = document.getElementById('carta');
  const paypalRadio = document.getElementById('paypal');

  const formCarta = document.getElementById('cartaCreditoFields');
  const formPaypal = document.getElementById('paypalFields');

  function aggiornaForm() {
    if (cartaRadio.checked) {
      formCarta.style.display = 'block';
      formPaypal.style.display = 'none';
    } else if (paypalRadio.checked) {
      formCarta.style.display = 'none';
      formPaypal.style.display = 'block';
    } else {
      formCarta.style.display = 'none';
      formPaypal.style.display = 'none';
    }
  }

  cartaRadio.addEventListener('change', aggiornaForm);
  paypalRadio.addEventListener('change', aggiornaForm);

  // Chiamata iniziale per settare la visualizzazione giusta
  aggiornaForm();
});