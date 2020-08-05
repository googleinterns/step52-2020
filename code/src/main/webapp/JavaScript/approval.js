function removeLines() {
  document.getElementById("wrapper-background").classList.add("remove")
}

function sendListToServlet() {
  

  let emails = [];
  for (let contact of document.getElementsByClassName('contact')) {
    const input = contact.getElementsByClassName("container")[0].getElementsByTagName("input")[0];
    if (input.checked) {
       emails.push(contact.getElementsByClassName("email")[0].innerText);
    }
  }
  
  
  localStorage.setItem("email-addresses", emails);
  window.location = "../html/getCustomizableMessage.html";
  // getCustomizableMessage();
  // fetch(new Request('/send-messages', {method: 'POST', body: params}));
}

function getCustomizableMessage() {
  // var labelForEmailBoxes = document.createElement("label");
  // labelForEmailBoxes.setAttribute("for", "Emails");
  // labelForEmailBoxes.innerHTML = "Input email addresses below:";
  // document.getElementById("list-of-emails").appendChild(labelForEmailBoxes);
  // document.getElementById("list-of-emails").appendChild(document.createElement("br"));
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
  params.append('idToken', localStorage.idToken);
  var emails = localStorage.getItem("email-addresses");
  params.append('emails', emails);
  params.append('systemMessageLangauge', localStorage.systemMessageLangauge);
  params.append('localityResourceLangauge', localStorage.localityResourceLangauge);
  params.append('customMessage', localStorage.customMessage);
   fetch(new Request('/compile-user-email', {method: 'POST', body: params}))
   .then(response => respone.text())
   .then(response => {
     document.getElementById("full-message").innerHTML = response;
     console.log(response);
     });
}