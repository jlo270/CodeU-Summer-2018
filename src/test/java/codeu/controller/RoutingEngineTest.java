package codeu.controller;

import java.util.ArrayList;
import java.util.UUID;

import org.mockito.Mockito;

import codeu.model.data.BotRequest;
import codeu.controller.RocketBotHandler;

public class RoutingEngineTest {

  private RoutingEngine routingEngine;
  private BotRequest mockBotRequest;
  private ArrayList<String> fakeList;
  private RocketBotHandler rocketHandler;
  
  @Before
  public void setup() {
    fakeList = new ArrayList<String>();
    mockBotRequest =
        new BotRequest("rocket", 
            fakeList, 
            UUID.randomUUID());
  }
  
  @Test
  public void testRoutingEngine() throws IOException, ServletException{
    routingEngine.routeCommand(mockBotRequest);
    
    Mockito.verify(rocketHandler).handler(request);
  }
}
