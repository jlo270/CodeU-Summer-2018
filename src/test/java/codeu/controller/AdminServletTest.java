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
		
		int userCount = mockUserStore.getNumUsers();
		int messageCount = mockMessageStore.getNumMessages();
		int conversationCount = mockConversationStore.getNumConversations();
		
	    adminServlet.doGet(mockRequest, mockResponse);
	    
	    Mockito.verify(mockRequest).setAttribute("userCount", userCount);
	    Mockito.verify(mockRequest).setAttribute("messageCount", messageCount);
	    Mockito.verify(mockRequest).setAttribute("conversationCount", conversationCount);
	    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
	  }

}
