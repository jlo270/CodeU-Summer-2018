package codeu.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.mockito.Mockito;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.persistence.PersistentStorageAgent;

import org.junit.Assert;

import org.junit.Test;

public class AdminServletTest {

	private AdminServlet adminServlet;
	private HttpServletRequest mockRequest;
	private HttpServletResponse mockResponse;
	private RequestDispatcher mockRequestDispatcher;
	private ConversationStore mockConversationStore;
	private MessageStore mockMessageStore;
	private UserStore mockUserStore;
	private PersistentStorageAgent mockPersistentStorageAgent;
	private User user;


	@Before
	public void setup() {
	    adminServlet = new AdminServlet();
		mockRequest = Mockito.mock(HttpServletRequest.class);
		mockResponse = Mockito.mock(HttpServletResponse.class);
		mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
		Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/adminpage.jsp")).thenReturn(mockRequestDispatcher);
		mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);
		
		mockConversationStore = ConversationStore.getTestInstance(mockPersistentStorageAgent);
		adminServlet.setConversationStore(mockConversationStore);

	    mockMessageStore = MessageStore.getTestInstance(mockPersistentStorageAgent);
	    adminServlet.setMessageStore(mockMessageStore);

		mockUserStore = UserStore.getTestInstance(mockPersistentStorageAgent);
		adminServlet.setUserStore(mockUserStore);
		
	}

	@Test
	public void testDoGetUsers() throws IOException, ServletException {
		User USER_ONE =
		    new User(
		        UUID.randomUUID(),
                "username one",
	            "$2a$10$.e.4EEfngEXmxAO085XnYOmDntkqod0C384jOR9oagwxMnPNHaGLa",
	            Instant.now());
		User USER_TWO =
			 new User(
	             UUID.randomUUID(),
	            "username two",
	            "$2a$10$.e.4EEfngEXmxAO085XnYOmDntkqod0C384jOR9oagwxMnPNHaGLa",
			    Instant.now());
		mockUserStore.addUser(USER_ONE);
		mockUserStore.addUser(USER_TWO);
	
		
	    adminServlet.doGet(mockRequest, mockResponse);
	    
	    Mockito.verify(mockRequest).setAttribute("userCount", 2);
	    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
	  }
	
	@Test
	public void testDoGetMessages() throws IOException, ServletException {
		Message MESSAGE_ONE =
		        new Message(
				    UUID.randomUUID(), 
				    UUID.randomUUID(), 
				    UUID.randomUUID(), 
				    "contents_one", 
				    Instant.now());
		Message MESSAGE_TWO =
		        new Message(
				    UUID.randomUUID(), 
				    UUID.randomUUID(), 
				    UUID.randomUUID(), 
				    "contents_two", 
				    Instant.now());
		mockMessageStore.addMessage(MESSAGE_ONE);
		mockMessageStore.addMessage(MESSAGE_TWO);
	    adminServlet.doGet(mockRequest, mockResponse);

		
	    Mockito.verify(mockRequest).setAttribute("messageCount", 2);
	}

	@Test
	public void testDoGetConversation() throws IOException, ServletException {
		Conversation CONVERSATION_ONE =
		        new Conversation(
		        UUID.randomUUID(),
				UUID.randomUUID(),
				"title_one",
				Instant.now());
		Conversation CONVERSATION_TWO =
		        new Conversation(
		        UUID.randomUUID(),
				UUID.randomUUID(),
				"title_two",
				Instant.now());
		mockConversationStore.addConversation(CONVERSATION_ONE);
		mockConversationStore.addConversation(CONVERSATION_TWO);
	    adminServlet.doGet(mockRequest, mockResponse);

		
		Mockito.verify(mockRequest).setAttribute("conversationCount", 2);
	}
}
