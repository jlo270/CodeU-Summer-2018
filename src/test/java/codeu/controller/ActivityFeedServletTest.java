package codeu.controller;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.data.Conversation;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.persistence.PersistentStorageAgent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


public class ActivityFeedServletTest {

  private ActivityFeedServlet activityFeedServlet;
  private HttpServletRequest mockRequest;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private MessageStore messageStore;
  private ConversationStore conversationStore;
  private UserStore userStore;
  private PersistentStorageAgent mockPersistentStorageAgent;

  private final UUID CONVERSATION_ID_ONE = UUID.randomUUID();
  private final String creationTime = "test time";
  private final String output = "test output";
  private final Message MESSAGE_ONE =
      new Message(
          UUID.randomUUID(),
          CONVERSATION_ID_ONE,
          UUID.randomUUID(),
          "message one",
          Instant.ofEpochMilli(1000));
  private final Message MESSAGE_TWO =
      new Message(
          UUID.randomUUID(),
          CONVERSATION_ID_ONE,
          UUID.randomUUID(),
          "message two",
          Instant.ofEpochMilli(4000));
  private final User USER_ONE =
      new User(
          UUID.randomUUID(),
          "test_username_one",
          "$2a$10$/zf4WlT2Z6tB5sULB9Wec.QQdawmF0f1SbqBw5EeJg5uoVpKFFXAa",
          Instant.ofEpochMilli(2000));
  private final User USER_TWO =
      new User(
          UUID.randomUUID(),
          "test_username_two",
          "$2a$10$lgZSbmcYyyC7bETcMo/O1uUltWYDK3DW1lrEjCumOE1u8QPMlzNVy",
          Instant.ofEpochMilli(5000));
  private final Conversation CONVERSATION_ONE =
      new Conversation(
          UUID.randomUUID(), UUID.randomUUID(), "conversation_one", Instant.ofEpochMilli(3000));
  private final Conversation CONVERSATION_TWO =
      new Conversation(
          UUID.randomUUID(), UUID.randomUUID(), "conversation_two", Instant.ofEpochMilli(6000));
  @Before
  public void setup() {
    activityFeedServlet = new ActivityFeedServlet();
    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/activityfeed.jsp"))
        .thenReturn(mockRequestDispatcher);

    final List<Message> messageList = new ArrayList<>();
    messageList.add(MESSAGE_ONE);
    messageList.add(MESSAGE_TWO);
    messageStore.setMessages(messageList);
    final List<User> userList = new ArrayList<>();
    userList.add(USER_ONE);
    userList.add(USER_TWO);
    userStore.setUsers(userList);
    final List<Conversation> conversationList = new ArrayList<>();
    conversationList.add(CONVERSATION_ONE);
    conversationList.add(CONVERSATION_TWO);
    conversationStore.setConversations(conversationList);
  }

  @Test
  public void testDoGet() throws IOException, ServletException {
    List<Activity> fakeActivityList = new ArrayList<>();
    fakeActivityList.add(new Activity(output,creationTime));

    activityFeedServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("activities", fakeActivityList);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

 /* @Test
  public void testGetActivities() {
    List<Message> testMessages = new ArrayList<>();
    testMessages.add(MESSAGE_ONE);
    testMessages.add(MESSAGE_TWO);
    List<User> testUsers = new ArrayList<>();
    testUsers.add(USER_ONE);
    testUsers.add(USER_TWO);
    List<Conversation> testConversations = new ArrayList<>();
    testConversations.add(CONVERSATION_ONE);
    testConversations.add(CONVERSATION_TWO);

    List<Activity> expectedActivities = new ArrayList<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone( ZoneId.systemDefault() );

    User author = userStore.getUserWithId(MESSAGE_ONE.getAuthorId());
    Conversation conversation = conversationStore.getConversationWithId(MESSAGE_ONE.getConversationId());
    String messageOutput = author.getName() + " sent in " + conversation.getTitle() + ": " + MESSAGE_ONE.getContent();
    Activity activity1 = new Activity(messageOutput,formatter.format(MESSAGE_ONE.getCreationTime()));
    expectedActivities.add(activity1);

    String userOutput = USER_ONE.getName() + "joined Charmer Chat.";
    Activity activity2 = new Activity(userOutput,formatter.format(USER_ONE.getCreationTime()));
    expectedActivities.add(activity2);

    User owner = userStore.getUserWithId(CONVERSATION_ONE.getOwnerId());
    String conversationOutput = owner.getName() + " started conversation " + conversation.getTitle();
    Activity activity3 = new Activity(conversationOutput,formatter.format(CONVERSATION_ONE.getCreationTime()));
    expectedActivities.add(activity3);

    author = userStore.getUserWithId(MESSAGE_TWO.getAuthorId());
    conversation = conversationStore.getConversationWithId(MESSAGE_TWO.getConversationId());
    messageOutput = author.getName() + " sent in " + conversation.getTitle() + ": " + MESSAGE_TWO.getContent();
    Activity activity4 = new Activity(messageOutput,formatter.format(MESSAGE_TWO.getCreationTime()));
    expectedActivities.add(activity4);

    userOutput = USER_TWO.getName() + "joined Charmer Chat.";
    Activity activity5 = new Activity(userOutput,formatter.format(USER_TWO.getCreationTime()));
    expectedActivities.add(activity5);

    owner = userStore.getUserWithId(CONVERSATION_TWO.getOwnerId());
    conversationOutput = owner.getName() + " started conversation " + CONVERSATION_TWO.getTitle();
    Activity activity6 = new Activity(conversationOutput,formatter.format(CONVERSATION_TWO.getCreationTime()));
    expectedActivities.add(activity6);

    List<Activity> resultActivities = ActivityFeedServlet.getActivities();

    Assert.assertEquals(resultActivities,expectedActivities);
  }*/

}

