<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<%
	long nowDateTime=new Date().getTime()/86400000*86400000-28800000L;
	System.out.print(nowDateTime);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="Student Info Page">
<meta http-equiv="x-rim-auto-match" content="none">
<title>学生信息页面</title>

<script src="<%=basePath%>/static/js/jquery-3.1.1.min.js" type="text/javascript"></script>
<script src="<%=basePath%>/static/js/bootstrap.min.js" type="text/javascript"></script>
<script src="<%=basePath%>/static/js/utils.js" type="text/javascript"></script>
<script src="<%=basePath%>/static/js/FileSaver.min.js" type="text/javascript"></script>
<script src="<%=basePath%>/static/js/xlsx.core.min.js" type="text/javascript"></script>
<script src="<%=basePath%>/static/js/tableExport.js" type="text/javascript"></script>

<link href="<%=basePath%>/static/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/static/css/mycss.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
	
	$(function() {
		className=window.parent.document.getElementById("className").value;
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
		if(param==2||param==5||param==7){
			return sortForNumber(data, param, order);
		}else if(param==3||param==4||param==6){
			return sortForDate(data, param, order);
		}else{
			return sortForChina(data, param, order);
		}
	}
		
	function exportExcelWithJS(){
		$('#default').tableExport({
        	type:'excel',
        	fileName:className,//文件名
        	worksheetName:'学生信息',//sheet表的名字
//         	ignoreColumn:[],//忽略的列，从0开始
//         	ignoreRow:[],//忽略的行，从0开始
        	excelstyles:['text-align']//使用样式，不用填值只写属性，值读取的是html中的
        });
	}
	
	function updateStudent(path){
		var loginName=window.parent.document.getElementById("loginName").value;
		var loginPwd=window.parent.document.getElementById("loginPwd").value;
		var classId=window.parent.document.getElementById("classId").value;
		
		var url="<%=basePath%>"+path;
		var params={loginName:loginName,loginPwd:loginPwd,classId:classId};
		
		fromPost(url,params);
	}
	
	function updateLastDate(path){
		var loginName=window.parent.document.getElementById("loginName").value;
		var loginPwd=window.parent.document.getElementById("loginPwd").value;
		var classId=window.parent.document.getElementById("classId").value;
		
		var url="<%=basePath%>"+path;
		var params={loginName:loginName,loginPwd:loginPwd,classId:classId};
		
		fromPost(url,params);
	}
	
</script>

</head>
<body>
	<div>
		<div class="form-inline navbar-fixed-top">
			<span style="margin-left: 10px"></span>
			<select class="form-control" id="sortType">
				<option value="7">未上线天数</option>
				<option value="5">剩余天数</option>
				<option value="1">学生姓名</option>
				<option value="2">手机号码</option>
				<option value="3">开通日期</option>
<!-- 				<option value="4">结束日期</option> -->
<!-- 				<option value="6">最后上线日期</option> -->
			</select>
			<select class="form-control" id="sortMethod">
				<option value="desc">降序</option>
				<option value="asc">升序</option>
			</select>
			<button type="button" class="btn btn-primary" onclick="goSort()">确定</button>
			<span style="margin-left: 50px"></span>
			<input type="text" placeholder="输入文字回车搜索" onchange="searchTable(this)">
			<!-- js导出 （释放服务器性能） -->
			<button class="btn btn-primary" type="button" style="margin:1px;float: right;" onclick="exportExcelWithJS()">下载本表格</button>
			<button class="btn btn-primary" type="button" style="margin:1px;float: right;" onclick="updateLastDate('/crawler/updateLastDate.action')">更新上线日期</button>
			<button class="btn btn-primary" type="button" style="margin:1px;float: right;" onclick="updateStudent('/crawler/updateStuInfo.action')">更新学生信息</button>
			
		</div>
		<br>
		<br>
		
		<table style="margin-left: 10px;" class="table table-hover" id="default">
			<thead id="head">
				<tr>
					<th>编号</th>
					<th>学生姓名</th>
<!-- 					<th>家长姓名</th> -->
					<th>手机号码</th>
					<th>开通日期</th>
					<th>结束日期</th>
					<th>剩余天数</th>
					<th>最后上线日期</th>
					<th>未上线天数</th>
				</tr>
			</thead>
			<tbody id="body">
				<c:set var="now" value="<%=nowDateTime%>"></c:set>
				<c:forEach items="${stuList}" var="stu" varStatus="status">
					<tr>
						<td><c:out value="${status.count}"/></td>
						<td><a data-toggle="modal" data-target="#myModal" onclick="queryStudyInfo('${stu.stuId}')"><c:out value="${stu.stuName}"/></a></td>
<%-- 						<td><c:out value="${stu.parName}"/></td> --%>
						<td><c:out value="${stu.telNum}"/></td>
						<td><fmt:formatDate value="${stu.startDate}" pattern="yyyy-MM-dd" /></td>
						<td><fmt:formatDate value="${stu.endDate}" pattern="yyyy-MM-dd" /></td>
						<td>
							<c:if test="${stu.endDate!=null && stu.startDate!=null}">
								<fmt:formatNumber value="${(stu.endDate.getTime()-stu.startDate.getTime())/86400000}"/>
							</c:if>
						</td>
						<td><fmt:formatDate value="${stu.lastDate}" pattern="yyyy-MM-dd" /></td>
						<td>
							<c:if test="${stu.lastDate!=null}">
								<fmt:formatNumber value="${(now-stu.lastDate.getTime())/86400000}"/>
							</c:if>
						</td>
						
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
<!-- 	模拟框——弹出学生记录 -->
<jsp:include page="/WEB-INF/jsp/studyInfo.jsp"></jsp:include>
</body>
<jsp:include page="/WEB-INF/jsp/alert.jsp"></jsp:include>
</html>