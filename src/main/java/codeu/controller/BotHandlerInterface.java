package codeu.controller;

import codeu.model.data.BotRequest;

/**
 * The interface which is implemented by each bot type in order to produce the desired
 * output. Every bot have a bot-specific handler which implements BotHandlerInterface.
 */
public interface BotHandlerInterface {
  /**
   * Handles BotRequests. When a bot is triggered and a BotRequest created, the handler
   * locates the conversation where it was triggered, consolidates the desired output
   * into a single string and sends that string as a Message in the located Conversation.
   */
  public String handler(BotRequest request);
}

