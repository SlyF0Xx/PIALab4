<%--
  Created by IntelliJ IDEA.
  model.User: SlyFox
  Date: 09.12.2017
  Time: 22:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- <%@ page import="DotServiceImpl" %> --%>
<html>
  <body>



  <form action="/untitled_war/rest/API/login" method="post">
    <input id="name" name="name">
    <input type="password" id="password" name="password">
    <input type="submit">
  </form>

  <form action="/untitled_war/rest/API/register" method="post">
    <input name="name">
    <input type="password" name="password">
    <input type="submit">
  </form>
  <%
    out.print("Ou");


/*    DotServiceImpl dotService =(DotServiceImpl)session.getAttribute("service");
    if (dotService.isIn(1, 1.0, 4)) {
      out.print("Yey");
    } else {
      out.print("Ou");
    }
    */
  %>

  </body>
</html>
