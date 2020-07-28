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
      client_id: '1080865471187-u1vse3ccv9te949244t9rngma01r226m.apps.googleusercontent.com',
    });
    attachSignin(document.getElementById('login-button-left-or-top'), false);
    attachSignin(document.getElementById('negative-login-button'), true);
  });
};

function attachSignin(element, negativeUser) {
  auth2.attachClickHandler(element, {}, googleUser => {

    document.getElementById('name').innerText = "Signed in: " + googleUser.getBasicProfile().getName();

    const idToken = googleUser.getAuthResponse().id_token;
    localStorage.setItem('idToken', idToken.toString());
    const params = new URLSearchParams()
    params.append('idToken', idToken);

    fetch(makeRequest(negativeUser, params))
    .then(response => response.text())
    .then(url => { if (url.length < 20) {
        handleLoginError(url);
      } else {
        window.location = url;
      }
    });

  }, error => {
    alert(JSON.stringify(error, undefined, 2));
  });
}

function makeRequest(negativeUser, params) {    
  if (negativeUser) {
    return new Request('/get-negative-user-info', {method: 'POST', body: params})
  } else {
    return new Request('/get-positive-user-info', {method: 'POST', body: params})
  }
}

function handleLoginError(error) {
  if (error == "GeneralError") {
    alert("something went wrong, please try again");
  } else if (error == "FileError") {
    alert("We have encountered issues, please try again later");
  }
}