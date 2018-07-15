package codeu.controller;

import codeu.model.data.BotStructure;
import codeu.model.data.BotRequest;
import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;

import java.time.Instant;
import java.util.UUID;

/**
 * Handles the Rocket Bot. This bot is triggered by the word "rocket" and responds with a
 * hard-coded message: "3...2...1...BLASTOFF!". The handler first creates a User for the
 * bot, then handles its actions as outlined in BotHandlerInterface.
 */
public class RocketBotHandler implements BotHandlerInterface {

  private String BOT_NAME = "Rocket Bot";
  private String BOT_OUTPUT = "3...2...1...BLASTOFF!";
  private UUID BOT_USER_ID = UUID.randomUUID();

  /** Handles bot requests and sends as messages to conversations. */
  public void handler(BotRequest request) {
    User botUser = new User(
                       BOT_USER_ID,
                       BOT_NAME,
          "$2a$10$/zf4WlT2Z6tB5sULB9Wec.QQdawmF0f1SbqBw5EeJg5uoVpKFFXAa",
                       Instant.now());
    //currentBot.setRequest(request);

    // Find conversation where command was sent/bot was triggered
    UUID conversationId = request.getConversationId();
    Conversation conversation = findConversation(conversationId);

    // Convert output and required arguments into Message
    Message message = new Message(
        UUID.randomUUID(),
        conversationId,
        BOT_USER_ID,
        BOT_OUTPUT,
        Instant.now());
    messageStore.addMessage(message);

    // somehow send message to conversation
  }

  /** Store class that gives access to Conversations. */
  private ConversationStore conversationStore = ConversationStore.getInstance();

  /** Store class that gives access to Messages. */
  private MessageStore messageStore = MessageStore.getInstance();

  private Conversation findConversation(UUID conversationID) {
    return conversationStore.getConversationWithId(conversationID);
  }
}
