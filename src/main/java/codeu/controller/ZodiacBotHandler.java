package codeu.controller;

import codeu.model.data.BotRequest;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.MessageStore;

import java.time.Instant;
import java.util.UUID;

/**
 * Handles the Rocket Bot. This bot is triggered by the word "rocket" and responds with a
 * hard-coded message: "3...2...1...BLASTOFF!". The handler first creates a User for the
 * bot, then handles its actions as outlined in BotHandlerInterface.
 */
public class ZodiacBotHandler implements BotHandlerInterface {

  /** Handles bot requests and sends as messages to conversations. */
  public void handler(BotRequest request) {
    String BOT_NAME = "Zodiac Bot";
    String starSign = "Sample Sign";
    String starBio = "Sample signs are just like everyone else.";

    String ARIES_BIO = "Aries people are ambitious, driven and like to be number one.";
    String TAURUS_BIO = "Taurus people are determined, driven and known for their stubborn streaks.";
    String GEMINI_BIO = "Gemini people are quick-witted, versatile and known for changing their mind.";
    String CANCER_BIO = "Cancer people seek to create situations where they feel important, indispensable and needed.";
    String LEO_BIO = "Leo people are natural leaders who take extreme pride in their talents and beliefs.";
    String VIRGO_BIO = "Virgo people can be excellent planners, but they’re also friendly, social and spontaneous.";
    String LIBRA_BIO = "Libra people are gracious, discerning and like to quietly take charge.";
    String SCORPIO_BIO = "Scorpio people are driven, loyal, focused and don’t trust easily.";
    String SAGGITARIUS_BIO = "Sagittarius people are seekers, forever striving for the highest truth.";
    String CAPRICORN_BIO = "Capricorn people are ambitious, hardworking and driven to achieve.";
    String AQUARIUS_BIO = "Aquarius people will passionately advocate for their cutting-edge ideas.";
    String PISCES_BIO = "Pisces people are compassionate, accommodating and can even be emotional sponges.";

    // Splits the given argument into month and day. i.e. "Mar 25" into "Mar" "25".
    String[] tokens = request.getArguments().get(0).split(" ");
    int day = Integer.parseInt(tokens[1]);
    switch (tokens[0]) {
      case "Jan":
        if (day < 20) {
          starSign = "Capricorn";
          starBio = CAPRICORN_BIO;
          break;
        }
        else {
          starSign = "Aquarius";
          starBio = AQUARIUS_BIO;
          break;
        }
      case "Feb":
        if (day < 19) {
          starSign = "Aquarius";
          starBio = AQUARIUS_BIO;
          break;
        }
        else {
          starSign = "Pisces";
          starBio = PISCES_BIO;
          break;
        }
      case "Mar":
        if (day < 21) {
          starSign = "Pisces";
          starBio = PISCES_BIO;
          break;
        }
        else {
          starSign = "Aries";
          starBio = ARIES_BIO;
          break;
        }
      case "Apr":
        if (day < 20) {
          starSign = "Aries";
          starBio = ARIES_BIO;
          break;
        }
        else {
          starSign = "Taurus";
          starBio = TAURUS_BIO;
          break;
        }
      case "May":
        if (day < 21) {
          starSign = "Taurus";
          starBio = TAURUS_BIO;
          break;
        }
        else {
          starSign = "Gemini";
          starBio = GEMINI_BIO;
          break;
        }
      case "Jun":
        if (day < 21) {
          starSign = "Gemini";
          starBio = GEMINI_BIO;
          break;
        }
        else {
          starSign = "Cancer";
          starBio = CANCER_BIO;
          break;
        }
      case "Jul":
        if (day < 23) {
          starSign = "Cancer";
          starBio = CANCER_BIO;
          break;
        }
        else {
          starSign = "Leo";
          starBio = LEO_BIO;
          break;
        }
      case "Aug":
        if (day < 23) {
          starSign = "Leo";
          starBio = LEO_BIO;
          break;
        }
        else {
          starSign = "Virgo";
          starBio = VIRGO_BIO;
          break;
        }
      case "Sep":
        if (day < 23) {
          starSign = "Virgo";
          starBio = VIRGO_BIO;
          break;
        }
        else {
          starSign = "Libra";
          starBio = LIBRA_BIO;
          break;
        }
      case "Oct":
        if (day < 23) {
          starSign = "Libra";
          starBio = LIBRA_BIO;
          break;
        }
        else {
          starSign = "Scorpio";
          starBio = SCORPIO_BIO;
          break;
        }
      case "Nov":
        if (day < 22) {
          starSign = "Scorpio";
          starBio = SCORPIO_BIO;
          break;
        }
        else {
          starSign = "Saggitarius";
          starBio = SAGGITARIUS_BIO;
          break;
        }
      case "Dec":
        if (day < 22) {
          starSign = "Saggitarius";
          starBio = SAGGITARIUS_BIO;
          break;
        }
        else {
          starSign = "Capricon";
          starBio = CAPRICORN_BIO;
          break;
        }
    }

    String BOT_OUTPUT = "Your sign is: " + starSign + "! " + starBio;
    UUID BOT_USER_ID = UUID.randomUUID();

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

  /** Store class that gives access to Conversations. */
  private UserStore userStore = UserStore.getInstance();

  /** Store class that gives access to Messages. */
  private MessageStore messageStore = MessageStore.getInstance();

}
