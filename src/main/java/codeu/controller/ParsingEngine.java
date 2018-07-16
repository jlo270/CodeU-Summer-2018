package codeu.controller;

import codeu.model.data.BotRequest;
import codeu.model.data.BotStructure;
import codeu.model.data.Message;
import codeu.model.data.RoutingEngine;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
 * This class parses the BotCommands and will be invoked from ChatServlet.
 * MessangeContent of DoPost() in ChatServlet will be passed in as parameter.  
 * 
 */
public class ParsingEngine {
  /*
   * This regex looks for a command name as /<command_name>, where command_name is alphanumeric, followed by whitespace-separated 0-N alphanumeric arguments.
   * Examples:
   * /command
   * /command arg1
   * /command arg1 arg2 arg3 arg4
   */

  static final String commandRegex = "^/(?P<command_name>\\w+)(?:\\s+(?P<argument>\\w+))?\\s*$";

  // compile the Regex command
  static final Pattern pattern = Pattern.compile(commandRegex);

  public static void parseCommands(Message message) {
    
    // Matches the regex command with the Message.
    final Matcher match = pattern.matcher(message.getContent());
    
    // If match is found, pass the command to RoutingEngine.
    if (match.find()) {
      final BotRequest request = new BotRequest(match.group("command_name"), match.group("argument"),
    		  message.getConversationId());;

      RoutingEngine.routeCommand(request);
    }
  }
}
