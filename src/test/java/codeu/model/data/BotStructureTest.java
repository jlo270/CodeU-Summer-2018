package codeu.model.data;

import java.time.Instant;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class BotStructureTest {

  @Test
  public void testCreate() {
    User user = new User(UUID.randomUUID(),"Username One","abc123",Instant.ofEpochMilli(1000));
    String botName = "Bot Name One";
    BotRequest request = new BotRequest("Command one", new BotArguments(),UUID.randomUUID());
    String outputMessage = "Output message";

    BotStructure structure = new BotStructure(user, botName, request, outputMessage);

    Assert.assertEquals(user, structure.getBotUser());
    Assert.assertEquals(botName, structure.getBotName());
    Assert.assertEquals(request, structure.getRequest());
    Assert.assertEquals(outputMessage, structure.getOutputMessage());
  }
}
