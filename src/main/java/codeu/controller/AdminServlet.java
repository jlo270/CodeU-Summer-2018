package codeu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;

/** Servlet class responsible for the admin page */
public class AdminServlet extends HttpServlet {
	/** Store class that gives access to Conversations. */
	private ConversationStore conversationStore;

	/** Store class that gives access to Messages. */
	private MessageStore messageStore;

	/** Store class that gives access to Users. */
	private UserStore userStore;

	@Override
	public void init() throws ServletException {
		super.init();
		setConversationStore(ConversationStore.getInstance());
		setMessageStore(MessageStore.getInstance());
		setUserStore(UserStore.getInstance());
	}

	/**
	 * Sets the ConversationStore used by this servlet. This function provides a
	 * common setup method for use by the test framework or the servlet's init()
	 * function.
	 */
	void setConversationStore(ConversationStore conversationStore) {
		this.conversationStore = conversationStore;
	}

	/**
	 * Sets the MessageStore used by this servlet. This function provides a common
	 * setup method for use by the test framework or the servlet's init() function.
	 */
	void setMessageStore(MessageStore messageStore) {
		this.messageStore = messageStore;
	}

	/**
	 * Sets the UserStore used by this servlet. This function provides a common
	 * setup method for use by the test framework or the servlet's init() function.
	 */
	void setUserStore(UserStore userStore) {
		this.userStore = userStore;
	}
	
	/**
	 * This function fires when a user requests the /admin URL. It forwards the
	 * request to adminpage.jsp
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setAttribute("userCount", userStore.getNumUsers());
		request.setAttribute("messageCount", messageStore.getNumMessages());
		request.setAttribute("conversationCount", conversationStore.getNumConversations());
		request.getRequestDispatcher("/WEB-INF/view/adminpage.jsp").forward(request, response);

	}
}
