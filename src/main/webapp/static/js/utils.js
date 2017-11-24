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
		var td1 = document.createElement('td');
		td1.innerText = i+1;
		tr.appendChild(td1);
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
			console.log('asc');
			return a[param].localeCompare(b[param], "zh");
		} else {//默认降序desc
			console.log('desc');
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
			console.log('asc');
			return a[param] - b[param];
		} else {//默认降序desc
			console.log('desc');
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
			console.log('asc');
			return Date.parse(a[param]) - Date.parse(b[param]);
		} else {//默认降序desc
			console.log('desc');
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