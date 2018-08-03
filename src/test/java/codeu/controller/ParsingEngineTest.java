package codeu.controller;

import java.util.ArrayList;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import codeu.model.data.Message;
import codeu.model.data.BotRequest;
import java.io.IOException;
import java.time.Instant;

public class ParsingEngineTest {
	private ParsingEngine parsingEngine;
	private RoutingEngine mockRoutingEngine;
	private final UUID CONVERSATION_ID = UUID.randomUUID();
	private final UUID USER_ID = UUID.randomUUID();
    
	
	@Before
	public void setup() {
		parsingEngine = new ParsingEngine();
	}
	@Test
	
	public void testParsingEngine() throws IOException {
		mockRoutingEngine = Mockito.mock(RoutingEngine.class);
	    parsingEngine.setRoutingEngine(mockRoutingEngine);
	    
	    final Message callMessage =
			      new Message(
			          UUID.randomUUID(),
			          CONVERSATION_ID,
			          USER_ID,
			          "/commandName args",
			          Instant.ofEpochMilli(1000));
	    
	    parsingEngine.parseCommands(callMessage);
	    
	    ArrayList<String> fakeList1 = new ArrayList<String>();
		fakeList1.add("args");
		
		Mockito.verify(mockRoutingEngine).routeCommand(new BotRequest("commandName", fakeList1,
				CONVERSATION_ID));
	    
	    
		final Message callMessage2 =
			      new Message(
			          UUID.randomUUID(),
			          CONVERSATION_ID,
			          USER_ID,
			          "/commandName     arg1 arg2",
			          Instant.ofEpochMilli(1000));
	    
	    parsingEngine.parseCommands(callMessage2);
	    
	    ArrayList<String> fakeList2 = new ArrayList<String>();
		fakeList1.add("arg1");
		fakeList2.add("arg2");
		
		Mockito.verify(mockRoutingEngine).routeCommand(new BotRequest("commandName", fakeList2,
				CONVERSATION_ID));
		
		final Message callMessage3 =
			      new Message(
			          UUID.randomUUID(),
			          CONVERSATION_ID,
			          USER_ID,
			          "/commandName hello ",
			          Instant.ofEpochMilli(1000));
	    
	    parsingEngine.parseCommands(callMessage3);
	    
	    ArrayList<String> fakeList3 = new ArrayList<String>();
		fakeList3.add("hello");
			
	    Mockito.verify(mockRoutingEngine).routeCommand(new BotRequest("commandName", fakeList3,
				CONVERSATION_ID));
	
	}
}
	