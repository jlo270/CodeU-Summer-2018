package codeu.controller;

import java.util.ArrayList;
import java.util.UUID;
import javax.servlet.ServletException;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import codeu.model.data.BotRequest;
import java.lang.IllegalArgumentException;



public class RoutingEngineTest {

  private RoutingEngine routingEngine;
  private BotRequest mockBotRequest;
  private ArrayList<String> fakeList;
  
  
  @Before
  public void setup() { 
    routingEngine = new RoutingEngine();
  }
  
  @Test (expected = IllegalArgumentException.class)
  public void testDefault() throws IOException, ServletException{
    mockBotRequest =
        new BotRequest("test_command", 
            fakeList, 
            UUID.randomUUID());
    routingEngine.routeCommand(mockBotRequest);
  }
  
  @Test
  public void testRoutingEngine() throws IOException, ServletException{
    
    
  }
}
