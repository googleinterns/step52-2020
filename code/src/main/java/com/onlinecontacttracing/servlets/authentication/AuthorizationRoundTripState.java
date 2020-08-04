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

  public AuthorizationRoundTripState(String idToken, List<AuthenticationScope> authenticationScopes) {
    this.idToken = idToken;
    this.authenticationScopes = authenticationScopes;
  }

  public static List<String> getScopeNames(List<AuthenticationScope> scopes) {
    List<String> scopeNames = new ArrayList<String> ();
    for (AuthenticationScope scope : scopes) {
      scopeNames.add(AuthenticationScope.getScopeName(scope));
           
    }
    return scopeNames;
  }

  public static List<AuthenticationScope> getScopes(HashMap<String, Boolean> scopeAuthenticationStatus) {
    List<AuthenticationScope> scopes = new ArrayList<AuthenticationScope> ();
    for (String scope : scopeAuthenticationStatus.keySet()) {
      if (scopeAuthenticationStatus.get(scope)) {
        scopes.add(AuthenticationScope.getScope(scope));   
      }
    }
    return scopes;
  }

  public List<AuthenticationScope> getAuthenticationScopes() {
    return authenticationScopes;
  }
}