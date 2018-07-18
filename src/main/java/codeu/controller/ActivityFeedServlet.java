package codeu.controller;

import codeu.model.data.Activity;
import codeu.model.data.Message;
import codeu.model.data.Conversation;
import codeu.model.data.User;

import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;

import java.io.IOException;
import java.text.Collator;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActivityFeedServlet extends HttpServlet {

  /**
   * Activities can take three forms; this defines the options we might select from queues
   * when creating a comprehensive timeline of activities.
   */
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
   * Formats creation time into a string.
   */
  private String formatTime(Instant creationTime) {
    DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone( ZoneId.systemDefault() );
    return formatter.format(creationTime);
  }
  /**
   * Formats User data into an Activity.
   */
  private Activity formatUser(User user) {
    final String creationTime = formatTime(user.getCreationTime());
    final String output = user.getName() + " joined Charmer Chat.";
    return new Activity(creationTime,output);
  }
  /**
   * Formats Conversation data into an Activity.
   */
  private Activity formatConversation(Conversation conversation) {
    User owner = userStore.getUserWithId(conversation.getOwnerId());
    final String creationTime = formatTime(conversation.getCreationTime());
    final String output = owner.getName() + " started conversation " + conversation.getTitle();
    return new Activity(creationTime,output);
  }
  /**
   * Formats Message data into an Activity.
   */
  private Activity formatMessage(Message message) {
    User author = userStore.getUserWithId(message.getAuthorId());
    Conversation conversation = conversationStore.getConversationWithId(message.getConversationId());
    final String creationTime = formatTime(message.getCreationTime());
    final String output = author.getName() + " sent in " + conversation.getTitle() + ": " + message.getContent();
    return new Activity(creationTime,output);
  }

  /**
   * Gets all activity on app, iterates through to sort by timestamp and prepares printed statements.
   */
  List<Activity> getActivities() {
    List<Activity> activities = new LinkedList<>();
    LinkedList<Conversation> conversations = new LinkedList<>(conversationStore.getAllConversations());
    LinkedList<User> users = new LinkedList<>(userStore.getAllUsers());
    LinkedList<Message> messages = new LinkedList<>(messageStore.getAllMessages());

    // Sorts the lists of Conversations, Users and Messages to ensure they are in ascending order.
    conversations.sort(new Comparator<Conversation>() {
      @Override
      public int compare(Conversation o1, Conversation o2) {
        return o1.getCreationTime().compareTo(o2.getCreationTime());
      }
    });
    messages.sort(new Comparator<Message>() {
      @Override
      public int compare(Message o1, Message o2) {
        return o1.getCreationTime().compareTo(o2.getCreationTime());
      }
    });
    users.sort(new Comparator<User>() {
      @Override
      public int compare(User o1, User o2) {
        return o1.getCreationTime().compareTo(o2.getCreationTime());
      }
    });

    // Iterates through all new conversations, users and messages in the lists, compares the creation times of
    //  each then inserts objects chronologically into list of activities.
    while (!conversations.isEmpty() || !users.isEmpty() || !messages.isEmpty()) {
      ActivityType nextActivity = null;
      Instant nextActivityTime = null;

      if (!conversations.isEmpty()) {
        if (nextActivityTime == null || users.peek().getCreationTime().compareTo(nextActivityTime) < 0) {
          nextActivity = ActivityType.ConversationCreated;
          nextActivityTime = conversations.peek().getCreationTime();
        }
      }
      if (!users.isEmpty()) {
        if (nextActivityTime == null || users.peek().getCreationTime().compareTo(nextActivityTime) < 0) {
          nextActivity = ActivityType.UserJoined;
          nextActivityTime = users.peek().getCreationTime();
        }
      }
      if (!messages.isEmpty()) {
        if (nextActivityTime == null || messages.peek().getCreationTime().compareTo(nextActivityTime) < 0) {
          nextActivity = ActivityType.MessageSent;
          nextActivityTime = messages.peek().getCreationTime();
        }
      }
      try {
        switch (nextActivity) {
          case MessageSent:
            activities.add(formatMessage(messages.remove()));
            break;
          case ConversationCreated:
            activities.add(formatConversation(conversations.remove()));
            break;
          case UserJoined:
            activities.add(formatUser(users.remove()));
            break;
        }
      }
      catch (NullPointerException n){
        System.out.println("Uh oh. Something's gone very, very wrong.");
      }
    }
    return activities;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    List<Activity> activities = getActivities();
    request.setAttribute("activities", activities);
    request.getRequestDispatcher("/WEB-INF/view/activityfeed.jsp").forward(request, response);
  }
}
