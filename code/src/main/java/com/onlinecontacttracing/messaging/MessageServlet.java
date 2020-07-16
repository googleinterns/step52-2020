package com.onlinecontacttracing.messaging;

import com.onlinecontacttracing.messaging.LocalityResource;
import com.onlinecontacttracing.storage.CustomizableMessage;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import com.onlinecontacttracing.messaging.filters.CheckMessagesForFlags;
import java.util.List;


@WebServlet("/messaging")
public class MessageServlet extends HttpServlet {
  private SystemMessage systemMessage;
  private LocalityResource localityResource;
  private CustomizableMessage customizableMessage;
  private String userMessage;
  private String errorMessage;
  private PositiveUser user;
  private List<String> listOfFlagMessages;


  public Message(SystemMessage systemMessage, LocalityResource localityResource, CustomizableMessage customizableMessage, PositiveUser user) {
    this.systemMessage = systemMessage;
    this.localityResource = localityResource;
    this.customizableMessage =  customizableMessage;
    this.userMessage = customizableMessage.getMessage();
    this.errorMessage = "";
    this.user = user;

  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html;");
    String messageLanguage = request.getParameter("language")
    response.getWriter().println(compileMessage(messageLanguage));
  }

  public void checkForFlags() {
    CheckMessagesForFlags flagChecker = new CheckMessagesForFlags();
    listOfFlagMessages = CheckMessagesForFlags.findTriggeredFlags(flagChecker, user, userMessage);
  }

  public ArrayList<String> compileMessage(String messageLanguage) {
    //need to adjust to change with getting different translations
    String translatedResourceMessage;
    String translatedSystemMessage;
    user.incrementAttemptedEmailDrafts();
    if (user.userCanMakeMoreDraftsAfterBeingFlagged()) {
      if (listOfFlagMessages.size() == 0) {
        if (messageLanguage.equals("SP")) {
        translatedResourceMessage = localityResource.getEnglishTranslation();
        translatedSystemMessage = systemMessage.getEnglishTranslation();
        return (new ArrayList()).add(translatedSystemMessage.concat(userMessage).concat(translatedResourceMessage));
        }
        else {
          translatedResourceMessage = localityResource.getEnglishTranslation();
          translatedSystemMessage = systemMessage.getEnglishTranslation();
          return (new ArrayList()).add(translatedSystemMessage.concat(userMessage).concat(translatedResourceMessage));
        }
      }
      return listOfFlagMessages;//should return error messages
    }
    return (new ArrayList()).add("You've exceeded your number of tries");;
  }

}