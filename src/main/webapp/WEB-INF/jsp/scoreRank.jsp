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
	
	function updateMonthScore(path){
		var loginName=window.parent.document.getElementById("loginName").value;
		var loginPwd=window.parent.document.getElementById("loginPwd").value;
		var classId=window.parent.document.getElementById("classId").value;
		
		var url="<%=basePath%>"+path;
		var params={loginName:loginName,loginPwd:loginPwd,classId:classId};
		
		fromPost(url,params);
	}
</script>

</head>
<body>
<div style="width: 100%;">
	<span style="margin-left: 15px;"></span>
	<input type="text" placeholder="输入文字回车搜索" onchange="searchTable(this)">
	<button class="btn btn-primary" type="button" style="float: right;" onclick="updateMonthScore('/crawler/updateMonthScore.action')">更新成绩</button>
	<br><br>
	<table style="margin-left: 10px;" class="table table-hover" id="default">
			<thead id="head">
				<tr>
					<th>编号</th>
					<th>学生姓名</th>
					<th>成绩</th>
					<th>目标</th>
					<th>是否完成</th>
				</tr>
			</thead>
			<tbody id="body">
				<c:forEach items="${monthScoreList}" var="score" varStatus="status">
					<tr>
						<td><c:out value="${status.count}"/></td>
						<td><c:out value="${score.stuName}"/></td>
						<td><c:out value="${score.score}"/></td>
						<td><c:out value="${score.goalScore}"/></td>
						<td><c:out value="${score.finishGoal}"/></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>
</body>
<jsp:include page="/WEB-INF/jsp/alert.jsp"></jsp:include>
</html>