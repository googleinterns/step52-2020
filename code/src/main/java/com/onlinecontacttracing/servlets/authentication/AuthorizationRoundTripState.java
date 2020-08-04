package com.onlinecontacttracing.authentication;
import com.onlinecontacttracing.authentication.AuthenticationScope;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
/*
 * Helper class to pass along state information in URL and servlets
 */
class AuthorizationRoundTripState {
  final String idToken;
  String userId;
  List<AuthenticationScope> authenticationScopes;

  public AuthorizationRoundTripState(String idToken) {
    this.idToken = idToken;
    this.authenticationScopes = new ArrayList<AuthenticationScope>();
  }

  public List<String> getScopeNames() {
    List<String> scopeNames = new ArrayList<String> ();
    for (AuthenticationScope scope : authenticationScopes) {
      scopeNames.add(scope.getScopeName());
    }
    return scopeNames;
  }

  public void addScope(boolean scopeRequested, AuthenticationScope scope) {
    if (scopeRequested) {
      authenticationScopes.add(scope);
    }
  }
}