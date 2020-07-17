// public class Send extends GmailRequest<com.google.api.services.gmail.model.Message> {

//         private static final String REST_PATH = "gmail/v1/users/{userId}/messages/send";

//         /**
//          * Sends the specified message to the recipients in the `To`, `Cc`, and `Bcc` headers.
//          *
//          * Create a request for the method "messages.send".
//          *
//          * This request holds the parameters needed by the the gmail server.  After setting any optional
//          * parameters, call the {@link Send#execute()} method to invoke the remote operation. <p> {@link
//          * Send#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must be
//          * called to initialize this instance immediately after invoking the constructor. </p>
//          *
//          * @param userId The user's email address. The special value `me`
//       can be used to indicate the authenticated user.
//        *        [default: me]
//          * @param content the {@link com.google.api.services.gmail.model.Message}
//          * @since 1.13
//          */
//         protected Send(java.lang.String userId, com.google.api.services.gmail.model.Message content) {
//           super(Gmail.this, "POST", REST_PATH, content, com.google.api.services.gmail.model.Message.class);
//           this.userId = com.google.api.client.util.Preconditions.checkNotNull(userId, "Required parameter userId must be specified.");
//           checkRequiredParameter(content, "content");
//           checkRequiredParameter(content.getRaw(), "Message.getRaw()");
//         }

//         /**
//          * Sends the specified message to the recipients in the `To`, `Cc`, and `Bcc` headers.
//          *
//          * Create a request for the method "messages.send".
//          *
//          * This request holds the parameters needed by the the gmail server.  After setting any optional
//          * parameters, call the {@link Send#execute()} method to invoke the remote operation. <p> {@link
//          * Send#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must be
//          * called to initialize this instance immediately after invoking the constructor. </p>
//          *
//          * <p>
//          * This constructor should be used for uploading media content.
//          * </p>
//          *
//          * @param userId The user's email address. The special value `me`
//       can be used to indicate the authenticated user.
//        *        [default: me]
//          * @param content the {@link com.google.api.services.gmail.model.Message} media metadata or {@code null} if none
//          * @param mediaContent The media HTTP content or {@code null} if none.
//          * @since 1.13
//          */
//         protected Send(java.lang.String userId, com.google.api.services.gmail.model.Message content, com.google.api.client.http.AbstractInputStreamContent mediaContent) {
//           super(Gmail.this, "POST", "/upload/" + getServicePath() + REST_PATH, content, com.google.api.services.gmail.model.Message.class);
//           this.userId = com.google.api.client.util.Preconditions.checkNotNull(userId, "Required parameter userId must be specified.");
//           initializeMediaUpload(mediaContent);
//         }

//         @Override
//         public Send set$Xgafv(java.lang.String $Xgafv) {
//           return (Send) super.set$Xgafv($Xgafv);
//         }

//         @Override
//         public Send setAccessToken(java.lang.String accessToken) {
//           return (Send) super.setAccessToken(accessToken);
//         }

//         @Override
//         public Send setAlt(java.lang.String alt) {
//           return (Send) super.setAlt(alt);
//         }

//         @Override
//         public Send setCallback(java.lang.String callback) {
//           return (Send) super.setCallback(callback);
//         }

//         @Override
//         public Send setFields(java.lang.String fields) {
//           return (Send) super.setFields(fields);
//         }

//         @Override
//         public Send setKey(java.lang.String key) {
//           return (Send) super.setKey(key);
//         }

//         @Override
//         public Send setOauthToken(java.lang.String oauthToken) {
//           return (Send) super.setOauthToken(oauthToken);
//         }

//         @Override
//         public Send setPrettyPrint(java.lang.Boolean prettyPrint) {
//           return (Send) super.setPrettyPrint(prettyPrint);
//         }

//         @Override
//         public Send setQuotaUser(java.lang.String quotaUser) {
//           return (Send) super.setQuotaUser(quotaUser);
//         }

//         @Override
//         public Send setUploadType(java.lang.String uploadType) {
//           return (Send) super.setUploadType(uploadType);
//         }

//         @Override
//         public Send setUploadProtocol(java.lang.String uploadProtocol) {
//           return (Send) super.setUploadProtocol(uploadProtocol);
//         }

//         /**
//          * The user's email address. The special value `me` can be used to indicate the
//          * authenticated user.
//          */
//         @com.google.api.client.util.Key
//         private java.lang.String userId;

//         /** The user's email address. The special value `me` can be used to indicate the authenticated user.
//        [default: me]
//          */
//         public java.lang.String getUserId() {
//           return userId;
//         }

//         /**
//          * The user's email address. The special value `me` can be used to indicate the
//          * authenticated user.
//          */
//         public Send setUserId(java.lang.String userId) {
//           this.userId = userId;
//           return this;
//         }

//         @Override
//         public Send set(String parameterName, Object value) {
//           return (Send) super.set(parameterName, value);
//         }
//       }