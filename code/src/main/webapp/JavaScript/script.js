class LandingPage {
  constructor(){
    this.background = document.getElementById("wrapper-background");
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
}

function TransitionFromLandingToLogin() {
  history.pushState('', 'Login', '/login.html');
  const landingPage = new LandingPage();
  landingPage.hide();
  const loginPage = new LoginPage();
  loginPage.show();
}

