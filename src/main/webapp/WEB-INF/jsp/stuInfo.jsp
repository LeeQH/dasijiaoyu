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

<!--     <script src="/static/js/jquery-3.1.1.min.js" type="text/javascript"></script> -->
<script type="text/javascript">
	data=${stuInfo};
	function infoSort(param,order){
		data.sort(function(a,b){
			if(order=='asc'){//升序
				return a[param]-b[param]
			}else{//默认降序desc
				return b[param]-a[param]
			}
		});
		
	}

</script>


</head>
<body>
	<button onclick="sort(0,'ades')">12312</button>
	<table id="default">
		<c:forEach items="${stuInfo}" var="row">
			<tr>
				<c:forEach items="${row}" var="col">
					<td><c:out value="${col}" /></td>
				</c:forEach>
			</tr>
		</c:forEach>
	</table>

</body>

</html>