function removeLines() {
  document.getElementById("wrapper-background").classList.add("remove")
}

function sendListToServlet() {
  const params = new URLSearchParams();
  params.append('idToken', localStorage.idToken);

  fetch(new Request('/delete-data-after-approval', {method: 'POST', body: params}));

  let emails = [];
  let systemMessageLanguages = [];
  let localityResourceLanguages = [];
  let emailSubjectLanguages = [];
  let systemMessageVersions = [];
  let localityResourceVersions = [];
  let emailSubjectVersions = [];
  
  let emailIndex = 1;
  // let checkedIndex = 2;
  // let languageIndex = 3;
  var contacts = document.getElementById("contactsTable");
  var checkBoxesList = contacts.getElementsByTagName("INPUT");
  var localityResourceLanguage = contacts.getElementsByClassName("localityResourceLanguage");
  var emailSubjectLanguage = contacts.getElementsByClassName("emailSubjectLanguage");
  var systemMessageLanguage = contacts.getElementsByClassName("systemMessageLanguage");
  var systemMessageVersion = contacts.getElementsByClassName("systemMessageVersion");
  var localityResourceVersion = contacts.getElementsByClassName("localityResourceVersion");
  var emailSubjectVersion = contacts.getElementsByClassName("emailSubjectVersion");
  console.log(localityResourceLanguage);
  var checkBoxesListLength = checkBoxesList.length;
  // var languageList = contacts.getElementsByTagName("SELECT");
  // var emailsListLength = emailsList.cells.length;
  // var languageListLength = languageList.length;
  console.log(checkBoxesListLength);

  for(var index = 0; index < checkBoxesListLength; index++) {
    if (checkBoxesList[index].checked) {
      emails.push(document.getElementById("contactsTable").rows[index+1].cells[emailIndex].innerHTML);
      systemMessageLanguages.push(systemMessageLanguage[index].value);
      // console.log("AC. "+systemMessageLanguage[index].value);
      localityResourceLanguages.push(localityResourceLanguage[index].value);
      // console.log("AA. "+localityResourceLanguage[index].value);
      emailSubjectLanguages.push(emailSubjectLanguage[index].value);
      // console.log("AB. "+emailSubjectLanguage[index].value);
      systemMessageVersions.push(systemMessageVersion[index].value);
      localityResourceVersions.push(localityResourceVersion[index].value);
      emailSubjectVersions.push(emailSubjectVersion[index].value);
    }
  }
  params.append('emails', emails);
  params.append('systemMessageLanguages', systemMessageLanguages);
  params.append('localityResourceLanguages', localityResourceLanguages);
  params.append('emailSubjectLanguages', emailSubjectLanguages);
  params.append('systemMessageVersions', systemMessageVersions);
  params.append('localityResourceVersions', localityResourceVersions);
  params.append('emailSubjectVersions', emailSubjectVersions);

  fetch(new Request('/send-messages', {method: 'POST', body: params}));
  window.location = "../html/userCustomizeMessage.html"
}

function displayCompiledMessage() {
  const params = new URLSearchParams();
  params.append('idToken', localStorage.idToken);

  params.append('customMessage', localStorage.customMessage);
  var messages;
  fetch(new Request('/compile-user-email', {method: 'POST', body: params}))
  .then(response => {
    response.text();
    console.log("woohooo");})
  .then(response => {
    console.log("woop");
    messages = response;
    console.log(messages);
    console.log("helloeeee");
    });
  console.log(messages);
  console.log(typeof(messages));
}

function sendEmails() {
  // const params = new URLSearchParams();

  // var emails = localStorage.getItem("email-addresses");
  
  // params.append('idToken', localStorage.idToken);
  // params.append('emails', emails);
  // params.append('systemMessageLanguage', localStorage.systemMessageLangauge);
  // params.append('localityResourceLanguage', localStorage.localityResourceLangauge);
  // params.append('customMessage', localStorage.customMessage);
  // var url = '/send-messages?idToken='+localStorage.idToken + "&systemMessageLanguage="+localStorage.systemMessageLangauge
  //   +"&localityResourceLanguage="+localStorage.localityResourceLangauge+"&customMessage="+localStorage.customMessage;
  var url = '/send-messages?idToken='+localStorage.idToken + "&customMessage="+localStorage.customMessage;

  fetch(new Request(url, {method: 'GET'}));
}