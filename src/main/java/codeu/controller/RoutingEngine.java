package codeu.controller;

import codeu.model.data.BotRequest;
import codeu.controller.RocketBotHandler;

/** This is the Routing Engine that will receive a BotRequest and pass it to the 
 * appropriate BotHandler class*/
public class RoutingEngine {

  private RocketBotHandler rocketHandler;
  
  /**
   * primary routing function that passes the request to the appropriate Handler
   * needs to be updated for each bot
   */
  public String routeCommand(BotRequest request) {

    switch (request.getCommand()) {
      case "rocket":
        rocketHandler.handler(request);
    }

  }
}
