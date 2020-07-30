package com.onlinecontacttracing.authentication;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.auth.oauth2.AuthorizationRequestUrl;
import com.onlinecontacttracing.authentication.CheckForApiAuthorizationServlet;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.security.GeneralSecurityException;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

/**
*  This class tests whether a user's Credential have been accessed
*  successfully.
*/

@WebServlet("/authentication-test")
public class AuthenticationTestServlet extends CheckForApiAuthorizationServlet{

  public void useCredential(String userId, Credential credential, HttpServletResponse response) {
    //replace with whichever API want to test access to
    System.out.println("SUCCESSFUL USER AUTH");
  }
  
  public String getServletURIName() {
    return "/authentication-test";
  }
  
  //Update the userId with the newly created credential
  public void updateUser(String userId, String email) {
  }
}