<!DOCTYPE html>
<html>
<head>
  <title>CodeU Chat App</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
  </nav>

  <div id="container">
    <div
      style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">

      <p>
        Sorry, you don't have the correct permissions to view this page.
      </p>

    </div>
  </div>
</body>
</html>