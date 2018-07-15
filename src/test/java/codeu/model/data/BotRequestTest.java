package codeu.model.data;

import java.time.Instant;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class BotRequestTest {

  @Test
  public void testCreate() {
    String command = "Test command";
    BotArguments arguments = new BotArguments();
    UUID conversationId = UUID.randomUUID();

    BotRequest request = new BotRequest(command, arguments, conversationId);

    Assert.assertEquals(command, request.getCommand());
    Assert.assertEquals(arguments, request.getArguments());
    Assert.assertEquals(conversationId, request.getConversationId());
  }
}