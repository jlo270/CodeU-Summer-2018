package codeu.controller;

import codeu.model.data.BotRequest;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import codeu.model.store.persistence.PersistentStorageAgent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;

public class RocketBotHandlerTest {
  private UserStore mockUserStore;
  private final UUID CONVERSATION_ID_ONE = UUID.randomUUID();
  private final UUID USER_ID_ONE = UUID.randomUUID();
  private final Message MESSAGE_ONE =
      new Message(
          UUID.randomUUID(),
          CONVERSATION_ID_ONE,
          USER_ID_ONE,
          "3...2...1...BLASTOFF!",
          Instant.ofEpochMilli(1000));

  @Before
  public void setup() {
    PersistentStorageAgent mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);
    mockUserStore = UserStore.getTestInstance(mockPersistentStorageAgent);
  }

  /**
   * This only tests that a User was not created, and does not check on the other functionality
   * of the implementation. I'd like some feedback on the next logical steps to take.
   */
  @Test
  public void testHandleExistingUser() {
    User existingUser = new User(
        USER_ID_ONE,
        "RocketBot",
        "unusable-password-hash",
        Instant.ofEpochMilli(1000));
    final List<User> userList = new ArrayList<>();
    userList.add(existingUser);
    mockUserStore.setUsers(userList);
    ArrayList<String> args = new ArrayList<>();
    BotRequest request = new BotRequest("rocket",args,CONVERSATION_ID_ONE);

    RocketBotHandler rocketBot = new RocketBotHandler();

    rocketBot.handler(request);
    Assert.assertEquals(mockUserStore.getUser("RocketBot"),existingUser);
  }

  /**
   * This only tests that a User was created when the handler was called for a bot that
   * had never been called before. Other checks must be added to test for other functionality
   * of the implementation.
   */
  @Test
  public void testHandleNewUser() {
    final List<User> userList = new ArrayList<>();
    mockUserStore.setUsers(userList);
    ArrayList<String> args = new ArrayList<>();
    BotRequest request = new BotRequest("rocket",args,CONVERSATION_ID_ONE);

    User testUser = new User(
        USER_ID_ONE,
        "RocketBot",
        "unusable-password-hash",
        Instant.now());

    RocketBotHandler rocketBot = new RocketBotHandler();

    rocketBot.handler(request);
    Assert.assertEquals(mockUserStore.getUser("RocketBot"),testUser);
  }
}
