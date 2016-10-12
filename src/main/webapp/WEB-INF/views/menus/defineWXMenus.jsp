<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ include file="/WEB-INF/views/comm/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/comm/meta.jsp"%>
<html>
<head>
	<title>菜单定义</title>
	<link rel="stylesheet" href="${_ContextPath}/CacheableResource/simplePagination/simplePagination.css"/> 
	<link rel="stylesheet" href="${_ContextPath}/CacheableResource/util/jquery.tree.css"/> 
	<script src="${_ContextPath}/CacheableResource/jquery200/jquery-1.9.1.js"></script>
	<script src="${_ContextPath}/CacheableResource/util/jquery.tree.js"></script>
	<script src="${_ContextPath}/CacheableResource/rpc/jquery-sso-patch.js"></script>
	<script src="${_ContextPath}/CacheableResource/rpc/JsonStub.js"></script>
	<script src="${_ContextPath}/PUBLIC/JsonBeanStub/MenuWXJsBean.js"></script>
	<style type="text/css">
		table tr td {
			white-space: nowrap;
			padding: 2px 5px;
		}
	</style>

<script type="text/javascript">
var menuCount = 0;
var menuMap = {id:'Root', name:'ROOT', pId: '', pName: '', index: '1', key: 'root', type: 'click', subMenus: []};
/* function testData()
{
	menuMap = {
		id:'Root', name:'ROOT', pId: '', pName: '', index: '1', key: 'root', type: 'click', 
		subMenus: [
			{id:'', name:'xxx1', pId: '', pName: 'ROOT', index: '1', key: 'root', type: 'view', subMenus: []},
			{id:'', name:'xxx2', pId: '', pName: 'ROOT', index: '2', key: 'root', type: 'click', 
				subMenus: [
					{id:'', name:'zs1', pId: '', pName: 'xxx2', index: '1', key: 'root', type: 'view', subMenus: []},
					{id:'', name:'zs2', pId: '', pName: 'xxx2', index: '2', key: 'root', type: 'view', subMenus: []},
					{id:'', name:'zs3', pId: '', pName: 'xxx2', index: '3', key: 'root', type: 'view', subMenus: []},
					{id:'', name:'zs4', pId: '', pName: 'xxx2', index: '4', key: 'root', type: 'view', subMenus: []}]},
			{id:'', name:'xxx3', pId: '', pName: 'ROOT', index: '3', key: 'root', subMenus: []},
			{id:'', name:'xxx4', pId: '', pName: 'ROOT', index: '4', key: 'root', type: 'click', 
				subMenus: [
					{id:'', name:'fs1', pId: '', pName: 'xxx4', index: '1', key: 'root', type: 'view', subMenus: []},
					{id:'', name:'fs2', pId: '', pName: 'xxx4', index: '2', key: 'root', type: 'view', subMenus: []}]},
			{id:'', name:'xxx5', pId: '', pName: 'ROOT', index: '5', key: 'root', type: 'click', 
				subMenus: [{id:'', name:'as1', pId: '', pName: 'xxx5', index: '1', key: 'root', type: 'view', subMenus: []}]}]	
	};
} */

$(document).ready(function(){
	// testData();	
	setTimeout(function(){
		RpcContext.getBean('MenuWXJsBean').queryWXMenu({
	    	onSucess:function(rt)
	    	{
	    		var res = eval('('+rt+')'); 
	    		
	    		var userList = res.state;
				if(res.state)
				{
					menuMap = res.value.menuMap;

					initMenus(menuMap);
					$(function(){
						$('#'+menuMap.id).tree({expanded: 'li'});
					});
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
	}, 500);
});


function saveWXMenu()
{
	var mm = getMenuMap();

	RpcContext.getBean('MenuWXJsBean').saveWXMenu(JSON.stringify(mm), {
    	onSucess:function(rt)
    	{
    		alert('ok');
    		/* var res = eval('('+rt+')'); 
    		
    		var userList = res.state;
			if(res.state)
			{
				alert("添加成功！");
			}
			else
			{
    			alert(res.errorMessage);
			} */
		},onError:function(err)
		{
			alert(err);
		}
	});
}

function initMenus(mm)
{
	var h = "";
	if(mm != null && mm.subMenus != null)
	{
		$.each(mm.subMenus,function(index,item){
			item.pId = mm.id;
			h += getHtmlByObj(item);
		});
	}
	$("#"+menuMap.id).html(h);
}

function getMenuMap()
{
	var mm = {id:'Root', name:'ROOT', pId: '', pName: '', index: '1', key: 'root', type: 'click'};
	
	var liList = $("#"+menuMap.id).children("li");
	if(liList != null)
	{
		mm.subMenus = [];
		$.each(liList,function(index,item){
			mm.subMenus.push(getMapFromLI(item));
		});
	}

	// console.log(JSON.stringify(mm));
	// $("#show").html(JSON.stringify(mm));
	return mm;
}

function getMapFromLI(liObj)
{
	var mm = {};
	
	mm.id = $(liObj).attr("id");
	mm.name = $(liObj).children("a").attr("m-name");
	mm.pId = $(liObj).children("a").attr("m-pid");
	mm.pName = $(liObj).children("a").attr("m-parent");
	mm.index = $(liObj).children("a").attr("m-index");
	mm.type = $(liObj).children("a").attr("m-type");
	mm.key = $(liObj).children("a").attr("m-key");
	
	var ulObj = $(liObj).children("ul");
	if(ulObj != null && ulObj.children("li") != null)
	{
		mm.subMenus = [];
		$.each(ulObj.children("li"),function(index,item){
			mm.subMenus.push(getMapFromLI(item));
		});
	}
	
	return mm;
}

function selectMenu(id)
{
	clearInputHtml();
	var a = $("#"+id).children("a");
	$("#p_id").val(a.attr("m-pid"));
	$("#p_name").val(a.attr("m-parent"));
	$("#isAdd").val(0);
	$("#m_id").val(id);
	$("#m_name").val(a.attr("m-name"));
	$("#m_index").val(a.attr("m-index"));
	$("#m_type").val(a.attr("m-type"));
	$("#m_key").val(a.attr("m-key"));
}

function addFirstMenu()
{
	$("#m_id").val(menuMap.id);
	$("#m_name").val(menuMap.name);
	toAddChildMenu();
}

function addChildMenu()
{
	if(!checkMenu())
	{
		return false;
	}
	var pId = $("#p_id").val();
	var pLI = $("#"+pId).children("a");
	
	var newMenu = {id:"m_"+(menuCount++), name:$("#m_name").val(), pId: pId, pName: $("#p_name").val(), index: $("#m_index").val(), key: $("#m_key").val(), type: $("#m_type").val()};
	var h = getHtmlByObj(newMenu);
	
	if(pId != menuMap.id)
	{
		var subUL = $("#"+pId).children("ul");
		if(subUL != null && subUL.length > 0)
		{
			subUL.append(h);
		}
		else
		{
			h = "<ul>" + h + "</ul>";
			$("#"+pId).append(h);
			$('#'+menuMap.id).tree({expanded: 'li'});
		}
	}
	else
	{
		$("#"+pId).append(h);
	}
	clearInputHtml();
}

function deleteMenu()
{
	var id = $("#m_id").val();
	if(id == null || id == "")
	{
		alert("请选择一个菜单节点进行此操作");
		return false;
	}
	
	if($("#"+id).parent().children().length == 1 && $("#"+id).parent().attr("id") != menuMap.id)
	{
		$("#"+$("#"+id).children("a").attr("m-pid")).children("a").attr("class",'');
		$("#"+id).parent().remove();
		$('#'+menuMap.id).tree({expanded: 'li'});
	}
	else
	{
		$("#"+id).remove();
	}
	clearInputHtml();
}

function saveMenu()
{
	if(!checkMenu())
	{
		return false;
	}
	var id = $("#m_id").val();
	var name = $("#m_name").val();
	var index = $("#m_index").val();
	var type = $("#m_type").val();
	var key = $("#m_key").val();
	var a = $("#"+id).children("a");
	a.attr("m-index", index);
	a.attr("m-name", name);
	a.attr("m-key", key);
	a.attr("m-type", type);
	a.attr("title", name);
	a.text(name);
	
	clearInputHtml();
}


/*** edit HTML ***/
function getHtmlByObj(mm)
{
	var h = "";
	if(mm != null)
	{
		var id = "m_"+(menuCount++);
		mm.id = id.replace('.','_');
		h += "<li id=\""+mm.id+"\">";
		h += "<a href=\"javascript:void(0);\" m-pid=\""+mm.pId+"\" m-parent=\""+mm.pName+"\" m-index=\""+mm.index+"\" m-name=\""+mm.name+"\" m-key=\""+mm.key+"\" m-type=\""+mm.type+"\" title=\""+mm.name+"\" onclick=\"selectMenu('"+mm.id+"')\">";
		h += mm.name;
		h += "</a>";
		// subMenus
		if(mm.subMenus != null && mm.subMenus.length > 0)
		{
			h += "<ul>";
			$.each(mm.subMenus,function(index,item){
				item.pId = mm.id;
				h += getHtmlByObj(item);
			});
			h += "</ul>";
		}
		
		h += "</li>";
	}
	return h;
}

function checkMenu()
{
	var name = $("#m_name").val();
	var index = $("#m_index").val();
	var type = $("#m_type").val();
	var key = $("#m_key").val();
	if(name == null || name == "")
	{
		alert("菜单名称不能为空");
		return false;
	}
	if(type == null || type == "")
	{
		alert("必须选择菜单类型");
		return false;
	}
	if(key == null || key == "")
	{
		alert("菜单值不能为空");
		return false;
	}
	
	return true;
}

function toEditMenu()
{
	var id = $("#m_id").val();
	if(id == null || id == "")
	{
		alert("请选择一个菜单节点进行此操作");
		return false;
	}

	$("#m_name").attr("disabled", false);
	$("#m_index").attr("disabled", false);
	$("#m_type").attr("disabled", false);
	$("#m_key").attr("disabled", false);
	/***************/
	$("#edit_btn").css("display", "none");
	$("#save_edit_btn").css("display", "");
	$("#save_add_btn").css("display", "none");
	$("#add_btn").css("display", "none");
	$("#delete_btn").css("display", "none");
	$("#cancel_btn").css("display", "");
}

function toAddChildMenu()
{
	var pId = $("#m_id").val();
	var pName = $("#m_name").val();
	clearInputHtml();
	
	if(pId == null || pId == "" || $("#"+pId).length <= 0)
	{
		alert("请选择一个菜单节点进行此操作");
		return false;
	}
	$("#p_id").val(pId);
	$("#p_name").val(pName);
	$("#isAdd").val(1);

	$("#m_name").val('').attr("disabled", false);
	$("#m_index").val('').attr("disabled", false);
	$("#m_type").val('').attr("disabled", false);
	$("#m_key").val('').attr("disabled", false);
	/***************/
	$("#edit_btn").css("display", "none");
	$("#save_edit_btn").css("display", "none");
	$("#save_add_btn").css("display", "");
	$("#add_btn").css("display", "none");
	$("#delete_btn").css("display", "none");
	$("#cancel_btn").css("display", "");
}

function clearInputHtml()
{
	$("#p_id").val('');
	$("#isAdd").val('');
	$("#m_id").val('');
	$("#p_name").val('').attr("disabled", true);
	$("#m_name").val('').attr("disabled", true);
	$("#m_index").val('').attr("disabled", true);
	$("#m_type").val('').attr("disabled", true);
	$("#m_key").val('').attr("disabled", true);
	/***************/
	$("#edit_btn").css("display", "");
	$("#save_edit_btn").css("display", "none");
	$("#save_add_btn").css("display", "none");
	$("#add_btn").css("display", "");
	$("#delete_btn").css("display", "");
	$("#cancel_btn").css("display", "none");
}

</script>
</head>
<body>
<div class="area">&nbsp;&nbsp;当前位置：微信平台 / 微信管理 / <span class="text-red">菜单定义</span></div>
    <div>
        <!--左边 开始--->
        <div class="l-content">
            <table cellpadding="0" cellspacing="15" class="table-form">
                <tbody>
					<tr>
	                    <td></td>
	                    <td>
							<input type="button" value="新增一级菜单" class="subbut" onclick="addFirstMenu()">
							<input type="button" value="保存菜单结构" class="subbut" onclick="saveWXMenu()"></td>
	                </tr>
	                <tr>
	                    <th>上级菜单：</th>
	                    <td>
	                        <input name="p_name" id="p_name" type="text" class="input_ui" style="width: 176px;" placeholder="请输入上级菜单名" disabled="true"/>
	                        <input id="p_id" type="hidden"/>
	                        <input id="m_id" type="hidden"/>
	                        <input id="isAdd" type="hidden" value="0"/>
	                        <span id="userIdMsg"></span>
	                    </td>
	                </tr>
	                <tr>
	                    <th>序号：</th>
							<td>
							<input name="m_index" id="m_index" type="text" class="input_ui" style="width: 176px;" placeholder="请输入菜单序号" disabled="true"/> 
							<span id="selectMsg"></span>
							</td>
						</tr>
	                <tr>
	                    <th>菜单名称：</th>
	                    <td>
	                       <input name="m_name" id="m_name" type="text" class="input_ui" style="width: 176px;" placeholder="请输入菜单名称" disabled="true"/>
	                       <span id="smsMsg"></span>
	                    </td>
	                </tr>
	                <tr>
	                    <th>菜单类型：</th>
	                    <td>
	                       <select name="m_type" id="m_type" class="input_ui" style="width: 176px;" placeholder="请确认菜单类型" disabled="true">
	                       		<option value="">- 请选择 -</option>
	                       		<option value="view">view</option>
	                       		<option value="click">click</option>
	                       </select>
	                       <span id="smsMsg"></span>
	                    </td>
	                </tr>
	                <tr>
	                    <th>菜单值：</th>
	                    <td>
	                       <input name="m_key" id="m_key" type="text" class="input_ui" style="width: 176px;" placeholder="菜单值" disabled="true"/> 
	                    </td>
	                </tr>
					<tr>
	                    <td></td>
	                    <td><input type="button" class="subbut" value="编 辑" id="edit_btn" onclick="toEditMenu()">
	                    	<input type="button" class="subbut" value="保 存" id="save_edit_btn" style="display: none;" onclick="saveMenu()">
	                    	<input type="button" class="subbut" value="保 存" id="save_add_btn" style="display: none;" onclick="addChildMenu()">
	                    	<input type="button" class="subbut" value="新增子菜单" id="add_btn" onclick="toAddChildMenu()">
	                    	<input type="button" class="subbut" value="删除当前菜单" id="delete_btn" onclick="deleteMenu()">
	                    	<input type="button" class="subbut" value="取 消" id="cancel_btn" style="display: none;" onclick="clearInputHtml()"></td>
	                </tr>
	            </tbody>
	        </table>
        </div>
        <div class="r-content" style="width: 200px;">
			<ul id="Root">
			</ul>
         </div>
      </div>
</body>
</html>