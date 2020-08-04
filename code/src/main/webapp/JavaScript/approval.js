function removeLines() {
  document.getElementById("wrapper-background").classList.add("remove")
}

function suPeRCooLFuNcTiONGoEsHerE() {
  const params = new URLSearchParams();
  params.append('idToken', localStorage.idToken);

  let emails = [];
  for (let contact of document.getElementsByClassName('contact')) {
    const input = contact.getElementsByClassName("container")[0].getElementsByTagName("input")[0];
    if (input.checked) {
       emails.push(contact.getElementsByClassName("email")[0].innerText);
    }
  }
  
  params.append('emails', emails);

  fetch(new Request('/message-sender', {method: 'POST', body: params}));
}