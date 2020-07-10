public class Message {
  private SystemMessage systemMessage;
  private LocalityResource localityResource;
  private CustomizableMessage customizableMessage;
  private String completeMessage;

  public Message(SystemMessage systemMessage, String LocalityResource, CustomizableMessage customizableMessage) {
    this.systemMessage = systemMessage;
    this.localityResource = localityResource;
    this.customizableMessage = customizableMessage;
  }

  public boolean checkForFlags(CustomizableMessage customizableMessage) {
    String userMessage = customizableMessage.getMessage();
    String userId = customizableMessage.getUserId();

    return NumberOfMessagesFlaggingFilter.passesFilter(userId, userMessage) &&
            ProganityFlaggingFilter.passesFilter(userId, userMessage) &&
            LinkFlaggingFilter.passesFilter(userId, userMessage) &&
            HtmlFlaggingFilter.passesFilter(userId, userMessage) &&
            LengthFlaggingFilter.passesFilter(userId, userMessage);
  }


}