<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="x-rim-auto-match" content="none">
    <title>功能页面</title>
	
<!--     <script src="/static/js/jquery-3.1.1.min.js" type="text/javascript"></script> -->
    <script src="<%=basePath%>/static/js/index.js" type="text/javascript"></script>

    
  </head>
<body>

	<c:forEach items="${classids}" var="entry">
		<input id="${entry.value}" type="radio" name="classids" value="${entry.value}"/>
		<label for="${entry.value}"><c:out value="${entry.key}" /></label>
		<br>
	</c:forEach>
	<br>
	<input id="1" value="stuInfo.action" type="radio" name="types" />
	<label for="1">学生信息</label>
	<br>
	<input id="2" value="scoreRank.action" type="radio" name="types" />
	<label for="2">成绩排名</label>
	<br>
		
	<form id="chooseAction" action="<%=basePath%>" method="post" onsubmit="return chooseAction()">
		<input type="hidden" name="loginId" value="${teacher.loginId}"/>
		<input type="hidden" name="password" value="${teacher.password}"/>
		<input type="hidden" id="classid" name="classid" />
		<input type="submit" value="确定" />
	</form>
	

</body>

</html>