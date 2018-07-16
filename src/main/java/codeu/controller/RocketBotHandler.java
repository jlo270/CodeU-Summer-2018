package codeu.controller;

import codeu.model.data.BotRequest;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;

import java.time.Instant;
import java.util.UUID;

/**
 * Handles the Rocket Bot. This bot is triggered by the word "rocket" and responds with a
 * hard-coded message: "3...2...1...BLASTOFF!". The handler first creates a User for the
 * bot, then handles its actions as outlined in BotHandlerInterface.
 */
public class RocketBotHandler implements BotHandlerInterface {

  /** Handles bot requests and sends as messages to conversations. */
  public void handler(BotRequest request) {
    final String BOT_NAME = "RocketBot";
    final String BOT_OUTPUT = "3...2...1...BLASTOFF!";
    final UUID BOT_USER_ID = UUID.randomUUID();

    // Create User and add the User to UserStore only if it does not already exist.
    if (userStore.getUserWithId(BOT_USER_ID) == null) {
      User botUser = new User(
          BOT_USER_ID,
          BOT_NAME,
          "$2a$10$/zf4WlT2Z6tB5sULB9Wec.QQdawmF0f1SbqBw5EeJg5uoVpKFFXAa",
          Instant.now());
      userStore.addUser(botUser);
    }

    // Convert output and required arguments into Message
    Message message = new Message(
        UUID.randomUUID(),
        request.getConversationId(),
        BOT_USER_ID,
        BOT_OUTPUT,
        Instant.now());
    messageStore.addMessage(message);

    // Send message to conversation
    // May need to invoke the doPost() function of ChatServlet, unless it auto-updates when new Message is added

  }

  /** Store class that gives access to Messages. */
  private MessageStore messageStore = MessageStore.getInstance();

  /** Store class that gives access to Users. */
  private UserStore userStore = UserStore.getInstance();

}
