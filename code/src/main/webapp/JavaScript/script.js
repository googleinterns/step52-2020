class LandingPage {
  constructor(){
    this.background = document.getElementById("wrapper-background");
  }

  show() {
    // do nothing for now
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
    // do nothing for now
  }
}

class PageController {
  constructor () {
    this.landingPage = new LandingPage();
    this.loginPage = new LoginPage();
    this.currentlyShown = "none";
  }

  hideCurrentPage() {
    if (this.currentlyShown != "none") {
      this.currentlyShown.hide()
    }
  }

  show(pageToShow) {
    // Put the history.pushState here!
    this.hideCurrentPage();
    switch(pageToShow) {
      case "landing":
        history.pushState('', 'Online Contact Tracing', pageToShow);
        this.landingPage.show();
        this.currentlyShown = this.landingPage;
        break;
      case "login":
        history.pushState('', 'Login', pageToShow);
        this.loginPage.show();
        this.currentlyShown = this.loginPage;
        break;
    }
  }
}


