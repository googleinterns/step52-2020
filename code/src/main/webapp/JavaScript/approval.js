function removeLines() {
  document.getElementById("wrapper-background").classList.add("remove")
}

function sendListToServlet() {
  const params = new URLSearchParams();
  params.append('idToken', localStorage.idToken);

  fetch(new Request('/delete-data-after-approval', {method: 'POST', body: params}));

  let emails = [];
  let systemMessageLanguage = [];
  let localityMessageLanguage = [];
  let emailMessageSubject = [];
  
  let emailIndex = 1;
  let checkedIndex = 2;
  let languageIndex = 3;
  var contacts = document.getElementById("contactsTable");
  var checkBoxesList = contacts.getElementsByTagName("INPUT");
  var checkBoxesListLength = checkBoxesList.length;
  var languageList = contacts.getElementsByTagName("SELECT");
  // var emailsListLength = emailsList.cells.length;
  var languageListLength = languageList.length;
  console.log(checkBoxesListLength);

  for(var index = 0; index < checkBoxesListLength; index++) {
    if (checkBoxesList[index].checked) {
      emails.push(document.getElementById("contactsTable").rows[index+1].cells[emailIndex].innerHTML);
      systemMessageLanguage.push(languageList[index].value);
    }
  }
  console.log(emails);
  console.log(language);
  params.append('emails', emails);
  params.append('systemMessagelanguage', systemMessageLanguage);

  fetch(new Request('/send-messages', {method: 'POST', body: params}));
}