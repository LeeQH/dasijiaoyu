<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="login">
	<meta http-equiv="x-rim-auto-match" content="none">

    <title>Login</title>
  </head>
<body>

	<form action="<%=basePath%>/crawler/login.action" method="post">
		<input type="text" name="loginId"/>
		<input type="password" name="password"/>
		<input type="submit" value="login">
	</form>
</body>
</html>