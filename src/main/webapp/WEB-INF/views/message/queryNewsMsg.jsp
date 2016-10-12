<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ include file="/WEB-INF/views/comm/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/comm/meta.jsp"%>
<html>
<head>
	<title>图文消息查询</title>
	<link rel="stylesheet" href="${_ContextPath}/CacheableResource/simplePagination/simplePagination.css"/> 
	<link rel="stylesheet" href="${_ContextPath}/CacheableResource/main/css/PopupWin.css"/> 
	<script src="${_ContextPath}/CacheableResource/jquery200/jquery-1.9.1.js"></script>
	<script src="${_ContextPath}/CacheableResource/rpc/jquery-sso-patch.js"></script>
	<script src="${_ContextPath}/CacheableResource/rpc/JsonStub.js"></script>
	<script src="${_ContextPath}/PUBLIC/JsonBeanStub/MessageJsBean.js"></script>
	<style type="text/css">
		table tr td {
			white-space: nowrap;
			padding: 2px 5px;
		}
	</style>

<script type="text/javascript">

$(document).ready(function(){
	searchInfo();
});


function searchInfo(){	
	var msgKey = '';
	var msgType = 'news';
	var msgFlag = '1';
	
	RpcContext.getBean('MessageJsBean').queryMessageList(msgKey, msgType, msgFlag, {
    	onSucess:function(rt)
    	{
    		var res = eval('('+rt+')'); 
    		
    		var msgList = res.value;

			if(msgList==null||msgList==""){
				
				$("#msgListTb").html("<tr><td colspan='11'>暂无图文信息 </td></tr>");
			}else{
				var html = "";
				$.each(msgList,function(index,item){
					html+= "<tr class=\"table-tr\" id='msg_"+item['id']+"'>";
		    		html+= "<td style=\"text-align: center\">"+(index+1)+"</td>";
		    		html+= "<td style=\"text-align: center;\">"+item['title']+"</td>";
		    		html+= "<td style=\"text-align: center;\">"+item['key']+"</td>";
					html+= "<td style=\"text-align: center; width: 100px;\">";
					html+= "<input type='button' class='btn_write' value='编辑' onclick='msgOptionJs.toModifyNewsMsg(\""+item['id']+"\")'/>";
					html+= "<input type='button' class='btn_write' value='删除' onclick='msgOptionJs.toDeleteMessage(\""+item['id']+"\")'/>";
					html+= "</td>";
					html+= "</tr>";
				});
				
				$("#msgListTb").html(html);
			}
	 	 
		},onError:function(err)
		{
			alert(err);
		}
	});
	
}



var msgOptionJs = {
	toAddTextMsg:function(){
		location.href = "${_ContextPath}/message/toAddTextMsg";
	},
	toModifyTextMsg:function(id){
		location.href = "${_ContextPath}/message/toModifyTextMsg?id="+id;
	},
	toAddNewsMsg:function(){
		location.href = "${_ContextPath}/message/toAddNewsMsg";
	},
	toModifyNewsMsg:function(id){
		location.href = "${_ContextPath}/message/toModifyNewsMsg?id="+id;
	},
	toDeleteMessage:function(id){
		if(confirm('是否确定删除？'))
		{
			RpcContext.getBean('MessageJsBean').deleteMessage(id, {
		    	onSucess:function(rt)
		    	{
		    		var res = eval('('+rt+')'); 
		    		
		    		var groupList = res.state;
					if(res.state)
					{
						alert("删除成功！");
						$("#msg_"+id).remove();
					}
					else
					{
		    			alert(res.errorMessage);
					}
				},onError:function(err)
				{
					alert(err);
				}
			});
		}
	}
};

</script>
</head>
<body>
	<div class="area">&nbsp;&nbsp;当前位置：微信平台 / 消息回复 / <span class="text-red">图文消息</span></div>
    <div>
        <!--左边 开始--->
        <div class="l-content" style="width: 95%;">       
	        <div class="bd_title" style="padding-bottom: 5px;">
	            <div class="tab_menu" style="padding-left: 5px;">
	            	<!-- <label>关键字：</label>
	            	<input name='key' id='key' type='text' class='input_ui' style='width:200px'/>
	            	<input type="button" id="query" value="查询" class="btn_red" onclick="searchInfo();"> -->
	            	<input type="button" id="add" value="新增" class="btn_red" onclick="msgOptionJs.toAddNewsMsg();">
	            	<!-- <input type="hidden" id="sortColumn" value="client_name"/>
	            	<input type="hidden" id="sortType" value="asc"/> -->
	            </div>
	        </div>
	        <div id="divMsg" class="bd_content" style="display: black;height: auto;">
				<table width="100%" border="0" cellspacing="1" cellpadding="0" bgcolor="#D1D1D1" style="font-size:14px">
					<thead>
						<tr class="table-title">
							<td style="text-align: center; width: 50px;">序号</td>
							<td style="text-align: center;">消息标题</td>
							<td style="text-align: center;">关键字</td>
							<td style="text-align: center; width: 100px;">操作</td>
						</tr>
					</thead>
					<tbody id="msgListTb">
					</tbody>
					<!-- <tfoot style="display: black;">
						<tr class="table-title">
							<td colspan="11">
								<span class="text-red" style="cursor: pointer;">继续加载...</span>
							</td>
						</tr>
					</tfoot> -->
				</table>
	            <div id="gBar"></div>
	        </div>
	        <div id="pagingBar" ></div>
         </div>
      </div>
</body>
</html>