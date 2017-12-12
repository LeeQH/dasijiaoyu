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
function queryStuInfo(path,classid,classname){
	document.getElementById("classId").value=classid;
	document.getElementById("className").value=classname;

	var url="<%=basePath%>"+path;
	var params={classid:classid};
	var target="mainView";
	
	fromPost(url,params,target);
}

function queryScoreInfo(path,classid,classname){
	document.getElementById("classId").value=classid;
	document.getElementById("className").value=classname;

	var date=new Date;
	var year=date.getFullYear(); 
	var month=date.getMonth()+1;
	if(month<10)
		month="0"+month;
	month=(month<10?"0"+month:""+month);
	var time=year+month;
	
	var url="<%=basePath%>"+path;
	var params={classid:classid,month:time};
	var target="mainView";
	
	fromPost(url,params,target);
}

function updateInfo(path){
	var id=document.getElementById("id").value;
	var loginName=document.getElementById("loginName").value;
	var loginPwd=document.getElementById("loginPwd").value;
	
	var url="<%=basePath%>"+path;
	var params={id:id,loginName:loginName,loginPwd:loginPwd};
	
	fromPost(url,params);
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
							  <button type="button" class="list-group-item" onclick="queryStuInfo('/base/queryStuInfo.action','${entry.value}','${entry.key}')">学生信息</button>
							  <button type="button" class="list-group-item" onclick="queryScoreInfo('/base/queryMonthScore.action','${entry.value}','${entry.key}')">成绩排名</button>
<%-- 							  <button type="button" class="list-group-item" onclick="updateInfo('/crawler/UpdateStuInfo.action','${entry.value}')">更新(学生变动)</button> --%>
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
						  <button type="button" class="list-group-item" onclick="updateInfo('/crawler/updateClasses.action')">更新班级列表</button>
						</div>
					</div>
				</div>
			</div>
		</div>
<!-- 		<form id="query" action="" target="mainView" method="post"> -->
<!-- 			<input type="hidden" id="queryClassId" name="classid" />  -->
<!-- 			<input type="hidden" id="queryClassName" />  -->
<!-- 		</form> -->
		
<!-- 		<form id="update" action="" method="post"> -->
<%-- 			<input type="hidden" name="id" value="${teacherInfo.id}" />  --%>
<%-- 			<input type="hidden" name="loginName" value="${teacherInfo.loginName}" />  --%>
<%-- 			<input type="hidden" name="loginPwd" value="${teacherInfo.loginPwd}" />  --%>
<!-- 		</form> -->
	</nav>
</div>
<input type="hidden" id="id"  value="${teacherInfo.id}" /> 
<input type="hidden" id="loginName" value="${teacherInfo.loginName}" /> 
<input type="hidden" id="loginPwd" value="${teacherInfo.loginPwd}" /> 
<input type="hidden" id="classId" /> 
<input type="hidden" id="className" /> 
</body>
<jsp:include page="/WEB-INF/jsp/alert.jsp"></jsp:include>
</html>