package codeu.model.data;

/** Class representing the BotStructure, which establishes the User that represents the bot. */
public class BotStructure {
  private User botUser;
  private String botName;
  private BotRequest request;
  private String outputMessage;

  /**
   * Constructs a new BotStructure.
   *
   * @param botUser  User for this bot
   * @param botName  name of the bot
   * @param request  the command which triggered the bot
   * @param outputMessage  the resulting output when bot is triggered
   */
  public BotStructure(User botUser, String botName, BotRequest request, String outputMessage/*, List<String> requiredArg*/) {
    this.botUser = botUser;
    this.botName = botName;
    this.request = request;
    this.outputMessage = outputMessage;
  }

  /** Returns the User for this BotStructure. */
  public User getBotUser() {
    return botUser;
  }

  /** Returns the name of this BotStructure. */
  public String getBotName() {
    return botName;
  }

  /** Returns the BotRequest for this BotStructure. */
  public BotRequest getRequest() {
    return request;
  }

  /** Sets the BotRequest for this BotStructure. */
  public void setRequest(BotRequest request) {
    this.request = request;
  }

  /** Returns the output message for this BotStructure. */
  public String getOutputMessage() {
    return outputMessage;
  }
}
