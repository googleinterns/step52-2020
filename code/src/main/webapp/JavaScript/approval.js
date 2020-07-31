function removeLines() {
  document.getElementById("wrapper-background").classList.add("remove")
}

function suPeRCooLFuNcTiONGoEsHerE() {
  const params = new URLSearchParams();
  params.append('idToken', localStorage.idToken);
  fetch(new Request('/message-sender', {method: 'POST', body: params}));

  for (let contact of document.getElementsByClassName('contact')) {
   if (contact.getElementsByTagName("input")[0].checked) {
       //do stuff
       console.log(contact.getElementsByTagName("p")[0])
   }
  }
}