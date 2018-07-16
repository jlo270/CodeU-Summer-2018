package codeu.controller;

import codeu.model.data.BotRequest;
import java.lang.IllegalArgumentException;

/** This is the Routing Engine that will receive a BotRequest and pass it to the 
 * appropriate BotHandler class*/
public class RoutingEngine {

  private BotHandlerInterface botHandler;
  /**
   * primary routing function that passes the request to the appropriate Handler
   * needs to be updated for each bot
   */
  public void routeCommand(BotRequest request) {

    switch (request.getCommand()) {
      //case = "rocket":
        //botHandler = new RocketBotHandler();
      default:
        throw new IllegalArgumentException();
    }
    botHandler.handler(request);
  }
}
