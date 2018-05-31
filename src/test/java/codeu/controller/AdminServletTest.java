package codeu.controller;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.mockito.Mockito;

import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;

import org.junit.Test;

public class AdminServletTest {

	private AdminServlet adminServlet;
	private HttpServletRequest mockRequest;
	private HttpServletResponse mockResponse;
	private RequestDispatcher mockRequestDispatcher;
	private ConversationStore mockConversationStore;
	private MessageStore mockMessageStore;
	private UserStore mockUserStore;

	@Before
	public void setup() {
		adminServlet = new AdminServlet();
		mockRequest = Mockito.mock(HttpServletRequest.class);
		mockResponse = Mockito.mock(HttpServletResponse.class);
		mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
		Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/adminpage.jsp")).thenReturn(mockRequestDispatcher);

		mockConversationStore = Mockito.mock(ConversationStore.class);
		adminServlet.setConversationStore(mockConversationStore);

		mockMessageStore = Mockito.mock(MessageStore.class);
		adminServlet.setMessageStore(mockMessageStore);

		mockUserStore = Mockito.mock(UserStore.class);
		adminServlet.setUserStore(mockUserStore);
	}

	@Test
	public void testDoGet() throws IOException, ServletException {

		int userCount = 0;
		Mockito.when(mockUserStore.getNumUsers()).thenReturn(userCount);
		int messageCount = 0;
		Mockito.when(mockMessageStore.getNumMessages()).thenReturn(messageCount);
		int conversationCount = 0;
		Mockito.when(mockConversationStore.getNumConversations()).thenReturn(conversationCount);
		
	    adminServlet.doGet(mockRequest, mockResponse);
	    
	    Mockito.verify(mockRequest).setAttribute("userCount", userCount);
	    Mockito.verify(mockRequest).setAttribute("messageCount", messageCount);
	    Mockito.verify(mockRequest).setAttribute("conversationCount", conversationCount);
	    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
	  }

}
