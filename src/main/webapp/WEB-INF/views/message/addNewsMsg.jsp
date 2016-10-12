<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ include file="/WEB-INF/views/comm/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/comm/meta.jsp"%>
<html>
<head>
	<title>添加新图文消息</title>
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
	
});


function checkForm()
{
	var title = $("#title").val();
	var key = $("#key").val();
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
	if(newsItem == null || newsItem.length <= 0)
	{
		alert("图文消息内容不能为空");
		return false;
	}
	return true;
}

function saveNewsMsg()
{
	if(!checkForm())
	{
		return false;
	}

	var title = $("#title").val();
	var key = $("#key").val();
	var content = JSON.stringify({"newsItem": newsItem});
	// var content = getNewsItemStr();
	
	RpcContext.getBean('MessageJsBean').addNewsMessage(title, key, content, {
    	onSucess:function(rt)
    	{
    		var res = eval('('+rt+')'); 
    		
    		var userList = res.state;
			if(res.state)
			{
				alert("添加成功！");
				location.href='${_ContextPath}/message/queryNewsMsg';
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

var newsIndex = 0;
function addNewsTr(nIndex, title, picUrl, url, desc)
{
	var html = "";
	if($("#newsItem_"+nIndex).html() == null)
	{
		html+= "<tr class=\"table-tr\" id='newsItem_"+nIndex+"'>";
		html+= "<td style=\"text-align: center\">"+nIndex+"</td>";
		html+= "<td style=\"text-align: center; width: 100px;\">"+title+"</td>";
		html+= "<td style=\"text-align: center\">"+picUrl+"</td>";
		html+= "<td style=\"text-align: center\">"+url+"</td>";
		html+= "<td style=\"text-align: center\">"+desc+"</td>";
		html+= "<td style=\"text-align: center; width: 150px;\">";
		html+= "<input type='button' class='btn_write' value='修改' onclick='modifyNewsItem(\""+nIndex+"\")'/>";
		html+= "<input type='button' class='btn_write' value='删除' onclick='deleteNewsItem(\""+nIndex+"\")'/>";
		html+= "</td>";
		html+= "</tr>";
		
		$("#msgItemListTb").append(html);
		newsIndex++;
	}
	else
	{
		html+= "<td style=\"text-align: center\">"+nIndex+"</td>";
		html+= "<td style=\"text-align: center; width: 100px;\">"+title+"</td>";
		html+= "<td style=\"text-align: center\">"+picUrl+"</td>";
		html+= "<td style=\"text-align: center\">"+url+"</td>";
		html+= "<td style=\"text-align: center\">"+desc+"</td>";
		html+= "<td style=\"text-align: center; width: 150px;\">";
		html+= "<input type='button' class='btn_write' value='修改' onclick='modifyNewsItem(\""+nIndex+"\")'/>";
		html+= "<input type='button' class='btn_write' value='删除' onclick='deleteNewsItem(\""+nIndex+"\")'/>";
		html+= "</td>";
		
		$("#newsItem_"+nIndex).html(html);
	}
	
}



var newsItem = [];
function getIndexById(id)
{
	var ind = -1;
	if(newsItem != null && newsItem.length > 0)
	{
		$.each(newsItem,function(index,item){
			if(id == item.id)
			{
				ind = index;
			}
		});
	}
	return ind;
}
function checkItem()
{
	var title = $("#itemTitle").val();
	var picUrl = $("#picUrl").val();
	var url = $("#itemUrl").val();
	var desc = $("#itemDesc").val();
	
	if(title == null || title == '')
	{
		alert("图文消息标题不能为空");
		return false;
	}
	if(picUrl == null || picUrl == '')
	{
		alert("图片链接不能为空");
		return false;
	}
	if(url == null || url == '')
	{
		alert("跳转连接地址不能为空");
		return false;
	}
	
	return true;
}
function addItem(nIndex)
{
	nIndex = nIndex || newsIndex;
	if(!checkItem())
	{
		return false;
	}
	var title = $("#itemTitle").val();
	var picUrl = $("#picUrl").val();
	var url = $("#itemUrl").val();
	var desc = $("#itemDesc").val();
	
	// newsItem.push({id:nIndex, title: title, picurl: picUrl, url: url, description: desc});
	var i = getIndexById(nIndex);
	if(i == -1)
	{
		i = newsItem.length;
	}
	newsItem.splice(i, 1, {id:nIndex, title: title, picurl: picUrl, url: url, description: desc});
	addNewsTr(nIndex, title, picUrl, url, desc);
	closeNewsItemDiv();
}

function deleteNewsItem(id)
{
	$("#newsItem_"+id).remove();
	newsItem.splice(getIndexById(id), 1);
}

function modifyNewsItem(id)
{
	// newsItem.splice(nIndex, 1, {id:nIndex, title: title, picurl: picUrl, url: url, description: desc});
	var nIndex = getIndexById(id);
	var title = newsItem[nIndex].title;
	var picUrl = newsItem[nIndex].picurl;
	var url = newsItem[nIndex].url;
	var desc = newsItem[nIndex].description;
	
	$("#indexId").val(id);
	$("#itemTitle").val(title);
	$("#picUrl").val(picUrl);
	$("#itemUrl").val(url);
	$("#itemDesc").val(desc);
	openNewsItemDiv();
}

function getNewsItemStr()
{
	var s = "";
	if(newsItem != null && newsItem.length > 0)
	{
		$.each(newsItem,function(index,item)
		{
			if(index != 0)
			{
				s += ";";
			}
			s += "title="+item.title;
			s += ",picurl="+item.picurl;
			s += ",url="+item.url;
			s += ",description="+item.description;
		});
	}
	return s;
}


function toAddItem()
{
	openNewsItemDiv();
}


function closeNewsItemDiv()
{
	$("#indexId").val('');
	$("#itemTitle").val('');
	$("#picUrl").val('');
	$("#itemUrl").val('');
	$("#itemDesc").val('');
	$("#newsItemBGDiv").css('display','none'); 
	$("#newsItemDiv").css('display','none'); 
}

function openNewsItemDiv()
{
	$("#newsItemBGDiv").css('display','block'); 
	$("#newsItemDiv").css('display','block'); 
}
</script>
</head>
<body>
<div class="area">&nbsp;&nbsp;当前位置：微信平台 / 消息回复 / <span class="text-red">添加新图文消息</span></div>
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
                    <th>图文消息内容：<input type="button" value="添 加" class="subbut" onclick="toAddItem()"></th>
                    <td>
				        <div id="divMsgItem" class="bd_content" style="display: black;height: auto;">
							<table width="100%" border="0" cellspacing="1" cellpadding="0" bgcolor="#D1D1D1" style="font-size:14px">
								<thead>
									<tr class="table-title">
										<td style="text-align: center; width: 50px;">序号</td>
										<td style="text-align: center; width: 100px;">标题</td>
										<td style="text-align: center;">图片链接</td>
										<td style="text-align: center;">跳转链接</td>
										<td style="text-align: center;">描述</td>
										<td style="text-align: center; width: 100px;">操作</td>
									</tr>
								</thead>
								<tbody id="msgItemListTb">
								</tbody>
							</table>
				            <div id="gBar"></div>
				        </div>
				        <div id="pagingBar" ></div>
                    </td>
                </tr>
				<tr>
                    <td></td>
                    <td><input type="button" value="提 交" class="subbut" onclick="saveNewsMsg()">&nbsp;&nbsp;&nbsp;<input type="button" value="取消" class="subbut" onclick="javascript:history.go(-1);"></td>
                </tr>
            </tbody></table>
         </div>
      </div>
      
<div id="newsItemBGDiv" class="black_overlay" style="display: none;"> </div>
<div id="newsItemDiv" class="white_content" style="top: 10%; width: 750px; height: 500px;display: none;">
	<table cellpadding="0" cellspacing="15" class="table-form">
		<tr>
			<td align="left" style="padding-left: 10px; font-size:16px;">图文消息内容<input type="hidden" id="indexId" value=""/></td>
			<td align="right" style="padding-right: 10px;">[<a href="#" onclick="closeNewsItemDiv();" style="color:red;"><u>关闭</u></a>]</td>
		</tr>
	</table>
        <table cellpadding="0" cellspacing="15" class="table-form">
            <tbody>
                <tr>
                    <th>标题：</th>
                    <td>
                        <input name="itemTitle" id="itemTitle" type="text" class="input_ui" style="width: 476px;" placeholder="请输入消息标题"/>
                    </td>
                </tr>
                <tr>
                    <th>图片链接：</th>
                    <td>
                        <input name="picUrl" id="picUrl" type="text" class="input_ui" style="width: 476px;" placeholder="请输入图片链接"/>
                    </td>
                </tr>
                <tr>
                    <th>跳转连接：</th>
                    <td>
                        <input name="itemUrl" id="itemUrl" type="text" class="input_ui" style="width: 476px;" placeholder="请输入跳转连接"/>
                    </td>
                </tr>
                <!-- <tr>
                    <th>序号：</th>
                    <td>
                        <input name="title" id="title" type="text" class="input_ui" style="width: 176px;" placeholder="请输入消息标题"/>
                        <span id="userIdMsg"></span>
                    </td>
                </tr> -->
                <tr>
                    <th>描述：</th>
					<td>
						<textarea rows="5" cols="50" name="itemDesc" id="itemDesc" class="input_ui" style="width: 476px; height: 100px;" placeholder="请输入消息描述"></textarea>
					</td>
				</tr>
				<tr>
                    <td></td>
                    <td><input type="button" value="确 定" class="subbut" onclick="addItem($('#indexId').val())"></td>
                </tr>
			</tbody>
		</table>
</div>
</body>
</html>