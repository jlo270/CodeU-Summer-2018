package codeu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AdminServlet extends HttpServlet {
	
	/**
	 * This function fires when a user requests the /admin URL. It forwards the request to adminpage.jsp
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		request.getRequestDispatcher("/WEB-INF/view/adminpage.jsp").forward(request, response);
	}
	
	
}
