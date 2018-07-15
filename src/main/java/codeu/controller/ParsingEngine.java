package codeu.controller;

import codeu.model.data.BotRequest;
import codeu.model.data.BotStructure;
import codeu.model.data.Message;
import codeu.model.data.RoutingEngine;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class parsingEngine {
 	public void ParseCommands(Message message){
 		
 
 		final String commandRegex = "^/(?P<command_name>\\w+)(?:\\s+(?P<argument>\\w+))?\\s*$";
 		// compile the Regex command
 		Pattern pattern = Pattern.compile(commandRegex);
 		
 		//matches the regex command with the Message. 
		Matcher match = pattern.matcher(message.getContent());
		
		//if match is found, pass the command to RoutingEngine. 
		if (match.find()) {
			BotRequest request;
			request.command = match.group("command_name");
			//request.arguments.argument = match.group("argument"); 
			request.conversationId = message.getConversationId();

			RoutingEngine.routeCommand(request); 
		}
	}
}
