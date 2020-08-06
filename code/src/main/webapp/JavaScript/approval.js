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
  
  
  localStorage.setItem("email-addresses", emails);
  window.location = "../html/getCustomizableMessage.html";
}

function getCustomizableMessage() {
  var numberOfEmails = localStorage.getItem("email-addresses").length;
  var emails = localStorage.getItem("email-addresses");
  for(var i = 0; i < numberOfEmails; i++) {
    var para = document.createElement("p");
    var node = document.createTextNode(emails[i]);
    para.appendChild(node);
    var element = document.getElementById("div1");
    element.appendChild(para);
  }
}

function getFormData() {
  var formElements = document.getElementById("user-message-form").elements;
  localStorage.setItem("systemMessageLangauge", formElements["system-message-language"].value)
  localStorage.setItem("localityResourceLangauge", formElements["locality-resource-language"].value)
  localStorage.setItem("customMessage", formElements["custom-message-box"].value)
  var text;
  
  text +=  formElements["system-message-language"].value+ "<br>";
  text +=  formElements["locality-resource-language"].value+ "<br>";
  text +=  formElements["custom-message-box"].value+ "<br>";

  const params = new URLSearchParams();
  
  var emails = localStorage.getItem("email-addresses");
  
  params.append('idToken', localStorage.idToken);
  params.append('emails', emails);
  params.append('systemMessageLanguage', localStorage.systemMessageLangauge);
  params.append('localityResourceLanguage', localStorage.localityResourceLangauge);
  params.append('customMessage', localStorage.customMessage);

   fetch(new Request('/compile-user-email', {method: 'POST', body: params}))
   .then(response => response.text())
   .then(response => {
     document.getElementById("full-message").innerHTML = response;
     });
}

function sendEmails() {
  const params = new URLSearchParams();

  var emails = localStorage.getItem("email-addresses");
  
  params.append('idToken', localStorage.idToken);
  params.append('emails', emails);
  params.append('systemMessageLanguage', localStorage.systemMessageLangauge);
  params.append('localityResourceLanguage', localStorage.localityResourceLangauge);
  params.append('customMessage', localStorage.customMessage);
  var url = '/send-messages?idToken='+localStorage.idToken + "&systemMessageLanguage="+localStorage.systemMessageLangauge
    +"&localityResourceLanguage="+localStorage.localityResourceLangauge+"&customMessage="+localStorage.customMessage;

  fetch(new Request(url, {method: 'GET'}));
}