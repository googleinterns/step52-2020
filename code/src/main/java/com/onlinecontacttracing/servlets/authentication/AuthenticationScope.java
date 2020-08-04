package com.onlinecontacttracing.authentication;

import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

public enum AuthenticationScope {
  CALENDAR(CalendarScopes.CALENDAR_READONLY), CONTACTS(PeopleServiceScopes.CONTACTS_READONLY);

  private String scopeName;

  AuthenticationScope(String scopeName) {
    this.scopeName = scopeName;
  }

  public String getScopeName() {
    return this.scopeName;
  }

  public static AuthenticationScope getScope(String scopeStringName) {
    for (AuthenticationScope scope : AuthenticationScope.values()) { 
      if (scope.name().equals(scopeStringName)) {
        return scope;
      }
    }
    return AuthenticationScope.CALENDAR;
  }


}