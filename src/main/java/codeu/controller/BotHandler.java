package codeu.controller;

import codeu.model.data.BotRequest;
import codeu.model.data.BotStructure;

public interface BotHandler {
  public String handler(BotRequest request);
}

