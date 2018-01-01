<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>

<script type="text/javascript">
	function queryStudyInfo(stuId){
	alert($(document).scrollTop());
		var loginName=window.parent.document.getElementById("loginName").value;
		var loginPwd=window.parent.document.getElementById("loginPwd").value;
		$("#studyBody").html("");
		$("#myModalLabel").html("正在加载请稍后...");
		$.ajax({  
		    url:"<%=basePath%>/crawler/queryStudyInfo.action",    //请求的url地址  
		    dataType:"json",   //返回格式  
		    async:true,//请求是否异步，默认为异步，这也是ajax重要特性  
		    data:{"loginName":loginName,"loginPwd":loginPwd,"stuId":stuId},    //参数值  
		    type:"post",   //请求方式 get 或者post  
		    success:function(studyInfo){ 
		    	var studys=eval(studyInfo);
		    	for(var i=0;i<studys.length;i++){
			    	var str="<tr>"+
			    	"<td>"+(i+1)+"</td>"+
			    	"<td>"+studys[i].time+"</td>"+
			    	"<td>"+studys[i].course+"</td>"+
			    	"<td>"+studys[i].passNum+"</td>"+
			    	"<td>"+studys[i].score+"</td>"+
			    	"<td>"+studys[i].useTime+"</td>"+
			    	"</tr>";
			    	$("#studyBody").append(str);
		    	}
		    	$("#studyBody").append("<tr><td colspan='6'>已全部加载</td></tr>");
		    	$("#myModalLabel").html("学生记录("+studyInfo.length+")条记录");
		    }
		}); 
	}
	
	$(function(){
		//隐藏的时候触发
		$('#myModal').on('hide.bs.modal', function (e) {
			$("body",parent.document).removeClass("modal-open");
		});
		
		//显示的时候触发
		$('#myModal').on('show.bs.modal', function (e) {
			
			$('#myModal').css({"height":window.screen.height-80});
			$('#myModal').css({"margin-top":document.body.scrollHeight});
			$("body",parent.document).addClass("modal-open");
		});
	});
	
</script>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog modal-lg" role="document" style="z-index: 1050">
    <div class="modal-content" style="margin-bottom: 100px">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">学生记录</h4>
      </div>
      <div class="modal-body">
       <table class="table table-hover">
			<thead>
				<tr>
					<th>编号</th>
					<th>时间</th>
					<th>课程</th>
					<th>练习</th>
					<th>得分</th>
					<th>用时</th>
				</tr>
			</thead>
			
			<tbody id="studyBody">
				
			</tbody>
		</table>
      </div>
    </div>
  </div>
</div>


