package codeu.controller;

import codeu.model.data.BotRequest;

/**
 * Defines interface for all bots to implement. Bots are invoked by users entering a
 * regex-matched syntax, which may or may not pass additional arguments into implementing
 * classes. Those implementations will return a string to be posted into the conversation
 * that executed the command.
 */
public interface BotHandlerInterface {
  /**
   * Handles BotRequests. When a bot is triggered and a BotRequest created, the handler
   * locates the conversation where it was triggered, consolidates the desired output
   * into a single string and sends that string as a Message in the located Conversation.
   */
  public void handler(BotRequest request);
}

