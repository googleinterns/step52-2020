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
  }

  hide() {
    this.background.classList.remove("login-background");
  }
}

var listOfClassesHiddenStatus = {"landing" : true, "login" : true};

class PageController {
  constructor() {
    this.landingPage = new LandingPage();
    this.loginPage = new LoginPage();
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
        this.setPageState("landing", 'Online Contact Tracing', this.landingPage, pageToShow, listOfClassesHiddenStatus)
        break;
      case "login":
        this.setPageState("login", 'Login', this.loginPage, pageToShow, listOfClassesHiddenStatus)
        startupGoogleLogin() 
        startApp();
        break;
    }
  }

  setPageState(pageID, pageName, thisPage, pageToShow,  listOfClassesHiddenStatus){
    history.pushState({page: pageToShow}, pageName, pageToShow);
    thisPage.show();
    this.currentlyShown = thisPage;
    for (const [key, value] of Object.entries(listOfClassesHiddenStatus)) {
      listOfClassesHiddenStatus[key] = true;
    }
    listOfClassesHiddenStatus[pageID] = false;
    this.setFadeoutStatus(listOfClassesHiddenStatus);
  }

  setFadeoutStatus(listOfClassesHiddenStatus) {
    var numElements;
    var elements;
    var isHidden;

    for (const [key, value] of Object.entries(listOfClassesHiddenStatus)) {
      numElements = document.getElementsByClassName(key).length
      elements = document.getElementsByClassName(key);
      isHidden = value;
      for (var index = 0; index < numElements; index++) {
        if (isHidden) {
          elements[index].classList.add("fade-out");
          elements[index].classList.remove("fade-in");
        } else {
          elements[index].classList.remove("fade-out");
          elements[index].classList.add("fade-in");
        }
      } 
    }
    setTimeout(this.setHiddenStatus, 900, listOfClassesHiddenStatus);
  }
  setHiddenStatus(listOfClassesHiddenStatus) {
    var numElements;
    var elements;
    var isHidden;

    for (const [key, value] of Object.entries(listOfClassesHiddenStatus)) {
      numElements = document.getElementsByClassName(key).length
      elements = document.getElementsByClassName(key);
      isHidden = value;
      for (var index = 0; index < numElements; index++) {
        if (isHidden) {
          elements[index].classList.add("hidden");
        } else {
          elements[index].classList.remove("hidden");
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
  window.onpopstate = function(event) {
    PAGE_CONTROLLER.show(event.state.page);
  }
}

function startupGoogleLogin() {
  var googleUser = {};
  startApp;
}
  
var startApp = function() {
  gapi.load('auth2', function(){
    // Retrieve the singleton for the GoogleAuth library and set up the client.
    auth2 = gapi.auth2.init({
      client_id: '1080865471187-u1vse3ccv9te949244t9rngma01r226m.apps.googleusercontent.com',
      cookiepolicy: 'single_host_origin',
      // Request scopes in addition to 'profile' and 'email'
      //scope: 'additional_scope'
    });
    attachSignin(document.getElementById('login-button-left-or-top'));
  });
};

function attachSignin(element) {
    console.log(element.id);
    auth2.attachClickHandler(element, {},
        function(googleUser) {
          document.getElementById('name').innerText = "Signed in: " +
              googleUser.getBasicProfile().getName();
        }, function(error) {
          alert(JSON.stringify(error, undefined, 2));
        });
  }

function backToLogin() {
  window.location = "/landing";
}

function getFAQ() {
  window.location = "../html/faq.html";
}