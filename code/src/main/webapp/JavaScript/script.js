class LandingPage {
  constructor() {
    this.background = document.getElementById("wrapper-background");
  }

  show() {
    this.background.classList.add("landing-background");
  }

  hide() {
    this.background.classList.remove("landing-background");
  }
}

class LoginPage {
  constructor() {
    this.background = document.getElementById("wrapper-background");
  }

  show() {
    this.background.classList.add("login-background");
    startupGoogleLogin();
    startApp();
  }

  hide() {
    this.background.classList.remove("login-background");
  }
}

class NegativeLoginPage {
  constructor() {
    this.background = document.getElementById("wrapper-background");
  }

  show() {
    this.background.classList.add("login-background");
    startupGoogleLogin();
    startApp();
  }

  hide() {
    this.background.classList.remove("login-background");
  }
}

class NotificationPage {
  constructor() {
    this.background = document.getElementById("wrapper-background");
  }

  show() {
    this.background.classList.add("notification");
  }

  hide() {
    this.background.classList.remove("notification");
  }
}

var listOfClassesHiddenStatus = {"landing" : true, "login" : true, "negative-login" : true, "notification" : true};

class PageController {
  constructor() {
    this.landingPage = new LandingPage();
    this.loginPage = new LoginPage();
    this.negativePage = new NegativeLoginPage();
    this.notificationPage = new NotificationPage();
    this.currentlyShown = undefined;
    this.idToken = undefined;
  }

  hideCurrentPage() {
    if (this.currentlyShown != undefined) {
      this.currentlyShown.hide()
    }
  }

  show(pageToShow) {
    // Put the history.pushState here!
    this.hideCurrentPage();
    switch(pageToShow) {
      case "landing":
        this.setPageState(pageToShow, 'Online Contact Tracing', this.landingPage);
        break;
      case "login":
        this.setPageState(pageToShow, 'Login', this.loginPage);
        break;
      case "negative-login":
        this.setPageState(pageToShow, 'Login', this.negativePage);
        break;
      case "notification":
        this.setPageState(pageToShow, 'Thank You', this.notificationPage);
        break;
    }
  }

  setPageState(pageToShow, pageName, thisPage) {
    history.pushState({page: pageToShow}, pageName, pageToShow);
    thisPage.show();
    this.currentlyShown = thisPage;
    for (const [key, value] of Object.entries(listOfClassesHiddenStatus)) {
      listOfClassesHiddenStatus[key] = true;
    }
    listOfClassesHiddenStatus[pageToShow] = false;
    this.setFadeoutAndHiddenStatus(listOfClassesHiddenStatus);
  }

  setFadeoutAndHiddenStatus(listOfClassesHiddenStatus) {
    this.setStatus(listOfClassesHiddenStatus, "fade-out", "fade-in");
    setTimeout(this.setStatus, 900, listOfClassesHiddenStatus, "hidden", "");
  }

  setStatus(listOfClassesHiddenStatus, wantedProperty, discardedProperty) {
    var numElements;
    var elements;
    var isHidden;

    for (const [key, value] of Object.entries(listOfClassesHiddenStatus)) {
      numElements = document.getElementsByClassName(key).length
      elements = document.getElementsByClassName(key);
      isHidden = value;
      for (var index = 0; index < numElements; index++) {
        if (isHidden) {
          elements[index].classList.add(wantedProperty);
          if (discardedProperty!="") {
          elements[index].classList.remove(discardedProperty);}
        } else {
          elements[index].classList.remove(wantedProperty);
          if (discardedProperty!="") {
          elements[index].classList.add(discardedProperty);}
        }
      } 
    }
  }

}

function LoadPage() {
  const urlParams = new URLSearchParams(window.location.search);
  const page = urlParams.get('page');
  if (page == null) {
    PAGE_CONTROLLER.show('landing');
  } else {
    PAGE_CONTROLLER.show(page);
  }

  const error = urlParams.get('error');
  if (error != null) {
    handleLoginError(error);
  }

  if (urlParams.get('deleted') != null) {
    window.alert("All the data was deleted. Thank you for using our services.")
  }

  window.onpopstate = event => {
    PAGE_CONTROLLER.show(event.state.page);
  }
}

function startupGoogleLogin() {
  var googleUser = {};
  startApp;
}
  
var startApp = negativeUser => {
  gapi.load('auth2', () => {
    // Retrieve the singleton for the GoogleAuth library and set up the client.
    auth2 = gapi.auth2.init({
      client_id: '83357506440-etvnksinbmnpj8eji6dk5ss0tbk9fq4g.apps.googleusercontent.com',
    });
    attachSignin(document.getElementById('login-button-left-or-top'), false);
    attachSignin(document.getElementById('negative-login-button'), true);
  });
};

function attachSignin(element, negativeUser) {
  auth2.attachClickHandler(element, {}, googleUser => {

    const idToken = googleUser.getAuthResponse().id_token;
    localStorage.setItem('idToken', idToken.toString());

    const params = new URLSearchParams()
    params.append('idToken', idToken);
    params.append('timeZoneOffset', new Date().getTimezoneOffset());

    // params.append('systemMessage', 'VERSION_1');
    // params.append('localityResource', 'US');
    // params.append('messageLanguage', 'SP');

    var servlet = "";
    if (negativeUser) {
      servlet = '/get-negative-user-info';
      params.append('calendar', true);
      params.append('contacts', false);
    } else {
      servlet = '/get-positive-user-info';
      params.append('calendar', document.getElementById('calendar').checked);
      params.append('contacts', document.getElementById('contacts').checked);
    }

    fetch(new Request(servlet, {method: 'POST', body: params}))
    .then(response => response.text())
    .then(url => window.location = url);

  }, error => {
    alert(JSON.stringify(error, undefined, 2));
  });
}

function deleteNegativeUserData() {
  const params = new URLSearchParams();
  params.append('idToken', localStorage.idToken);
  fetch(new Request('/delete-all-negative-user-data', {method: 'POST', body: params}))
    .then(response => response.text())
    .then(url => window.location = url);
}

function handleLoginError(error) {
  if (error == "GeneralError") {
    alert("something went wrong, please try again");
  } else if (error == "FileError") {
    alert("We have encountered issues, please try again later");
  }
}
function addEmailBoxes() {
    var labelForEmailBoxes = document.createElement("label");
    labelForEmailBoxes.setAttribute("for", "Emails");
    labelForEmailBoxes.innerHTML = "Input email addresses below:";
    document.getElementById("list-of-emails").appendChild(labelForEmailBoxes);
    document.getElementById("list-of-emails").appendChild(document.createElement("br"));
    var numberOfEmails = document.getElementById("number-of-recipients-box").value;
    for(var i = 0; i < numberOfEmails; i++) {
      var emailBox = document.createElement("input");
      emailBox.setAttribute("type","text");
      emailBox.setAttribute("name","email-box-" + (i + 1));
      emailBox.setAttribute("id","email-box-" + (i + 1));
      document.getElementById("list-of-emails").appendChild(emailBox);
      document.getElementById("list-of-emails").appendChild(document.createElement("br"));
    }
}

function redirectManualInput() {
  window.location = "../html/customizeMessage.html";
}

function getFAQ() {
  window.location = "../html/faq.html";
}