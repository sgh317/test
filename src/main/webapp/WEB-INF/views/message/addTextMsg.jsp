<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ include file="/WEB-INF/views/comm/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/comm/meta.jsp"%>
<html>
<head>
	<title>添加新文本消息</title>
	<link rel="stylesheet" href="${_ContextPath}/CacheableResource/simplePagination/simplePagination.css"/> 
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
	
});


function checkForm()
{
	var title = $("#title").val();
	var key = $("#key").val();
	var content = $("#content").val();
	if(title == null || title == '')
	{
		alert("消息标题不能为空");
		return false;
	}
	if(key == null || key == '')
	{
		alert("消息关键字不能为空");
		return false;
	}
	if(content == null || content == '')
	{
		alert("消息内容不能为空");
		return false;
	}
	return true;
}

function saveTextMsg()
{
	if(!checkForm())
	{
		return false;
	}

	var title = $("#title").val();
	var key = $("#key").val();
	var content = $("#content").val();
	
	RpcContext.getBean('MessageJsBean').addTextMessage(title, key, content, {
    	onSucess:function(rt)
    	{
    		var res = eval('('+rt+')'); 
    		
    		var userList = res.state;
			if(res.state)
			{
				alert("添加成功！");
				location.href='${_ContextPath}/message/queryTextMsg';
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



</script>
</head>
<body>
<div class="area">&nbsp;&nbsp;当前位置：微信平台 / 消息回复 / <span class="text-red">添加新文本消息</span></div>
    <div>
        <!--左边 开始--->
        <div class="l-content" style="width: 95%;">
            <table cellpadding="0" cellspacing="15" class="table-form">
                <tbody><tr>
                    <th>消息标题：</th>
                    <td>
                        <input name="title" id="title" type="text" class="input_ui" style="width: 176px;" placeholder="请输入消息标题"/>
                        <span id="userIdMsg"></span>
                    </td>
                </tr>
                <tr>
                    <th>消息关键字：</th>
						<td>
						<textarea rows="5" cols="50" name="key" id="key" class="input_ui" style="width: 176px; height: 100px;" placeholder="请输入消息关键字"></textarea>
						<div class="tips-text" style="font-size: 14px; color: red;">请用英文字符的逗号 "," 将多个关键字分开</div>
						</td>
					</tr>
                <tr>
                    <th>消息内容：</th>
                    <td>
                       <textarea rows="5" cols="50" name="content" id="content" class="input_ui" style="width: 276px; height: 150px;" placeholder="请输入消息内容"></textarea>
                    </td>
                </tr>
				<tr>
                    <td></td>
                    <td><input type="button" value="提 交" class="subbut" onclick="saveTextMsg()">&nbsp;&nbsp;&nbsp;<input type="button" value="取消" class="subbut" onclick="javascript:history.go(-1);"></td>
                </tr>
            </tbody></table>
         </div>
         
         <!-- <div class="r-content">
            <div class="tips-text">
                <p>xxxxxxxxxxxxxxxxxxxx。<br>
                                                        xxxxxxxxxxxxxxxxxxxxxxxxxxx。<br>
                                                         xxxxxxxxxxxxxxxxxxxxxxxxxxx。
                </p>
            </div>
        </div> -->
      </div>
</body>
</html>