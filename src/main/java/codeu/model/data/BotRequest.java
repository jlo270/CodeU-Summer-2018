package codeu.model.data;

import java.util.ArrayList;
import java.util.UUID;

/** Once a command has been given in the Conversation, a BotRequest is created which holds
 * the data vital to the bot's function. This information is then used by the handler in
 * order to make the bot respond to the given command.
 */
public class BotRequest {
  private final String command;
  private final ArrayList<String> arguments;
  private final UUID conversationId;

  /**
   * Constructs a new BotRequest.
   *
   * @param command the command which triggered the bot
   * @param arguments  the required arguments for the bot
   * @param conversationId the id of the conversation where the bot was triggered
   */
  public BotRequest(String command, ArrayList<String> arguments, UUID conversationId) {
    this.command = command;
    this.arguments = arguments;
    this.conversationId = conversationId;
  }

  /** Returns the command for this BotRequest. */
  public String getCommand() { return command; }

  /** Returns the arguments needed for this BotRequest. */
  public ArrayList<String> getArguments() {
    return arguments;
  }

  /** Returns the UUID for the conversation in which the bot was triggered */
  public UUID getConversationId() {
    return conversationId;
  }
}
