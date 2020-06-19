function TransitionFromLandingToLogin() {
    const background = document.getElementById("wrapper-background");
    background.classList.add("login-background");
    background.classList.remove("landing-background");
}