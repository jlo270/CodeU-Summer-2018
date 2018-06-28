package codeu.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	public void testDoGet() throws IOException, ServletException {
		User fakeUser1 = new User(UUID.randomUUID(), "test_name1", "test_pass1", Instant.now());
		User fakeUser2 = new User(UUID.randomUUID(), "test_name2", "test_pass2", Instant.now());
		mockUserStore.addUser(fakeUser1);
		mockUserStore.addUser(fakeUser2);
		Conversation fakeConversation1 = new Conversation(UUID.randomUUID(), fakeUser1.getId(), "test_conversation1", Instant.now());
		Conversation fakeConversation2 = new Conversation(UUID.randomUUID(), fakeUser2.getId(), "test conversation2", Instant.now());
		mockConversationStore.addConversation(fakeConversation1);
		mockConversationStore.addConversation(fakeConversation2);
		Message fakeMessage1 = new Message(UUID.randomUUID(), fakeConversation1.getId(), fakeUser1.getId(), "test_content", Instant.now());
		Message fakeMessage2 = new Message(UUID.randomUUID(), fakeConversation2.getId(), fakeUser2.getId(), "test_content_two", Instant.now());
		mockMessageStore.addMessage(fakeMessage1);
		mockMessageStore.addMessage(fakeMessage2);
		
		int userCount = mockUserStore.getNumUsers();
		Assert.assertEquals(2, userCount);
		int messageCount = mockMessageStore.getNumMessages();
		Assert.assertEquals(2, messageCount);
		int conversationCount = mockConversationStore.getNumConversations();
		Assert.assertEquals(2, conversationCount);
		
	    adminServlet.doGet(mockRequest, mockResponse);
	    
	   // Mockito.verify(mockRequest).setAttribute("wordiest", wordiest);
	   //Mockito.verify(mockRequest).setAttribute("newest", newest);
	    Mockito.verify(mockRequest).setAttribute("userCount", userCount);
	    Mockito.verify(mockRequest).setAttribute("messageCount", messageCount);
	    Mockito.verify(mockRequest).setAttribute("conversationCount", conversationCount);
	    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
	  }

}
