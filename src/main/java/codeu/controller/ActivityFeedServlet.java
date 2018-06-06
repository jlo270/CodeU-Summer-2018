package codeu.controller;

import codeu.model.data.Activity;
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

/*public class Activity {
  public String creationTime;
  public String output;
}*/

public class ActivityFeedServlet extends HttpServlet {

    private enum Type {
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
     * Formats creation time into String for messages.
     */
    private String formatTimeMessage(Message message) {
        DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    .withZone( ZoneId.systemDefault() );
        return formatter.format(message.getCreationTime());
    }
    /**
     * Formats creation time into String for users.
     */
    private String formatTimeUser(User user) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        .withZone( ZoneId.systemDefault() );
        return formatter.format(user.getCreationTime());
    }
    /**
     * Formats creation time into String for conversations.
     */
    private String formatTimeConversation(Conversation conversation) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        .withZone( ZoneId.systemDefault() );
        return formatter.format(conversation.getCreationTime());
    }
    /**
     * Formats User data into readable string.
     */
    private Activity formatUser(User user) {
      Activity activity = new Activity();
      String creationTime = formatTimeUser(user);
      String output = user.getName() + "joined Charmer Chat.";
      activity.creationTime = creationTime;
      activity.output = output;
      return activity;
    }
    /**
     * Formats Conversation data into readable string.
     */
    private Activity formatConversation(Conversation conversation) {
      Activity activity = new Activity();
      UserStore userStore = UserStore.getInstance();
      User owner = userStore.getUserWithId(conversation.getOwnerId());
      String creationTime = formatTimeConversation(conversation);
      String output = owner.getName() + " started conversation " + conversation.getTitle();
      activity.creationTime = creationTime;
      activity.output = output;
      return activity;
    }
    /**
     * Formats Message data into readable string.
     */
    private Activity formatMessage(Message message) {
      Activity activity = new Activity();
      UserStore userStore = UserStore.getInstance();
      User author = userStore.getUserWithId(message.getAuthorId());
      ConversationStore conversationStore = ConversationStore.getInstance();
      Conversation conversation = conversationStore.getConversationWithId(message.getConversationId());
      String creationTime = formatTimeMessage(message);
      String output = author.getName() + " sent in " + conversation.getTitle() + ": " + message.getContent();
      activity.creationTime = creationTime;
      activity.output = output;
      return activity;
    }
    /**
     * Gets all activity on app, iterates through to sort by timestamp and prepared printed statements.
     */
    private List<Activity> getActivities() {
      // iterate through user,message,conversation store
      List<Activity> activities = new LinkedList<>();
      LinkedList<Conversation> conversations = new LinkedList<>(conversationStore.getAllConversations());
      LinkedList<User> users = new LinkedList<>(userStore.getUsers());
      LinkedList<Message> messages = new LinkedList<>(messageStore.getAllMessages());

        while (!conversations.isEmpty() || !users.isEmpty() || !messages.isEmpty()) {
//      while (conversations.peek() != null || users.peek() != null || messages.peek() != null) {
        // iterate through conversations,users,messages; compare creation times
          Type nextActivity = null;
          //Type.UserJoined;
          Instant nextActivityTime = null;
          // users.peek().getCreationTime();

          if (conversations.peek() != null) {
                    nextActivity = Type.ConversationCreated;
                    nextActivityTime = conversations.peek().getCreationTime();
          }
          if (!users.isEmpty()) {
              if (nextActivityTime == null || nextActivity == null) {
                  nextActivity = Type.UserJoined;
                  nextActivityTime = Objects.requireNonNull(users.peek()).getCreationTime();
              }
              else if (Objects.requireNonNull(users.peek()).getCreationTime().compareTo(nextActivityTime) < 0) {
                    nextActivity = Type.UserJoined;
                    nextActivityTime = Objects.requireNonNull(users.peek()).getCreationTime();
                }
          }
          if (!messages.isEmpty()) {
                if (nextActivityTime == null || nextActivity == null) {
                    nextActivity = Type.MessageSent;
                    nextActivityTime = Objects.requireNonNull(messages.peek()).getCreationTime();
                }
              if (Objects.requireNonNull(messages.peek()).getCreationTime().compareTo(nextActivityTime) < 0) {
                      nextActivity = Type.MessageSent;
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
