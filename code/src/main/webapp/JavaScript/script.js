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

  setPageState(pageID, pageName, thisPage, pageToShow,  listOfClassesHiddenStatus) {
    history.pushState({page: pageToShow}, pageName, pageToShow);
    thisPage.show();
    this.currentlyShown = thisPage;
    for (const [key, value] of Object.entries(listOfClassesHiddenStatus)) {
      listOfClassesHiddenStatus[key] = true;
    }
    listOfClassesHiddenStatus[pageID] = false;
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
  window.onpopstate = function(event) {
    PAGE_CONTROLLER.show(event.state.page);
  }
}

function startupGoogleLogin() {
  var googleUser = {};
  startApp;
}
  
var startApp = function() {
  gapi.load('auth2', function() {
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
      emailBox.setAttribute("id","email-box-" + (i + 1));
      document.getElementById("list-of-emails").appendChild(emailBox);
      document.getElementById("list-of-emails").appendChild(document.createElement("br"));
    }
    console.log(document.getElementById("email-box-1"));
}

function redirectManualInput() {
    window.location = "../html/customizeMessage.html";
}