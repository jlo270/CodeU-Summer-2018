package codeu.controller;

import java.util.ArrayList;
import java.util.UUID;
import javax.servlet.ServletException;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import codeu.model.data.BotRequest;


public class RoutingEngineTest {

  private RoutingEngine routingEngine;
  private BotRequest mockBotRequest;
  private ArrayList<String> fakeList;
  
  
  @Before
  public void setup() { 
    routingEngine = new RoutingEngine();
  }
  
  @Test
  public void testRoutingEngine() throws IOException, ServletException{
    mockBotRequest =
        new BotRequest("test_command", 
            fakeList, 
            UUID.randomUUID());
    String retVal = routingEngine.routeCommand(mockBotRequest);
    
    Assert.assertEquals(retVal, "error, command not found");
  }
}
