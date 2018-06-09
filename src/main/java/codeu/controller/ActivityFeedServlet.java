package codeu.controller;

import codeu.model.data.Message;
import codeu.model.data.Conversation;
import codeu.model.data.User;

import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.LinkedList;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class representing an activity, which can be thought of as any action
 * within the chat room. Activities are triggered by a User joining a
 * conversation, starting a conversation or replying to a message.
 */
class Activity {
  private final String creationTime;
  private final String output;

  /**
   * Constructs a new Activity.
   *
   * @param creationTime the creation time of this Activity as a String
   * @param output the formatted description of the Activity as a String
   */
  public Activity(String creationTime, String output) {
    this.creationTime = creationTime;
    this.output = output;
  }

  /** Returns the creation time of this Activity. */
  public String getCreationTime() { return creationTime; }

  /** Returns the title of this Conversation. */
  public String getOutput() {
    return output;
  }
}

public class ActivityFeedServlet extends HttpServlet {

  /** Enumerated type to define whether an activity is a Message, User, or Conversation. */
  private enum ActivityType {
    UserJoined,
    ConversationCreated,
    MessageSent
  }

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /** Store class that gives access to Conversations. */
  private ConversationStore conversationStore;

  /** Store class that gives access to Messages. */
  private MessageStore messageStore;

  /**
   * Set up state for handling activity-related requests. This method is only called when
   * running in a server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setConversationStore(ConversationStore.getInstance());
    setMessageStore(MessageStore.getInstance());
    setUserStore(UserStore.getInstance());
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * Sets the ConversationStore used by this servlet. This function provides a common setup method
   * for use by the test framework or the servlet's init() function.
   */
  void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }

  /**
   * Sets the MessageStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
  }

  /**
   * Formats creation time into String.
   */
  private String formatTime(Instant creationTime) {
    DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone( ZoneId.systemDefault() );
    return formatter.format(creationTime);
  }
  /**
   * Formats User data into readable string.
   */
  private Activity formatUser(User user) {
    String creationTime = formatTime(user.getCreationTime());
    String output = user.getName() + "joined Charmer Chat.";
    return new Activity(creationTime,output);
  }
  /**
   * Formats Conversation data into readable string.
   */
  private Activity formatConversation(Conversation conversation) {
    User owner = userStore.getUserWithId(conversation.getOwnerId());
    String creationTime = formatTime(conversation.getCreationTime());
    String output = owner.getName() + " started conversation " + conversation.getTitle();
    return new Activity(creationTime,output);
  }
  /**
   * Formats Message data into readable string.
   */
  private Activity formatMessage(Message message) {
    User author = userStore.getUserWithId(message.getAuthorId());
    Conversation conversation = conversationStore.getConversationWithId(message.getConversationId());
    String creationTime = formatTime(message.getCreationTime());
    String output = author.getName() + " sent in " + conversation.getTitle() + ": " + message.getContent();
    return new Activity(creationTime,output);
  }

  /**
   * Gets all activity on app, iterates through to sort by timestamp and prepares printed statements.
   */
  private List<Activity> getActivities() {
    List<Activity> activities = new LinkedList<>();
    LinkedList<Conversation> conversations = new LinkedList<>(conversationStore.getAllConversations());
    LinkedList<User> users = new LinkedList<>(userStore.getAllUsers());
    LinkedList<Message> messages = new LinkedList<>(messageStore.getAllMessages());

    while (!conversations.isEmpty() || !users.isEmpty() || !messages.isEmpty()) {
      // Iterates through all new conversations, users and messages in the lists, compares the creation times of
      //  each then inserts objects chronologically into list of activities.
      ActivityType nextActivity = null;
      Instant nextActivityTime = null;

      if (!conversations.isEmpty()) {
        nextActivity = ActivityType.ConversationCreated;
        nextActivityTime = conversations.peek().getCreationTime();
      }
      if (!users.isEmpty()) {
        if (nextActivityTime == null || nextActivity == null) {
          nextActivity = ActivityType.UserJoined;
          nextActivityTime = users.peek().getCreationTime();
        }
        else if (Objects.requireNonNull(users.peek()).getCreationTime().compareTo(nextActivityTime) < 0) {
          nextActivity = ActivityType.UserJoined;
          nextActivityTime = Objects.requireNonNull(users.peek()).getCreationTime();
        }
      }
      if (!messages.isEmpty()) {
        if (nextActivityTime == null || nextActivity == null) {
          nextActivity = ActivityType.MessageSent;
          nextActivityTime = Objects.requireNonNull(messages.peek()).getCreationTime();
        }
        if (Objects.requireNonNull(messages.peek()).getCreationTime().compareTo(nextActivityTime) < 0) {
          nextActivity = ActivityType.MessageSent;
          nextActivityTime = Objects.requireNonNull(messages.peek()).getCreationTime();
        }
      }

      if (nextActivity != null) {
        switch(nextActivity) {
          case MessageSent:
            activities.add(formatMessage(messages.remove()));
          case ConversationCreated:
            activities.add(formatConversation(conversations.remove()));
          case UserJoined:
            activities.add(formatUser(users.remove()));
        }
      }
    }
    return activities;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    List<Activity> activities = getActivities();
    request.setAttribute("activities", activities);
    request.getRequestDispatcher("/WEB-INF/view/activityfeed.jsp").forward(request, response);;

  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    System.out.print("in post request");
    response.getOutputStream().println("in post request");
    //response.sendRedirect("/activity");
  }
}
