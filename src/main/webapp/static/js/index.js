function chooseAction(){
	var classid="";
	var type="";
	var info="";
	
	var classids=document.getElementsByName("classids");
	for(var i=0;i<classids.length;i++){
		if(classids[i].checked){
			classid=classids[i].value;
		}
	}
	
	var types=document.getElementsByName("types");
	for(var i=0;i<types.length;i++){
		if(types[i].checked){
			type=types[i].value;
		}
	}
	
	if(classid=="")
		info+="请点击要查询的班级\n";
	if(type=="")
		info+="请点击操作的功能";
	if(info==""){
		document.getElementById("classid").value=classid;
		var form=document.getElementById("chooseAction");
		form.action+="/crawler/"+type;
		return true;
	}else{
		alert(info);
		return false;
	}	
}