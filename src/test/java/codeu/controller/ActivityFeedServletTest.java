package codeu.controller;

import codeu.model.data.Activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import codeu.model.store.persistence.PersistentDataStoreException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ActivityFeedServletTest {

    private ActivityFeedServlet activityFeedServlet;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private RequestDispatcher mockRequestDispatcher;

    @Before
    public void setup() {
        activityFeedServlet = new ActivityFeedServlet();
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);
        mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
        Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/activityfeed.jsp"))
                .thenReturn(mockRequestDispatcher);

    }

    @Test
    public void testDoGet() throws IOException, ServletException, PersistentDataStoreException {
        List<Activity> fakeActivityList = new ArrayList<>();
        fakeActivityList.add(new Activity());

        activityFeedServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockRequest).setAttribute("activities", fakeActivityList);
        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);}
}
