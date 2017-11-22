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
<base href="<%=basePath%>">

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="x-rim-auto-match" content="none">
<title>功能页面</title>

<script src="<%=basePath%>/static/js/jquery-3.1.1.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>/static/js/utils.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		//获取二维数组
		data = ${stuInfo};
		//获取表头
		dataHead = data[0];
		//删除表头
		data.splice(0, 1);
		//初始化select
		init();
	});

	function init() {
		var sortType = document.getElementById('sortType');
		for (var i = 0; i < dataHead.length; i++) {
			sortType.options.add(new Option(dataHead[i], i));
		}
		var thead = document.getElementById('head');
		addDataToTHead(thead,dataHead);
		var tbody = document.getElementById('body');
		addDataToTBody(tbody, data);
	}
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
		var dataSorted = sortMethod(data, param, order);
		var tbody = document.getElementById('body');
		clearTable(tbody);
		addDataToTBody(tbody, dataSorted);
	}
	function sortMethod(data, param, order){
		if(param==2||param==5){
			return sortForNumber(data, param, order);
		}else if(param==3||param==4){
			return sortForDate(data, param, order);
		}else{
			return sortForChina(data, param, order);
		}
	}
	

</script>


</head>
<body>
	<select id="sortType"></select>
	<select id="sortMethod">
		<option value="asc">升序</option>
		<option value="desc">降序</option>
	</select>
	<button onclick="goSort()">确定</button>

	<table id="default">
		<thead id="head"></thead>
		<tbody id="body"></tbody>
	</table>

</body>

</html>