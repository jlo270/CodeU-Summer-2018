package codeu.controller;

import codeu.model.data.BotRequest;
import codeu.model.data.BotStructure;
import codeu.model.data.Message;
import codeu.model.data.RoutingEngine;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


	

public class parsingEngine {
	/* 
	 * Here, regex is a better way to parse bot commands rather than matching them with some keywords.
	 * Regex can parse varying bot request according to pattern. 
	 * For example, the following regex can parse "/time", "/  time" or "/time    " as "time". 
	 * 
	 */
	
	static final String commandRegex = "^/(?P<command_name>\\w+)(?:\\s+(?P<argument>\\w+))?\\s*$";
	
	// compile the Regex command
	 static final Pattern pattern = Pattern.compile(commandRegex);
	
 	public void ParseCommands(Message message){ 
 		final UUID conversationId;
 		
 		
 		//matches the regex command with the Message. 
		Matcher match = pattern.matcher(message.getContent());
		
		//if match is found, pass the command to RoutingEngine. 
		if (match.find()) {
			final BotRequest request = new BotRequest(match.group("command_name"), match.group("argument"), conversationId ); 
			request.conversationId = message.getConversationId();

			RoutingEngine.routeCommand(request); 
		}
	}
}
