<%--
  Created by IntelliJ IDEA.
  User: hajames
  Date: 5/20/18
  Time: 7:51 PM
  To change this template use File | Settings | File Templates.
--%>
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
    <a href="/activityfeed.jsp">Activity Feed</a>
  </nav>

  <div id="container">
    <h1>Activity Feed</h1>
    <h3>Things are happening all over Charmer Chat!</h3>
    </br>
    <div id="activity-block">
        <h3>Recently...</h3>
    </div>
  </div>
</body>
</html>

