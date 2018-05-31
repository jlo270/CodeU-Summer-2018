<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Admin Page</title>
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

      <h1>Administration</h1>
      <h2>Site Statistics</h2>

	  <p>Here are some site stats:</p>
      <ul>
        <li><strong>Users:</strong> placeholder</li>
        <li><strong>Conversations:</strong> placeholder</li>
        <li><strong>Messages:</strong> placeholder</li>
        <li><strong>Most active user:</strong> placeholder</li>
        <li><strong>Newest User:</strong> placeholder</li>
        <li><strong>Wordiest User:</strong> placeholder</li>
      </ul>
    </div>
  </div>
</body>
</html>