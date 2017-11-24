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

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="x-rim-auto-match" content="none">
<title>功能页面</title>

<script src="<%=basePath%>/static/js/jquery-3.1.1.min.js" type="text/javascript"></script>
<script src="<%=basePath%>/static/js/bootstrap.min.js" type="text/javascript"></script>
<script src="<%=basePath%>/static/js/utils.js" type="text/javascript"></script>

<link href="<%=basePath%>/static/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/static/css/mycss.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
	$(function() {
		//设置iframe的高
		setIframeHeight();
	});
</script>

</head>
<body>
<div style="width: 100%;">
	<span style="margin-left: 15px;"></span>
	<input type="text" placeholder="输入文字回车搜索" onchange="searchTable(this)">
	<br><br>
	<c:forEach items="${page}" var="table" varStatus="status">
		<div style="float: left;width: 30%;margin-left: 15px;">
			<table class="table table-hover table-bordered" id="${status.count}">
				<c:if test="${status.count==1}">
					<tr><th colspan="3">日排名</th></tr>	
				</c:if>
				<c:if test="${status.count==2}">
					<tr><th colspan="3">月排名</th></tr>	
				</c:if>
				<c:if test="${status.count==3}">
					<tr><th colspan="3">年排名</th></tr>	
				</c:if>
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
</div>
</body>

</html>