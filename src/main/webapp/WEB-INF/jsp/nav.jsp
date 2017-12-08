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
function queryInfo(path,classid,classname){
	document.getElementById("queryClassid").value=classid;
	document.getElementById("classname").value=classname;
	var form=document.getElementById("query");
	form.action="<%=basePath%>"+path;
	form.submit();
}
function updateInfo(path,classid){
	document.getElementById("updateClassid").value=classid;
	var form=document.getElementById("update");
	form.action="<%=basePath%>"+path;
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
							<a style="display: block;" class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse${status.count}" aria-expanded="false" aria-controls="collapse${status.count}"> 
								<c:out value="${entry.key}" />
							</a>
						</h4>
					</div>

					<div id="collapse${status.count}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading${status.count}">
						<div class="panel-body">
							<div class="list-group">
							  <button type="button" class="list-group-item" onclick="queryInfo('stuInfo.action','${entry.value}','${entry.key}')">学生信息</button>
							  <button type="button" class="list-group-item" onclick="queryInfo('scoreRank.action','${entry.value}','${entry.key}')">成绩排名</button>
							  <button type="button" class="list-group-item" onclick="updateInfo('/crawler/UpdateStuInfo.action','${entry.value}')">更新(学生变动)</button>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
			<div class="panel panel-default">
				<div class="panel-heading" role="tab" id="heading${status.count}">
					<h4 class="panel-title">
						<a style="display: block;" class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse${status.count}" aria-expanded="false" aria-controls="collapse${status.count}"> 
							更新 
						</a>
					</h4>
				</div>

				<div id="collapse${status.count}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading${status.count}">
					<div class="panel-body">
						<div class="list-group">
						  <button type="button" class="list-group-item" onclick="updateInfo()">更新班级列表</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<form id="query" action="" target="mainView" method="post">
			<input type="hidden" name="id" value="${teacherInfo.id}" /> 
			<input type="hidden" name="loginName" value="${teacherInfo.loginName}" /> 
			<input type="hidden" name="loginPwd" value="${teacherInfo.loginPwd}" /> 
			<input type="hidden" id="queryClassid" name="classid" /> 
			<input type="hidden" id="classname" /> 
		</form>
		<form id="update" action="" method="post">
			<input type="hidden" name="id" value="${teacherInfo.id}" /> 
			<input type="hidden" name="loginName" value="${teacherInfo.loginName}" /> 
			<input type="hidden" name="loginPwd" value="${teacherInfo.loginPwd}" /> 
			<input type="hidden" id="updateClassid" name="classid" /> 
		</form>
	</nav>
</div>
	
</body>
<jsp:include page="/WEB-INF/jsp/alert.jsp"></jsp:include>
</html>