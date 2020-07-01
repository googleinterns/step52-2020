class LandingPage {
  constructor(){
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
  constructor(){
    this.background = document.getElementById("wrapper-background");
  }

  show() {
    this.background.classList.add("login-background");
  }

  hide() {
    this.background.classList.remove("login-background");
  }
}

class PageController {
  constructor () {
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
        history.pushState({page: pageToShow}, 'Online Contact Tracing', pageToShow);
        this.landingPage.show();
        this.currentlyShown = this.landingPage;

        var listOfClassesHiddenStatus = {"landing" : false, "login" : true};
        this.setHiddenStatus(listOfClassesHiddenStatus);

        break;
      case "login":
        history.pushState({page: pageToShow}, 'Login', pageToShow);
        this.loginPage.show();
        this.currentlyShown = this.loginPage;

        var listOfClassesHiddenStatus = {"landing" : true, "login" : false};
        this.setHiddenStatus(listOfClassesHiddenStatus);

        break;
    }
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

function googleLogIn() {
  window.location = "../html/tested-positive.html";
}

function backToLogin() {
  window.location = "/landing";
}

function getFAQ() {
  window.location = "../html/faq.html";
}