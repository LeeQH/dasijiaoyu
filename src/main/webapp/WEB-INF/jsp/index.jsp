<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<script src="<%=basePath%>/static/js/jquery-3.1.1.min.js" type="text/javascript"></script>
<script src="<%=basePath%>/static/js/bootstrap.min.js" type="text/javascript"></script>

<link href="<%=basePath%>/static/css/bootstrap.min.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
function chooseAction(){
	var classid="";
	var type="";
	var info="";
	
	var classids=document.getElementsByName("classids");
	for(var i=0;i<classids.length;i++){
		if(classids[i].checked){
			classid=classids[i].value;
		}
	}
	
	var types=document.getElementsByName("types");
	for(var i=0;i<types.length;i++){
		if(types[i].checked){
			type=types[i].value;
		}
	}
	
	if(classid=="")
		info+="请点击要查询的班级\n";
	if(type=="")
		info+="请点击操作的功能";
	if(info==""){
		document.getElementById("classid").value=classid;
		var form=document.getElementById("chooseAction");
		form.action+="/crawler/"+type;
		return true;
	}else{
		alert(info);
		return false;
	}	
}
</script>
</head>
<body>
	<div class="radio">
		<c:forEach items="${classids}" var="entry">
			<label>
				<input class="hidden" type="radio" name="classids" value="${entry.value}" />
				<c:out value="${entry.key}" />
			</label>
		</c:forEach>
	</div>
	
	
	<div class="radio">
		<label>
			<input class="hidden" value="stuInfo.action" type="radio" name="types" />
			学生信息
		</label>
		<label>
			<input class="hidden" value="scoreRank.action" type="radio" name="types" />
			成绩排名
		</label>
	</div>

	<form id="chooseAction" action="<%=basePath%>" method="post" onsubmit="return chooseAction()">
		<input type="hidden" name="loginId" value="${teacher.loginId}" /> 
		<input type="hidden" name="password" value="${teacher.password}" /> 
		<input type="hidden" id="classid" name="classid" /> 
		<input type="submit" value="确定" />
	</form>


</body>

</html>