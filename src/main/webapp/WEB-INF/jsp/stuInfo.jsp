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
		//获取二维数组
		data = ${stuInfo};
		//获取表头
		dataHead = data[0];
		//删除表头
		data.splice(0, 1);
		//班级id
		classId=window.parent.document.getElementById("classid").value;
		//班级名称
		className=window.parent.document.getElementById("classname").value;
		//初始化select
		init();
		//设置iframe的高
		setIframeHeight();
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
	
	function exportExcelWithJAVA(){
		var dataJson=[dataHead,data,classId+".xls"];
		$.ajax({
	        url: '<%=basePath%>/exportExcel/stuInfo.action',
	        type: 'post',
	        data: JSON.stringify(dataJson), // 以json字符串方式传递
	        contentType:"application/json;charset=utf-8",//data类型
	        dataType: 'text', //返回类型
	        success: function(a) {
	        	var url='<%=basePath%>/exportExcel/download.action?fileName='+classId+'.xls';
	        	location.href=url;
	        },
	        error: function(a) {
				alert("fail");
	        }
	    });
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
</script>

</head>
<body>
	<div>
		<div class="form-inline navbar-fixed-top">
			<span style="margin-left: 50px"></span>
			<select class="form-control" id="sortType"></select>
			<select class="form-control" id="sortMethod">
				<option value="asc">升序</option>
				<option value="desc">降序</option>
			</select>
			<button type="button" class="btn btn-primary" onclick="goSort()">确定</button>
			<span style="margin-left: 50px"></span>
			<input type="text" placeholder="输入文字回车搜索" onchange="searchTable(this)">
			<!-- java导出 -->
<!-- 			<button class="btn btn-primary" type="button" style="float: right;" onclick="exportExcelWithJAVA()">导出</button> -->
			<!-- js导出 （释放服务器性能） -->
			<button class="btn btn-primary"  type="button" style="float: right;" onclick="exportExcelWithJS()">下载本表格</button>
		</div>
		<br>
		<br>
		<table style="margin-left: 10px;" class="table table-hover" id="default">
			<thead id="head"></thead>
			<tbody id="body"></tbody>
		</table>
	</div>
</body>
<jsp:include page="/WEB-INF/jsp/alert.jsp"></jsp:include>
</html>