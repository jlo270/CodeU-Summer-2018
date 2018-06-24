package codeu.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import codeu.model.data.Activity;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.data.Conversation;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.persistence.PersistentStorageAgent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


public class ActivityFeedServletTest {

  private ActivityFeedServlet activityFeedServlet;
  private HttpServletRequest mockRequest;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private PersistentStorageAgent mockPersistentStorageAgent;
  private final String creationTime = "test time";
  private final String output = "test output";
  private MessageStore mockMessageStore;
  private ConversationStore mockConversationStore;
  private UserStore mockUserStore;

  private final UUID CONVERSATION_ID_ONE = UUID.randomUUID();
  private final UUID USER_ID_ONE = UUID.randomUUID();
  private final UUID USER_ID_TWO = UUID.randomUUID();
  private final Message MESSAGE_ONE =
      new Message(
          UUID.randomUUID(),
          CONVERSATION_ID_ONE,
          USER_ID_ONE,
          "message one",
          Instant.ofEpochMilli(1000));
  private final Message MESSAGE_TWO =
      new Message(
          UUID.randomUUID(),
          CONVERSATION_ID_ONE,
          USER_ID_TWO,
          "message two",
          Instant.ofEpochMilli(4000));
  private final User USER_ONE =
      new User(
          USER_ID_ONE,
          "test_username_one",
          "$2a$10$/zf4WlT2Z6tB5sULB9Wec.QQdawmF0f1SbqBw5EeJg5uoVpKFFXAa",
          Instant.ofEpochMilli(2000));
  private final User USER_TWO =
      new User(
          USER_ID_TWO,
          "test_username_two",
          "$2a$10$lgZSbmcYyyC7bETcMo/O1uUltWYDK3DW1lrEjCumOE1u8QPMlzNVy",
          Instant.ofEpochMilli(5000));
  private final Conversation CONVERSATION_ONE =
      new Conversation(
          CONVERSATION_ID_ONE, USER_ID_ONE, "conversation_one", Instant.ofEpochMilli(3000));

  @Before
  public void setup() {
    activityFeedServlet = new ActivityFeedServlet();
    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/activityfeed.jsp"))
        .thenReturn(mockRequestDispatcher);

    mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);

    mockConversationStore = ConversationStore.getTestInstance(mockPersistentStorageAgent);
    activityFeedServlet.setConversationStore(mockConversationStore);

    mockMessageStore = MessageStore.getTestInstance(mockPersistentStorageAgent);
    activityFeedServlet.setMessageStore(mockMessageStore);

    mockUserStore = UserStore.getTestInstance(mockPersistentStorageAgent);
    activityFeedServlet.setUserStore(mockUserStore);
  }

  @Test
  public void testDoGetNothing() throws IOException, ServletException {
    List<Activity> noActivities = new ArrayList<>();

    activityFeedServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("activities", noActivities);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGetOneOfEach() throws IOException, ServletException {
    final List<Message> messageList = new ArrayList<>();
    messageList.add(MESSAGE_ONE);
    mockMessageStore.setMessages(messageList);
    final List<User> userList = new ArrayList<>();
    userList.add(USER_ONE);
    mockUserStore.setUsers(userList);
    final List<Conversation> conversationList = new ArrayList<>();
    conversationList.add(CONVERSATION_ONE);
    mockConversationStore.setConversations(conversationList);
    activityFeedServlet.setConversationStore(mockConversationStore);
    activityFeedServlet.setMessageStore(mockMessageStore);
    activityFeedServlet.setUserStore(mockUserStore);

    List<Activity> allActivities = new LinkedList<>();
    String messageOneContent = "test_username_one sent in conversation_one: message one";
    String creationTimeOne = "1969-12-31 18:00:01";
    String creationTimeTwo = "1969-12-31 18:00:02";
    String creationTimeThree = "1969-12-31 18:00:03";
    allActivities.add(new Activity(creationTimeOne, messageOneContent));
    String userOneContent = "test_username_one joined Charmer Chat.";
    allActivities.add(new Activity(creationTimeTwo, userOneContent));
    String conversationOneContent = "test_username_one started conversation conversation_one";
    allActivities.add(new Activity(creationTimeThree, conversationOneContent));

    activityFeedServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).setAttribute("activities", allActivities);
  }

  @Test
  public void testDoGetUserOnly() throws IOException, ServletException {
    final List<User> userList = new ArrayList<>();
    userList.add(USER_ONE);
    mockUserStore.setUsers(userList);
    activityFeedServlet.setUserStore(mockUserStore);

    List<Activity> allActivities = new LinkedList<>();
    String creationTimeTwo = "1969-12-31 18:00:02";
    String userOneContent = "test_username_one joined Charmer Chat.";
    allActivities.add(new Activity(creationTimeTwo, userOneContent));

    activityFeedServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).setAttribute("activities", allActivities);
  }

  @Test
  public void testDoGetUserAndConversationOnly() throws IOException, ServletException {
    final List<User> userList = new ArrayList<>();
    userList.add(USER_ONE);
    mockUserStore.setUsers(userList);
    final List<Conversation> conversationList = new ArrayList<>();
    conversationList.add(CONVERSATION_ONE);
    mockConversationStore.setConversations(conversationList);
    activityFeedServlet.setConversationStore(mockConversationStore);
    activityFeedServlet.setUserStore(mockUserStore);

    List<Activity> allActivities = new LinkedList<>();
    String creationTimeTwo = "1969-12-31 18:00:02";
    String creationTimeThree = "1969-12-31 18:00:03";
    String userOneContent = "test_username_one joined Charmer Chat.";
    allActivities.add(new Activity(creationTimeTwo, userOneContent));
    String conversationOneContent = "test_username_one started conversation conversation_one";
    allActivities.add(new Activity(creationTimeThree, conversationOneContent));

    activityFeedServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).setAttribute("activities", allActivities);
  }
}

