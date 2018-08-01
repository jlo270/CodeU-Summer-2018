package codeu.controller;

import codeu.model.data.BotRequest;
import codeu.model.data.BotStructure;
import codeu.model.data.Message;
import codeu.model.data.RoutingEngine;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;

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
   * Also this can handle any whitespace in between or trailing the arguments. 
   * Examples:
   * /command      args1
   * /command   args1     args2
   */

  static final String commandRegex = "^/(?<commandName>\\w+)(\\s+(?<arguments>\\w+)?)*$";

  // compile the Regex command
  static final Pattern pattern = Pattern.compile(commandRegex);

  public static void parseCommands(Message message) {
    
    // Matches the regex command with the Message.
    final Matcher match = pattern.matcher(message.getContent());
    
    // If match is found, pass the command to RoutingEngine.
    if (match.matches()) {
      final BotRequest request = new BotRequest(match.group("commandName"), splitArguments(match.group("arguments")),
    		  message.getConversationId());

      RoutingEngine.routeCommand(request);
    }
  }
  
  public static ArrayList<String> splitArguments(String arguments){
	  String[] splitStr = arguments.trim().split("\\s+");
	  ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(splitStr));
	  return arrayList;

  }
}
