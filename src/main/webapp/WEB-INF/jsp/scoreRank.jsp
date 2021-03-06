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
	
	function goSort() {
		var sortType = document.getElementById('sortType');
		var index1 = sortType.selectedIndex;
		var typeValue = sortType.options[index1].value;

		var sortMethod = document.getElementById('sortMethod');
		var index2 = sortMethod.selectedIndex;
		var methodValue = sortMethod.options[index2].value;
		console.log(typeValue+","+methodValue);
		infoSort(typeValue, methodValue);
	}
	function infoSort(param, order) {
		var tbody = document.getElementById('body');
		if("undefined" == typeof data||data.length==0){
			data=getTableData(tbody);
		}
		var dataSorted = sortMethod(data, param, order);
		clearTable(tbody);
		addDataToTBody(tbody, dataSorted);
	}
	function sortMethod(data, param, order){
		if(param==2||param==3){
			return sortForNumber(data, param, order);
		}else{
			return sortForChina(data, param, order);
		}
	}
	
	function updateMonthScore(path){
		var loginName=window.parent.document.getElementById("loginName").value;
		var loginPwd=window.parent.document.getElementById("loginPwd").value;
		var classId=window.parent.document.getElementById("classId").value;
		
		var url="<%=basePath%>"+path;
		var params={loginName:loginName,loginPwd:loginPwd,classId:classId};
		
		fromPost(url,params);
	}
	
	function showInput(stuId){
		$("#"+stuId+"1").removeClass("hidden");//输入框
		$("#"+stuId+"2").addClass("hidden");//文本：目标成绩
		$("#"+stuId+"3").addClass("hidden");//按钮：修改成绩
		$("#"+stuId+"4").removeClass("hidden");//确认
		$("#"+stuId+"5").removeClass("hidden");//取消
	}
	function closeInput(stuId){
		$("#"+stuId+"1").addClass("hidden");//输入框
		$("#"+stuId+"2").removeClass("hidden");//文本：目标成绩
		$("#"+stuId+"3").removeClass("hidden");//按钮：修改成绩
		$("#"+stuId+"4").addClass("hidden");//确认
		$("#"+stuId+"5").addClass("hidden");//取消
	}
	function updateGoal(stuId){
		var goalScore=$("#"+stuId+"1").val();
		$.ajax({  
		    url:"<%=basePath%>/base/updateGoal.action",    //请求的url地址  
		    dataType:"text",   //返回格式  
		    async:true,//请求是否异步，默认为异步，这也是ajax重要特性  
		    data:{"stuId":stuId,"goalScore":goalScore},    //参数值  
		    type:"post",   //请求方式 get 或者post  
		    success:function(msg){ 
		    	if(msg=='success'){
		    		$("#"+stuId+"2").text(goalScore);
		    	}
		        closeInput(stuId);
		    }
		});  
	}
	function queryScoreInfo(time){
		var classid=window.parent.document.getElementById("classId").value;
		console.log(classid+"."+time);
		var url="<%=basePath%>/base/queryMonthScore.action";
		var params={classid:classid,month:time};
		fromPost(url,params);
	}
</script>

</head>
<body>
	<div>
		<div class="form-inline navbar-fixed-top">
			<span style="margin-left: 10px"></span>
			<select class="form-control" id="sortType">
				<option value="1">学生姓名</option>
				<option value="2">成绩</option>
				<option value="3">目标</option>
				<option value="4">是否完成</option>
			</select>
			<select class="form-control" id="sortMethod">
				<option value="desc">降序</option>
				<option value="asc">升序</option>
			</select>
			<button type="button" class="btn btn-primary" onclick="goSort()">确定</button>
			<span style="margin-left: 50px"></span>
			<input type="text" placeholder="输入文字回车搜索" onchange="searchTable(this)">
			
			<button class="btn btn-primary" type="button" style="margin:1px;float: right;" onclick="updateMonthScore('/crawler/updateMonthScore.action')">更新成绩</button>
			
			<div class="dropdown" style="margin:1px;float: right;">
			  <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
				 月份<span class="caret"></span>
			  </button>
			  <ul class="dropdown-menu" style="min-width: 62px;" aria-labelledby="dropdownMenu1">
			  	<c:forEach items="${monthList}" var="month">
				    <li style="cursor:pointer;text-align: center;" onclick="queryScoreInfo('${month}')"><c:out value="${month}"/></li>
				</c:forEach>
			  </ul>
			</div>
		</div>
	
		<br><br>
		<table style="margin-left: 10px;" class="table table-hover" id="default">
			<thead id="head">
				<tr>
					<th>编号</th>
					<th>学生姓名</th>
					<th>成绩</th>
					<th>目标</th>
					<th>是否完成</th>
					<th>设置目标</th>
				</tr>
			</thead>
			<tbody id="body">
				<c:forEach items="${monthScoreList}" var="score" varStatus="status">
					<tr>
						<td><c:out value="${status.count}"/></td>
						<td><c:out value="${score.stuName}"/></td>
						<td><c:out value="${score.score}"/></td>
						<td>
							<input class="hidden" type="number" id="${score.stuId}1" value="${score.goalScore}">
							<span id="${score.stuId}2" ><c:out value="${score.goalScore}"/></span>
						</td>
						<td><c:out value="${score.finishGoal}"/></td>
						<td>
							<button class="btn btn-info" id="${score.stuId}3" type="button" onclick="showInput('${score.stuId}')">修改目标</button>
							<button class="btn btn-success btn-sm hidden" id="${score.stuId}4" type="button" onclick="updateGoal('${score.stuId}')">确认</button>
							<button class="btn btn-warning btn-sm hidden" id="${score.stuId}5" type="button" onclick="closeInput('${score.stuId}')">取消</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
<jsp:include page="/WEB-INF/jsp/alert.jsp"></jsp:include>
</html>
