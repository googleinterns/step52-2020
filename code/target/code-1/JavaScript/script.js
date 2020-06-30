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

        var dict = {"landing" : true, "login" : false};
        this.changeHiddenStatus(dict);

        break;
      case "login":
        history.pushState({page: pageToShow}, 'Login', pageToShow);
        this.loginPage.show();
        this.currentlyShown = this.loginPage;

        var dict = {"landing" : false, "login" : true};
        this.changeHiddenStatus(dict);

        break;
    }
  }

  changeHiddenStatus(dict) {
    var numElements;
    var elements;
    var shouldRemoveHidden;

    for (const [k, v] of Object.entries(dict)) {
      numElements = document.getElementsByClassName(k).length
      elements = document.getElementsByClassName(k);
      shouldRemoveHidden = dict[k];
      // alert(k);
      // alert(dict[k]);
      for (var index = 0; index < numElements; index++) {
        if (shouldRemoveHidden) {
          elements[index].classList.remove("hidden");
        } else {
          elements[index].classList.add("hidden");
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

