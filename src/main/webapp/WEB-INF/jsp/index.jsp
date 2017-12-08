<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
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

<link href="<%=basePath%>/static/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/static/css/mycss.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
$(window).scroll(function() {  
    var top= $(document).scrollTop();
    $('#navtd').css({"padding-top": top+"px"});
 
}) 
</script>

</head>
<body>
	<table width="100%">
		<tr><td colspan="4" height="30px;"></td></tr>
		<tr>
			<td width="25px;"></td>
			<td id="navtd" valign="top" width="185px;"><jsp:include page="/WEB-INF/jsp/nav.jsp"></jsp:include></td>
			<td><iframe id="iframe" name="mainView" frameborder="0" scrolling="no" width="100%" src="<%=basePath%>/default.jsp"></iframe></td>
			<td width="25px;"></td>
		</tr>
	</table>
</body>
</html>