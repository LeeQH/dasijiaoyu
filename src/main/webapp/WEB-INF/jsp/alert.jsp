<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<script type="text/javascript">
	function alertInfo(){
		if('${alertInfo}'!=''){
			alert('${alertInfo}');
			if('${relogin}'=='true'){
				top.location.href="<%=basePath%>/login.jsp";
			}
		}
	}
	alertInfo();
</script>