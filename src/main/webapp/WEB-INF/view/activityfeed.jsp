<%--
  Created by IntelliJ IDEA.
  User: hajames
  Date: 5/20/18
  Time: 7:51 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="codeu.model.data.Activity" %>
<%@ page import="java.util.List" %>

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

        <h3>Recent Activity (all times in CDT):
          <a href="" style="float: right">Refresh</a></h3>

        <hr/>

        <div id="feed">
          <%
          List<Activity> activities =
            (List<Activity>) request.getAttribute("activities");
          if(activities == null || activities.isEmpty()){
          %>
            <p>Get involved to see some activity.</p>
          <%
          }
          else {
          %>
            <ul class="mdl-list">
          <%
              for (Activity activity : activities) {
            %>
              <li><strong><%= activity.getCreationTime()%></strong>:
                <%= activity.getOutput()%></li>
            <%
            }
            %>
           <%
           }
           %>
          </ul>
        </div>

  </div>
</body>
</html>
