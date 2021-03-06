function removeLines() {
  document.getElementById("wrapper-background").classList.add("remove")
}

function sendListToServlet() {
  const params = new URLSearchParams();
  params.append('idToken', localStorage.idToken);

  fetch(new Request('/delete-data-after-approval', {method: 'POST', body: params}));

  let emails = [];
  for (let contact of document.getElementsByClassName('contact')) {
    const input = contact.getElementsByClassName("container")[0].getElementsByTagName("input")[0];
    if (input.checked) {
       emails.push(contact.getElementsByClassName("email")[0].innerText);
    }
  }
  
  params.append('emails', emails);

  fetch(new Request('/send-messages', {method: 'POST', body: params}));
}