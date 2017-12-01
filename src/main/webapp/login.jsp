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
<script type="text/javascript">
	$(function() {
		var alertInfo='${alertInfo}';
		if(alertInfo!=''){
			alert(alertInfo);
		}
	});

</script>
</head>
<body>

	<form action="<%=basePath%>/crawler/login.action" method="post">
		<input type="text" name="loginId" />
		<input type="password"name="password" />
		<input type="submit" value="login">
	</form>
</body>
</html>