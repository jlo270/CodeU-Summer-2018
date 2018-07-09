package codeu.controller;

import codeu.model.data.BotRequest;
import codeu.model.data.RocketBotHandler;
	
public class RoutingEngine {
	
	//primary routing function that passes the request to the appropriate Handler
	//needs to be updated for each bot
	public String routeCommand(BotRequest request) {
		
		switch (request.getCommand()) {
		case "rocket":
			RocketBotHandler.handler(request);
		}
		
			
	}
}
