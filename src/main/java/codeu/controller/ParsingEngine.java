package codeu.controller;

import codeu.model.data.BotRequest;
import codeu.model.data.BotStructure;
import codeu.model.data.RoutingEngine;
import java.util.regex.*;


public class ParsingEngine {
 	public void ParseCommands(String message){
 		BotRequest request;

 		// ^ to make sure we always start at the beginning of a string without whitespace.
 		// / to detect our command syntax.
 		// (?P<command_name>\w+) as a required parameter capture group that is made up of at least 1 alphanumeric character
 		// \s as a white space chracter. : space, tab, newline, carriage return, vertical tab. 
 		final String commandRegex = "^/(?P<command_name>\w+)(?:\s+(?P<argument>\w+))?\s*$";

 		RegexMatch match = regex.match(commandRegex, message);

		if (match != null) {
			
			request.command = match.get(command_name);
			request.arguments.argument = match.get(argument); 
			request.conversationId = message.getConversationId();

			RoutingEngine.routeCommand(request); 
		}
	}
}
