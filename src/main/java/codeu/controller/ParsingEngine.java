package codeu.controller;

import codeu.model.data.BotRequest;
import codeu.model.data.BotStructure;
import codeu.model.data.Message;
import codeu.model.data.RoutingEngine;
import java.util.regex.*;


public class parsingEngine {
 	public void ParseCommands(Message message){
 		BotRequest request;

 		// ^ to make sure we always start at the beginning of a string without whitespace.
 		// / to detect our command syntax.
 		// // to escape the following /.  
 		// (?P<command_name>\w+) as a required parameter capture group that is made up of at least 1 alphanumeric character
 		// \s as a white space chracter. : space, tab, newline, carriage return, vertical tab. 
 		final String commandRegex = "^/(?P<command_name>\\w+)(?:\\s+(?P<argument>\\w+))?\\s*$";
 		// compile the Regex command
 		Pattern pattern = Pattern.compile(commandRegex);
 		
 		//matches the regex command with the Message. 
		Matcher match = pattern.matcher(message.getContent());
		
		//if match is found, pass the command to RoutingEngine. 
		if (match.find()) {
			
			request.command = match.group("command_name");
			request.arguments.argument = match.group("argument"); 
			request.conversationId = message.getConversationId();

			RoutingEngine.routeCommand(request); 
		}
	}
}
