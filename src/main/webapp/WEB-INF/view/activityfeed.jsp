<%--
  Created by IntelliJ IDEA.
  User: hajames
  Date: 5/20/18
  Time: 7:51 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Activity" %>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.time.ZoneId"%>
<%@ page import="codeu.model.store.basic.ActivityStore" %>
<%
Activity activity = (Activity) request.getAttribute("activity");
List<Activity> sortedActivities = (List<Activity>) request.getAttribute("sortedActivities");
%>

<!DOCTYPE html>
<html>
<head>
  <title>Activity Feed</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null) { %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else { %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <a href="/activity">Activity Feed</a>
  </nav>

  <div id="container">
    <h1>Activity Feed</h1>

    <div id="container">

        <h3>Recent Activity:
          <a href="" style="float: right">Refresh</a></h3>

        <hr/>

        <div id="feed">
          <ul>
            <%--
              DateTimeFormatter formatter =
                  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                   .withZone( ZoneId.systemDefault() );
              for (Activity activity : sortedActivities) {
                String creationTime = formatter.format(ActivityStore.getInstance());
                  .getObjectId();
            --%>
              <li><strong><%--= creationTime --%>creationTime:</strong> Activity details here</li>
            <%--
              }
            --%>
          </ul>
        </div>

  </div>
</body>
</html>
