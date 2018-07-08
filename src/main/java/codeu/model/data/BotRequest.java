package codeu.model.data;

import java.util.UUID;

public class BotRequest {
  private final String command;
  private final BotArguments arguments;
  private final UUID conversationId;

  /**
   * Constructs a new BotRequest.
   *
   * @param command the command which triggered the bot
   * @param arguments  the required arguments for the bot
   * @param conversationId the id of the conversation where the bot was triggered
   */
  public BotRequest(String command, BotArguments arguments, UUID conversationId) {
    this.command = command;
    this.arguments = arguments;
    this.conversationId = conversationId;
  }

  /** Returns the command for this BotRequest. */
  public String getCommand() { return command; }

  /** Returns the arguments needed for this BotRequest. */
  public BotArguments getArguments() {
    return arguments;
  }

  /** Returns the UUID for the conversation in which the bot was triggered */
  public UUID getConversationId() {
    return conversationId;
  }
}
