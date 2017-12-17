function getTableData(tbody){
	var trs=tbody.getElementsByTagName("tr");
	var data=new Array(trs.length);
	for(var i=0;i<trs.length;i++){
		var tds=trs[i].getElementsByTagName("td");
		var temp=new Array(tds.length);
		for(var j=0;j<tds.length;j++){
			temp[j]=tds[j].innerText;
		}
		data[i]=temp;
	}
	return data;
}


/**
 * 清空元素table下的所有行
 * @param table table元素
 */
function clearTable(table) {
	var rowNum = table.rows.length;
	for (i = 0; i < rowNum; i++) {
		table.deleteRow(i);
		rowNum = rowNum - 1;
		i = i - 1;
	}
}

/**
 * 设置table的thead
 * @param thead thead的元素
 * @param dataHead 表头数据
 */
function addDataToTHead(thead, dataHead) {
	var tr = document.createElement('tr');
	var th1 = document.createElement('th');
	th1.innerText = '编号';
	tr.appendChild(th1);
	for (var i = 0; i < dataHead.length; i++) {
		var th = document.createElement('th');
		th.innerText = dataHead[i];
		tr.appendChild(th);
	}
	thead.appendChild(tr);
}

/**
 * 在将data数据以table的形式填充
 * @param table 需要添加的数据的table元素
 * @param data 二维数组
 */
function addDataToTBody(tbody, data) {
	for (var i = 0; i < data.length; i++) {
		var tr = document.createElement('tr');
		for (var j = 0; j < data[i].length; j++) {
			var td = document.createElement('td');
			td.innerText = data[i][j];
			tr.appendChild(td);
		}
		tbody.appendChild(tr);
	}
}

/**
 * 对二维数组进行拼音排序
 * @param data 二维数组
 * @param param 需要根据哪列进行排序的索引，0开始
 * @param order 传入asc为升序，其他为降序
 */
function sortForChina(data, param, order) {
	data.sort(function(a, b) {
		if (order == 'asc') {//升序
			return a[param].localeCompare(b[param], "zh");
		} else {//默认降序desc
			return b[param].localeCompare(a[param], "zh");
		}
	});
	return data;
}

/**
 * 对二维数组进行数字排序
 * @param data 二维数组
 * @param param 需要根据哪列进行排序的索引，0开始
 * @param order 传入asc为升序，其他为降序
 */
function sortForNumber(data, param, order) {
	data.sort(function(a, b) {
		if (order == 'asc') {//升序
			return a[param] - b[param];
		} else {//默认降序desc
			return b[param] - a[param];
		}
	});
	return data;
}

/**
 * 对二维数组进行时间排序
 * @param data 二维数组
 * @param param 需要根据哪列进行排序的索引，0开始
 * @param order 传入asc为升序，其他为降序
 */
function sortForDate(data, param, order) {
	data.sort(function(a, b) {
		if (order == 'asc') {//升序
			return Date.parse(a[param]) - Date.parse(b[param]);
		} else {//默认降序desc
			return Date.parse(b[param]) - Date.parse(a[param]);
		}
	});
	return data;
}

/**
 * 设置外层iframe的高大于内容的高
 */
function setIframeHeight(){
	if(window.parent != window){
		parent.document.getElementById("iframe").style.height = (document.body.scrollHeight+30)+"px";
	}
}

/**
 * 对table的内容进行检索
 * @param obj 输入框的对象
 */
function searchTable(obj){
    var tables = document.getElementsByTagName('table'); 
    for(var i=0;i<tables.length;i++){
    	 var rowsLength = tables[i].rows.length;//表格总共有多少行  
         var key = obj.value;//获取输入框的值  
         for(var j=1;j<rowsLength;j++){//先全部隐藏
        	 tables[i].rows[j].style.display='none';  
         }
         for(var j=1;j<rowsLength;j++){//按表的行数进行循环，第一行是标题，所以i=1，从第二行开始筛选（从0数起）  
             colsLength=tables[i].rows[j].cells.length;//单元格子数目
        	 for(var k=0;k<colsLength;k++){  
            	 var searchText = tables[i].rows[j].cells[k].innerHTML;//取得table行，列的值  
                 if(searchText.match(key)){//用match函数进行筛选（返回匹配的值，否则为null），if有内容 或不为空返回true 
                	 tables[i].rows[j].style.display='';//显示行操作，  
                 }
        	 }
         }  
    }
}

/**
 * 模拟提交表单
 * @param url 提交的链接
 * @param params json参数
 * @param target 返回到哪个目标（iframe框架）
 */
function fromPost(url,params,target){  
    var tempform = document.createElement("form");  
    tempform.action = url;  
    tempform.method = "post";  
    tempform.style.display="none"  
    if(target) {  
        tempform.target = target;  
    }  
    for (var x in params) {  
        var opt = document.createElement("input");  
        opt.name = x;  
        opt.value = params[x];  
        tempform.appendChild(opt);  
    }  
    var opt = document.createElement("input");  
    opt.type = "submit";  
    tempform.appendChild(opt);  
    document.body.appendChild(tempform);  
    tempform.submit();  
    document.body.removeChild(tempform);  
}