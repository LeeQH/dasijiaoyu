<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<script type="text/javascript">
function queryInfo(type,classid){
	document.getElementById("classid").value=classid;
	var form=document.getElementById("from");
	form.action="<%=basePath%>/crawler/"+type;
	form.submit();
}
</script>
</head>
<body>
<!-- <div class="col-lg-3"> -->
<div>
	<nav class="bs-docs-sidebar">
		<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
			<c:forEach items="${classids}" var="entry" varStatus="status">
				<div class="panel panel-default">
					<div class="panel-heading" role="tab" id="heading${status.count}">
						<h4 class="panel-title">
							<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse${status.count}" aria-expanded="false" aria-controls="collapse${status.count}"> 
								<c:out value="${entry.key}" />
							</a>
						</h4>
					</div>

					<div id="collapse${status.count}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading${status.count}">
						<div class="panel-body">
							<div class="list-group">
							  <button type="button" class="list-group-item" onclick="queryInfo('stuInfo.action','${entry.value}')">学生信息</button>
							  <button type="button" class="list-group-item" onclick="queryInfo('scoreRank.action','${entry.value}')">成绩排名</button>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		<form id="from" action="" target="mainView" method="post">
			<input type="hidden" name="loginId" value="${teacher.loginId}" /> 
			<input type="hidden" name="password" value="${teacher.password}" /> 
			<input type="hidden" id="classid" name="classid" /> 
		</form>
	</nav>
</div>
	
</body>

</html>