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
<script src="<%=basePath%>/static/js/jquery-3.1.1.min.js" type="text/javascript"></script>
<script src="<%=basePath%>/static/js/bootstrap.min.js" type="text/javascript"></script>

<link href="<%=basePath%>/static/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
</head>

<script type="text/javascript">
	function regist(){
		var pwd1=document.getElementById("pwd1");
		var pwd2=document.getElementById("pwd2");
		if(pwd1.value!=""&&pwd2.value!=""){
			if(pwd1.value==pwd2.value){
				document.getElementById("regist").submit();
			}else{
				alert("两次密码不一致！");
				pwd1.value="";
				pwd2.value="";
			}
		}else{
			alert("密码不能为空！");
		}
	}
</script>

<body>

	<form action="<%=basePath%>/user/login.action" method="post">
		<input type="text" name="loginName" />
		<input type="password"name="loginPwd" />
		<button type="submit" class="list-group-item">登录</button>
	</form>
	
	<form id="regist" action="<%=basePath%>/user/regist.action" method="post">
		<input type="text" name="loginName" />
		<input type="password" id="pwd1" name="loginPwd" />
		<input type="password" id="pwd2" name="loginPwd2" />
		<button type="button" class="list-group-item" onclick="regist()">注册</button>
	</form>
</body>
<jsp:include page="/WEB-INF/jsp/alert.jsp"></jsp:include>
</html>