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

        var numLandingElements = document.getElementsByClassName("landing").length
        var landingElements = document.getElementsByClassName("landing");
        for (var index = 0; index < numLandingElements; index++) {
          landingElements[index].classList.remove("hidden");
        }

        break;
      case "login":
        history.pushState({page: pageToShow}, 'Login', pageToShow);
        this.loginPage.show();
        this.currentlyShown = this.loginPage;

        var numLandingElements = document.getElementsByClassName("landing").length
        var landingElements = document.getElementsByClassName("landing");
        for (var index = 0; index < numLandingElements; index++) {
          landingElements[index].classList.add("hidden");
        }

        break;
    }
  }
}
