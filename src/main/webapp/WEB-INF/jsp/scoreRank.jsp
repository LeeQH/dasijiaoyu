<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
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
<%-- <script src="<%=basePath%>/static/js/index.js" type="text/javascript"></script> --%>


</head>
<body>
	<c:forEach items="${page}" var="table" varStatus="status">
		<div style="width: 230px;  float: left;margin-left: 85px;">
		<table id="${status.count}">
			<c:forEach items="${table}" var="row">
				<tr>
					<c:forEach items="${row}" var="col">
						<td><c:out value="${col}" /></td>
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
		</div>
	</c:forEach>

</body>

</html>